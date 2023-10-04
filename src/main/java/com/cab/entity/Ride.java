package com.cab.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ride {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	private Driver driver;
	
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
	
	@Embedded
	private PaymentDetails paymentDetails = new PaymentDetails();

	@JsonIgnore
	private List<Long>  declinedDrivers  = new ArrayList<>();
}
