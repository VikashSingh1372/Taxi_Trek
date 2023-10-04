package com.cab.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RideRequest {
	private double  pickupLatitude;
	private double pickupLongitude;
	private double  destinationLatitude;
	private double destinationLongitude;
	private String pickupArea;
	private String destinationArea;
	
	
}
