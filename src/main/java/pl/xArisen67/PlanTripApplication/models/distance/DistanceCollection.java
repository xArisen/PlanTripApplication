package pl.xArisen67.PlanTripApplication.models.distance;

import lombok.Data;
import pl.xArisen67.PlanTripApplication.models.ExternalDataObject;

@Data
public class DistanceCollection extends ExternalDataObject {
    private Distance[] distances;
}
