package com.example.systems.Services.Imp;

import com.example.systems.Dto.ListToResponseWith.CompanyDetilsList;
import com.example.systems.Repository.CompanyDetailsRepository;
import com.example.systems.Services.CompaniesDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;

@Service
@Transactional
@AllArgsConstructor
public class CompaniesDetailsServiceImp implements CompaniesDetailsService {

    private final CompanyDetailsRepository companyDetailsRepository;

    @Override
    public CompanyDetilsList findByName(String name, int page, int size) {
        // Get total count
        long totalRecords = companyDetailsRepository.countByCompanyName(name);

        int maxpage = (int) Math.max(0, (totalRecords + size - 1) / size - 1);
        if(page>maxpage){
            return new CompanyDetilsList(
                    new ArrayList<>()
            );
        }

        return new CompanyDetilsList(
                companyDetailsRepository.findAllByCompanyName(name,  PageRequest.of(maxpage-page, size))
        );
    }

}
