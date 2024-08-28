package pl.milosz.employees_database.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.employees_database.entity.Employee;
import pl.milosz.employees_database.exception.EmployeeNotFoundException;
import pl.milosz.employees_database.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        employee1 = Employee.builder()
                .id(1L)
                .fullName("Jack Smith")
                .lastMedicalExamDate(LocalDate.of(2023, 5, 1))
                .bhpExamDate(LocalDate.of(2023, 5, 15))
                .ppk(true)
                .remarks("No remarks")
                .build();

        employee2 = Employee.builder()
                .id(2L)
                .fullName("Jack Smith")
                .lastMedicalExamDate(LocalDate.of(2023, 4, 20))
                .bhpExamDate(LocalDate.of(2023, 4, 25))
                .ppk(false)
                .remarks("Pending review")
                .build();
    }

    @Test
    void getAllEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Jack Smith", result.get(0).getFullName());
        assertEquals("Jack Smith", result.get(1).getFullName());
    }

    @Test
    void addEmployee() {
        // Arrange
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        // Act
        employeeService.addEmployee(employee1);

        // Assert
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void findEmployeeById_ExistingId() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        // Act
        Employee result = employeeService.findEmployeeById(1L);

        // Assert
        assertEquals("Jack Smith", result.getFullName());
    }

    @Test
    void findEmployeeById_NonExistingId() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeById(1L));
    }

    @Test
    void updateEmployee() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        Employee updatedEmployee = Employee.builder()
                .fullName("Jack Updated")
                .build();

        // Act
        employeeService.updateEmployee(1L, updatedEmployee);

        // Assert
        verify(employeeRepository, times(1)).save(employee1);
        assertEquals("Jack Updated", employee1.getFullName());
    }

    @Test
    void deleteEmployee() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        doNothing().when(employeeRepository).delete(employee1);

        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).delete(employee1);
    }

    @Test
    void findEmployeeByName() {
        // Arrange
        when(employeeRepository.findByFullName("Jack Smith")).thenReturn(List.of(employee1));

        // Act
        List<Employee> result = employeeService.findEmployeeByName("Jack Smith");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Jack Smith", result.get(0).getFullName());
    }
}