package com.cab.service.Implementation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.Repository.DriverRepository;
import com.cab.Repository.RideRepository;
import com.cab.entity.Calculator;
import com.cab.entity.Driver;
import com.cab.entity.Ride;
import com.cab.entity.RideStatus;
import com.cab.entity.User;
import com.cab.exception.DriverException;
import com.cab.exception.RideException;
import com.cab.request.RideRequest;
import com.cab.service.DriverService;
import com.cab.service.RideService;


@Service
public class RideServiceImplementation implements RideService {

	@Autowired
	private DriverService driverService;

	@Autowired
	private RideRepository rideRepo;

	@Autowired
	private DriverRepository driverRepo;

	@Autowired
	private Calculator calculator;

	@Override
	public Ride requestRide(RideRequest rideRequest, User user) throws DriverException {
		// TODO Auto-generated method stub
		Ride existingRide = new Ride();

		List<Driver> availableDrivers = driverService.getAvailableDrivers(rideRequest.getPickupLatitude(),
				rideRequest.getPickupLongitude(), existingRide);
		Driver nearestDriver = driverService.findNearestDriver(availableDrivers, rideRequest.getPickupLatitude(),
				rideRequest.getPickupLongitude());
		if (nearestDriver == null) {
			throw new DriverException("Driver not avialable");
		}

		Ride ride = createRideRequest(user, nearestDriver, rideRequest.getPickupLatitude(),
				rideRequest.getPickupLongitude(), rideRequest.getDestinationLatitude(),
				rideRequest.getDestinationLongitude(), rideRequest.getPickupArea(), rideRequest.getDestinationArea());
		return ride;
	}

	@Override
	public Ride createRideRequest(User user, Driver nearesDriver, double picuplatitude, double pickupLongitude,
			double destinationLatitude, double destinationLongitude, String pickupArea, String destinationArea) {
		Ride ride = new Ride();
		ride.setDriver(nearesDriver);
		ride.setUser(user);
		ride.setPickupArea(pickupArea);
		ride.setPickupLatitude(picuplatitude);
		ride.setDestinationLatitude(destinationLatitude);
		ride.setDestinationLongitude(destinationLongitude);
		ride.setPickupLongitude(pickupLongitude);
		ride.setDestinationArea(destinationArea);
		ride.setStatus(RideStatus.REQUESTED);

		return rideRepo.save(ride);
	}

	@Override
	public void acceptRide(Long rideld) throws RideException {
		Ride ride = findRideByld(rideld);
		ride.setStatus(RideStatus.ACCEPTED);
		Driver driver = ride.getDriver();
		driver.setCurrentRide(ride);
		Random random = new Random();
		int otp = random.nextInt(9000) + 1000;
		ride.setOtp(otp);
		driverRepo.save(driver);
		rideRepo.save(ride);
	}

	@Override
	public void declineRide(Long rideld, Long driverId) throws RideException {
		Ride ride = findRideByld(rideld);
		ride.getDeclinedDrivers().add(driverId);

		// finding another driver
		List<Driver> availableDrivers = driverService.getAvailableDrivers(ride.getPickupLatitude(),
				ride.getPickupLongitude(), ride);
		Driver nearestDriver = driverService.findNearestDriver(availableDrivers, ride.getPickupLatitude(),
				ride.getPickupLongitude());

		ride.setDriver(nearestDriver);
		rideRepo.save(ride);
	}

	@Override
	public void startRide(Long rideld, int otp) throws RideException {
		Ride ride = findRideByld(rideld);

		if (otp != ride.getOtp()) {
			throw new RideException("please provide a valid otp");
		}
		ride.setStatus(RideStatus.STARTED);
		ride.setStartTime(LocalDateTime.now());
		rideRepo.save(ride);

	}

	@Override
	public void completeRide(Long rideld) throws RideException {
		Ride ride = findRideByld(rideld);
		ride.setStartTime(LocalDateTime.now());
		ride.setStatus(RideStatus.COMPLETED);

		double distance = calculator.calculateDistance(ride.getDestinationLatitude(), ride.getDestinationLongitude(),
				ride.getPickupLatitude(), ride.getPickupLongitude());
     LocalDateTime startTime = ride.getStartTime();
     LocalDateTime endTime = ride.getEndTime();
     
     Duration duration = Duration.between(startTime, endTime);
     long miilliSecond = duration.toMillis();
     
     double  fare = calculator.calculateFair(distance);
     
     ride.setDistance(Math.round(distance));
     ride.setFare(Math.round(fare));
     ride.setDuration(miilliSecond);
     ride.setEndTime(LocalDateTime.now());
     
     Driver driver = ride.getDriver();
     driver.getRides().add(ride);
      driver.setCurrentRide(null);
      
    Integer driverRevenue =(int) (driver.getTotalRevenue()+Math.round(fare *0.8));
       driver.setTotalRevenue(driverRevenue);
       driverRepo.save(driver);
       rideRepo.save(ride);
	}

	@Override
	public void cancleRide(Long rideld) throws RideException {
        Ride  ride = findRideByld(rideld);
        ride.setStatus(RideStatus.CANCELLED);
        rideRepo.save(ride);
	}

	@Override
	public Ride findRideByld(Long rideld) throws RideException {
         Optional<Ride> ride = rideRepo.findById(rideld);
         
         if(ride.isPresent()) {
        	 return ride.get();
         }
		throw new RideException("Ride not found eith id:" + rideld);
	}



}
