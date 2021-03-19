package pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation;

import lombok.Data;

@Data
public class Timetable {
    private String source;
    private String destination;
    private Travel[] travels;

    public Timetable() {
    }

    public Timetable(String source, String destination, Travel[] travels) {
        this.source = source;
        this.destination = destination;
        this.travels = travels;
    }
}
