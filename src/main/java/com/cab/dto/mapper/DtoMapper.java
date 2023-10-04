package com.cab.dto.mapper;

import com.cab.dto.DriverDto;
import com.cab.dto.RideDto;
import com.cab.dto.UserDto;
import com.cab.entity.Driver;
import com.cab.entity.Ride;
import com.cab.entity.User;

public class DtoMapper {

	public static DriverDto driverToDto(Driver driver) {

		DriverDto dto = new DriverDto();
		dto.setEmail(driver.getEmail());
		dto.setLatitude(driver.getLatitude());
		dto.setLongitude(driver.getLongitude());
		dto.setId(driver.getId());
		dto.setMobile(driver.getMobile());
		dto.setName(driver.getName());
		dto.setRating(driver.getRating());
		dto.setRole(driver.getRole());
		dto.setVehicle(driver.getVehicle());

		return dto;

	}

	public static UserDto userToDto(User user) {

		UserDto dto = new UserDto();
		dto.setEmail(user.getEmail());
		dto.setId(user.getId());
		dto.setMobile(user.getMobile());
		dto.setName(user.getName());
		dto.setRole(user.getRole());
		return dto;
	}

	public static RideDto rideToDto(Ride ride) {

		DriverDto driverDto = driverToDto(ride.getDriver());
		UserDto userDto = userToDto(ride.getUser());

		RideDto dto = new RideDto();
		dto.setDestinationArea(ride.getDestinationArea());
		dto.setDestinationLatitude(ride.getDestinationLatitude());
		dto.setDestinationLongitude(ride.getDestinationLongitude());
		dto.setDistance(ride.getDistance());
		dto.setDuration(ride.getDuration());
		dto.setEndTime(ride.getEndTime());
		dto.setFare(ride.getFare());
		dto.setId(ride.getId());
		dto.setOtp(ride.getOtp());
		dto.setPickupArea(ride.getPickupArea());
		dto.setPickupLatitude(ride.getPickupLatitude());
		dto.setPickupLongitude(ride.getPickupLongitude());
		dto.setRating(ride.getRating());
		dto.setStartTime(ride.getStartTime());
		dto.setStatus(ride.getStatus());
		dto.setUser(userDto);
		dto.setDriver(driverDto);
		
		return dto;

	}

}
