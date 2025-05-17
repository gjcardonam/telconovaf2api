package com.telconova.telconovaf2.domain.repository;

import com.telconova.telconovaf2.domain.entities.Assignment;
import com.telconova.telconovaf2.domain.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentsRepositoryI extends JpaRepository<Assignment, Integer> {

    Optional<Assignment> findByOrderId(Integer orderId);

    List<Assignment> findByOrderIdIn(List<Integer> orderIds);

    @Query("""
        SELECT a
        FROM Assignment a
        WHERE a.order.id = :orderId
        ORDER BY a.timeStamp DESC
    """)
    List<Assignment> findLatestByOrderId(@Param("orderId") Integer orderId);

    long countByTechnicianId(Integer technicianId);

    List<Assignment> findAllByTechnician(Technician technician);

    @Query("""
        SELECT a FROM Assignment a
        JOIN FETCH a.order o
        LEFT JOIN FETCH o.zone
        JOIN FETCH a.technician t
        WHERE a.id = :assignmentId
    """)
    Optional<Assignment> findByIdWithDetails(@Param("assignmentId") Integer id);

}
