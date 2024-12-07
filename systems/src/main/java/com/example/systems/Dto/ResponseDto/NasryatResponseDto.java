package com.example.systems.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NasryatResponseDto {

    private Long id;
    private LocalDate date;
    private String name;
    private String category;
    private double price;

}
