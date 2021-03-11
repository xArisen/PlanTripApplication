package pl.xArisen67.PlanTripApplication.services.calculation;

import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.exceptions.WrongResultException;
import pl.xArisen67.PlanTripApplication.models.transportation.Timetable;
import pl.xArisen67.PlanTripApplication.services.calculation.interfaces.TimeService;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.DistanceService;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.TransportationService;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.WeatherService;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
public class TimeServiceImp implements TimeService {
    private double walkSpeedKmPerMinutes = 6/60f;
    private double bikeSpeedKmPerMinutes = 25/60f;

    private final DistanceService distanceService;
    private final TransportationService transportationService;
    private final WeatherService weatherService;

    public TimeServiceImp(DistanceService distanceService, TransportationService transportationService, WeatherService weatherService) {
        this.distanceService = distanceService;
        this.transportationService = transportationService;
        this.weatherService = weatherService;
    }

    public double getWalkSpeedKmPerMinutes() {
        return walkSpeedKmPerMinutes;
    }

    public void setWalkSpeedKmPerMinutes(double walkSpeedKmPerMinutes) {
        this.walkSpeedKmPerMinutes = walkSpeedKmPerMinutes;
    }

    public double getBikeSpeedKmPerMinutes() {
        return bikeSpeedKmPerMinutes;
    }

    public void setBikeSpeedKmPerMinutes(double bikeSpeedKmPerMinutes) {
        this.bikeSpeedKmPerMinutes = bikeSpeedKmPerMinutes;
    }

    @Override
    public int getTimeInMinutesToWalkFromSourceToDestination(String source, String destination){
        int distance = distanceService.getDistanceBetweenCities(source, destination);
        return (int)(distance / walkSpeedKmPerMinutes);
    }

    @Override
    public int getTimeInMinutesToBusFromSourceToDestination(String source, String destination) {
        LocalTime[] departureAndDestinationTime;
        departureAndDestinationTime = transportationService.getDepartureAndDestinationTime(source, destination);

        return (int) departureAndDestinationTime[0].until(departureAndDestinationTime[1], ChronoUnit.MINUTES);
    }

    @Override
    public int getTimeInMinutesToBikeFromSourceToDestination(String source, String destination, String day) {
        int distance;
        Timetable timeTable = transportationService.getTimetable(source, destination);
        boolean isWeatherOk = weatherService.checkIfWeatherForDayAndCityIsOkForBike(source, day);

        if(!isWeatherOk){
            throw new WrongResultException("Invalid weather for bike.");
        }
        distance = distanceService.getDistanceBetweenCities(source, destination);

        return (int)(distance / bikeSpeedKmPerMinutes);
    }
}
