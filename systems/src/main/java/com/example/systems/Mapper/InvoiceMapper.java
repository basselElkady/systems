package com.example.systems.Mapper;

import com.example.systems.Dto.RequestDto.InvoiceDto;
import com.example.systems.Dto.ResponseDto.InvoiceReponseDTO;
import com.example.systems.Entity.Invoices;
import com.example.systems.Repository.CompaniesRepository;

import java.time.LocalDate;

public class InvoiceMapper {

    public static CompaniesRepository companiesRepository;

    public static void initialize(CompaniesRepository companiesRepository) {
        InvoiceMapper.companiesRepository = companiesRepository;
    }
    public static Invoices InvoiceDtoToInvoices(InvoiceDto invoiceDto) {
        Invoices invoices = new Invoices();
        invoices.setPrice(invoiceDto.price());
        invoices.setQuantity(invoiceDto.quantity());
        invoices.setTaxes(invoiceDto.taxes());
        invoices.setProductName(invoiceDto.productName());
        invoices.setCategory(invoiceDto.category());
        invoices.setCompanyFromId(companiesRepository.findByName(invoiceDto.companyNameFrom()));
        invoices.setCompanyToId(companiesRepository.findByName(invoiceDto.companyNameTo()));
        invoices.setDate(LocalDate.now());
        invoices.setTotal(
                (invoiceDto.price() * invoiceDto.quantity())
                        +
                (invoiceDto.price() * invoiceDto.quantity() * (invoiceDto.taxes() / 100))
        );

        return invoices;
    }

    public static InvoiceReponseDTO InvoicesToInvoiceDto(Invoices invoices) {

        return new InvoiceReponseDTO(
                invoices.getId(),
                invoices.getDate(),
                invoices.getProductName(),
                invoices.getCategory(),
                invoices.getCompanyFromId().getName(),
                invoices.getCompanyToId().getName(),
                invoices.getPrice(),
                invoices.getQuantity(),
                invoices.getTaxes(),
                invoices.getTotal()
        );
    }

}
