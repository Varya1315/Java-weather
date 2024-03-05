package weather.springwea.controller;



import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weather.springwea.model.Region;
import weather.springwea.service.RegionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/region")
@AllArgsConstructor
public class RegionController {

    private final RegionService service;


    @GetMapping()
    public List<Region> findAllRegion() {
        return service.findAll();
    }


    @PostMapping("/saveRegion")
    public ResponseEntity<Object> saveRegion(@RequestBody Region region) {
        try {
            Optional<Region> savedRegion = service.saveRegion(region);
            return savedRegion.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>("Region already exists", HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
            return new ResponseEntity<>("Updated region: " , HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Region not found", HttpStatus.NOT_FOUND);
        }
    }

}

