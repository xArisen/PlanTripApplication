package pl.xArisen67.PlanTripApplication.models.externalData.api1.distance;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.ExternalDataObject;

import java.util.Arrays;

@Data
@EqualsAndHashCode(callSuper=false)
public class DistanceCollection extends ExternalDataObject {
    private Distance[] distances;

    public DistanceCollection() {
    }

    public DistanceCollection(Distance[] distances) {
        this.distances = distances;
    }
}
