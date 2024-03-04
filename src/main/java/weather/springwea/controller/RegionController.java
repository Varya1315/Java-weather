package weather.springwea.controller;



import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weather.springwea.model.Region;
import weather.springwea.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
@AllArgsConstructor
public class RegionController {

    private final RegionService service;


    @GetMapping()
    public List<Region> findAllRegion() {
        return service.findAll();
    }


    @PostMapping("saveRegion")
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
}

