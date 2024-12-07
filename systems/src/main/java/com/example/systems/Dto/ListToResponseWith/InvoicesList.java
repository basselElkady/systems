package com.example.systems.Dto.ListToResponseWith;

import com.example.systems.Dto.ResponseDto.InvoiceReponseDTO;

import java.util.List;

public record InvoicesList(
        List<InvoiceReponseDTO> invoices
) {
}
