package weather.springwea.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Represents a town entity.
 */
@Data
@Entity
@EnableCaching
public class Towns {
    /**
     * Marks this field as the primary key for the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The coordinates of the town.
     */
    private String coordinates;

    /**
     * The name of the town.
     */
    private String nameTowns;

    /**
     * The time of the town.
     */
    private int time;

    /**
     * The position of the sun in the town.
     */
    private String positionSun;

    /**
     * An interesting fact about the town.
     */
    private String interestingFact;
}
