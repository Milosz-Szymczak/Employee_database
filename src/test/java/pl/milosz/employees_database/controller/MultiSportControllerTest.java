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
import pl.milosz.employees_database.entity.MultiSport;
import pl.milosz.employees_database.service.MultiSportService;

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
@WebMvcTest(controllers = MultiSportController.class)
class MultiSportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MultiSportService multiSportService;

    private MultiSport multiSport1;
    private MultiSport multiSport2;

    @BeforeEach
    void setUp() {
        multiSport1 = MultiSport.builder()
                .id(1L)
                .packageName("Fitness Plan A")
                .build();

        multiSport2 = MultiSport.builder()
                .id(2L)
                .packageName("Fitness Plan B")
                .build();
    }

    @Test
    void getAllMultiSports() throws Exception {
        // Arrange
        List<MultiSport> multiSports = Arrays.asList(multiSport1, multiSport2);
        when(multiSportService.getAllMultiSports()).thenReturn(multiSports);

        // Act & Assert
        mockMvc.perform(get("/api/multisport"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].packageName").value("Fitness Plan A"))
                .andExpect(jsonPath("$[1].packageName").value("Fitness Plan B"));
    }

    @Test
    void addMultiSport() throws Exception {
        // Arrange
        doNothing().when(multiSportService).addMultiSport(any(MultiSport.class));

        // Act & Assert
        mockMvc.perform(post("/api/multisport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"packageName\":\"Fitness Plan A\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateMultiSport() throws Exception {
        // Arrange
        MultiSport updatedMultiSport = MultiSport.builder()
                .packageName("Updated Fitness Plan")
                .build();

        doNothing().when(multiSportService).updateMultiSport(1L, updatedMultiSport);

        // Act & Assert
        mockMvc.perform(patch("/api/multisport/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"packageName\":\"Updated Fitness Plan\"}"))
                .andExpect(status().isNoContent());
    }
}