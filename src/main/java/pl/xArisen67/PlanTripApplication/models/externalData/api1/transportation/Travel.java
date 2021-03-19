package pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation;

import lombok.Data;

@Data
public class Travel {
    private String departureTime;
    private String destinationTime;

    public Travel() {
    }

    public Travel(String departureTime, String destinationTime) {
        this.departureTime = departureTime;
        this.destinationTime = destinationTime;
    }
}
