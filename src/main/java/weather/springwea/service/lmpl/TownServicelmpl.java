/*package weather.springwea.service.lmpl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import weather.springwea.model.Towns;
import weather.springwea.repository.TownRepository;
import weather.springwea.service.TownService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class TownServicelmpl implements TownService {
    private final TownRepository repository;


    @Override
    public List<Towns> findAllTowns() {
        return repository.findAll();
    }

    @Override
    public Towns saveTowns(Towns towns) {
        return repository.save(towns);
    }

    @Override
    public Towns findByPositionSun(String positionSun) {
        return repository.findByPositionSun(positionSun);
    }

    @Override
    public void deleteTownsByPosition(String positionSun) {
         repository.deleteByPositionSun(positionSun);
    }

    @Override
    public void deleteTownsByName(String nameTowns) {
        repository.deleteTownsByNameTowns(nameTowns);
    }

    @Override
    public Towns findByNameTowns(String nameTowns) {
        return repository.findByNameTowns(nameTowns);
    }
}
*/