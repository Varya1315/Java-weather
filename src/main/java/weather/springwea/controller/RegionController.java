package weather.springwea.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;
import weather.springwea.service.RegionService;

import java.util.List;

import org.slf4j.*;


@RestController
@RequestMapping("/api/v1/region")
@AllArgsConstructor
public class RegionController {


    private static final Logger log = LoggerFactory.getLogger(RegionController.class);
    private final RegionService service;

    @GetMapping()
    public List<Region> findAllRegion() {
        return service.findAll();
    }


    @PostMapping("/saveRegion")
    public Region saveRegion(@RequestBody Region region) {
        return service.saveRegion(region);

    }


    @GetMapping("findName")
    public Region findByNameRegion(@RequestParam String name) {
        return service.findByNameRegion(name);

    }

    @DeleteMapping("deleteByName")
    public ResponseEntity<String> deleteRegionByName(@RequestParam String name) {
        String result = service.deleteRegionByName(name);
        if ("Delete".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Region not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("updateByName")
    public ResponseEntity<String> updateRegionByName(@RequestParam String name, @RequestParam String newName) {
        Region updatedRegion = service.updateRegionByName(name, newName);
        if (updatedRegion != null) {
            return new ResponseEntity<>("Updated region: ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Region not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/town&fact")
    public ResponseEntity<List<Towns>> getUsefulData(
            @RequestParam("regionName") String regionName,
            @RequestParam("interestingFact") String interestingFact) {

        List<Towns> towns = service.findTownsByRegionAndInterestingFact(regionName, interestingFact);

        if (!towns.isEmpty()) {
            return ResponseEntity.ok(towns);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/regions")
    public ResponseEntity<List<Region>> getRegionsWithMoreTowns(@RequestParam("townCount") int townCount) {
        try {
            List<Region> result = service.findRegionsWithMoreTowns(townCount);
            if (result != null && !result.isEmpty()) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
