package com.example.systems.Dto.RequestDto;

import lombok.Data;


public record InvoiceDto(
        double price,
        double quantity,
        double taxes,
        String productName,
        String category,
        String companyNameFrom,
        String companyNameTo
) {
}
