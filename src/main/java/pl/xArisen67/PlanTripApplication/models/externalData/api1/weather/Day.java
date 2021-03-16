package pl.xArisen67.PlanTripApplication.models.externalData.api1.weather;

import lombok.Data;

@Data
public class Day {
    private String day;
    private City[] cities;
}
