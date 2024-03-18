package weather.springwea.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestParam;
import weather.springwea.cache.Cache;
import weather.springwea.model.Towns;
import weather.springwea.repository.TownRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class TownService {
    private final Cache<String, Towns> townCache;
    private final TownRepository repository;
    private static final Logger log = LoggerFactory.getLogger(TownService.class);

    public List<Towns> findAllTowns() {
        List<Towns> cachedTowns = new ArrayList<>();
        for (Map.Entry<String, Towns> entry : townCache.getNativeCache()) {
            cachedTowns.add(entry.getValue());
        }
        if (!cachedTowns.isEmpty()) {
            log.info("Found {} towns in cache", cachedTowns.size());

            return cachedTowns;
        }

        List<Towns> towns = repository.findAll();
        for (Towns town : towns) {
            townCache.put(town.getNameTowns(), town);
        }
        log.info("Fetched {} towns from repository and saved to cache", towns.size());

        return towns;
    }

    public Towns saveTowns(Towns towns) {
        townCache.put(towns.getNameTowns(), towns);
        log.info("Town '{}' saved to cache", towns.getNameTowns());

        return repository.save(towns);
    }

    public String deleteTownsByNameTowns(String nameTowns) {
        if (townCache.get(nameTowns) != null) {
            townCache.remove(nameTowns);
            log.info("Town removed from cache");
        }
        Towns townToDelete = repository.findByNameTowns(nameTowns);
        if (townToDelete != null) {
            repository.delete(townToDelete);
            log.info("Town deleted from repository");
            return "Delete";
        } else {
            log.info("Town not found, deletion failed");
            return null;
        }
    }


    public Towns findByNameTowns(String nameTowns) {
        Towns cachedTown = townCache.get(nameTowns);
        if (cachedTown != null) {
            return cachedTown;
        }
        Towns town = repository.findByNameTowns(nameTowns);
        if (town != null) {
            townCache.put(nameTowns, town);
            log.info("Town fetched from repository and cached");
        } else {
            log.info("Town not found in cache or repository");
        }
        return town;
    }


    public Towns updateTownByName(@RequestParam String nameTowns, @RequestParam String coordinates) {
        Towns existingTown = findByNameTowns(nameTowns);
        if (existingTown != null) {
            existingTown.setCoordinates(coordinates);
            townCache.put(nameTowns, existingTown);
            log.info("Town updated in cache");
            return repository.save(existingTown);
        } else {
            log.warn("Town update operation failed due to not found in cache or repository");
            return null;
        }
    }
}


