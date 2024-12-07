package com.example.systems.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invoices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double price;
    private double quantity;
    private double taxes;
    private double total;
    @ManyToOne
    @JoinColumn(name = "companyToId")
    private Companies companyToId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyFromId")
    private Companies companyFromId;
    private String productName;

    private String category;

    private Long[] CompanyDetailsId;



}
