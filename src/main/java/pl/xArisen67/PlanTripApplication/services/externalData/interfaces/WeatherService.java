package pl.xArisen67.PlanTripApplication.services.externalData.interfaces;

import pl.xArisen67.PlanTripApplication.exceptions.UpdateDataException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.City;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Day;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Weather;

import java.net.MalformedURLException;

public interface WeatherService {
    public void changeWeatherDataUrl(String weatherDataUrl) throws MalformedURLException, UpdateDataException;
    public boolean checkIfWeatherForDayAndCityIsOkForBike(String sourceCity, String dayName);
    public Weather getDayWeatherForCity(String sourceCity, String dayName);
    public Day getDayWeatherForAllCities(String dayName);
    public City getCityForDay(Day day, String sourceCity);
}
