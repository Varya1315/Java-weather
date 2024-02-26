package weather.springwea.service;

import org.springframework.stereotype.Service;
import weather.springwea.model.Town;

import java.util.List;
@Service
public interface WeatherService {

     List<Town> findAllTowes();
     Town saveTowns(Town town);
     Town findByPositionSun(String positionSun);
     String deleteTownsByPosition(String nameTowns);

     String deleteTownsByName(String positionSun);

     Town findByNameTowns(String nameTowns);
}
