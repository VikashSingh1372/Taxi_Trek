package com.cab.dto;

import com.cab.entity.UserRole;
import com.cab.entity.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	private String mobile;
	private double rating;
	private double latitude;
	private double longitude;
	private UserRole role;
	private Vehicle vehicle;
}
