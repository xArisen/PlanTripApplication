package pl.xArisen67.PlanTripApplication.models.externalData.api1.distance;

import lombok.Data;

import java.util.Objects;

@Data
public class Distance {
    private String source;
    private String destination;
    int distance;

    public Distance() {
    }

    public Distance(String source, String destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }
}
