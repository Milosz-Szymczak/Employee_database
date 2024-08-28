package pl.milosz.employees_database.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.milosz.employees_database.entity.Employee;
import pl.milosz.employees_database.service.EmployeeService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void getAllEmployees_shouldShowAllEmployees() throws Exception {
        // Arrange
        Employee employee1 = Employee.builder()
                .id(1L)
                .fullName("Jack Smith")
                .lastMedicalExamDate(LocalDate.of(2023, 5, 1))
                .bhpExamDate(LocalDate.of(2023, 5, 15))
                .ppk(true)
                .remarks("No remarks")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .fullName("Jack Smith")
                .lastMedicalExamDate(LocalDate.of(2023, 4, 20))
                .bhpExamDate(LocalDate.of(2023, 4, 25))
                .ppk(false)
                .remarks("Pending review")
                .build();

        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Jack Smith"))
                .andExpect(jsonPath("$[1].fullName").value("Jack Smith"));
    }

    @Test
    void addEmployee_shouldAddNewEmployees() throws Exception {
        // Arrange
        Employee employee = Employee.builder()
                .id(1L)
                .fullName("John Doe")
                .lastMedicalExamDate(LocalDate.of(2023, 5, 1))
                .bhpExamDate(LocalDate.of(2023, 5, 15))
                .ppk(true)
                .remarks("No remarks")
                .build();

        doNothing().when(employeeService).addEmployee(employee);
        // Act & Assert
        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Jack Smith\", \"lastMedicalExamDate\":\"2023-05-01\", \"bhpExamDate\":\"2023-05-15\", \"ppk\":true, \"remarks\":\"No remarks\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateEmployee_shouldUpdateEmployee() throws Exception {
        // Arrange
        Long id = 1L;
        Employee employeeEdit = Employee.builder()
                .fullName("Updated Name")
                .build();

        doNothing().when(employeeService).updateEmployee(id, employeeEdit);

        // Act & Assert
        mockMvc.perform(patch("/api/employee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Updated Name\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEmployee_shouldDeleteEmployee() throws Exception {
        // Arrange
        Long id = 1L;

        doNothing().when(employeeService).deleteEmployee(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/employee/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void findEmployee_shouldFindEmployee() throws Exception {
        // Arrange
        String employeeName = "Jack Smith";
        Employee employee1 = Employee.builder()
                .id(1L)
                .fullName("Jack Smith")
                .lastMedicalExamDate(LocalDate.of(2023, 5, 1))
                .bhpExamDate(LocalDate.of(2023, 5, 15))
                .ppk(true)
                .remarks("No remarks")
                .build();

        when(employeeService.findEmployeeByName(employeeName)).thenReturn(List.of(employee1));

        // Act & Assert
        mockMvc.perform(get("/api/employee/{employeeName}", employeeName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Jack Smith"));
    }
}