package com.example.systems.Mapper;

import com.example.systems.Dto.RequestDto.NasryatDto;
import com.example.systems.Dto.ResponseDto.NasryatResponseDto;
import com.example.systems.Entity.Nasryat;

import java.time.LocalDate;

public class NasryatMapper {

    public static Nasryat NasryatDtoToNasryat(NasryatDto nasryatDto) {
        Nasryat nasryat = new Nasryat();
        nasryat.setName(nasryatDto.name());
        nasryat.setCategory(nasryatDto.category());
        nasryat.setPrice(nasryatDto.price());
        nasryat.setDate(LocalDate.now());
        return nasryat;
    }

    public static NasryatResponseDto NasryatToNasryatResponseDto(Nasryat nasryat) {
        NasryatResponseDto nasryatResponseDto = new NasryatResponseDto();
        nasryatResponseDto.setId(nasryat.getId());
        nasryatResponseDto.setDate(nasryat.getDate());
        nasryatResponseDto.setName(nasryat.getName());
        nasryatResponseDto.setCategory(nasryat.getCategory());
        nasryatResponseDto.setPrice(nasryat.getPrice());
        return nasryatResponseDto;
    }

}
