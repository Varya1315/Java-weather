package weather.springwea.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import weather.springwea.model.Towns;
import weather.springwea.repository.TownRepository;

import java.util.List;
@Service
@AllArgsConstructor
@Primary

public class TownService {
     private final TownRepository repository;

     public List<Towns> findAllTowns() {
          return repository.findAll();
     }

     public Towns saveTowns(Towns towns) {
          return repository.save(towns);
     }

     public String deleteTownsByNameTowns(String nameTowns) {
          Towns townToDelete = repository.findByNameTowns(nameTowns);
          if (townToDelete != null) {
               repository.delete(townToDelete);
               return "Delete";
          } else {
               return "Not found.";
          }
     }


     public Towns findByNameTowns(String nameTowns) {
          repository.findByNameTowns(nameTowns);
          return null;
     }

     public Towns updateTownByName(String nameTowns, String coordinates) {
          Towns existingTown = repository.findByNameTowns(nameTowns);

          if (existingTown != null) {

               existingTown.setCoordinates(coordinates);
               existingTown.setNameTowns(nameTowns);
               repository.save(existingTown);
              return repository.save(existingTown);
          } else {
               return null;
          }
     }
}


