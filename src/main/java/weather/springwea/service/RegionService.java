package weather.springwea.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import weather.springwea.model.Region;
import weather.springwea.repository.RegionRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
@Transactional

public class RegionService {

    private final RegionRepository repository;

   public List<Region>  findAll(){
        return repository.findAll();
    }


    public Region saveRegion(Region region)
    {
        return repository.save(region);
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
    }



