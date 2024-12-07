package com.example.systems.Mapper;

import com.example.systems.Dto.RequestDto.CompanyDetailsDto;
import com.example.systems.Dto.ResponseDto.CompanyDetailsResponseDto;
import com.example.systems.Entity.CompanyDetails;

import java.time.LocalDate;

public class CompanyDetailsMapper {

    public static CompanyDetails companyDetailsDtoToCompanyDetails(CompanyDetailsDto companyDetailsDto) {
        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setCompanyName(companyDetailsDto.getCompanyName());
        companyDetails.setCategory(companyDetailsDto.getCategory());
        companyDetails.setMoney(companyDetailsDto.getMoney());
        companyDetails.setDate(LocalDate.now());
        companyDetails.setTotal(companyDetailsDto.getTotal());
        return companyDetails;
    }

    public static CompanyDetailsResponseDto companyDetailsResponseDtoToCompanyDetails(CompanyDetails companyDetails) {
        CompanyDetailsResponseDto companyDetailsDto = new CompanyDetailsResponseDto();
        companyDetailsDto.setCompanyName(companyDetails.getCompanyName());
        companyDetailsDto.setCategory(companyDetails.getCategory());
        companyDetailsDto.setMoney(companyDetails.getMoney());
        companyDetailsDto.setDate(companyDetails.getDate());
        companyDetailsDto.setTotal(companyDetails.getTotal());
        return companyDetailsDto;
    }



}
