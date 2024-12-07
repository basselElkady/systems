package com.example.systems.Controller;

import com.example.systems.Dto.ListToResponseWith.CompanyDetilsList;
import com.example.systems.Services.CompaniesDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bluenile/api/v1/companies/details")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CompaniesDetailsController {

    private final CompaniesDetailsService companyDetailsService;

    @GetMapping
    public ResponseEntity<CompanyDetilsList> getCompanyDetailsService(
            @RequestParam String name,
            @RequestParam (defaultValue = "0" ) int page
    ) {
        CompanyDetilsList companyDetilsList = companyDetailsService.findByName(name, page, 20);
        return ResponseEntity.ok(companyDetilsList);
    }



}
