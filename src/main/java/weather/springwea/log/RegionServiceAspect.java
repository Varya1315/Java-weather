package weather.springwea.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import weather.springwea.model.Region;
import weather.springwea.model.Towns;

import java.util.List;

@Aspect
@Component
public class RegionServiceAspect {


    private static final Logger LOG = LoggerFactory.getLogger(
            RegionServiceAspect.class);


    @Pointcut(value = "execution(* weather.springwea.service.RegionService."
            + "findTownsByRegionAndInterestingFact(String, String)) "
            + "&& args(regionName, interestingFact)",
            argNames = "regionName,interestingFact")
    public void findTownsByRegionAndInterestingFactPointcut(
            final String regionName,
            final String interestingFact) {
    }

    @Before(value = "findTownsByRegionAndInterestingFactPointcut("
            + "regionName, interestingFact)",
            argNames = "regionName,interestingFact")
    public void logMethodCall(
            final String regionName,
            final String interestingFact) {
        LOG.info("Method findTownsByRegionAndInterestingFact"
                        + " called with parameters:"
                        + " regionName = {}, interestingFact = {}",
                regionName, interestingFact);
    }

    /**
     * Логирует информацию о городах, найденных
     * по заданному региону и интересному факту.
     *
     * @param regionName      Название региона.
     * @param interestingFact Интересный факт.
     * @param towns           Список городов.
     */
    @AfterReturning(pointcut = "execution("
            + "* weather.springwea.service.RegionService."
            + "findTownsByRegionAndInterestingFact(String, String)) "
            + "&& args(regionName, interestingFact)",
            returning = "towns", argNames = "regionName,interestingFact,towns")
    public void logFindTownsByRegionAndInterestingFact(
            final String regionName,
            final String interestingFact,
            final List<Towns> towns) {
        if (!towns.isEmpty()) {
            LOG.info("Successfully retrieved data for region"
                            + " '{}' and interesting fact '{}'",
                    regionName, interestingFact);
        } else {
            LOG.error("No data found for the specified region '{}'"
                            + " and interesting fact '{}'",
                    regionName, interestingFact);
        }
    }

    @Pointcut("execution(* weather.springwea.service.RegionService.findAll())")
    public void findAllPointcut() {
    }

    @Before("findAllPointcut()")
    public void logMethodCall() {
        LOG.info("Method findAll called.");
    }

    /**
     * Логирует успешное выполнение метода findAll.
     *
     * @param regions Список регионов, возвращенный методом findAll.
     */
    @AfterReturning(pointcut = "findAllPointcut()", returning = "regions")
    public void logFindAllSuccess(final List<Region> regions) {
        if (regions != null) {
            LOG.info("Method findAll completed successfully.");
        } else {
            LOG.error("Error occurred:"
                    + "Method findAll returned null.");
        }
    }

    @Pointcut("execution(* weather.springwea.service."
            + "RegionService.saveRegion(..)) && args(newRegion)")
    public void saveRegionPointcut(final Region newRegion) {
    }

    @Before(value = "saveRegionPointcut(newRegion)", argNames = "newRegion")
    public void logBeforeSaveRegion(final Region newRegion) {
        LOG.info("Attempting to save region '{}'", newRegion.getName());
    }

    /**
     * Логирует успешное сохранение региона.
     *
     * @param newRegion   Новый регион, который был попыткой сохранить.
     * @param savedRegion Сохраненный регион.
     */
    @AfterReturning(pointcut = "saveRegionPointcut(newRegion)",
            returning = "savedRegion", argNames = "newRegion, savedRegion")
    public void logSaveRegionSuccess(
            final Region newRegion, final Region savedRegion) {
        if (savedRegion != null) {
            LOG.info("Region '{}' saved successfully", savedRegion.getName());
        } else {
            LOG.error("Failed to save region '{}'", newRegion.getName());
        }
    }

    //******
    @Pointcut("execution(* weather.springwea.service."
            + "RegionService.findByNameRegion(String)) && args(name)")
    public void findByNameRegionPointcut(final String name) {
    }

