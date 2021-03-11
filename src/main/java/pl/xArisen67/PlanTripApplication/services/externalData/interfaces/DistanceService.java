package pl.xArisen67.PlanTripApplication.services.externalData.interfaces;

public interface DistanceService {
    public void changeDistanceDataUrl(String distanceDataUrl);
    public int getDistanceBetweenCities(String source, String destination);
}
