package com.example.systems.Repository.Spacifications;

import com.example.systems.Dto.ResponseDto.NasryatResponseDto;
import com.example.systems.Dto.Specifications.SpecificationsNasryat;
import com.example.systems.Entity.Nasryat;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class NasryatSpecifications {

    public static Specification<Nasryat> getfilterSpecification(SpecificationsNasryat specificationInvoice) {
        return (root,quary,critraiaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(specificationInvoice.getToDate() != null && specificationInvoice.getFromDate() != null) {
                if(specificationInvoice.getToDate().equals(specificationInvoice.getFromDate())) {
                    predicates.add(critraiaBuilder.equal(root.get("date"), specificationInvoice.getFromDate()));
                } else {
                    predicates.add(critraiaBuilder.between(root.get("date"), specificationInvoice.getFromDate(), specificationInvoice.getToDate()));
                }
            }


            if (specificationInvoice.getName() != null && !specificationInvoice.getName().isEmpty()) {
                predicates.add(critraiaBuilder.equal(root.get("name"), specificationInvoice.getName()));
            }
            if (specificationInvoice.getCategory() != null && !specificationInvoice.getCategory().isEmpty()) {
                predicates.add(critraiaBuilder.equal(root.get("category"), specificationInvoice.getCategory()));
            }
            if (specificationInvoice.getPrice() != null) {
                predicates.add(critraiaBuilder.equal(root.get("price"), specificationInvoice.getPrice()));
            }
            return critraiaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
