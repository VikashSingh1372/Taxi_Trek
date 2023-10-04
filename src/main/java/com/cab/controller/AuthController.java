package com.cab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab.Repository.DriverRepository;
import com.cab.Repository.UserRepository;
import com.cab.config.JwtProvider;
import com.cab.entity.Driver;
import com.cab.entity.User;
import com.cab.entity.UserRole;
import com.cab.exception.UserException;
import com.cab.request.DriverSignupRequest;
import com.cab.request.LoginRequest;
import com.cab.request.SignupRequest;
import com.cab.response.JwtResponse;
import com.cab.service.CustomUserDetailService;
import com.cab.service.DriverService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private CustomUserDetailService userDetail;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private DriverService driverService;

	@Autowired
	private DriverRepository driverRepo;



	@PostMapping("/signup")
	public ResponseEntity<JwtResponse> createUserHandler(@RequestBody SignupRequest user) throws UserException {

		User isEmailExist = this.userRepo.findByEmail(user.getEmail());

		if (isEmailExist != null) {
			throw new UserException("User Already exist with email:" + user.getEmail());
		}

		User createdUser = new User();
		createdUser.setName(user.getName());
		createdUser.setEmail(user.getEmail());
		createdUser.setMobile(user.getMobile());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		createdUser.setRole(UserRole.USER);
		User savedUser = userRepo.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.gererateToken(authentication);
		JwtResponse response = new JwtResponse();
		response.setJwt(token);
		response.setMessage("Sucessfully signup");
		response.setAutenticated(true);
		response.setError(false);
		response.setErrorDetail(null);
		response.setRole(UserRole.USER);
		return new ResponseEntity<JwtResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/driver/signup")
	public ResponseEntity<JwtResponse> createDriverHandler(@RequestBody DriverSignupRequest driver)
			throws UserException {

		Driver isEmailExist = this.driverRepo.findByEmail(driver.getEmail());

		if (isEmailExist != null) {
			throw new UserException("Driver Already exist with email:" + driver.getEmail());
		}

		Driver registerDriver = driverService.registerDriver(driver);
		Authentication authentication = new UsernamePasswordAuthenticationToken(registerDriver.getEmail(),
				registerDriver.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.gererateToken(authentication);
		JwtResponse response = new JwtResponse();
		response.setJwt(token);
		response.setMessage("Sucessfully signup");
		response.setAutenticated(true);
		response.setError(false);
		response.setErrorDetail(null);
		response.setRole(UserRole.DRIVER);
		return new ResponseEntity<JwtResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> loginUserHandler(@RequestBody LoginRequest request) throws UserException {
		Authentication authentication = authenticate(request.getUsername(), request.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.gererateToken(authentication);
		JwtResponse response = new JwtResponse();
		response.setJwt(token);
		response.setMessage("Sucessfully logged in");
		return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);

	}

	private Authentication authenticate(String username, String password) {
		UserDetails user = this.userDetail.loadUserByUsername(username);

		if (user == null) {
			throw new BadCredentialsException("Invalid username");
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}

		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

}
