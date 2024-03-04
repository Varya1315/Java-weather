/*package weather.springwea.repository;

import org.springframework.stereotype.Repository;
import weather.springwea.model.Towns;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryTownDAO {
    private final List<Towns> mainTowns = new ArrayList<>();

    public List<Towns> findAllTowns() {
        return mainTowns;
    }

    public Towns saveTowns(Towns towns) {
        mainTowns.add(towns);
        return towns;
    }


    public Towns findByPositionSun(String positionSun) {
        for (Towns temp : mainTowns) {
            if (temp.getPositionSun().equals(positionSun)) {
                return temp;
            }
        }
        return null;
    }

    public Towns findByNameTown(String nameTowns) {
        for (Towns temp : mainTowns) {
            if (temp.getNameTowns().equals(nameTowns)) {
                return temp;
            }
        }
        return null;
    }

    public void deleteTownsByName(String nameTowns) {

        var towns = findByNameTown(nameTowns);
        if (towns != null)
        {
            mainTowns.remove(towns);
        }
    }

    public void deleteByPosition(String positionSun) {
        var towns = findByPositionSun(positionSun);
        if (towns != null)
        {
            mainTowns.remove(towns);
        }

    }
}
*/