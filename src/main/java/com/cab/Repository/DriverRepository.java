package com.cab.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cab.entity.Driver;
import com.cab.entity.Ride;

public interface DriverRepository extends JpaRepository<Driver, Long> {
	
	public Driver findByEmail(String email);
	
	
	@Query("Select R from Ride R where R.status=:REQUESTED AND R.driver.id=:driverId ")
	public List<Ride> getAllocatedRide(@Param("driverId") Long driverld);
	

	@Query("Select R from Ride R where R.status=:COMPLETED AND R.driver.id=:driverId ")
	public List<Ride> getCompletedRide(@Param("driverId") Long driverId);

}
