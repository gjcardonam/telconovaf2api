package com.telconova.telconovaf2.domain.repository;

import com.telconova.telconovaf2.domain.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepositoryI extends JpaRepository<Zone, Integer> {
}
