package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;
import pl.xArisen67.PlanTripApplication.exceptions.JsonToObjectMappingException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.distance.Distance;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.distance.DistanceCollection;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Timetable;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Transportation;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Travel;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.City;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Day;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Weather;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.weather.Week;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JsonMapperTest {
    private static Week week;
    private static Transportation transportation;
    private static DistanceCollection distanceCollection;

    private static String jsonWeekDataWithType;
    private static String jsonTransportationDataWithType;
    private static String jsonDistanceCollectionDataWithType;
    private static String wrongJsonInString;

    @Autowired
    ObjectMapper objectMapper;

    private Week weekObjectFromJson;
    private Transportation transportationObjectFromJson;
    private DistanceCollection distanceCollectionObjectFromJson;

    private Logger jsonMapperLogger;
    private List<ILoggingEvent> logsList;

    @BeforeAll
    public static void setUp() {
        week = new Week(
                new Day[]{
                        new Day("monday",
                                new City[]{
                                        new City("Bialystok",
                                                new Weather(159, 2, 60)),
                                        new City("Gdansk",
                                                new Weather(5, -26, 71)),
                                        new City("Krakow",
                                                new Weather(171, 13, 6)),
                                        new City("Lublin",
                                                new Weather(126, 8, 75)),
                                        new City("Poznan",
                                                new Weather(14, 1, 43)),
                                        new City("Szczecin",
                                                new Weather(189, -28, 13)),
                                        new City("Warszawa",
                                                new Weather(196, -27, 64))
                                }
                        ),
                        new Day("tuesday",
                                new City[]{
                                        new City("Bialystok",
                                                new Weather(68, -26, 25)),
                                        new City("Gdansk",
                                                new Weather(21, -21, 20)),
                                        new City("Krakow",
                                                new Weather(71, 4, 67)),
                                        new City("Lublin",
                                                new Weather(44, -3, 36)),
                                        new City("Poznan",
                                                new Weather(63, 11, 97)),
                                        new City("Szczecin",
                                                new Weather(187, 16, 57)),
                                        new City("Warszawa",
                                                new Weather(152, -13, 25))
                                }
                        )
                }
        );
        transportation = new Transportation(
                new Timetable[]{
                        new Timetable("Szczecin",
                                "Gdansk",
                                new Travel[]{
                                        new Travel("09:14", "13:18")
                                }
                        ),
                        new Timetable("Szczecin",
                                "Krakow",
                                new Travel[]{
                                        new Travel("15:19", "22:06")
                                }
                        ),
                        new Timetable("Szczecin",
                                "Warszawa",
                                new Travel[]{
                                        new Travel("17:36", "23:42"),
                                        new Travel("15:19", "22:06")
                                }
                        )
                }
        );
        distanceCollection = new DistanceCollection(
                new Distance[]{
                        new Distance("Szczecin", "Gdansk", 343),
                        new Distance("Szczecin", "Bialystok", 662),
                        new Distance("Szczecin", "Warszawa", 530),
                        new Distance("Szczecin", "Poznan", 234)
                }
        );

        jsonWeekDataWithType = "{\"@type\": \"week\", \"days\":[{\"day\":\"monday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":159,\"temperature\":2,\"probabilityOfPrecipitation\":60}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":5,\"temperature\":-26,\"probabilityOfPrecipitation\":71}},{\"city\":\"Krakow\",\"weather\":{\"wind\":171,\"temperature\":13,\"probabilityOfPrecipitation\":6}},{\"city\":\"Lublin\",\"weather\":{\"wind\":126,\"temperature\":8,\"probabilityOfPrecipitation\":75}},{\"city\":\"Poznan\",\"weather\":{\"wind\":14,\"temperature\":1,\"probabilityOfPrecipitation\":43}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":189,\"temperature\":-28,\"probabilityOfPrecipitation\":13}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":196,\"temperature\":-27,\"probabilityOfPrecipitation\":64}}]},{\"day\":\"tuesday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":68,\"temperature\":-26,\"probabilityOfPrecipitation\":25}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":21,\"temperature\":-21,\"probabilityOfPrecipitation\":20}},{\"city\":\"Krakow\",\"weather\":{\"wind\":71,\"temperature\":4,\"probabilityOfPrecipitation\":67}},{\"city\":\"Lublin\",\"weather\":{\"wind\":44,\"temperature\":-3,\"probabilityOfPrecipitation\":36}},{\"city\":\"Poznan\",\"weather\":{\"wind\":63,\"temperature\":11,\"probabilityOfPrecipitation\":97}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":187,\"temperature\":16,\"probabilityOfPrecipitation\":57}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":152,\"temperature\":-13,\"probabilityOfPrecipitation\":25}}]}]}";
        jsonTransportationDataWithType = "{\"@type\": \"transportation\", \"timetable\":[{\"source\":\"Szczecin\",\"destination\":\"Gdansk\",\"travels\":[{\"departureTime\":\"09:14\",\"destinationTime\":\"13:18\"}]},{\"source\":\"Szczecin\",\"destination\":\"Krakow\",\"travels\":[{\"departureTime\":\"15:19\",\"destinationTime\":\"22:06\"}]},{\"source\":\"Szczecin\",\"destination\":\"Warszawa\",\"travels\":[{\"departureTime\":\"17:36\",\"destinationTime\":\"23:42\"}, {\"departureTime\":\"15:19\",\"destinationTime\":\"22:06\"}]}]}";
        jsonDistanceCollectionDataWithType = "{\"@type\": \"distances\", \"distances\":[{\"source\":\"Szczecin\",\"destination\":\"Gdansk\",\"distance\":343},{\"source\":\"Szczecin\",\"destination\":\"Bialystok\",\"distance\":662},{\"source\":\"Szczecin\",\"destination\":\"Warszawa\",\"distance\":530},{\"source\":\"Szczecin\",\"destination\":\"Poznan\",\"distance\":234}]}";
        wrongJsonInString = "{name: \"John\", \"age\": \"31\", \"city\": \"New York\" }";
    }

    @Test
    public void whenJsonStringAndObjectGiven_thenMapItToObjectOfGivenType()  throws JsonToObjectMappingException{
        createObjectsFromJsonString();

        assertIfObjectsCreatedFromJsonStringAndPreCreatedObjectsAreEqual();
    }

    private void createObjectsFromJsonString() throws JsonToObjectMappingException {
        weekObjectFromJson = (Week) JsonMapper.mapJsonToObject(jsonWeekDataWithType, new Week());
        transportationObjectFromJson = (Transportation) JsonMapper.mapJsonToObject(jsonTransportationDataWithType, new Transportation());
        distanceCollectionObjectFromJson = (DistanceCollection) JsonMapper.mapJsonToObject(jsonDistanceCollectionDataWithType, new DistanceCollection());
    }

    private void assertIfObjectsCreatedFromJsonStringAndPreCreatedObjectsAreEqual() {
        assertEquals(week, weekObjectFromJson);
        assertEquals(transportation, transportationObjectFromJson);
        assertEquals(distanceCollection, distanceCollectionObjectFromJson);
    }

    @Test
    public void whenJsonMappingToObjectGoesWrong_thenThrowJsonToObjectMappingException(){
        Exception exception = assertThrows(JsonToObjectMappingException.class, () -> {
            JsonMapper.mapJsonToObject(wrongJsonInString, new Week());
        });

        String expectedMessage = "Error during mapping Json string to object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //TODO delete comments

/*    @Test
    public void whenJsonMappingToObjectGoesWrong_thenLogError() {
        createJsonMapperLogger();
        attachLogsListToJsonMapperLogger();

        JsonMapper.mapJsonToObject(wrongJsonInString, new Week());

        assertIfMapJsonToObjectMethodLoggedCorrectMessage();
    }*/

    private void createJsonMapperLogger() {
        jsonMapperLogger = (Logger) LoggerFactory.getLogger(JsonMapper.class);
    }

    private void attachLogsListToJsonMapperLogger(){
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        logsList = listAppender.list;
        jsonMapperLogger.addAppender(listAppender);

        listAppender.start();
    }

    private void assertIfMapJsonToObjectMethodLoggedCorrectMessage() {
        assertAll(() -> {
            assertEquals("Context message", logsList.get(0)
                    .getMessage());
            assertEquals(Level.ERROR, logsList.get(0)
                    .getLevel());
        });
    }

    @Test
    public void whenNameAndValueGiven_thenMapItToHashMap() {
        String name = "time";
        int value = 30;
        Map<String, Integer> expectedResult = new HashMap<String, Integer>();
        expectedResult.put(name, value);

        Map<String, Integer> result = JsonMapper.mapVariableToHashMap(name, value);

        assertEquals(expectedResult.get(name), result.get(name));
    }
}
