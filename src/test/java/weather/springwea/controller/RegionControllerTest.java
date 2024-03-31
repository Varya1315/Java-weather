package weather.springwea.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;
import weather.springwea.service.RegionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class RegionControllerTest {

    @Mock
    private RegionService regionService;

    @InjectMocks
    private RegionController regionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllRegion() {
        List<Region> regions = new ArrayList<>();
        regions.add(new Region());
        regions.add(new Region());

        when(regionService.findAll()).thenReturn(regions);

        assertEquals(regions, regionController.findAllRegion());
    }

    @Test
    void saveRegion() {
        Region region = new Region();
        when(regionService.saveRegion(region)).thenReturn(region);

        assertEquals(region, regionController.saveRegion(region));
    }

    @Test
    void findByNameRegion() {
        Region region = new Region();
        when(regionService.findByNameRegion("Test Region")).thenReturn(region);

        assertEquals(region, regionController.findByNameRegion("Test Region"));
    }

    @Test
    void deleteRegionByName() {
        when(regionService.deleteRegionByName("Test Region")).thenReturn("Delete");
        ResponseEntity<String> response = regionController.deleteRegionByName("Test Region");
        assertEquals("Delete", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        when(regionService.deleteRegionByName("Unknown Region")).thenReturn(null);
        response = regionController.deleteRegionByName("Unknown Region");
        assertEquals("Region not found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateRegionByName() {
        Region updatedRegion = new Region();
        when(regionService.updateRegionByName("Test Region", "Updated Region")).thenReturn(updatedRegion);

        ResponseEntity<String> response = regionController.updateRegionByName("Test Region", "Updated Region");
        assertEquals("Updated region: ", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        when(regionService.updateRegionByName("Unknown Region", "Updated Region")).thenReturn(null);
        response = regionController.updateRegionByName("Unknown Region", "Updated Region");
        assertEquals("Region not found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getTownsWithFact() {
        List<Towns> towns = new ArrayList<>();
        towns.add(new Towns());
        towns.add(new Towns());

        when(regionService.findTownsByRegionAndInterestingFact("Region", "Fact")).thenReturn(towns);

        ResponseEntity<List<Towns>> response = regionController.getTownsWithFact("Region", "Fact");
        assertEquals(towns, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        when(regionService.findTownsByRegionAndInterestingFact("Unknown Region", "Fact")).thenReturn(new ArrayList<>());
        response = regionController.getTownsWithFact("Unknown Region", "Fact");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getRegionsWithMoreTowns() {
        List<Region> regions = new ArrayList<>();
        regions.add(new Region());
        regions.add(new Region());

        when(regionService.findRegionsWithMoreTowns(5)).thenReturn(regions);

        ResponseEntity<List<Region>> response = regionController.getRegionsWithMoreTowns(5);
        assertEquals(regions, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        when(regionService.findRegionsWithMoreTowns(10)).thenReturn(new ArrayList<>());
        response = regionController.getRegionsWithMoreTowns(10);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        when(regionService.findRegionsWithMoreTowns(0)).thenThrow(new RuntimeException());
        response = regionController.getRegionsWithMoreTowns(0);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    }