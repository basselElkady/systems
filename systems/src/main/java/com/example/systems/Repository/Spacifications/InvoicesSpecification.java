package com.example.systems.Repository.Spacifications;

import com.example.systems.Dto.Specifications.SpecificationInvoice;
import com.example.systems.Entity.Companies;
import com.example.systems.Entity.Invoices;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


public class InvoicesSpecification {

    public static Specification<Invoices> getfilterSpecification(
            SpecificationInvoice invoice
    ) {
        return (root,query,criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (invoice.getFromDate() != null && invoice.getToDate() != null) {
                if (invoice.getFromDate().equals(invoice.getToDate())) {
                    // Specific date
                    predicates.add(criteriaBuilder.equal(root.get("date"), invoice.getFromDate()));
                } else {
                    // Date range
                    predicates.add(criteriaBuilder.between(root.get("date"), invoice.getFromDate(), invoice.getToDate()));
                }
            }
            if (invoice.getPrice() != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), invoice.getPrice() ));
            }
            if (invoice.getQuantity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("quantity"), invoice.getQuantity()));
            }
            if (invoice.getTaxes() != null) {
                predicates.add(criteriaBuilder.equal(root.get("taxes"), invoice.getTaxes()));
            }
            if (invoice.getTotal() != null) {
                predicates.add(criteriaBuilder.equal(root.get("total"), invoice.getTotal()));
            }
            if (invoice.getProductName() != null && !invoice.getProductName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("productName"), invoice.getProductName()));
            }
            if (invoice.getCategory() != null && !invoice.getCategory().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), invoice.getCategory()));
            }
            if (invoice.getCompanyToName() != null && !invoice.getCompanyToName().isEmpty()) {
                Join<Invoices, Companies> companyTo = root.join("companyToId", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(companyTo.get("name"), "%" + invoice.getCompanyToName() + "%"));
            }

            // Filter by companyFrom name
            if (invoice.getCompanyFromName() != null && !invoice.getCompanyFromName().isEmpty()) {
                Join<Invoices, Companies> companyFrom = root.join("companyFromId", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(companyFrom.get("name"), "%" + invoice.getCompanyFromName() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


        };

    }


}
