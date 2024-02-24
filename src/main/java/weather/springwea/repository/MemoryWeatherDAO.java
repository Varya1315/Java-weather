package weather.springwea.repository;

import org.springframework.stereotype.Repository;
import weather.springwea.model.Towns;

import java.util.ArrayList;
import java.util.List;

@Repository
//для чего мы делаем константу final
public class MemoryWeatherDAO {
    private final List<Towns> mainTown = new ArrayList<>();

    public List<Towns> findAllTowes() {
        return mainTown;
    }

    public Towns saveTowns(Towns towns) {
        mainTown.add(towns);
        return towns;
    }


    public Towns findByPositionSun(String positionSun) {
        for (Towns temp : mainTown) {
            if (temp.getPositionSun().equals(positionSun)) {
                return temp;
            }
        }
        return null;
    }

    public Towns findByNameTown(String nameTowns) {
        for (Towns temp : mainTown) {
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
            mainTown.remove(towns);
        }
    }

    public void deleteTowns2(String nameTowns) {

        var towns = findByNameTown(nameTowns);
        if (towns != null)
        {
            mainTown.remove(towns);
        }
    }
}
