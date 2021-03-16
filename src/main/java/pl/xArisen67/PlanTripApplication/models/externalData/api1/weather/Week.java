package pl.xArisen67.PlanTripApplication.models.externalData.api1.weather;

import lombok.Data;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.ExternalDataObject;

@Data
public class Week extends ExternalDataObject {
    private Day[] days;
}
