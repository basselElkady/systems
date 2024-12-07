package com.example.systems.Dto.ListToResponseWith;

import com.example.systems.Entity.CompanyDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompanyDetilsList {

    List<CompanyDetails> companyDetails;

}
