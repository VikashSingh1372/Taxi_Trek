package com.cab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {
	
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetail> userExceptionHandler(UserException userException,WebRequest request){
		 ErrorDetail err = new ErrorDetail(userException.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DriverException.class)
	public ResponseEntity<ErrorDetail> driverExceptionHandler(DriverException driverException,WebRequest request){
		 ErrorDetail err = new ErrorDetail(driverException.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RideException.class)
	public ResponseEntity<ErrorDetail> rideExceptionHandler(RideException rideException,WebRequest request){
		 ErrorDetail err = new ErrorDetail(rideException.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception exception,WebRequest request){
		 ErrorDetail err = new ErrorDetail(exception.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
}
