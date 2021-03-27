package pl.xArisen67.PlanTripApplication.services.externalData.interfaces;

import pl.xArisen67.PlanTripApplication.exceptions.UpdateDataException;

import java.net.MalformedURLException;

public interface DistanceService {
    public void changeDistanceDataUrl(String distanceDataUrl) throws MalformedURLException, UpdateDataException;
    public int getDistanceBetweenCities(String source, String destination);
}
