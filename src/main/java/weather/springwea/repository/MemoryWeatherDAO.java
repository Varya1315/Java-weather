package weather.springwea.repository;

import org.springframework.stereotype.Repository;
import weather.springwea.model.Towns;

import java.util.ArrayList;
import java.util.List;

@Repository
//для чего мы делаем константу final
public class MemoryWeatherDAO {
    private final List<Towns> TOWN = new ArrayList<>();

    public List<Towns> findAllTowes() {
        return TOWN;
    }

    public Towns saveTowns(Towns towns) {
        TOWN.add(towns);
        return towns;
    }


    public Towns findByPositionSun(String positionSun) {
        for (Towns temp : TOWN) {
            if (temp.getPositionSun().equals(positionSun)) {
                return temp;
            }
        }
        return null;
    }

    public Towns FindByNameTowns (String nameTowns) {
        for (Towns temp : TOWN) {
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
            TOWN.remove(towns);
        }
    }

    public void deleteTowns2(String nameTowns) {

        var towns = FindByNameTowns(nameTowns);
        if (towns != null)
        {
            TOWN.remove(towns);
        }
    }
}
