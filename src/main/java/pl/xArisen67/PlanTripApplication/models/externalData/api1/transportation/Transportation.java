package pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.ExternalDataObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class Transportation extends ExternalDataObject {

    public Transportation() {
    }

    public Transportation(Timetable[] timetable) {
        this.timetable = timetable;
    }

    private Timetable[] timetable;
}
