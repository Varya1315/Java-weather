package weather.springwea.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;
import weather.springwea.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
@Transactional

public class RegionService {

    private final RegionRepository repository;

   public List<Region>  findAll(){
        return repository.findAll();
    }


   public Optional<Region> saveRegion(Region newRegion) {
       if (repository.existsByName(newRegion.getName())) {
           return Optional.empty();
       }

       Region region = new Region();
       region.setName(newRegion.getName());

       List<Towns> townsList = newRegion.getTowns();
       if (townsList != null) {
           for (Towns town : townsList) {
               town.setRegion(region);
           }
           region.setTowns(townsList);
       }

       Region savedRegion = repository.save(region);
       return Optional.of(savedRegion);
   }
    public Region findByNameRegion(String name){
        return  repository.findByName(name);
    }

    public String deleteRegionByName(String name) {
        Region regionToDelete = repository.findByName(name);
        if (regionToDelete != null) {
            repository.delete(regionToDelete);
            return "Delete";
        } else {
            return "Not found.";
        }
    }
    public Region updateRegionByName(String name, String newName) {
        Region existingRegion = repository.findByName(name);

        if (existingRegion != null) {
            existingRegion.setName(newName);
            repository.save(existingRegion);
            return existingRegion;
        } else {
            return null;
        }
    }

}



