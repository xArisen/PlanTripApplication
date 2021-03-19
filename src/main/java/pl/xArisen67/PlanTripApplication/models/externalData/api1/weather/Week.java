package pl.xArisen67.PlanTripApplication.models.externalData.api1.weather;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.ExternalDataObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class Week extends ExternalDataObject {
    private Day[] days;

    public Week(){

    }

    public Week(Day[] days){
        this.days = days;
    }
}
