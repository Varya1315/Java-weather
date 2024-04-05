package weather.springwea.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegionTest {

    @Mock
    private Towns town1;

    @Mock
    private Towns town2;

    @Test
    public void testRegionConstructor() {
        // Arrange
        Long id = 1L;
        String name = "TestRegion";
        List<Towns> towns = new ArrayList<>();
        towns.add(town1);
        towns.add(town2);

        // Act
        Region region = new Region();
        region.setId(id);
        region.setName(name);
        region.setTowns(towns);

        // Assert
        assertNotNull(region);
        assertEquals(id, region.getId());
        assertEquals(name, region.getName());
        assertEquals(towns, region.getTowns());
    }
}
