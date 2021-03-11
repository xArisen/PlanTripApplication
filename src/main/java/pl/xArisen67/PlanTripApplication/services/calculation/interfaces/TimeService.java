package pl.xArisen67.PlanTripApplication.services.calculation.interfaces;

public interface TimeService {
    public int getTimeInMinutesToWalkFromSourceToDestination(String source, String destination);
    public int getTimeInMinutesToBusFromSourceToDestination(String source, String destination);
    public int getTimeInMinutesToBikeFromSourceToDestination(String source, String destination, String day);
}
