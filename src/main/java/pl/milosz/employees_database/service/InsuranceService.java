package pl.milosz.employees_database.service;

import pl.milosz.employees_database.entity.Insurance;

import java.util.List;

public interface InsuranceService {
    List<Insurance> getAllInsurance();

    void addInsurance(Insurance insurance);

    Insurance findInsuranceById(Long id);

    void updateInsurance(Long id, Insurance insuranceEdit);

    void deleteInsurance(Long id);
}
