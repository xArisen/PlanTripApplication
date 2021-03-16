package pl.xArisen67.PlanTripApplication.models.externalData.api1.weather;

import lombok.Data;

@Data
public class Weather{
    private int wind;
    private int temperature;
    private int probabilityOfPrecipitation;
}
