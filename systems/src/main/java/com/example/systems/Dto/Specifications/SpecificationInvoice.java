package com.example.systems.Dto.Specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecificationInvoice {

    LocalDate fromDate; // Start date for range
    LocalDate toDate;   // End date for range
    Double price;
    Double quantity;
    Double taxes;
    Double total;
    String productName;
    String category;
    String companyToName;
    String companyFromName;

}
