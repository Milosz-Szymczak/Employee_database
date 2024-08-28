package pl.milosz.employees_database.service;

import pl.milosz.employees_database.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    void addEmployee(Employee employee);

    Employee findEmployeeById(Long id);

    void updateEmployee(Long id, Employee employeeEdit);

    void deleteEmployee(Long id);

    List<Employee> findEmployeeByName(String employeeName);
}
