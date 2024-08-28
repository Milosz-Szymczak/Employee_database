package pl.milosz.employees_database.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.milosz.employees_database.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends ListCrudRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE LOWER(e.fullName) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Employee> findByFullName(@Param("keyword") String keyword);
}
