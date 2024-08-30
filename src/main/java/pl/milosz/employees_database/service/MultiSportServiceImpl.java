package pl.milosz.employees_database.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.milosz.employees_database.entity.MultiSport;
import pl.milosz.employees_database.exception.MultiSportNotFoundException;
import pl.milosz.employees_database.repository.MultiSportRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MultiSportServiceImpl implements MultiSportService {
    private final MultiSportRepository multiSportRepository;

    @Override
    public List<MultiSport> getAllMultiSports() {
        log.info("Getting all MultiSports");
        return multiSportRepository.findAll();
    }

    @Override
    @Transactional
    public void addMultiSport(MultiSport multisport) {
        log.info("Adding MultiSport");
        multiSportRepository.save(multisport);
    }

    @Override
    public MultiSport findMultiSportById(Long id) {
        log.info("Getting MultiSport by ID");
        Optional<MultiSport> optionalMultiSport = multiSportRepository.findById(id);
        if (optionalMultiSport.isPresent()) {
            return optionalMultiSport.get();
        }
        else {
            throw new MultiSportNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public void updateMultiSport(Long id, MultiSport multiSportEdit) {
        log.info("Updating MultiSport");
        MultiSport multiSportById = findMultiSportById(id);

        if (multiSportEdit.getPackageName() != null)
            multiSportById.setPackageName(multiSportEdit.getPackageName());
        if (multiSportEdit.getPackageValue() != null)
            multiSportById.setPackageValue(multiSportEdit.getPackageValue());

        multiSportRepository.save(multiSportById);
    }

    @Override
    public void deleteMultiSport(Long id) {
        log.info("Deleting MultiSport");
        multiSportRepository.deleteById(id);
    }

}
