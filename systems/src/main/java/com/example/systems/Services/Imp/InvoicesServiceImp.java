package com.example.systems.Services.Imp;

import com.example.systems.Dto.ListToResponseWith.CompanyDetilsList;
import com.example.systems.Dto.RequestDto.CompanyDetailsDto;
import com.example.systems.Dto.RequestDto.InvoiceDto;
import com.example.systems.Dto.ListToResponseWith.InvoicesList;
import com.example.systems.Dto.ResponseDto.CompaniesResponseDto;
import com.example.systems.Dto.ResponseDto.InvoiceReponseDTO;
import com.example.systems.Dto.Specifications.SpecificationInvoice;
import com.example.systems.Entity.Companies;
import com.example.systems.Entity.CompanyDetails;
import com.example.systems.Entity.Invoices;
import com.example.systems.Exception.InvoiceNotFound;
import com.example.systems.Mapper.CompanyDetailsMapper;
import com.example.systems.Mapper.InvoiceMapper;
import com.example.systems.Repository.CompaniesRepository;
import com.example.systems.Repository.CompanyDetailsRepository;
import com.example.systems.Repository.InvoicesRepository;
import com.example.systems.Repository.Spacifications.InvoicesSpecification;
import com.example.systems.Services.CompaniesService;
import com.example.systems.Services.InvoicesService;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.awt.print.Pageable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
@Transactional
public class InvoicesServiceImp implements InvoicesService {

    private final InvoicesRepository invoicesRepository;
    private final CompaniesRepository companiesRepository;
    private final CompaniesService companiesService;
    private final CompanyDetailsRepository companyDetailsRepository;
    private final Logger logger = LoggerFactory.getLogger(InvoicesServiceImp.class);

    private void initialize(CompaniesRepository companiesRepository) {
        InvoiceMapper.companiesRepository = companiesRepository;
    }

    private void updateMoney(Invoices invoice) {
        Companies fromCompany = invoice.getCompanyFromId();
        Companies toCompany = invoice.getCompanyToId();
        fromCompany.setMoneyLena(fromCompany.getMoneyLena() + invoice.getTotal());
        toCompany.setMoneyLena(toCompany.getMoneyLena() - invoice.getTotal());
        companiesRepository.save(fromCompany);
        companiesRepository.save(toCompany);
        companiesRepository.flush();
    }

    private void ReturnMoney(Invoices invoice) {
        Companies fromCompany = invoice.getCompanyFromId();
        Companies toCompany = invoice.getCompanyToId();
        fromCompany.setMoneyLena(fromCompany.getMoneyLena() - invoice.getTotal());
        toCompany.setMoneyLena(toCompany.getMoneyLena() + invoice.getTotal());
        companiesRepository.save(fromCompany);
        companiesRepository.save(toCompany);
        companiesRepository.flush();
    }

    private void checkCompany(String fromCompany, String toCompany) {
        if (fromCompany == null || toCompany == null) {
            logger.error("Companies not found +" + fromCompany + " or " + toCompany);
            throw new IllegalArgumentException("Company not found");
        }

        CompaniesResponseDto companyFrom = companiesService.findByName(fromCompany);
        CompaniesResponseDto companyTo = companiesService.findByName(toCompany);
    }

    @Override
    public boolean save(InvoiceDto invoiceDto) {
        initialize(companiesRepository);
        checkCompany(invoiceDto.companyNameFrom(), invoiceDto.companyNameTo());
        Invoices newInvoice = InvoiceMapper.InvoiceDtoToInvoices(invoiceDto);

        updateMoney(newInvoice);

        Long[] companyDetailsId = saveCompanyDetails(invoiceDto, newInvoice, newInvoice.getCompanyFromId(), newInvoice.getCompanyToId());
        newInvoice.setCompanyDetailsId(companyDetailsId);

        invoicesRepository.save(newInvoice);
        return true;
    }



    private Long[] saveCompanyDetails(InvoiceDto invoiceDto, Invoices newInvoice, Companies fromCompany, Companies toCompany) {
        Long[] companyDetailsId = new Long[2];
        CompanyDetails companyDetailsFrom = companyDetailsRepository.save(
                CompanyDetailsMapper.companyDetailsDtoToCompanyDetails(
                        new CompanyDetailsDto(
                                invoiceDto.companyNameFrom(),
                                "فاتوره",
                                newInvoice.getTotal(),
                                fromCompany.getMoneyLena()
                        )
                )
        );

        CompanyDetails companyDetailsTo = companyDetailsRepository.save(
                CompanyDetailsMapper.companyDetailsDtoToCompanyDetails(
                        new CompanyDetailsDto(
                                invoiceDto.companyNameTo(),
                                "فاتوره",
                                newInvoice.getTotal(),
                                toCompany.getMoneyLena()
                        )
                )
        );

        companyDetailsId[0] = companyDetailsFrom.getId();
        companyDetailsId[1] = companyDetailsTo.getId();
        return companyDetailsId;
    }


