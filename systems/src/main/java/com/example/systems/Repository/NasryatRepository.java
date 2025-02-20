package com.example.systems.Repository;

import com.example.systems.Entity.Nasryat;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NasryatRepository extends JpaRepository<Nasryat, Long>, JpaSpecificationExecutor<Nasryat> {

    @Query("SELECT e FROM Nasryat e WHERE " +
            "YEAR(e.date) = YEAR(CURRENT_DATE) AND " +
            "MONTH(e.date) = MONTH(CURRENT_DATE)")
    List<Nasryat> findAllByCurrentMonth();

}
