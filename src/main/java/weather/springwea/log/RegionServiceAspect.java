package weather.springwea.log;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;

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
}
