package weather.springwea.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import weather.springwea.model.Towns;
import weather.springwea.service.TownService;

import java.util.List;

@RestController //requstbody -show in web
@RequestMapping("/api/v1/weather")
@AllArgsConstructor
public final class TownController {

    private final TownService service;

    /**
     * Retrieve all towns.
     *
     * @return List of all towns
     */
    @GetMapping()
    public List<Towns> findAllTowns() {
        return service.findAllTowns();
    }

    /**
     * Save a town.
     *
     * @param towns The town to save
     * @return The saved town
     */
    @PostMapping("saveTowns")
    public Towns saveTowns(
            @RequestBody final Towns towns) {
        return service.saveTowns(towns);
    }

    /**
     * Find town by name.
     *
     * @param nameTowns The name of the town to find
     * @return The found town
     */
    @GetMapping("findName")
    public Towns findByNameTowns(
            @RequestParam final String nameTowns) {
        return service.findByNameTowns(nameTowns);
    }

    /**
     * Delete town by name.
     *
     * @param nameTowns The name of the town to delete
     * @return ResponseEntity indicating success or failure
     */
    @DeleteMapping("deleteByName")
    public ResponseEntity<String> deleteRegionByName(
            @RequestParam final String nameTowns) {
        String result = service.deleteTownsByNameTowns(nameTowns);
        if ("Delete".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Region not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update town by name.
     *
     * @param nameTowns   The name of the town to update
     * @param coordinates The new coordinates of the town
     * @return The updated town
     */
    @PutMapping("updateByName")
    public Towns updateTownByName(@RequestParam final String nameTowns,
                                  @RequestParam final String coordinates) {
        return service.updateTownByName(nameTowns, coordinates);
    }
}
