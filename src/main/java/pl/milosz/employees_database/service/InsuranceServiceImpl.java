package pl.milosz.employees_database.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.milosz.employees_database.entity.Insurance;
import pl.milosz.employees_database.exception.InsuranceNotFoundException;
import pl.milosz.employees_database.repository.InsuranceRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsuranceServiceImpl implements InsuranceService{
    private final InsuranceRepository insuranceRepository;

    @Override
    public List<Insurance> getAllInsurance() {
        log.info("Insurance List");
        return insuranceRepository.findAll();
    }

    @Override
    @Transactional
    public void addInsurance(Insurance insurance) {
        log.info("Insurance added");
        insuranceRepository.save(insurance);
    }

    @Override
    public Insurance findInsuranceById(Long id) {
        log.info("Insurance found");
        Optional<Insurance> optionalInsurance = insuranceRepository.findById(id);
        if (optionalInsurance.isPresent()) {
            return optionalInsurance.get();
        }
        else {
            throw new InsuranceNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public void updateInsurance(Long id, Insurance insuranceEdit) {
        log.info("Insurance updated");
        Insurance insuranceById = findInsuranceById(id);

        if (insuranceEdit.getInsuranceName() != null)
            insuranceById.setInsuranceName(insuranceEdit.getInsuranceName());
        if (insuranceEdit.getInsuranceRate() != null)
            insuranceById.setInsuranceRate(insuranceEdit.getInsuranceRate());

        insuranceRepository.save(insuranceById);
    }

    @Override
    public void deleteInsurance(Long id) {
        log.info("Insurance deleted");
        Insurance insuranceById = findInsuranceById(id);
        insuranceRepository.delete(insuranceById);
    }
}
