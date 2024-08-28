package pl.milosz.employees_database.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.employees_database.entity.MultiSport;
import pl.milosz.employees_database.exception.MultiSportNotFoundException;
import pl.milosz.employees_database.repository.MultiSportRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MultiSportServiceImplTest {

    @Mock
    private MultiSportRepository multiSportRepository;

    @InjectMocks
    private MultiSportServiceImpl multiSportService;

    private MultiSport multiSport1;
    private MultiSport multiSport2;

    @BeforeEach
    void setUp() {
        multiSport1 = MultiSport.builder()
                .id(1L)
                .packageName("MultiSport Plan A")
                .packageValue(100.0)
                .build();

        multiSport2 = MultiSport.builder()
                .id(2L)
                .packageName("MultiSport Plan B")
                .packageValue(200.0)
                .build();
    }

    @Test
    void getAllMultiSports() {
        // Arrange
        List<MultiSport> multiSports = Arrays.asList(multiSport1, multiSport2);
        when(multiSportRepository.findAll()).thenReturn(multiSports);

        // Act
        List<MultiSport> result = multiSportService.getAllMultiSports();

        // Assert
        assertEquals(2, result.size());
        assertEquals("MultiSport Plan A", result.get(0).getPackageName());
        assertEquals("MultiSport Plan B", result.get(1).getPackageName());
    }

    @Test
    void addMultiSport() {
        // Arrange
        when(multiSportRepository.save(any(MultiSport.class))).thenReturn(multiSport1);

        // Act
        multiSportService.addMultiSport(multiSport1);

        // Assert
        verify(multiSportRepository).save(multiSport1);
    }

    @Test
    void findMultiSportById_ExistingId() {
        // Arrange
        when(multiSportRepository.findById(1L)).thenReturn(Optional.of(multiSport1));

        // Act
        MultiSport result = multiSportService.findMultiSportById(1L);

        // Assert
        assertEquals("MultiSport Plan A", result.getPackageName());
    }

    @Test
    void findMultiSportById_NonExistingId() {
        // Arrange
        when(multiSportRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MultiSportNotFoundException.class, () -> multiSportService.findMultiSportById(1L));
    }

    @Test
    void updateMultiSport() {
        // Arrange
        MultiSport updatedMultiSport = MultiSport.builder()
                .packageName("Updated Plan")
                .packageValue(150.0)
                .build();

        when(multiSportRepository.findById(1L)).thenReturn(Optional.of(multiSport1));
        when(multiSportRepository.save(any(MultiSport.class))).thenReturn(updatedMultiSport);

        // Act
        multiSportService.updateMultiSport(1L, updatedMultiSport);

        // Assert
        verify(multiSportRepository).save(multiSport1);
        assertEquals("Updated Plan", multiSport1.getPackageName());
        assertEquals(150.0, multiSport1.getPackageValue());
    }
}