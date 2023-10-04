package com.cab.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.entity.Licence;

public interface LicenceRepository  extends JpaRepository<Licence, Long> {

}
