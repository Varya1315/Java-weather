package weather.springwea.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Represents a region entity.
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "region")
public class Region {

    /**
     * The unique identifier of the region.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the region. Must be unique.
     */
    @Column(unique = true)
    private String name;

    /**
     * The list of towns belonging to this region.
     */
    @OneToMany
    @JoinColumn(name = "region_id")
    private List<Towns> towns;

}
