package com.ebf.backend.db.repository;

import com.ebf.backend.db.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
