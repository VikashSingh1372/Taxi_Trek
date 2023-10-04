package com.cab.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cab.entity.Ride;
import com.cab.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
	
	
	@Query("Select r from Ride r Where r.user.id=:userId AND r.status=:COMPLETED")
	public List<Ride> getCompletedRide(@Param("userId") Long userId);

}
