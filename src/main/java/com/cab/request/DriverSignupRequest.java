package com.cab.request;

import com.cab.entity.Licence;
import com.cab.entity.Vehicle;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class DriverSignupRequest {
	
	@NotBlank(message = "Email is Required !!!")
	@Email
	private String email;
	private String name;
	private String password;
	private String  mobile;
	private double latitude;
	private double longitude;
	private Licence licence;
	private Vehicle vehicle;
	


}
