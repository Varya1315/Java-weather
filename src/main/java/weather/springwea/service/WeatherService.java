package weather.springwea.service;

import weather.springwea.model.Towns;

import java.util.List;
//почему не нужна аннотация сервис
public interface WeatherService {

     List<Towns> findAllTowes();
     Towns saveTowns(Towns towns);
     Towns findByPositionSun(String positionSun);
     String deleteTownsByPosition(String nameTowns);

     String deleteTownsByName(String positionSun);

     Towns findByNameTowns(String nameTowns);
}
