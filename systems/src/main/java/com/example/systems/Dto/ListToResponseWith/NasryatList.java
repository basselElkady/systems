package com.example.systems.Dto.ListToResponseWith;

import com.example.systems.Dto.ResponseDto.NasryatResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NasryatList {
    private List<NasryatResponseDto> nasryat;
}
