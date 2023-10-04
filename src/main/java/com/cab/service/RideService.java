package com.cab.service;

import com.cab.entity.Driver;
import com.cab.entity.Ride;
import com.cab.entity.User;
import com.cab.exception.DriverException;
import com.cab.exception.RideException;
import com.cab.request.RideRequest;

public interface RideService {
	public Ride requestRide(RideRequest rideRequest, User user) throws DriverException;

	public Ride createRideRequest(User user, Driver nearesDriver, double picuplatitude, double pickupLongituLongitude,	double destinationLatitude, double destinationLongitude, String pickupArea, String destinationArea);

	public void acceptRide(Long rideld) throws RideException;

	public void declineRide(Long rideld, Long driverId) throws RideException;

	public void startRide(Long rideld, int opt) throws RideException;

	public void completeRide(Long rideld) throws RideException;

	public void cancleRide(Long rideld) throws RideException;

	public Ride findRideByld(Long rideld) throws RideException;

}
