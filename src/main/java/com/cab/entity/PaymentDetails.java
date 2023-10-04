package com.cab.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDetails {
	
	private PaymentStatus paymentStatus;
	private String paymentId;
	

}
