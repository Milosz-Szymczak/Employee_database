package pl.milosz.employees_database.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.employees_database.entity.Insurance;

@Repository
public interface InsuranceRepository  extends ListCrudRepository<Insurance, Long> {
}
