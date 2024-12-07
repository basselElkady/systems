package com.example.systems.Services;

import com.example.systems.Dto.ListToResponseWith.NasryatList;
import com.example.systems.Dto.RequestDto.InvoiceDto;
import com.example.systems.Dto.RequestDto.NasryatDto;
import com.example.systems.Dto.Specifications.SpecificationsNasryat;

public interface NasryatService {

    boolean save(NasryatDto nasryatDto);
    boolean update(NasryatDto nasryatDto,Long id);
    boolean delete(Long id);
    NasryatList findAll( int page, int size, SpecificationsNasryat specificationInvoice);


    double findTheSumForThisMonth();


}
