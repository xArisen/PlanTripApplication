package pl.xArisen67.PlanTripApplication.services.externalData;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.models.weather.City;
import pl.xArisen67.PlanTripApplication.models.weather.Day;
import pl.xArisen67.PlanTripApplication.models.weather.Weather;
import pl.xArisen67.PlanTripApplication.models.weather.Week;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonFormatter;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonMapper;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonReader;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.WeatherService;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ExternalApi1WeatherService implements WeatherService {
    private Week week;
    private String weatherDataUrl;

    //default takes Company1 data
    public ExternalApi1WeatherService(){
        weatherDataUrl = Company1.WEATHER_DATA_URL.toString();
        updateWeatherData();
    }

    public ExternalApi1WeatherService(String weatherDataUrl){
        changeWeatherDataUrl(weatherDataUrl);
    }

    @Override
    public void changeWeatherDataUrl(String weatherDataUrl) {
        this.weatherDataUrl = weatherDataUrl;
        updateWeatherData();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5) //Refresh time every 5 minutes
    private void updateWeatherData(){
        String urlJsonWeatherData = JsonReader.readJsonFromUrl(weatherDataUrl);
        String resString = JsonFormatter.addTypeToJsonData(urlJsonWeatherData, "week");
        week = (Week) JsonMapper.mapJsonToObject(resString, week);
    }

    @Override
    public boolean checkIfWeatherForDayAndCityIsOkForBike(String sourceCity, String dayName){
        Weather weather = getDayWeatherForCity(sourceCity, dayName);
        if((weather.getTemperature() >= 10) && (weather.getTemperature() <= 25)){
            if (weather.getProbabilityOfPrecipitation() < 50){
                if(weather.getWind() < 70){
                    return true;
                }
            }
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
        Optional<Day> day = Arrays.stream(week.getDays())
                .filter(anyDay -> anyDay.getDay().equals(dayName)).findAny();

        if (day.isPresent()){
            return day.get();
        }
        else {
            throw new IllegalArgumentException("Invalid day.");
        }
    }

    @Override
    public City getCityForDay(Day day, String sourceCity){
        Optional<City> city = Arrays.stream(day.getCities())
                .filter(anyCity -> anyCity.getCity().equals(sourceCity)).findAny();

        if (city.isPresent()){
            return city.get();
        }
        else {
            throw new IllegalArgumentException("Invalid city.");
        }
    }
}
