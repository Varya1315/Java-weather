package weather.springwea.model;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import weather.springwea.repository.TownRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@DataJpaTest
public class TownsTest {
    @Autowired
    private TownRepository townsRepository;

    @Test
    public void testTownsSetters() {
        // Arrange
        Towns town = new Towns();
        Long id = 1L;
        String coordinates = "40.7128° N, 74.0060° W";
        String nameTowns = "New York";
        int time = 3;
        String positionSun = "West";
        String interestingFact = "New York City is the largest city in the United States.";

        // Act
        town.setId(id);
        town.setCoordinates(coordinates);
        town.setNameTowns(nameTowns);
        town.setTime(time);
        town.setPositionSun(positionSun);
        town.setInterestingFact(interestingFact);

        // Assert
        assertEquals(id, town.getId());
        assertEquals(coordinates, town.getCoordinates());
        assertEquals(nameTowns, town.getNameTowns());
        assertEquals(time, town.getTime());
        assertEquals(positionSun, town.getPositionSun());
        assertEquals(interestingFact, town.getInterestingFact());
    }
}
