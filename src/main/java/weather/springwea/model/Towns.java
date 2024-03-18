package weather.springwea.model;

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
    private String interestingFact;
}