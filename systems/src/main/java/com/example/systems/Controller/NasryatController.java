package com.example.systems.Controller;

import com.example.systems.Dto.ListToResponseWith.NasryatList;
import com.example.systems.Dto.RequestDto.NasryatDto;
import com.example.systems.Dto.Specifications.SpecificationsNasryat;
import com.example.systems.Services.NasryatService;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bluenile/api/v1/nasryat")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class NasryatController {

    private final NasryatService nasryatService;


    @PostMapping
    public ResponseEntity<Boolean> newNasryat(@RequestBody NasryatDto nasryatDto) {
        boolean save = nasryatService.save(nasryatDto);
        return ResponseEntity.ok(save);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateNasryat(@RequestBody NasryatDto nasryatDto,@RequestParam @NotNull Long id) {
        boolean update = nasryatService.update(nasryatDto,id);
        return ResponseEntity.ok(update);
    }
    @DeleteMapping
    public ResponseEntity<Boolean> deleteNasryat(@RequestParam @NotNull Long id) {
        boolean delete = nasryatService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @PostMapping("/search")
    public ResponseEntity<NasryatList> findAllNasryat(
            @RequestParam(defaultValue = "0") int page,
            @RequestBody SpecificationsNasryat specificationInvoice) {
        NasryatList findAll = nasryatService.findAll(page, 20, specificationInvoice);
        return ResponseEntity.ok(findAll);
    }

    @GetMapping
    public ResponseEntity<Double> sumOfMonth(){
        double sum = nasryatService.findTheSumForThisMonth();
        return ResponseEntity.ok(sum);
    }


}
