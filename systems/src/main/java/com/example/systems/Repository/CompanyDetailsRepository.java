package com.example.systems.Repository;

import com.example.systems.Entity.CompanyDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
    List<CompanyDetails> findAllByCompanyName(String companyName, PageRequest pageRequest);
    long countByCompanyName(String companyName);
}
