package com.example.systems.Mapper;


import com.example.systems.Dto.RequestDto.CompaniesDto;
import com.example.systems.Dto.ResponseDto.CompaniesResponseDto;
import com.example.systems.Entity.Companies;

public class CompaniesMapper {

    public static CompaniesResponseDto companiestoCompaniesRespDto(Companies companies) {
        CompaniesResponseDto companiesResponseDto = new CompaniesResponseDto();
        companiesResponseDto.setName(companies.getName());
        companiesResponseDto.setMoneyLena(companies.getMoneyLena());
        return companiesResponseDto;
    }

    public static Companies CompaniesDtotoCompanies(CompaniesDto companiesDto) {
        Companies companies = new Companies();
        companies.setName(companiesDto.name());
        CompaniesDto companiesDto1 = new CompaniesDto("he");
        return companies;
    }



}
