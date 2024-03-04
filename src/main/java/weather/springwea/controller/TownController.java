package weather.springwea.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weather.springwea.model.Towns;
import weather.springwea.service.TownService;

import java.util.List;

@RestController //requstbody -show in web
@RequestMapping("/api/v1/weather")
@AllArgsConstructor
public final class TownController {
    private TownService service;

    @GetMapping()
    public List<Towns> findAllTowns() {
        return service.findAllTowns();
    }

    @PostMapping("saveTowns")
    public Towns saveTowns(@RequestBody Towns towns) {
        return service.saveTowns(towns);
    }


    @GetMapping("findName")
    public Towns findByNameTowns(@RequestParam String nameTowns) {
        return service.findByNameTowns(nameTowns);

    }


    @DeleteMapping("deleteByName")
    public ResponseEntity<String> deleteRegionByName(@RequestParam String nameTowns) {
        String result = service.deleteTownsByNameTowns(nameTowns);
        if ("Delete".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Region not found", HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("updateByName")
    public Towns updateTownByName(@RequestParam String nameTowns, @RequestParam String coordinates) {
        return service.updateTownByName(nameTowns, coordinates);
    }

//localhost:8080/api/v1/weather/updateByName?nameTowns=Minsk&coordinates=53.9,27.5667
}
