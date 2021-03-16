package pl.xArisen67.PlanTripApplication.models.externalData.api1.distance;

import lombok.Data;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.ExternalDataObject;

@Data
public class DistanceCollection extends ExternalDataObject {
    private Distance[] distances;
}
