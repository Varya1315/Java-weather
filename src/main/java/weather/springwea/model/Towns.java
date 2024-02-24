package weather.springwea.model;

import lombok.*;

import java.sql.Time;
@Data
@Builder

public class Towns {

    private String nameTowns;
    private String coordinates;
    private int time;
    private String positionSun;

}
