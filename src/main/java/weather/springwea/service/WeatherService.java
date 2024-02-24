package weather.springwea.service;

import weather.springwea.model.Towns;

import java.util.List;
//почему не нужна аннотация сервис
public interface WeatherService {

     List<Towns> findAllTowes();
     Towns saveTowns(Towns towns);
     Towns findByPositionSun(String positionSun);
     void deleteTowns(String nameTowns);

     void deleteTowns2(String positionSun);

     Towns findByNameTowns(String nameTowns);
}
