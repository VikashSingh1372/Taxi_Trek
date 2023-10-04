package com.cab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

}
