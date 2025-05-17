package com.telconova.telconovaf2.domain.repository;

import com.telconova.telconovaf2.domain.entities.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepositoryI extends JpaRepository<Supervisor, Integer> {
}
