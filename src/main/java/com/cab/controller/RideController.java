package com.cab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab.dto.RideDto;
import com.cab.dto.mapper.DtoMapper;
import com.cab.entity.Driver;
import com.cab.entity.Ride;
import com.cab.entity.User;
import com.cab.exception.DriverException;
import com.cab.exception.RideException;
import com.cab.exception.UserException;
import com.cab.request.RideRequest;
import com.cab.request.StartRideRequest;
import com.cab.service.DriverService;
import com.cab.service.RideService;
import com.cab.service.UserService;

@RestController
@RequestMapping("/api/ride")
public class RideController {

	@Autowired
	private DriverService driverService;

	@Autowired
	private RideService rideService;

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/request")
	public ResponseEntity<RideDto> userRequestRideHandler(@RequestBody RideRequest rideRequest,@RequestHeader("Authorization") String jwt) throws UserException, DriverException{
		 User user = userService.findUserProfileByJwt(jwt);

	  Ride requestRide = rideService.requestRide(rideRequest, user);
	  RideDto rideToDto = DtoMapper.rideToDto(requestRide);
	  
	  return  new ResponseEntity<RideDto>(rideToDto,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{rideId}/accept")
	public ResponseEntity<String> acceptRideHandler(@PathVariable Long rideId) throws UserException, DriverException, RideException{

	 rideService.acceptRide(rideId);
	  
	  return  new ResponseEntity<>("Ride accepted successfully",HttpStatus.ACCEPTED);
	}
	@PutMapping("/{rideId}/decline")
	public ResponseEntity<String> declineRideHandler(   @RequestHeader("Authorization") String jwt, @PathVariable Long rideId) throws UserException, DriverException, RideException{
   Driver driver = driverService.getReqDriverProfile(jwt);
   
	 rideService.declineRide(rideId, driver.getId());
	  
	  return  new ResponseEntity<>("Ride declined successfully",HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{rideId}/start")
	public ResponseEntity<String> startRideHandler(@PathVariable Long rideId , @RequestBody StartRideRequest request ) throws UserException, DriverException, RideException{
   
	 rideService.startRide(rideId, request.getOtp());
	  
	  return  new ResponseEntity<>("Ride Started successfully",HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{rideId}/complete")
	public ResponseEntity<String> completeRideHandler(@PathVariable Long rideId) throws UserException, DriverException, RideException{

	 rideService.completeRide(rideId);
	  
	  return  new ResponseEntity<>("Ride completed successfully",HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{rideId}")
	public ResponseEntity<RideDto> findRideByIdHandler(   @RequestHeader("Authorization") String jwt, @PathVariable Long rideId) throws UserException, DriverException, RideException{
   Driver driver = driverService.getReqDriverProfile(jwt);
   
	 Ride ride = rideService.findRideByld(rideId);
	 RideDto rideDto = DtoMapper.rideToDto(ride);
	     
	  
	  return  new ResponseEntity<>(rideDto	,HttpStatus.ACCEPTED);
	}

}
