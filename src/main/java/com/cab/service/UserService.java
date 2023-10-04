package com.cab.service;

import java.util.List;

import com.cab.entity.Ride;
import com.cab.entity.User;
import com.cab.exception.UserException;

public interface UserService {
	
	
	public User findUserById(long userId) throws UserException;
	public User findUserProfileByJwt(String jwt)throws UserException;
	
	public List<Ride> completedRide(long userId);
}
