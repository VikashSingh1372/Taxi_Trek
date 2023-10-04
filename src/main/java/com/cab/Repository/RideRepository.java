package com.cab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.entity.Ride;

public interface RideRepository extends JpaRepository<Ride, Long> {

}
