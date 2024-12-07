package com.example.systems.Dto.ListToResponseWith;

import com.example.systems.Dto.ResponseDto.CompaniesResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompaniesList {

    private List<CompaniesResponseDto> companies;

}
