package com.example.systems.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Companies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double moneyLena;
    @OneToMany(mappedBy = "companyToId", cascade = CascadeType.PERSIST)
    private List<Invoices> invoicesTO;
    @OneToMany(mappedBy = "companyFromId", cascade = CascadeType.PERSIST)
    private List<Invoices> invoicesFROM;


}
