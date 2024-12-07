package com.example.systems.Dto.ResponseDto;

import lombok.NoArgsConstructor;

import java.time.LocalDate;


public record InvoiceReponseDTO(
        Long id,
        LocalDate date,
        String productName,
        String category,
        String companyNameFrom,
        String companyNameTo,
        double price,
        double quantity,
        double taxes,
        double total
) {
}
