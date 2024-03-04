package weather.springwea.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})

public class Towns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coordinates;

    @Column(unique=true)
    private String nameTowns;
    private int time;
    private String positionSun;

   @ManyToOne(cascade =  CascadeType.ALL)
    private Region region;

}