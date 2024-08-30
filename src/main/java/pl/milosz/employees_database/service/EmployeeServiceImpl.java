package pl.milosz.employees_database.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.milosz.employees_database.entity.Employee;
import pl.milosz.employees_database.exception.EmployeeNotFoundException;
import pl.milosz.employees_database.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Getting all employees");
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public void addEmployee(Employee employee) {
        log.info("Adding employee: {}", employee);
        employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployeeById(Long id) {
        log.info("Finding employee by id: {}", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        }
        else {
            throw new EmployeeNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public void updateEmployee(Long id, Employee employeeEdit) {
        log.info("Updating employee: {}", employeeEdit);
        Employee employeeById = findEmployeeById(id);

        if (employeeEdit.getFullName() != null) {
            employeeById.setFullName(employeeEdit.getFullName());
        }
        if (employeeEdit.getLastMedicalExamDate() != null) {
            employeeById.setLastMedicalExamDate(employeeEdit.getLastMedicalExamDate());
        }
        if (employeeEdit.getBhpExamDate() != null) {
            employeeById.setBhpExamDate(employeeEdit.getBhpExamDate());
        }
        if (employeeEdit.getPpk() != null) {
            employeeById.setPpk(employeeEdit.getPpk());
        }

        employeeById.setInsurance(employeeEdit.getInsurance());
        employeeById.setMultisport(employeeEdit.getMultisport());
        employeeById.setRemarks(employeeEdit.getRemarks());

        employeeRepository.save(employeeById);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        log.info("Deleting employee: {}", id);
        Employee employeeById = findEmployeeById(id);
        employeeRepository.delete(employeeById);
    }

    @Override
    public List<Employee> findEmployeeByName(String employeeName) {
        log.info("Finding employee by name: {}", employeeName);
        return employeeRepository.findByFullName(employeeName);
    }
}
