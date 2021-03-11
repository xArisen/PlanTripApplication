package pl.xArisen67.PlanTripApplication.services.externalData.interfaces;

import pl.xArisen67.PlanTripApplication.models.weather.City;
import pl.xArisen67.PlanTripApplication.models.weather.Day;
import pl.xArisen67.PlanTripApplication.models.weather.Weather;

public interface WeatherService {
    public void changeWeatherDataUrl(String weatherDataUrl);
    public boolean checkIfWeatherForDayAndCityIsOkForBike(String sourceCity, String dayName);
    public Weather getDayWeatherForCity(String sourceCity, String dayName);
    public Day getDayWeatherForAllCities(String dayName);
    public City getCityForDay(Day day, String sourceCity);
}
