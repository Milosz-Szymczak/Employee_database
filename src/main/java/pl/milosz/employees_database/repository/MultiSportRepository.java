package pl.milosz.employees_database.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.employees_database.entity.MultiSport;

@Repository
public interface MultiSportRepository extends ListCrudRepository<MultiSport, Long> {
}
