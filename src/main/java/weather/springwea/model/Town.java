package weather.springwea.model;

import lombok.*;


@Data
@Builder

public class Town {

    private String nameTowns;
    private String coordinates;
    private int time;
    private String positionSun;

}
