package pl.xArisen67.PlanTripApplication.services.externalData.interfaces;

import java.net.MalformedURLException;

public interface DistanceService {
    public void changeDistanceDataUrl(String distanceDataUrl) throws MalformedURLException;
    public int getDistanceBetweenCities(String source, String destination);
}
