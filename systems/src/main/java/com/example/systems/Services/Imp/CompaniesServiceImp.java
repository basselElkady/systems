package com.example.systems.Services.Imp;

import com.example.systems.Dto.RequestDto.CompaniesDto;
import com.example.systems.Dto.ListToResponseWith.CompaniesList;
import com.example.systems.Dto.RequestDto.CompanyDetailsDto;
import com.example.systems.Dto.ResponseDto.CompaniesResponseDto;
import com.example.systems.Entity.Companies;
import com.example.systems.Entity.CompanyDetails;
import com.example.systems.Exception.CompanyAlreadyExists;
import com.example.systems.Exception.NotFoundCompany;
import com.example.systems.Mapper.CompaniesMapper;
import com.example.systems.Mapper.CompanyDetailsMapper;
import com.example.systems.Repository.CompaniesRepository;
import com.example.systems.Repository.CompanyDetailsRepository;
import com.example.systems.Services.CompaniesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
@Transactional

public class CompaniesServiceImp implements CompaniesService {


    private final Logger logger = LoggerFactory.getLogger(CompaniesServiceImp.class);
    private final CompaniesRepository companiesRepository;
    private final CompanyDetailsRepository companyDetailsRepository;

    @Override
    public CompaniesList findAll() {
        CompaniesList companiesList = new CompaniesList();
        List<Companies> companies = companiesRepository.findAll();
        List<CompaniesResponseDto> companiesResponseDtos = companies.stream().map(CompaniesMapper::companiestoCompaniesRespDto).toList();
        companiesList.setCompanies(companiesResponseDtos);
        return companiesList;
    }

    @Override
    public CompaniesResponseDto findByName(String name) {
        Optional<Companies> company= Optional.ofNullable(companiesRepository.findByName(name));
        if (company.isEmpty()) {
            logger.error("Company not found with name: " + name);
            throw new NotFoundCompany("Company not found with name: " + name);
        }
        return CompaniesMapper.companiestoCompaniesRespDto(company.get());
    }

    @Override
    public Boolean save(CompaniesDto companiesDto) {
        Optional<Companies> company= Optional.ofNullable(companiesRepository.findByName(companiesDto.name()));
        if (company.isPresent()) {
            logger.error("Company already exists :"+companiesDto.name());
            throw new CompanyAlreadyExists("Company already exists");
        }
        Companies companies = CompaniesMapper.CompaniesDtotoCompanies(companiesDto);
        companiesRepository.save(companies);
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<Companies> company= Optional.ofNullable(companiesRepository.findByName(name));
        if (company.isEmpty()) {
            logger.error("Company not found with name: " + name);
            throw new NotFoundCompany("Company not found with name: " + name);
        }
        companiesRepository.delete(company.get());
        return true;
    }

    @Override
    public double getMoneyLena(String name) {
        Optional<Companies> company= Optional.ofNullable(companiesRepository.findByName(name));
        if (company.isEmpty()) {
            logger.error("Company not found with name: " + name);
            throw new NotFoundCompany("Company not found with name: " + name);
        }

        return company.get().getMoneyLena();
    }

    @Override
    public boolean update(CompaniesResponseDto companiesDto,String name,String category) {
        Optional<Companies> company= Optional.ofNullable(companiesRepository.findByName(name));
        if (company.isEmpty()) {
            logger.error("Company not found with name: " + name);
            throw new NotFoundCompany("Company not found with name: " + name);
        }
        company.get().setName(companiesDto.getName());
        company.get().setMoneyLena(
                company.get().getMoneyLena() +
                companiesDto.getMoneyLena()
        );
        companiesRepository.save(company.get());
        companiesRepository.flush(); // for update we are trying this
        companyDetailsRepository.save(
                CompanyDetailsMapper.companyDetailsDtoToCompanyDetails(
                    new CompanyDetailsDto(
                            company.get().getName(),
                                            category,
                            companiesDto.getMoneyLena(),   // money updated now will be added
                            company.get().getMoneyLena()
        )));
        return true;
    }


}
