package weather.springwea.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import weather.springwea.model.Towns;
import weather.springwea.service.WeatherService;

import java.util.List;

@RestController //requstbody -show in web
@RequestMapping("/api/v1/weather")
@AllArgsConstructor

public final class WeatherController {
    private WeatherService service;

    @GetMapping()
    public List<Towns> findAllTowes() {
        return service.findAllTowes();

//saveTowns  findByPositionSun deleteTowns findByNameTowns
    }
    @PostMapping("save_towns")
public  Towns saveTowns (@RequestBody Towns towns){
         return service.saveTowns(towns);
}

@GetMapping("findposition/{positionSun}")
public  Towns findByPositionSun (@PathVariable String positionSun){
        return  service.findByPositionSun(positionSun);

}
    @GetMapping("findname/{nameTowns}")
    public  Towns findByNameTowns (@PathVariable String nameTowns){
        return  service.findByNameTowns(nameTowns);

    }

    @DeleteMapping("delete_byname/{nameTowns}")
public String deleteTowns (@PathVariable String nameTowns){

        service.deleteTowns2(nameTowns);
        return "Towns was successfully delete";
}

    @DeleteMapping("delete_byposition/{positionSun}")
    public String deleteTowns2 (@PathVariable String positionSun){

        service.deleteTowns(positionSun);
        return "Towns was successfully delete";
    }

}
