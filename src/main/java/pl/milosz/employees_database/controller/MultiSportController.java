package pl.milosz.employees_database.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.milosz.employees_database.entity.MultiSport;
import pl.milosz.employees_database.service.MultiSportService;

import java.util.List;

@RestController
@RequestMapping("/api/multisport")
@RequiredArgsConstructor
public class MultiSportController {
    private final MultiSportService multisportService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MultiSport> getAllMultiSports() {
        return multisportService.getAllMultiSports();
    }

    @GetMapping("id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MultiSport getMultiSportById(@PathVariable("id") Long id) {
        return multisportService.findMultiSportById(id);
    }

    @PostMapping("/addMultiSport")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMultiSport(@RequestBody MultiSport multisport) {
        multisportService.addMultiSport(multisport);
    }

    @PatchMapping("/updateMultiSport/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMultiSport(@PathVariable Long id, @RequestBody MultiSport multiSportEdit) {
        multisportService.updateMultiSport(id, multiSportEdit);
    }

    @DeleteMapping("/deleteMultiSport/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMultiSport(@PathVariable Long id) {
        multisportService.deleteMultiSport(id);
    }

}
