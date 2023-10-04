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

import com.cab.entity.Ride;
import com.cab.entity.User;
import com.cab.exception.UserException;
import com.cab.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt)
			throws UserException {
		User user = userService.findUserProfileByJwt(jwt);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserByIdHandler(@PathVariable Long userId)
			throws UserException {
		User user = userService.findUserById(userId);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	@GetMapping("/completed_rides")
	public ResponseEntity<List<Ride>> getUserCompletedRidesHandler(@RequestHeader("Authorization") String jwt)
			throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		  List<Ride> completedRide = userService.completedRide(user.getId());
			return new ResponseEntity<>(completedRide, HttpStatus.OK);
	}

}
