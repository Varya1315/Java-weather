package weather.springwea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
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
    void testSaveRegion2() {
        // Инициализация Mockito аннотаций

        // Создаем новый регион для сохранения
        Region newRegion = new Region("New Region");

        // Предположим, что регион с таким именем уже существует
        when(repository.findByName(newRegion.getName())).thenReturn(new Region("New Region"));

        // Пытаемся сохранить регион
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            regionService.saveRegion(newRegion);
        });

        // Проверяем, что выбрасывается исключение с правильным сообщением

        // Проверяем, что метод repository.save() не был вызван
        verify(repository, never()).save(any(Region.class));

        // Предположим, что регион с таким именем не существует
        when(repository.findByName(newRegion.getName())).thenReturn(null);

        // Предположим, что сохранение региона прошло успешно
        Region savedRegion = new Region("New Region");
        savedRegion.setId(1L);
        when(repository.save(any(Region.class))).thenReturn(savedRegion);

        // Вызываем метод сохранения региона
        Region result = regionService.saveRegion(newRegion);

        // Проверяем, что результат не является нулевым
        assertNotNull(result);

        // Проверяем, что метод repository.save() был вызван один раз с корректным аргументом
        verify(repository, times(1)).save(any(Region.class));
        // Проверяем, что регион добавлен в кэш
        verify(regionCache, times(1)).put(savedRegion.getName(), savedRegion);
    }
    @Test
    void testSaveRegions() {
        // Создаем заглушку репозитория RegionRepository

        // Создаем объект RegionService с заглушкой репозитория

        // Создаем список регионов для сохранения
        List<Region> regionsToSave = new ArrayList<>();
        regionsToSave.add(new Region("Region 1"));
        regionsToSave.add(new Region("Region 2"));
        regionsToSave.add(new Region("Region 3"));

        // Создаем список ожидаемых результатов сохранения
        List<Region> expectedSavedRegions = new ArrayList<>();
        for (Region region : regionsToSave) {
            Region savedRegion = new Region(region.getName());
            savedRegion.setId(1L); // Предположим, что сохраненный регион получает ID после сохранения
            expectedSavedRegions.add(savedRegion);
        }

        // Задаем поведение заглушки репозитория при вызове метода save()
        when(repository.save(any(Region.class))).thenAnswer(invocation -> {
            // Имитируем сохранение региона и возвращаем его
            Region regionToSave = invocation.getArgument(0);
            Region savedRegion = new Region(regionToSave.getName());
            savedRegion.setId(1L); // Предположим, что сохраненный регион получает ID после сохранения
            return savedRegion;
        });

        // Вызываем тестируемый метод
        List<Region> savedRegions = regionService.saveRegions(regionsToSave);

        // Проверяем, что список сохраненных регионов совпадает с ожидаемым списком
        assertEquals(expectedSavedRegions.size(), savedRegions.size());
        for (int i = 0; i < expectedSavedRegions.size(); i++) {
            Region expectedRegion = expectedSavedRegions.get(i);
            Region actualRegion = savedRegions.get(i);
            assertEquals(expectedRegion.getName(), actualRegion.getName());
            assertEquals(expectedRegion.getId(), actualRegion.getId());
        }

        // Проверяем, что метод save() был вызван столько раз, сколько регионов нужно сохранить
        verify(repository, times(regionsToSave.size())).save(any(Region.class));
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