package weather.springwea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weather.springwea.model.Region;


@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

        void deleteByName(String name);
    Region findByName(String name);

}
