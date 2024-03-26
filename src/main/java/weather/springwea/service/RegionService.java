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
    private static final Logger LOG =
            LoggerFactory.getLogger(RegionService.class);
    /**
     * Получает список всех регионов.
     *
     * @return Список всех регионов.
     */
    public List<Region> findAll() {
        List<Region> regions = repository.findAll();
        for (Region region : regions) {
            regionCache.put(region.getName(), region);
        }
        return regions;
    }

    /**
     * Находит и возвращает список регионов с
     * более чем указанным количеством городов.
     *
     * @param townCount Количество городов.
     * @return Список регионов с более
     * чем указанным количеством городов.
     */
    public List<Region> findRegionsWithMoreTowns(
            final int townCount) {
        List<Region> cachedRegions = new ArrayList<>();
        for (Map.Entry<String, Region> entry : regionCache.getNativeCache()) {
            Region region = entry.getValue();
            if (region.getTowns().size() > townCount) {
                cachedRegions.add(region);
                LOG.info("Region '{}' found in cache"
                       + " with more than {} towns",
                        region.getName(), townCount);
            }
        }
        if (!cachedRegions.isEmpty()) {
            return cachedRegions;
        }
        List<Region> regions = repository.findRegionsWithMoreTowns(townCount);
        for (Region region : regions) {
            regionCache.put(region.getName(), region);
            LOG.info("Region '{}' retrieved from repository"
                    + "and added to cache with more than {} towns",
                    region.getName(), townCount);
        }
        return regions;
    }
    /**
     * Возвращает список городов по указанному региону и интересному факту.
     *
     * @param regionName      Название региона.
     * @param interestingFact Интересный факт о городах.
     * @return Список городов, удовлетворяющих указанному
     * региону и интересному факту.
     */
    public List<Towns> findTownsByRegionAndInterestingFact(
            final String regionName,
            final String interestingFact) {
        Region cachedRegion = regionCache.get(regionName);
        if (cachedRegion != null) {
            LOG.info("Region found in cache."
                    + " Retrieving towns by interesting fact");
            return repository.findTownsByRegionAndInterestingFact(
                    regionName, interestingFact);
        }
        LOG.info("Region not found in cache."
                + "Retrieving towns by interesting fact from repository");
        List<Towns> towns = repository.findTownsByRegionAndInterestingFact(
                regionName, interestingFact);
        if (!towns.isEmpty()) {
            Region region = repository.findByName(regionName);
            regionCache.put(regionName, region);
            LOG.info("Region retrieved from repository and added to cache");
        }
        return towns;
    }

    /**
     * Сохраняет новый регион.
     *
     * @param newRegion Новый регион для сохранения.
     * @return Сохраненный регион.
     * @throws IllegalArgumentException
     * Если регион с таким именем уже существует.
     */
    public Region saveRegion(
            final Region newRegion) {
        Region existingRegion = repository.findByName(newRegion.getName());
        if (existingRegion != null) {
            throw new IllegalArgumentException("Region with name '"
                    + newRegion.getName() + "' already exists");
        }
        repos.saveAll(newRegion.getTowns());
        Region savedRegion = repository.save(newRegion);
        regionCache.put(savedRegion.getName(), savedRegion);
        LOG.info("Region '{}' saved and added to cache", savedRegion.getName());
        return savedRegion;
    }

    /**
     * Находит регион по его имени.
     *
     * @param name Имя региона для поиска.
     * @return Регион с указанным именем,
     * если найден; в противном случае возвращает null.
     */
    public Region findByNameRegion(
            final String name) {
        Region cachedRegion = regionCache.get(name);
        if (cachedRegion != null) {
            LOG.info("Region found in cache");
            return cachedRegion;
        } else {
            Region region = repository.findByName(name);
            if (region != null) {
                regionCache.put(name, region);
                LOG.info("Region found in database and added to cache");
            } else {
                LOG.info("Region not found");
            }
            return region;
        }
    }
    /**
     * Удаляет регион по его имени.
     *
     * @param name Имя региона для удаления.
     * @return Сообщение о результате операции удаления.
     */
    public String deleteRegionByName(
            final String name) {
        Region regionToDelete = regionCache.get(name);

        if (regionToDelete == null) {
            regionToDelete = repository.findByName(name);
        }
        if (regionToDelete != null) {
            List<Towns> temp = regionToDelete.getTowns();
            repos.deleteAll(temp);
            repository.delete(regionToDelete);
            regionCache.remove(name);
            return "Delete";
        } else {
            return "Region not found";
        }
    }

    /**
     * Обновляет название региона с указанным именем.
     *
     * @param name Имя региона для обновления.
     * @param newName Новое имя региона.
     * @return Обновленный объект региона или null,
     * если регион с указанным именем не найден.
     */
    public Region updateRegionByName(
            final String name,
            final String newName) {
        Region existingRegion = regionCache.get(name);

        if (existingRegion == null) {
            existingRegion = repository.findByName(name);
            if (existingRegion != null) {
                LOG.info("Region '{}' retrieved from database", name);
            }
        } else {
            LOG.info("Region '{}' retrieved from cache", name);
        }

        if (existingRegion != null) {
            existingRegion.setName(newName);
            repository.save(existingRegion);
            regionCache.put(newName, existingRegion);
            return existingRegion;
        } else {
            return null;
        }
    }


}
