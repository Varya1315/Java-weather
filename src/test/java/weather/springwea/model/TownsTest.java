package weather.springwea.model;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TownsTest {

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
