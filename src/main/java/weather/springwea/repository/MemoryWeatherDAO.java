package weather.springwea.repository;

import org.springframework.stereotype.Repository;
import weather.springwea.model.Towns;

import java.util.ArrayList;
import java.util.List;

@Repository
//для чего мы делаем константу final
public class MemoryWeatherDAO {
    private final List<Towns> MainTown = new ArrayList<>();

    public List<Towns> findAllTowes() {
        return MainTown;
    }

    public Towns saveTowns(Towns towns) {
        MainTown.add(towns);
        return towns;
    }


    public Towns findByPositionSun(String positionSun) {
        for (Towns temp : MainTown) {
            if (temp.getPositionSun().equals(positionSun)) {
                return temp;
            }
        }
        return null;
    }

    public Towns findByNameTown(String nameTowns) {
        for (Towns temp : MainTown) {
            if (temp.getNameTowns().equals(nameTowns)) {
                return temp;
            }
        }
        return null;
    }

    public void deleteTowns(String positionSun) {

        var towns = findByPositionSun(positionSun);
        if (towns != null)
        {
            MainTown.remove(towns);
        }
    }

    public void deleteTowns2(String nameTowns) {

        var towns = findByNameTown(nameTowns);
        if (towns != null)
        {
            MainTown.remove(towns);
        }
    }
}
