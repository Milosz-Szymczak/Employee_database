package pl.milosz.employees_database.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.milosz.employees_database.entity.Insurance;
import pl.milosz.employees_database.service.InsuranceService;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Insurance> getAllInsurance() {
        return insuranceService.getAllInsurance();
    }

    @GetMapping("id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Insurance getInsuranceById(@PathVariable("id") Long id) {
        return insuranceService.findInsuranceById(id);
    }

    @PostMapping("/addInsurance")
    @ResponseStatus(HttpStatus.CREATED)
    public void addInsurance(@RequestBody Insurance insurance) {
        insuranceService.addInsurance(insurance);
    }

    @PatchMapping("/updateInsurance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInsurance(@PathVariable Long id, @RequestBody Insurance insuranceEdit) {
        insuranceService.updateInsurance(id, insuranceEdit);
    }

    @DeleteMapping("/deleteInsurance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
    }
}
