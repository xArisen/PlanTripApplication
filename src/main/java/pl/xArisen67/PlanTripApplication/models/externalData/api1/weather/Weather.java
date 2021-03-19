package pl.xArisen67.PlanTripApplication.models.externalData.api1.weather;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Weather{
    private int wind;
    private int temperature;
    private int probabilityOfPrecipitation;

    public Weather() {
    }

    public Weather(int wind, int temperature, int probabilityOfPrecipitation) {
        this.wind = wind;
        this.temperature = temperature;
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }
}
