package pl.xArisen67.PlanTripApplication.services.externalData.interfaces;

import pl.xArisen67.PlanTripApplication.exceptions.UpdateDataException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Timetable;

import java.net.MalformedURLException;
import java.time.LocalTime;

public interface TransportationService {
    public void changeTransportationDataUrl(String transportationDataUrl) throws MalformedURLException, UpdateDataException;
    public LocalTime[] getDepartureAndDestinationTime(String source, String destination);
    public LocalTime[] getDepartureAndDestinationTimeFromTimetable(Timetable timetable);
    public Timetable getTimetable(String source, String destination);
}
