package com.cab.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cab.Repository.DriverRepository;
import com.cab.Repository.LicenceRepository;
import com.cab.Repository.RideRepository;
import com.cab.Repository.VehicleRepository;
import com.cab.config.JwtProvider;
import com.cab.entity.Calculator;
import com.cab.entity.Driver;
import com.cab.entity.Licence;
import com.cab.entity.Ride;
import com.cab.entity.RideStatus;
import com.cab.entity.UserRole;
import com.cab.entity.Vehicle;
import com.cab.exception.DriverException;
import com.cab.request.DriverSignupRequest;
import com.cab.service.DriverService;


@Service
public class DriverServiceImplementation implements DriverService {

	@Autowired
	private DriverRepository driverRepo;

	@Autowired
	private Calculator distanceCalculator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private VehicleRepository vehicleRepo;

	

	@Autowired
	private LicenceRepository licenceRepo;

	@Override
	public Driver registerDriver(DriverSignupRequest driverSignupRequest) {
		Licence licence = driverSignupRequest.getLicence();
		Vehicle vehicle = driverSignupRequest.getVehicle();

		Licence createLicence = new Licence();
		createLicence.setId(licence.getId());
		createLicence.setLicenceState(licence.getLicenceState());
		createLicence.setLicenceNumber(licence.getLicenceNumber());
		createLicence.setLicenceExpirationDate(licence.getLicenceExpirationDate());

		Licence savedLicence = licenceRepo.save(createLicence);

		Vehicle createdVehicle = new Vehicle();
		createdVehicle.setCapacity(vehicle.getCapacity());
		createdVehicle.setColor(vehicle.getColor());
		createdVehicle.setLicencePlate(vehicle.getLicencePlate());
		createdVehicle.setMake(vehicle.getMake());
		createdVehicle.setModel(vehicle.getModel());
		createdVehicle.setYear(vehicle.getYear());
		createdVehicle.setId(vehicle.getId());

		Vehicle savedVehicle = vehicleRepo.save(createdVehicle);

		Driver driver = new Driver();
		driver.setName(driver.getName());
		driver.setEmail(driver.getEmail());
		driver.setMobile(driver.getMobile());
		driver.setPassword(passwordEncoder.encode(driver.getPassword()));
		driver.setRole(UserRole.DRIVER);
		driver.setVehicle(savedVehicle);
		driver.setLicence(savedLicence);
		Driver savedDriver = driverRepo.save(driver);
		
		savedLicence.setDriver(savedDriver);
		savedVehicle.setDriver(savedDriver);
		
		licenceRepo.save(savedLicence);
		vehicleRepo.save(savedVehicle);

		return  savedDriver;
	}

	@Override
	public List<Driver> getAvailableDrivers(double pickuplatitude, double picupLongitude,  Ride ride) {

		List<Driver> allDriver = driverRepo.findAll();
		
		List<Driver> avialableDriver = new  ArrayList<>();
		
		for(Driver driver:allDriver) {
			
			if(driver.getCurrentRide()!= null && driver.getCurrentRide().getStatus()!= RideStatus.COMPLETED) {
				continue;
			}
			
			if(ride.getDeclinedDrivers().contains(driver.getId())) {
				continue;
			}
			avialableDriver.add(driver);
		}
		
		return avialableDriver;
	}

	@Override
	public Driver findNearestDriver(List<Driver> availableDrivers, double picuplatitude, double picupLongitude) {
		// TODO Auto-generated method stub
		double min = Double.MAX_VALUE;
		Driver nearestDriver = null;
		   for(Driver driver:availableDrivers) {
			   
			   
			   double longitude = driver.getLongitude();
			   double latitude = driver.getLatitude();
			   double distance = distanceCalculator.calculateDistance(latitude, picuplatitude, picupLongitude, min);
			   
			   if(min> distance) {
				   nearestDriver = driver;
			   }
		   }
		
		return nearestDriver;
	}

	@Override
	public Driver getReqDriverProfile(String jwt) throws DriverException {
		// TODO Auto-generated method stub
		String email= jwtProvider.getEmailFromToken(jwt);
		  Driver  driver = driverRepo.findByEmail(email);
		  if(driver== null) {
			  throw new DriverException("driver not found with email " +email);
		  }
		return driver;
	}

	@Override
	public Ride getDriversCurrentRide(Long driverld) throws DriverException {
		 Driver driver = findDriverByld(driverld);
		return driver.getCurrentRide();
	}

	@Override
	public List<Ride> getAllocatedRides(Long driverld) throws DriverException {
		// TODO Auto-generated method stub
		List<Ride> allocatedRide = driverRepo.getAllocatedRide(driverld);
		return allocatedRide;
	}

	@Override
	public Driver findDriverByld(Long driverld) throws DriverException {
		// TODO Auto-generated method stub
		Optional<Driver> driver = driverRepo.findById(driverld);
		if(driver.isPresent()) {
			return driver.get();
		}
		
		throw new DriverException("driver not found with id:" +driverld);
	}

	@Override
	public List<Ride> completedRids(Long driverld) throws DriverException {
		// TODO Auto-generated method stub
	   List<Ride> completedRide = driverRepo.getCompletedRide(driverld);
		return completedRide;
	}

}
