package com.cab.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Driver {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String mobile;
	private double rating;
	private double latitude;
	private double longitude;
	private UserRole role;

	@OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
	private Licence licence;

	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
	private List<Ride> rides;

	@OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
	private Vehicle vehicle;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private Ride currentRide;
	
	private Integer totalRevenue;

}
