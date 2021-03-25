package com.ebf.backend.db.repository;

import com.ebf.backend.db.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("SELECT c FROM company c LEFT JOIN FETCH c.employeeEntities e WHERE c.id = :id ")
    Optional<CompanyEntity> findById(Long id);

    @Query("SELECT c FROM company c ORDER BY c.name")
    List<CompanyEntity> findAll(Long id);
}
