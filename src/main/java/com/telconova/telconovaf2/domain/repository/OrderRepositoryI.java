package com.telconova.telconovaf2.domain.repository;

import com.telconova.telconovaf2.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepositoryI extends JpaRepository<Order, Integer> {

    @Query("""
        SELECT o 
        FROM Order o 
        JOIN o.zone z
        JOIN Assignment a ON a.order = o
        JOIN a.technician t
        WHERE (:zoneName IS NULL OR z.name = :zoneName)
          AND (:specialtyName IS NULL OR t.specialty.name = :specialtyName)
          AND (:technicianName IS NULL OR t.name = :technicianName)
    """)
    List<Order> findByFilters(
            @Param("zoneName") String zoneName,
            @Param("specialtyName") String specialtyName,
            @Param("technicianName") String technicianName
    );

}

