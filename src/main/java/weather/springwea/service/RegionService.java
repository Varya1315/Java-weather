package weather.springwea.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;
import weather.springwea.repository.RegionRepository;
import weather.springwea.repository.TownRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
@Transactional

public class RegionService {

    private final RegionRepository repository;
    private final TownRepository repos;

    public List<Region>  findAll(){
        return repository.findAll();
    }


    public Region saveRegion(Region newRegion) {
        List<Towns> temp = newRegion.getTowns();
        Towns temp1;
        for(int i=0;i<temp.size();i++)
        {
            temp1=temp.get(i);
            repos.save(temp1);
        }
        return repository.save(newRegion);
    }
    public Region findByNameRegion(String name){
        return  repository.findByName(name);
    }

    public String deleteRegionByName(String name) {

        Region regionToDelete = repository.findByName(name);
        List<Towns> temp = regionToDelete.getTowns();
        Towns temp1;
        for(int i=0;i<temp.size();i++)
        {
            temp1=temp.get(i);
            repos.delete(temp1);
        }
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


