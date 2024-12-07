package com.example.systems.Dto.Specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecificationsNasryat {

    private Long id;
    private LocalDate toDate;
    private LocalDate fromDate;
    private String name;
    private String category;
    private Double price;


}
