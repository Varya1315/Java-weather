package weather.springwea.repository;

import org.springframework.stereotype.Repository;
import weather.springwea.model.Town;

import java.util.ArrayList;
import java.util.List;

@Repository

public class MemoryWeatherDAO {
    private final List<Town> mainTown = new ArrayList<>();

    public List<Town> findAllTowes() {
        return mainTown;
    }

    public Town saveTowns(Town town) {
        mainTown.add(town);
        return town;
    }


    public Town findByPositionSun(String positionSun) {
        for (Town temp : mainTown) {
            if (temp.getPositionSun().equals(positionSun)) {
                return temp;
            }
        }
        return null;
    }

    public Town findByNameTown(String nameTowns) {
        for (Town temp : mainTown) {
            if (temp.getNameTowns().equals(nameTowns)) {
                return temp;
            }
        }
        return null;
    }

    public String deleteTownsByPosition(String positionSun) {

        var towns = findByPositionSun(positionSun);
        if (towns != null)
        {
            mainTown.remove(towns);
        }
        return null;
    }

    public String deleteTownsByName(String nameTowns) {

        var towns = findByNameTown(nameTowns);
        if (towns != null)
        {
            mainTown.remove(towns);
        }
        return null;
    }
}
