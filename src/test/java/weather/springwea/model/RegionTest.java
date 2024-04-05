package weather.springwea.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegionTest {

    private Region region;

    @BeforeEach
    public void setUp2() {
        region = new Region();
    }

    @Test
    public void testIdGetterAndSetter2() {
        Long id = 1L;
        region.setId(id);
        assertEquals(id, region.getId());
    }

    @Test
    public void testNameGetterAndSetter2() {
        String name = "TestRegion";
        region.setName(name);
        assertEquals(name, region.getName());
    }

    @Test
    public void testTownsGetterAndSetter2() {
        List<Towns> towns = new ArrayList<>();
        towns.add(new Towns());
        towns.add(new Towns());
        region.setTowns(towns);
        assertEquals(towns, region.getTowns());
    }

    @Test
    public void testNoArgsConstructor2() {
        assertNotNull(region);
    }

    @BeforeEach
    public void setUp() {
        region = new Region();
    }

    @Test
    public void testIdGetterAndSetter() {
        Long id = 1L;
        region.setId(id);
        assertEquals(id, region.getId());
    }

    @Test
    public void testNameGetterAndSetter() {
        String name = "TestRegion";
        region.setName(name);
        assertEquals(name, region.getName());
    }

    @Test
    public void testTownsGetterAndSetter() {
        List<Towns> towns = new ArrayList<>();
        towns.add(new Towns());
        towns.add(new Towns());
        region.setTowns(towns);
        assertEquals(towns, region.getTowns());
    }

    @Test
    public void testNoArgsConstructor() {
        assertNotNull(region);
    }


    @Test
    public void testToString() {
        String name = "TestRegion";
        region.setName(name);
        String expectedToString = "Region(id=null, name=TestRegion, towns=null)";
        assertEquals(expectedToString, region.toString());
    }

    @Test
    public void testToString2() {
        String name = "TestRegion";
        region.setName(name);
        String expectedToString = "Region(id=null, name=TestRegion, towns=null)";
        assertEquals(expectedToString, region.toString());
    }
}
