package weather.springwea.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "region")
public class Region {
    /**
     * Marks this field as the primary key for the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Specifies that this field maps to a unique column in the database.
     */
    @Column(unique = true)
    private String name;
    /**
     * Defines a one-to-many relationship between the region
     * entity and the towns entities.
     */
    @OneToMany

    @JoinColumn(name = "region_id")
    private List<Towns> towns;
}
