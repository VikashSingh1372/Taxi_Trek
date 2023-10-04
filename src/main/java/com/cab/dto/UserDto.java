package com.cab.dto;

import com.cab.entity.UserRole;

import lombok.NoArgsConstructor;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	 private String mobile;
	 
	 private UserRole role ;
}
