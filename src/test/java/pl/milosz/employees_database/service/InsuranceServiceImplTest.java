package pl.milosz.employees_database.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.employees_database.entity.Insurance;
import pl.milosz.employees_database.exception.InsuranceNotFoundException;
import pl.milosz.employees_database.repository.InsuranceRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InsuranceServiceImplTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @InjectMocks
    private InsuranceServiceImpl insuranceService;

    private Insurance insurance1;
    private Insurance insurance2;

    @BeforeEach
    void setUp() {
        insurance1 = Insurance.builder()
                .id(1L)
                .insuranceName("Health Plan A")
                .insuranceRate(100.0)
                .build();

        insurance2 = Insurance.builder()
                .id(2L)
                .insuranceName("Health Plan B")
                .insuranceRate(200.0)
                .build();
    }

    @Test
    void getAllInsurance() {
        // Arrange
        List<Insurance> insurances = Arrays.asList(insurance1, insurance2);
        when(insuranceRepository.findAll()).thenReturn(insurances);

        // Act
        List<Insurance> result = insuranceService.getAllInsurance();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Health Plan A", result.get(0).getInsuranceName());
        assertEquals("Health Plan B", result.get(1).getInsuranceName());
    }

    @Test
    void addInsurance() {
        // Arrange
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(insurance1);

        // Act
        insuranceService.addInsurance(insurance1);

        // Assert
        verify(insuranceRepository).save(insurance1);
    }

    @Test
    void findInsuranceById_ExistingId() {
        // Arrange
        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance1));

        // Act
        Insurance result = insuranceService.findInsuranceById(1L);

        // Assert
        assertEquals("Health Plan A", result.getInsuranceName());
    }

    @Test
    void findInsuranceById_NonExistingId() {
        // Arrange
        when(insuranceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InsuranceNotFoundException.class, () -> insuranceService.findInsuranceById(1L));
    }

    @Test
    void updateInsurance() {
        // Arrange
        Insurance updatedInsurance = Insurance.builder()
                .insuranceName("Updated Plan")
                .insuranceRate(150.0)
                .build();

        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance1));
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(updatedInsurance);

        // Act
        insuranceService.updateInsurance(1L, updatedInsurance);

        // Assert
        verify(insuranceRepository).save(insurance1);
        assertEquals("Updated Plan", insurance1.getInsuranceName());
        assertEquals(150.0, insurance1.getInsuranceRate());
    }
}