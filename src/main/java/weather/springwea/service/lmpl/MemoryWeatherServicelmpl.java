package weather.springwea.service.lmpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weather.springwea.model.Town;
import weather.springwea.repository.MemoryWeatherDAO;
import weather.springwea.service.WeatherService;

import java.util.List;

@Service
@AllArgsConstructor
public class MemoryWeatherServicelmpl implements WeatherService {

    private final MemoryWeatherDAO repository;

    @Override
    public List<Town> findAllTowes() {
        return repository.findAllTowes();
    }

    @Override
    public Town saveTowns(Town town) {
        return repository.saveTowns(town);
    }

    @Override
    public Town findByPositionSun(String positionSun) {
        return repository.findByPositionSun(String.valueOf(positionSun));
    }

    @Override
    public String deleteTownsByPosition(String positionSun) {
        return repository.deleteTownsByPosition(String.valueOf(positionSun));
    }

    @Override
    public String deleteTownsByName(String nameTowns) {
        return repository.deleteTownsByName(nameTowns);
    }

    @Override
    public Town findByNameTowns(String nameTowns) {
        return repository.findByNameTown(nameTowns);
    }

}
