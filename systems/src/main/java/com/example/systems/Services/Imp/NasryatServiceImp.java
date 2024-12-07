package com.example.systems.Services.Imp;

import com.example.systems.Dto.ListToResponseWith.NasryatList;
import com.example.systems.Dto.RequestDto.NasryatDto;
import com.example.systems.Dto.Specifications.SpecificationsNasryat;
import com.example.systems.Entity.Nasryat;
import com.example.systems.Exception.NasryatNotFound;
import com.example.systems.Mapper.NasryatMapper;
import com.example.systems.Repository.NasryatRepository;
import com.example.systems.Repository.Spacifications.NasryatSpecifications;
import com.example.systems.Services.NasryatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
@Transactional

public class NasryatServiceImp implements NasryatService {

    private final NasryatRepository nasryatRepository;


    @Override
    public boolean save(NasryatDto nasryatDto) {

        Nasryat nasryat = NasryatMapper.NasryatDtoToNasryat(nasryatDto);
        nasryatRepository.save(nasryat);
        return true;
    }

    @Override
    public boolean update(NasryatDto nasryatDto, Long id) {
        Nasryat nasryat=nasryatRepository.findById(id).orElseThrow(
                () ->  new NasryatNotFound("Nasryat not found")
        );
        Nasryat newNasryat = NasryatMapper.NasryatDtoToNasryat(nasryatDto);
        nasryatRepository.save(newNasryat);
        nasryatRepository.delete(nasryat);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Nasryat nasryat=nasryatRepository.findById(id).orElseThrow(
                () ->  new NasryatNotFound("Nasryat not found")
        );

        nasryatRepository.delete(nasryat);
        return true;
    }

    @Override
    public NasryatList findAll(int page, int size, SpecificationsNasryat specificationInvoice) {

        Specification<Nasryat> specification = NasryatSpecifications.getfilterSpecification(specificationInvoice);

        long totalRecords = nasryatRepository.count(specification);

        int maxpage = (int) Math.max(0, (totalRecords + size - 1) / size - 1);
        if(page>maxpage){
            return new NasryatList(
                    new ArrayList<>()
            );
        }

        return new NasryatList(nasryatRepository.findAll(specification, PageRequest.of(maxpage-page, size))
                .stream()
                .map(NasryatMapper::NasryatToNasryatResponseDto)
                .toList());

    }

    @Override
    public double findTheSumForThisMonth() {
        List<Nasryat> list = nasryatRepository.findAllByCurrentMonth();
        Double sum = list.stream().mapToDouble(Nasryat::getPrice).sum();
        return sum;
    }
}
