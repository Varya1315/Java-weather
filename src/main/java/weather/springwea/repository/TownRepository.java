package weather.springwea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weather.springwea.model.Towns;

import java.util.List;

public interface TownRepository extends  JpaRepository<Towns, Long> {

 void deleteTownsByNameTowns(String nameTowns);
 Towns findByNameTowns(String nameTowns);

}
