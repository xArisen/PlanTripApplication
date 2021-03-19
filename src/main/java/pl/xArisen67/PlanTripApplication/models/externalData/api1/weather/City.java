package pl.xArisen67.PlanTripApplication.models.externalData.api1.weather;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class City {
    private String city;
    private Weather weather;

    public City() {
    }

    public City(String city, Weather weather) {
        this.city = city;
        this.weather = weather;
    }
}
