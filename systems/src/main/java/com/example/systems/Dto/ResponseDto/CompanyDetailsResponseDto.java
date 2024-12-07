package com.example.systems.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailsResponseDto {

    private String companyName;
    private String category;
    private double money;
    private LocalDate date;
    private double total;

}
