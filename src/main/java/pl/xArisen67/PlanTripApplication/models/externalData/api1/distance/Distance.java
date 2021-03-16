package pl.xArisen67.PlanTripApplication.models.externalData.api1.distance;

import lombok.Data;

@Data
public class Distance {
    private String source;
    private String destination;
    int distance;
}
