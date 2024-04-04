package weather.springwea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import weather.springwea.cache.Cache;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;
import weather.springwea.repository.RegionRepository;
import weather.springwea.repository.TownRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {


    @Mock
    private Cache<String, Region> regionCache;

    @Mock
    private RegionRepository repository;

    @Mock
    private TownRepository repositoryT;

    @Mock
    private Logger LOG;

    @InjectMocks
    private RegionService regionService;


    @Test
    void testFindTownsByRegionAndInterestingFact() {
        // Arrange
        String regionName = "TestRegion";
        String interestingFact = "Interesting fact";
        List<Towns> expectedTowns = new ArrayList<>();
        expectedTowns.add(new Towns());

        // Регион не найден в кэше
        when(regionCache.get(regionName)).thenReturn(null);
        // Регион из репозитория
        Region regionFromRepository = new Region();
        regionFromRepository.setTowns(expectedTowns);
        regionFromRepository.setName(regionName);
        when(repository.findByName(regionName)).thenReturn(regionFromRepository);
        // Города из репозитория
        when(repository.findTownsByRegionAndInterestingFact(regionName, interestingFact)).thenReturn(expectedTowns);

        // Act
        List<Towns> result = regionService.findTownsByRegionAndInterestingFact(regionName, interestingFact);

        // Assert
        assertEquals(expectedTowns, result);

        // Verify interactions
        verify(regionCache, times(1)).get(regionName);
        verify(repository, times(1)).findByName(regionName);
        verify(regionCache, times(1)).put(regionName, regionFromRepository);
        verify(repository, times(1)).findTownsByRegionAndInterestingFact(regionName, interestingFact);
    }


    @Test
    void testUpdateRegionByName_ExistingRegionInCache() {
        // Arrange
        String name = "TestRegion";
        String newName = "NewTestRegion";

        // Создаем заглушку для существующего региона в кэше
        Region existingRegionInCache = new Region();
        existingRegionInCache.setName(name);
        when(regionCache.get(name)).thenReturn(existingRegionInCache);

        // Act
        Region updatedRegion = regionService.updateRegionByName(name, newName);

        // Assert
        assertNotNull(updatedRegion);
        assertEquals(newName, updatedRegion.getName());
        verify(repository, times(1)).save(updatedRegion);
        verify(regionCache, times(1)).put(newName, updatedRegion);
    }

    @Test
    void testUpdateRegionByName_ExistingRegionInRepository() {
        // Arrange
        String name = "TestRegion";
        String newName = "NewTestRegion";

        // Создаем заглушку для существующего региона в репозитории
        Region existingRegionInRepository = new Region();
        existingRegionInRepository.setName(name);
        when(regionCache.get(name)).thenReturn(null);
        when(repository.findByName(name)).thenReturn(existingRegionInRepository);

        // Act
        Region updatedRegion = regionService.updateRegionByName(name, newName);

        // Assert
        assertNotNull(updatedRegion);
        assertEquals(newName, updatedRegion.getName());
        verify(regionCache, times(1)).put(newName, updatedRegion);
    }

    @Test
    void testUpdateRegionByName_NonExistingRegion() {
        // Arrange
        String name = "NonExistingRegion";
        String newName = "NewTestRegion";

        // Создаем заглушку для несуществующего региона
        when(regionCache.get(name)).thenReturn(null);
        when(repository.findByName(name)).thenReturn(null);

        // Act
        Region updatedRegion = regionService.updateRegionByName(name, newName);

        // Assert
        assertNull(updatedRegion);
        verify(regionCache, never()).put(any(), any());
    }

    @Test
    void testFindByNameRegion_RegionInCache1() {
        // Arrange
        String regionName = "TestRegion";
        Region regionInCache = new Region();
        regionInCache.setName(regionName);

        // Stubbing
        when(regionCache.get(regionName)).thenReturn(regionInCache);

        // Act
        Region result = regionService.findByNameRegion(regionName);

        // Assert
        verify(regionCache, times(1)).get(regionName);
        verify(repository, never()).findByName(anyString());
        assertEquals(regionInCache, result);
    }
    @Test
    void testFindByNameRegion_RegionInDatabase() {
        // Arrange
        String regionName = "TestRegion";
        Region regionInDatabase = new Region();
        regionInDatabase.setName(regionName);

        // Stubbing
        when(regionCache.get(regionName)).thenReturn(null);
        when(repository.findByName(regionName)).thenReturn(regionInDatabase);

        // Act
        Region result = regionService.findByNameRegion(regionName);

        // Assert
        verify(regionCache, times(1)).get(regionName);
        verify(repository, times(1)).findByName(regionName);
        verify(regionCache, times(1)).put(regionName, regionInDatabase);
        assertEquals(regionInDatabase, result);
    }

    @Test
    void testFindByNameRegion_RegionNotFound() {
        // Arrange
        String regionName = "NonExistentRegion";

        // Stubbing
        when(regionCache.get(regionName)).thenReturn(null);
        when(repository.findByName(regionName)).thenReturn(null);

        // Act
        Region result = regionService.findByNameRegion(regionName);

        // Assert
        verify(regionCache, times(1)).get(regionName);
        verify(repository, times(1)).findByName(regionName);
        assertNull(null);
    }
    @Test
    void testDeleteRegionByName_RegionInCache() {
        // Arrange
        String regionName = "TestRegion";
        Region regionInCache = new Region();
        regionInCache.setName(regionName);

        // Stubbing
        when(regionCache.get(regionName)).thenReturn(regionInCache);

        // Act
        String result = regionService.deleteRegionByName(regionName);

        // Assert
        verify(regionCache, times(1)).get(regionName);
        verify(repository, never()).findByName(anyString());
        verify(repository, times(1)).delete(regionInCache);
        verify(regionCache, times(1)).remove(regionName);
        assertEquals("Delete", result);
    }
    @Test
    void testDeleteRegionByName_RegionInDatabase() {
        // Arrange
        String regionName = "TestRegion";
        Region regionInDatabase = new Region();
        regionInDatabase.setName(regionName);

        // Stubbing
        when(regionCache.get(regionName)).thenReturn(null);
        when(repository.findByName(regionName)).thenReturn(regionInDatabase);

        // Act
        String result = regionService.deleteRegionByName(regionName);

        // Assert
        verify(regionCache, times(1)).get(regionName);
        verify(repository, times(1)).findByName(regionName);
        verify(repositoryT, times(1)).deleteAll(regionInDatabase.getTowns());
        verify(repository, times(1)).delete(regionInDatabase);
        assertEquals("Delete", result);
    }



    @Test
    void testDeleteRegionByName_RegionNotFound() {
        // Arrange
        String name = "NonExistingRegion";

        // Создаем заглушку для несуществующего региона
        when(regionCache.get(name)).thenReturn(null);
        when(repository.findByName(name)).thenReturn(null);

        // Act
        String result = regionService.deleteRegionByName(name);

        // Assert
        assertEquals("Region not found", result);
        verify(repository, never()).deleteAll(any());
        verify(repository, never()).delete(any());
        verify(regionCache, never()).remove(any());
    }


}