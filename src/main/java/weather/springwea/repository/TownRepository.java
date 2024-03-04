package weather.springwea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weather.springwea.model.Towns;


public interface TownRepository extends  JpaRepository<Towns, Long> {

 void deleteTownsByNameTowns(String nameTowns);
 Towns findByNameTowns(String nameTowns);

}
