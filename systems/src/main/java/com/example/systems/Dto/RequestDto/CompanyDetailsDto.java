package com.example.systems.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailsDto {
    private String companyName;
    private String category;
    private double money;
    private double total;
}
