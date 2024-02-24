package weather.springwea.service.lmpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weather.springwea.model.Towns;
import weather.springwea.repository.MemoryWeatherDAO;
import weather.springwea.service.WeatherService;

import java.util.List;

@Service
@AllArgsConstructor//внедруние через конструктор
public class MemoryWeatherServicelmpl implements WeatherService {

    private final MemoryWeatherDAO repository;

    @Override
    public List<Towns> findAllTowes() {
        return repository.findAllTowes();
    }

    @Override
    public Towns saveTowns(Towns towns) {
        return repository.saveTowns(towns);
    }

    @Override
    public Towns findByPositionSun(String positionSun) {
        return repository.findByPositionSun(String.valueOf(positionSun));
    }

    @Override
    public String deleteTowns(String positionSun) {
        return repository.deleteTowns(String.valueOf(positionSun));
    }

    @Override
    public String deleteTowns2(String nameTowns) {
        return repository.deleteTowns2(nameTowns);
    }

    @Override
    public Towns findByNameTowns(String nameTowns) {
        return repository.findByNameTown(nameTowns);
    }

}
