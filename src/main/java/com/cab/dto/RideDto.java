package com.cab.dto;

import java.time.LocalDateTime;

import com.cab.entity.Driver;
import com.cab.entity.PaymentDetails;
import com.cab.entity.RideStatus;
import com.cab.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RideDto {
	private Long id;

	private UserDto user;

	private DriverDto driver;
	
	private double rating;
	private double  pickupLatitude;
	private double pickupLongitude;
	private double  destinationLatitude;
	private double destinationLongitude;
	private String pickupArea;
	private String destinationArea;
	
	private double distance;
	private long duration;
	private RideStatus status;

	private  LocalDateTime startTime;
	private  LocalDateTime endTime;
	private double fare;
	private int otp;
}
