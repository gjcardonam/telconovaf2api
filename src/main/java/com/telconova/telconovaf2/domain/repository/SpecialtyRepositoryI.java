package com.telconova.telconovaf2.domain.repository;

import com.telconova.telconovaf2.domain.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepositoryI extends JpaRepository<Specialty, Integer> {
}
