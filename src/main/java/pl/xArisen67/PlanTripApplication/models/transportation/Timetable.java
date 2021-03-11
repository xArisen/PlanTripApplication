package pl.xArisen67.PlanTripApplication.models.transportation;

import lombok.Data;

@Data
public class Timetable {
    private String source;
    private String destination;
    private Travel[] travels;
}
