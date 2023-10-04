package com.cab.service;

import java.util.List;

import com.cab.entity.Driver;
import com.cab.entity.Ride;
import com.cab.exception.DriverException;
import com.cab.request.DriverSignupRequest;

public interface DriverService {
	public Driver registerDriver(DriverSignupRequest driverSignupRequest);

	public List<Driver> getAvailableDrivers(double pickuplatitude, double picupLongitude, Ride ride);

	public Driver findNearestDriver(List<Driver> availableDrivers, double picuplatitude, double picupLongitude);

	public Driver getReqDriverProfile(String jwt) throws DriverException;

	public Ride getDriversCurrentRide(Long driverld) throws DriverException;


	public Driver findDriverByld(Long driverld) throws DriverException;

	public List<Ride> completedRids(Long driverld) throws DriverException;

	List<Ride> getAllocatedRides(Long driverld) throws DriverException;



}
