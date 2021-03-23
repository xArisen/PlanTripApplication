package pl.xArisen67.PlanTripApplication.services.externalData.api1;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;
import pl.xArisen67.PlanTripApplication.exceptions.JsonToObjectMappingException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Transportation;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.City;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Day;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Weather;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Week;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonFormatter;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonMapper;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.ExternalDataReader;
import pl.xArisen67.PlanTripApplication.services.externalData.providers.Company1;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.WeatherService;

import java.util.Arrays;

@Service
public class WeatherApi1Service implements WeatherService {
    private Week week;
    private String weatherDataUrl;

    //default takes Company1 data
    public WeatherApi1Service(){
        weatherDataUrl = Company1.WEATHER_DATA_URL.toString();
        updateWeatherData();
    }

    public WeatherApi1Service(String weatherDataUrl){
        changeWeatherDataUrl(weatherDataUrl);
    }

    @Override
    public void changeWeatherDataUrl(String weatherDataUrl) {
        this.weatherDataUrl = weatherDataUrl;
        updateWeatherData();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5) //Refresh time every 5 minutes
    private void updateWeatherData(){
        String urlJsonWeatherData = "";
        try {
        urlJsonWeatherData = ExternalDataReader.readStringFromUrl(weatherDataUrl);
        }catch (GettingDataFromUrlException e){
            //TODO
        }
        String resString = JsonFormatter.addTypeToJsonDataInTheBeginning(urlJsonWeatherData, "week");
        try{
            week = (Week) JsonMapper.mapJsonToObject(resString, week);
        }catch (JsonToObjectMappingException e){
            //TODO
        }
    }

    @Override
    public boolean checkIfWeatherForDayAndCityIsOkForBike(String sourceCity, String dayName){
        Weather weather = getDayWeatherForCity(sourceCity, dayName);
        if((weather.getTemperature() >= 10) && (weather.getTemperature() <= 25) && (weather.getProbabilityOfPrecipitation() < 50) && (weather.getWind() < 70)){
            return true;
        }
        return false;
    }

    @Override
    public Weather getDayWeatherForCity(String sourceCity, String dayName){
        Day day = getDayWeatherForAllCities(dayName);
        City city = getCityForDay(day, sourceCity);

        return city.getWeather();
    }

    @Override
    public Day getDayWeatherForAllCities(String dayName){
        Day day = Arrays.stream(week.getDays())
                .filter(anyDay -> anyDay.getDay().equals(dayName))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Invalid day."));

        return day;
    }

    @Override
    public City getCityForDay(Day day, String sourceCity){
        City city = Arrays.stream(day.getCities())
                .filter(anyCity -> anyCity.getCity().equals(sourceCity)).findAny().orElseThrow(() -> new IllegalArgumentException("Invalid city."));

        return city;
    }
}
