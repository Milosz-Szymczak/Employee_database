package pl.milosz.employees_database.service;

import pl.milosz.employees_database.entity.MultiSport;

import java.util.List;
import java.util.Optional;

public interface MultiSportService {
    List<MultiSport> getAllMultiSports();

    void addMultiSport(MultiSport multisport);

    MultiSport findMultiSportById(Long id);

    void updateMultiSport(Long id, MultiSport multiSportEdit);

    void deleteMultiSport(Long id);
}
