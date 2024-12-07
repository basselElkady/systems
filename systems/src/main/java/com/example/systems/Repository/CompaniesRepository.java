package com.example.systems.Repository;

import com.example.systems.Entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies, Long> {

    Companies findByName(String name);

}
