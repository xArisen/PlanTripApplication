package pl.xArisen67.PlanTripApplication.services.externalData.api1;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;
import pl.xArisen67.PlanTripApplication.exceptions.JsonToObjectMappingException;
import pl.xArisen67.PlanTripApplication.exceptions.UpdateDataException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.City;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Day;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Weather;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Week;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.ExternalApiConnector;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.ExternalApiStringReader;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonFormatter;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonMapper;
import pl.xArisen67.PlanTripApplication.services.externalData.providers.Company1;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.WeatherService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Service
public class WeatherApi1Service implements WeatherService {
    private Week week;
    private URL weatherDataUrl;

    //default takes Company1 data
    public WeatherApi1Service() throws MalformedURLException, UpdateDataException{
        changeWeatherDataUrl(Company1.WEATHER_DATA_URL.toString());
        updateWeatherData();
    }

    public WeatherApi1Service(String weatherDataUrl) throws MalformedURLException, UpdateDataException{
        changeWeatherDataUrl(weatherDataUrl);
    }

    @Override
    public void changeWeatherDataUrl(String weatherDataUrl) throws MalformedURLException, UpdateDataException{
        this.weatherDataUrl = new URL(weatherDataUrl);
        updateWeatherData();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5) //Refresh time every 5 minutes
    private void updateWeatherData()throws UpdateDataException{
        try{
            String urlJsonWeatherData = ExternalApiStringReader.readStringFromConnection(
                    ExternalApiConnector.getHttpUrlConnection(weatherDataUrl)
            );
            String resString = JsonFormatter.addTypeToJsonDataInTheBeginning(urlJsonWeatherData, "week");
            week = (Week) JsonMapper.mapJsonToObject(resString, week);
        }catch (GettingDataFromUrlException | JsonToObjectMappingException | IOException e){
            throw new UpdateDataException("Updating local data, by using external API failed.", e);
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
