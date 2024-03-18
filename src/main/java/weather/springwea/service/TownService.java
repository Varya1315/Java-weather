package weather.springwea.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import weather.springwea.cache.Cache;
import weather.springwea.controller.RegionController;
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
     private static final Logger log = LoggerFactory.getLogger(RegionController.class);

     public List<Towns> findAllTowns() {
          // Пробуем сначала получить данные из кэша
          List<Towns> cachedTowns = new ArrayList<>();
          for (Map.Entry<String, Towns> entry : townCache.getNativeCache()) {
               cachedTowns.add(entry.getValue());
          }
          if (!cachedTowns.isEmpty()) {
               log.info("Found {} towns in cache", cachedTowns.size());

               return cachedTowns;
          }

          // Если не найдено в кэше, получаем из репозитория и сохраняем в кэш
          List<Towns> towns = repository.findAll();
          for (Towns town : towns) {
               townCache.put(town.getNameTowns(), town);
          }
          log.info("Fetched {} towns from repository and saved to cache", towns.size());

          return towns;
     }

     public Towns saveTowns(Towns towns) {
          // Сохраняем в кэш перед сохранением в репозитории
          townCache.put(towns.getNameTowns(), towns);
          log.info("Town '{}' saved to cache", towns.getNameTowns());

          return repository.save(towns);
     }

     public String deleteTownsByNameTowns(String nameTowns) {
          // Пробуем сначала удалить из кэша
          if (townCache.get(nameTowns) != null) {
               townCache.remove(nameTowns);
               log.info("Town '{}' removed from cache", nameTowns);
          }

          // Затем удаляем из репозитория
          Towns townToDelete = repository.findByNameTowns(nameTowns);
          if (townToDelete != null) {
               repository.delete(townToDelete);
               log.info("Town '{}' deleted from repository", nameTowns);
               return "Delete";
          } else {
               log.info("Town '{}' not found in repository", nameTowns);
               return "Not found.";
          }
     }

     public Towns findByNameTowns(String nameTowns) {
          // Пробуем сначала получить данные из кэша
          Towns cachedTown = townCache.get(nameTowns);
          if (cachedTown != null) {
               return cachedTown;
          }

          // Если не найдено в кэше, получаем из репозитория и сохраняем в кэш
          Towns town = repository.findByNameTowns(nameTowns);
          if (town != null) {
               townCache.put(nameTowns, town);
               log.info("Town '{}' fetched from repository and cached", nameTowns);
          } else {
               log.info("Town '{}' not found in cache or repository", nameTowns);
          }
          return town;
     }

     public Towns updateTownByName(String nameTowns, String coordinates) {
          // Пробуем сначала получить данные из кэша
          Towns existingTown = findByNameTowns(nameTowns);

          if (existingTown != null) {
               existingTown.setCoordinates(coordinates);
               // Сохраняем обновленные данные в кэше перед сохранением в репозитории
               townCache.put(nameTowns, existingTown);
               log.info("Town '{}' updated in cache", nameTowns);
               return repository.save(existingTown);
          } else {
               log.warn("Town '{}' not found in cache or repository, update operation failed", nameTowns);
               return null;
          }
}}


