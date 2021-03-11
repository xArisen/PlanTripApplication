package pl.xArisen67.PlanTripApplication.models.transportation;

import lombok.Data;
import pl.xArisen67.PlanTripApplication.models.ExternalDataObject;

@Data
public class Transportation extends ExternalDataObject {
    private Timetable[] timetable;
}
