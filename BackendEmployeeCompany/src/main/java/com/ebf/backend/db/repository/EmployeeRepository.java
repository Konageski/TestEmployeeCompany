package com.ebf.backend.db.repository;

import com.ebf.backend.db.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("SELECT e FROM employee e LEFT JOIN FETCH e.companyEntity c WHERE e.id = :id ")
    Optional<EmployeeEntity> findById(Long id);

    @Query("SELECT e FROM employee e ORDER BY e.name")
    List<EmployeeEntity> findAll();
}
