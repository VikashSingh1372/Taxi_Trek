package com.cab.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Licence {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String licenceNumber;
	private String licenceState;
	private String licenceExpirationDate;
	private String mobile;

	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private Driver driver;
}
