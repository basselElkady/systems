package com.example.systems.Controller;


import com.example.systems.Dto.RequestDto.CompaniesDto;
import com.example.systems.Dto.ResponseDto.CompaniesResponseDto;
import com.example.systems.Services.CompaniesService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bluenile/api/v1/companies")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CompaniesController {

    private final CompaniesService companiesService;


    @PostMapping
    public ResponseEntity<Boolean> newCompany(@RequestBody CompaniesDto companiesDto) {
        boolean save = companiesService.save(companiesDto);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CompaniesResponseDto> findByName(
           @PathVariable String name
    ) {
        CompaniesResponseDto findByName = companiesService.findByName(name);
        return ResponseEntity.ok(findByName);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestParam String name) {
        boolean delete = companiesService.delete(name);
        return ResponseEntity.ok(delete);
    }

    @PutMapping
    public ResponseEntity<Boolean> update(
            @RequestBody CompaniesResponseDto companiesResDto,@RequestParam String name,@RequestParam String category
    ) {
        boolean update = companiesService.update(companiesResDto,name,category);
        return ResponseEntity.ok(update);
    }









}
