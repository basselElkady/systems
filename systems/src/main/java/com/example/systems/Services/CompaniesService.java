package com.example.systems.Services;

import com.example.systems.Dto.RequestDto.CompaniesDto;
import com.example.systems.Dto.ListToResponseWith.CompaniesList;
import com.example.systems.Dto.ResponseDto.CompaniesResponseDto;

public interface CompaniesService {

    CompaniesList findAll();

    CompaniesResponseDto findByName(String name);

    Boolean save(CompaniesDto companiesDto);

    boolean delete(String name);


    double getMoneyLena(String name);

    boolean update(CompaniesResponseDto companiesResDto,String name,String category);

}



