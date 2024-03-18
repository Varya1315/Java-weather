package weather.springwea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
    Region findByName(String name);

        @Query("SELECT t FROM Region r JOIN r.towns t WHERE r.name = :regionName AND t.interestingFact = :interestingFact")
        List<Towns> findTownsByRegionAndInterestingFact(@Param("regionName") String regionName, @Param("interestingFact") String interestingFact);

    @Query("SELECT r FROM Region r WHERE SIZE(r.towns) > :townCount")
    List<Region> findRegionsWithMoreTowns(@Param("townCount") int townCount);

}