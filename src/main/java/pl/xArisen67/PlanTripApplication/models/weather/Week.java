package pl.xArisen67.PlanTripApplication.models.weather;

import lombok.Data;
import pl.xArisen67.PlanTripApplication.models.ExternalDataObject;

@Data
public class Week extends ExternalDataObject {
    private Day[] days;
}
