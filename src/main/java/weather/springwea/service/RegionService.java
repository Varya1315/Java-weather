package weather.springwea.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import weather.springwea.cache.Cache;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;
import weather.springwea.repository.RegionRepository;
import weather.springwea.repository.TownRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class RegionService {
    private final RegionRepository repository;
    private final TownRepository repos;
    private final Cache<String, Region> regionCache;
    private static final Logger log = LoggerFactory.getLogger(RegionService.class);


    public List<Region> findAll() {
        List<Region> regions = repository.findAll();
        for (Region region : regions) {
            regionCache.put(region.getName(), region);
            log.info("Added region '{}' to cache", region.getName());

        }
        return regions;
    }

    public List<Region> findRegionsWithMoreTowns(int townCount) {
        List<Region> cachedRegions = new ArrayList<>();
        for (Map.Entry<String, Region> entry : regionCache.getNativeCache()) {
            Region region = entry.getValue();
            if (region.getTowns().size() > townCount) {
                cachedRegions.add(region);
                log.info("Region '{}' found in cache with more than {} towns", region.getName(), townCount);
            }
        }
        if (!cachedRegions.isEmpty()) {
            return cachedRegions;
        }
        List<Region> regions = repository.findRegionsWithMoreTowns(townCount);
        for (Region region : regions) {
            regionCache.put(region.getName(), region);
            log.info("Region '{}' retrieved from repository and added to cache with more than {} towns", region.getName(), townCount);
        }
        return regions;
    }

    public List<Towns> findTownsByRegionAndInterestingFact(String regionName, String interestingFact) {
        Region cachedRegion = regionCache.get(regionName);
        if (cachedRegion != null) {
            log.info("Region found in cache. Retrieving towns by interesting fact");
            return repository.findTownsByRegionAndInterestingFact(regionName, interestingFact);
        }
        log.info("Region not found in cache. Retrieving towns by interesting fact from repository");
        List<Towns> towns = repository.findTownsByRegionAndInterestingFact(regionName, interestingFact);
        if (!towns.isEmpty()) {
            Region region = repository.findByName(regionName);
            regionCache.put(regionName, region);
            log.info("Region retrieved from repository and added to cache");
        }
        return towns;
    }


    public Region saveRegion(Region newRegion) {
        Region existingRegion = repository.findByName(newRegion.getName());
        if (existingRegion != null) {
            throw new IllegalArgumentException("Region with name '" + newRegion.getName() + "' already exists");
        }
        repos.saveAll(newRegion.getTowns());
        Region savedRegion = repository.save(newRegion);
        regionCache.put(savedRegion.getName(), savedRegion);
        log.info("Region '{}' saved and added to cache", savedRegion.getName());
        return savedRegion;
    }


    public Region findByNameRegion(String name) {
        Region cachedRegion = regionCache.get(name);
        if (cachedRegion != null) {
            log.info("Region found in cache");
            return cachedRegion;
        } else {
            Region region = repository.findByName(name);
            if (region != null) {
                regionCache.put(name, region); // Save the query result to the cache
                log.info("Region found in database and added to cache");
            } else {
                log.info("Region not found");
            }
            return region;
        }
    }

    public String deleteRegionByName(String name) {
        Region regionToDelete = regionCache.get(name);

        if (regionToDelete == null) {
            regionToDelete = repository.findByName(name);
        }
        if (regionToDelete != null) {
            List<Towns> temp = regionToDelete.getTowns();
            repos.deleteAll(temp);
            repository.delete(regionToDelete);
            log.info("Region deleted successfully");
            regionCache.remove(name);
            return "Delete";
        } else {
            log.info("Region not found for deletion");
            return "Region not found";
        }

    }

    public Region updateRegionByName(String name, String newName) {
        Region existingRegion = repository.findByName(name);
        if (existingRegion != null) {
            existingRegion.setName(newName);
            repository.save(existingRegion);
            log.info("Region updated with new name. Cache updated.");
            regionCache.put(newName, existingRegion);
            return existingRegion;
        } else {
            log.warn("Region not found, update operation failed");
            return null;
        }
    }

}


