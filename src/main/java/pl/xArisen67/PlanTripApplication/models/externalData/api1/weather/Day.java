package pl.xArisen67.PlanTripApplication.models.externalData.api1.weather;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Day {
    private String day;
    private City[] cities;

    public Day() {
    }

    public Day(String day, City[] cities) {
        this.day = day;
        this.cities = cities;
    }
}
