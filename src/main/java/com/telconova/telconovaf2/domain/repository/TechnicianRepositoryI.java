package com.telconova.telconovaf2.domain.repository;

import com.telconova.telconovaf2.domain.entities.Specialty;
import com.telconova.telconovaf2.domain.entities.Technician;
import com.telconova.telconovaf2.domain.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepositoryI extends JpaRepository<Technician, Integer> {

    List<Technician> findByZoneAndSpecialty(Zone zone, Specialty specialty);
}
