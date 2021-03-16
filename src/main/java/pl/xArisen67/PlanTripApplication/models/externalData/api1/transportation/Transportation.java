package pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation;

import lombok.Data;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.ExternalDataObject;

@Data
public class Transportation extends ExternalDataObject {
    private Timetable[] timetable;
}
