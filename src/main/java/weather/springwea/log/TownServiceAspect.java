package weather.springwea.log;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import weather.springwea.model.Towns;
import weather.springwea.repository.TownRepository;

import java.util.List;

@Aspect
@Component
public class TownServiceAspect {
    private static final Logger LOG =
            LoggerFactory.getLogger(TownServiceAspect.class);

    private final TownRepository repository;

    public TownServiceAspect(
            final TownRepository townRepositoryParam) {
        this.repository = townRepositoryParam;
    }

    @Pointcut("execution(* weather.springwea.service."
            + "TownService.findAllTowns())")
    public void findAllTownsPointcut() {
    }

    @Before("execution(* weather.springwea.service."
            + "TownService.findAllTowns())")
    public void logBeforeFindAllTowns() {
        LOG.info("Attempting to find all towns");
    }
    /**
     * Логирует результаты выполнения метода findAllTowns.
     *
     * @param towns Список всех городов.
     */
    @AfterReturning(pointcut = "execution(* weather.springwea.service."
            + "TownService.findAllTowns())", returning = "towns")
    public void logFindAllTowns(final List<Towns> towns) {
        if (!towns.isEmpty()) {
            LOG.info("Retrieved {} towns", towns.size());
        } else {
            LOG.error("No towns found {}", repository.count());
        }
    }
    @Pointcut("execution(* weather.springwea.service."
            + "TownService.deleteTownsByNameTowns(String))"
            + " && args(nameTowns)")
    public void deleteTownsByNameTownsPointcut(
            final String nameTowns) {
    }

    @Before(value = "deleteTownsByNameTownsPointcut(nameTowns)",
            argNames = "nameTowns")
    public void logBeforeDeleteTowns(final String nameTowns) {
        LOG.info("Attempting to delete town with name '{}'", nameTowns);
    }
    /**
     * Логирует результаты удаления города по его имени.
     *
     * @param nameTowns Имя удаляемого города.
     * @param result    Результат операции удаления
     *                  (например, "Delete" или "Error").
     */
    @AfterReturning(pointcut = "deleteTownsByNameTownsPointcut(nameTowns)",
            returning = "result", argNames = "nameTowns,result")
    public void logDeleteTowns(
            final String nameTowns,
            final String result) {
        if ("Delete".equals(result)) {
            LOG.info("Town '{}' deleted successfully", nameTowns);
        } else {
            LOG.error("Failed to delete town '{}'", nameTowns);
        }
    }

    @Pointcut("execution(* weather.springwea.service."
            + "TownService.findByNameTowns(String)) && args(nameTowns)")
    public void findByNameTownsPointcut(
            final String nameTowns) {
    }

    @Before(value = "findByNameTownsPointcut(nameTowns)",
            argNames = "nameTowns")
    public void logBeforeFindByNameTowns(
            final String nameTowns) {
        LOG.info("Attempting to find town with name '{}'", nameTowns);
    }
    /**
     * Логирует результаты поиска города по его имени.
     *
     * @param nameTowns Имя искомого города.
     * @param town      Найденный город или null, если город не найден.
     */
    @AfterReturning(pointcut = "findByNameTownsPointcut(nameTowns)",
            returning = "town", argNames = "nameTowns,town")
    public void logFindByNameTowns(
            final String nameTowns,
            final Towns town) {
        if (town != null) {
            LOG.info("Town '{}' found", nameTowns);
        } else {
            LOG.error("Town '{}' not found", nameTowns);
        }
    }

    @Pointcut(value = "execution(* weather.springwea.service."
            + "TownService.updateTownByName(String, String))"
            + " && args(nameTowns, coordinates)",
            argNames = "nameTowns,coordinates")
    public void updateTownByNamePointcut(
            final String nameTowns,
            final String coordinates) {
    }

    @Before(value = "updateTownByNamePointcut(nameTowns, coordinates)",
            argNames = "nameTowns,coordinates")
    public void logBeforeUpdateTown(
            final String nameTowns,
            final String coordinates) {
        LOG.info("Attempting to update town '{}' with new coordinates '{}'",
                nameTowns, coordinates);
    }
    /**
     * Логирует результаты обновления города по его имени.
     *
     * @param nameTowns    Имя обновляемого города.
     * @param coordinates  Координаты обновляемого города.
     * @param town         Обновленный город или null,
     *                     если обновление не удалось.
     */
    @AfterReturning(pointcut = "updateTownByNamePointcut("
            + "nameTowns, coordinates)", returning = "town",
            argNames = "nameTowns,coordinates,town")
    public void logUpdateTown(final String nameTowns,
                              final String coordinates,
                              final Towns town) {
        if (town != null) {
            LOG.info("Town '{}' updated successfully", nameTowns);
        } else {
            LOG.error("Failed to update town '{}'", nameTowns);
        }
    }

}