package com.cab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab.config.JwtProvider;
import com.cab.entity.Driver;
import com.cab.entity.Ride;
import com.cab.exception.DriverException;
import com.cab.service.DriverService;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private DriverService driverService;

	@GetMapping("/profile")
	public ResponseEntity<Driver> getDriverProfileHandler(@RequestHeader("Authorization") String jwt)
			throws DriverException {
		Driver reqDriverProfile = driverService.getReqDriverProfile(jwt);
		return new ResponseEntity<Driver>(reqDriverProfile, HttpStatus.OK);
	}

	@GetMapping("/{driverId}/currrent_ride")
	public ResponseEntity<Ride> getCurrentRideHandler(@PathVariable Long driverId) throws DriverException {
		Ride ride = driverService.getDriversCurrentRide(driverId);
		return new ResponseEntity<>(ride, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{driverId}/allocated_ride")
	public ResponseEntity<List<Ride>> getAllocatedRideHandler(@PathVariable Long driverId) throws DriverException {
		List<Ride> ride = driverService.getAllocatedRides(driverId);
		return new ResponseEntity<>(ride, HttpStatus.ACCEPTED);
	}
	@GetMapping("/completed_ride")
	public ResponseEntity<List<Ride>> getCompletedRideHandler(@RequestHeader("Authorization") String jwt)
			throws DriverException {
		Driver reqDriverProfile = driverService.getReqDriverProfile(jwt);
		  List<Ride> completedRide = driverService.completedRids(reqDriverProfile.getId());
		return new ResponseEntity<>(completedRide, HttpStatus.ACCEPTED);
	}

}
