package com.example.systems.Services;

import com.example.systems.Dto.ListToResponseWith.CompanyDetilsList;
import com.example.systems.Entity.CompanyDetails;

public interface CompaniesDetailsService {

    public CompanyDetilsList findByName(String name,int page,int size);

}
