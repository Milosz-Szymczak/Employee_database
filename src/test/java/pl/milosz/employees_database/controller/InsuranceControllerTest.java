package pl.milosz.employees_database.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.milosz.employees_database.entity.Insurance;
import pl.milosz.employees_database.service.InsuranceService;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = InsuranceController.class)
class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceService insuranceService;

    private Insurance insurance1;
    private Insurance insurance2;

    @BeforeEach
    void setUp() {
        insurance1 = Insurance.builder()
                .id(1L)
                .insuranceName("Health Plan A")
                .build();

        insurance2 = Insurance.builder()
                .id(2L)
                .insuranceName("Health Plan B")
                .build();
    }

    @Test
    void getAllInsurance() throws Exception {
        // Arrange
        List<Insurance> insurances = Arrays.asList(insurance1, insurance2);
        when(insuranceService.getAllInsurance()).thenReturn(insurances);

        // Act & Assert
        mockMvc.perform(get("/api/insurance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].insuranceName").value("Health Plan A"))
                .andExpect(jsonPath("$[1].insuranceName").value("Health Plan B"));
    }

    @Test
    void addInsurance() throws Exception {
        // Arrange

        doNothing().when(insuranceService).addInsurance(any(Insurance.class));

        // Act & Assert
        mockMvc.perform(post("/api/insurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"insuranceName\":\"Health Plan C\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateInsurance() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(insuranceService).updateInsurance(anyLong(), any(Insurance.class));

        // Act & Assert
        mockMvc.perform(patch("/api/insurance/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"insuranceName\":\"Updated Health Plan\"}"))
                .andExpect(status().isNoContent());
    }
}