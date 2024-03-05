package weather.springwea.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Data
@Entity


public class Towns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coordinates;

    private String nameTowns;
    private int time;
    private String positionSun;

   /*@ManyToOne
    @JoinColumn(name = "region_id")
    @JsonIgnore
    private Region region;

    */
}