    @Override
    public boolean update(InvoiceDto invoiceDto, Long id) {
        Invoices invoices = invoicesRepository.findById(id).orElseThrow(() -> new InvoiceNotFound("Invoice not found"));
        initialize(companiesRepository);
        checkCompany(invoiceDto.companyNameFrom(), invoiceDto.companyNameTo());
        ReturnMoney(invoices);
        Invoices newInvoice = InvoiceMapper.InvoiceDtoToInvoices(invoiceDto);
        updateMoney(newInvoice);

        Long[] companyDetailsId = invoices.getCompanyDetailsId();
        Arrays.stream(companyDetailsId).forEach(
                companyDetailsRepository::deleteById
        );

        Long[] companyDetailsIdNew = saveCompanyDetails(invoiceDto, newInvoice, newInvoice.getCompanyFromId(), newInvoice.getCompanyToId());
        newInvoice.setCompanyDetailsId(companyDetailsIdNew);

        invoicesRepository.save(newInvoice);
        invoicesRepository.delete(invoices);

        return true;
    }



    @Override
    public boolean delete(Long id) {
        Invoices invoices = invoicesRepository.findById(id).orElseThrow(() -> new InvoiceNotFound("Invoice not found"));
        ReturnMoney(invoices);
        invoicesRepository.delete(invoices);
        return true;
    }

    @Override
    public InvoicesList findAll(int page, int size, SpecificationInvoice specificationInvoice) {

        Specification<Invoices> specification = InvoicesSpecification.getfilterSpecification(specificationInvoice);
        long totalRecords = invoicesRepository.count(specification);

        int maxpage = (int) Math.max(0, (totalRecords + size - 1) / size - 1);
        if(page>maxpage){
            return new InvoicesList(
                    new ArrayList<>()
            );
        }

        return new InvoicesList(
                invoicesRepository.findAll(specification, PageRequest.of(maxpage-page, size))
                        .stream()
                        .map(InvoiceMapper::InvoicesToInvoiceDto)
                        .toList()
        );
    }

    @Override
    public ByteArrayInputStream generatePdfWithTable(InvoicesList invoicesList) {

            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

//            BaseFont bf = BaseFont.createFont("arabic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            Font arabicFont = new Font(bf, 16, Font.NORMAL);

            // Add Arabic text
            Paragraph p1 = new Paragraph("فــواتـــيـــر بـــــلـــونــــايــــل"
            );
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);

            document.add(new Paragraph("\n"));

            LineSeparator line = new LineSeparator();
            line.setLineWidth(1); // Set line thickness
            document.add(new Chunk(line));

            Paragraph title = new Paragraph(" العربيه لغتى ");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));


            PdfPTable table = new PdfPTable(8);

            table.setWidthPercentage(100);
            PdfPCell header1 = new PdfPCell(new Paragraph("Date"));
            PdfPCell header2 = new PdfPCell(new Paragraph("Product Name"));
            PdfPCell header4 = new PdfPCell(new Paragraph("From Company"));
            PdfPCell header5 = new PdfPCell(new Paragraph("To Company"));
            PdfPCell header6 = new PdfPCell(new Paragraph("Price"));
            PdfPCell header7 = new PdfPCell(new Paragraph("Quantity"));
            PdfPCell header8 = new PdfPCell(new Paragraph("Taxes"));
            PdfPCell header9 = new PdfPCell(new Paragraph("Total"));

            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header4);
            table.addCell(header5);
            table.addCell(header6);
            table.addCell(header7);
            table.addCell(header8);
            table.addCell(header9);

            for (InvoiceReponseDTO invoice : invoicesList.invoices()) {
                table.addCell(invoice.date().toString());
                table.addCell(invoice.productName());
                table.addCell(invoice.companyNameFrom());
                table.addCell(invoice.companyNameTo());
                table.addCell(String.valueOf(invoice.price()));
                table.addCell(String.valueOf(invoice.quantity()));
                table.addCell(String.valueOf(invoice.taxes()));
                table.addCell(String.valueOf(invoice.total()));
            }

            document.add(table);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF",e);
        }
    }
}
