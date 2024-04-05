package weather.springwea;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import weather.springwea.model.Towns;
import weather.springwea.repository.TownRepository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SpringweaApplicationTest {


    @Autowired
    private TownRepository townsRepository;

    @MockBean
    private TownRepository mockTownsRepository;

    @Test
    public void testSaveTowns() {
        // Arrange
        Towns town = new Towns();
        town.setNameTowns("TestTown");
        town.setCoordinates("50.123,30.456");
        town.setTime(720); // 12:00 PM
        town.setPositionSun("Zenith");
        town.setInterestingFact("This is a test town");

        // Mocking the behavior of the repository
        when(mockTownsRepository.save(town)).thenReturn(town);
        when(mockTownsRepository.findById(town.getId())).thenReturn(java.util.Optional.of(town));

        // Act
        Towns savedTown = townsRepository.save(town);

        // Assert
        assertEquals("TestTown", savedTown.getNameTowns(), "Name should match");
        assertEquals("50.123,30.456", savedTown.getCoordinates(), "Coordinates should match");
        assertEquals("Zenith", savedTown.getPositionSun(), "Position of sun should match");
        assertEquals("This is a test town", savedTown.getInterestingFact(), "Interesting fact should match");
    }
}
