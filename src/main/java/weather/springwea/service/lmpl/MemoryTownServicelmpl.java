/*package weather.springwea.service.lmpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weather.springwea.model.Towns;
import weather.springwea.repository.MemoryTownDAO;
import weather.springwea.service.TownService;

import java.util.List;

@Service
@AllArgsConstructor
public class MemoryTownServicelmpl implements TownService {

    private final MemoryTownDAO repository;

    @Override
    public List<Towns> findAllTowns() {
        return repository.findAllTowns();
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
    public void deleteTownsByPosition(String positionSun) {
        repository.deleteByPosition(positionSun);
    }

    @Override
    public void deleteTownsByName(String nameTowns) {
      repository.deleteTownsByName(nameTowns);
    }


    @Override
    public Towns findByNameTowns(String nameTowns) {
        return repository.findByNameTown(nameTowns);
    }

}*/
