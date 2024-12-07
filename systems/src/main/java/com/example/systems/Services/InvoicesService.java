package com.example.systems.Services;

import com.example.systems.Dto.RequestDto.InvoiceDto;
import com.example.systems.Dto.ListToResponseWith.InvoicesList;
import com.example.systems.Dto.Specifications.SpecificationInvoice;

import java.awt.print.Pageable;
import java.io.ByteArrayInputStream;

public interface InvoicesService {

    boolean save(InvoiceDto invoiceDto);

    boolean update(InvoiceDto invoiceDto,Long id);

    boolean delete(Long id);

    InvoicesList findAll(int page, int size, SpecificationInvoice specificationInvoice);

    public ByteArrayInputStream generatePdfWithTable(InvoicesList invoicesList);



}
