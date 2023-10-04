package com.cab.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cab.Repository.DriverRepository;
import com.cab.Repository.UserRepository;
import com.cab.entity.Driver;
import com.cab.entity.User;


@Service
public class CustomUserDetailService implements UserDetailsService{

	private UserRepository userRepo;
	private DriverRepository driverRepo;

	public CustomUserDetailService(UserRepository userRepo,DriverRepository driverRepo) {
		super();
		this.userRepo = userRepo;
		this.driverRepo= driverRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  List<GrantedAuthority> authorities =  new   ArrayList<GrantedAuthority>();

		  User user = this.userRepo.findByEmail(username);
		  
		  
		  if(user != null) {

			return  new  org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
			}
		  
		  Driver driver = this.driverRepo.findByEmail(username);
		  if(driver != null) {

				return  new  org.springframework.security.core.userdetails.User(driver.getEmail(),driver.getPassword(),authorities);
				}
		
		  throw new  UsernameNotFoundException("User not found with email:"+username);
	}


}
