package pl.xArisen67.PlanTripApplication.models.weather;

import lombok.Data;

@Data
public class Day {
    private String day;
    private City[] cities;
}
