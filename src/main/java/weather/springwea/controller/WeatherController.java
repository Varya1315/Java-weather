package weather.springwea.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import weather.springwea.model.Town;
import weather.springwea.service.WeatherService;

import java.util.List;

@RestController //requstbody -show in web
@RequestMapping("/api/v1/weather")
@AllArgsConstructor

public final class WeatherController {
    private WeatherService service;

    @GetMapping()
    public List<Town> findAllTowes() {
        return service.findAllTowes();

//saveTowns  findByPositionSun deleteTowns findByNameTowns
    }

    @PostMapping("save_towns")
    public Town saveTowns(@RequestBody Town town) {
        return service.saveTowns(town);
    }

    @GetMapping("findposition")
    public Town findByPositionSun(@RequestParam String positionSun) {
        return service.findByPositionSun(positionSun);

    }

    @GetMapping("findname")
    public Town findByNameTowns(@RequestParam String nameTowns) {
        return service.findByNameTowns(nameTowns);

    }

    @DeleteMapping("delete_byname")
    public String deleteTowns(@RequestParam String nameTowns) {

        service.deleteTownsByName(nameTowns);
        return "Towns was successfully delete";
    }

    @DeleteMapping("delete_byposition")
    public String deleteTownsByPosition(@RequestParam String positionSun) {

        service.deleteTownsByPosition(positionSun);
        return "Towns was successfully delete";
    }

}
