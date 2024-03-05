package weather.springwea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weather.springwea.model.Towns;


public interface TownRepository extends  JpaRepository<Towns, Long> {


 Towns findByNameTowns(String nameTowns);

}