    @Before("execution(* weather.springwea.service."
            + "RegionService.findByNameRegion(String)) && args(name)")
    public void logBeforeFindByNameRegion(final String name) {
        LOG.info("Attempting to find region with name '{}'", name);
    }

    /**
     * Логирует успешное нахождение региона по его имени.
     *
     * @param name   Название региона.
     * @param region Найденный регион.
     */
    @AfterReturning(pointcut = "execution(* weather.springwea.service."
            + "RegionService.findByNameRegion(String)) && args(name)",
            returning = "region", argNames = "name,region")
    public void logFindRegionSuccess(final String name, final Region region) {
        if (region != null) {
            LOG.info("Region '{}' found", name);
        } else {
            LOG.error("Region '{}' not found", name);
        }
    }

    //*****
    @Pointcut("execution(* weather.springwea.service."
            + "RegionService.deleteRegionByName(String)) && args(name)")
    public void deleteRegionByNamePointcut(final String name) {
    }

    /**
     * Логирует попытку удаления региона по его имени.
     *
     * @param name Название региона.
     */
    @Before(value = "deleteRegionByNamePointcut(name)", argNames = "name")
    public void logBeforeDeleteRegion(final String name) {
        LOG.info("Attempting to delete region with name '{}'", name);
    }

    /**
     * Логирует успешное удаление региона по его имени.
     *
     * @param name   Название региона.
     * @param result Результат операции удаления
     *               (например, "Delete" при успешном удалении).
     */
    @AfterReturning(pointcut = "deleteRegionByNamePointcut(name)",
            returning = "result", argNames = "name,result")
    public void logDeleteRegionSuccess(final String name, final String result) {
        if ("Delete".equals(result)) {
            LOG.info("Region '{}' deleted successfully", name);
        } else {
            LOG.error("Region '{}' not found for deletion", name);
        }
    }

    @Pointcut(value = "execution(* weather.springwea.service."
            + "RegionService.updateRegionByName(String, String))"
            + " && args(name, newName)", argNames = "name,newName")
    public void updateRegionByNamePointcut(
            final String name, final String newName) {
    }

    @Before(value = "updateRegionByNamePointcut(name, newName)",
            argNames = "name,newName")
    public void logBeforeUpdateRegion(final String name,
                                      final String newName) {
        LOG.info("Attempting to update region"
                + " with name '{}' to '{}'", name, newName);
    }

    /**
     * Логирует успешное обновление региона по его имени.
     *
     * @param name          Имя региона, который был обновлен.
     * @param newName       Новое имя региона.
     * @param updatedRegion Обновленный регион.
     */
    @AfterReturning(pointcut = "updateRegionByNamePointcut(name, newName)",
            returning = "updatedRegion",
            argNames = "name,newName,updatedRegion")
    public void logUpdateRegionSuccess(final String name,
                                       final String newName,
                                       final Region updatedRegion) {
        if (updatedRegion != null) {
            LOG.info("Region '{}' updated to '{}' successfully", name, newName);
        } else {
            LOG.error("Region '{}' not found, update operation failed", name);
        }
    }
    @Pointcut("execution(* weather.springwea.service.RegionService.saveRegions(..)) && args(regions)")
    public void saveRegionsPointcut(List<Region> regions) {}

    @Before(value = "saveRegionsPointcut(regions)", argNames = "regions")
    public void logSaveRegionsCall(List<Region> regions) {
        LOG.info("Method saveRegions called with {} regions.", regions.size());
        // Additional logging if needed
    }

    @AfterReturning(pointcut = "saveRegionsPointcut(regions)", returning = "newRegions", argNames = "regions,newRegions")
    public void logSaveRegionsSuccess(List<Region> regions, List<Region> newRegions) {
        if (newRegions != null) {
            LOG.info("Method saveRegions completed successfully. Saved {} regions.", newRegions.size());
            // Additional logging if needed
        } else {
            LOG.error("Error occurred: Method saveRegions returned null.");
        }
    }
}