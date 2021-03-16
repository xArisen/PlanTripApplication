package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JsonMapperTest {

    private static String jsonWeekDataWithType;
    private static String jsonTransportationDataWithType;
    private static String jsonDistanceCollectionDataWithType;

    @BeforeAll
    public static void setUp(){
        jsonWeekDataWithType = "{\"@type\": \"distances\", \"distances\":[{\"source\":\"Szczecin\",\"destination\":\"Gdansk\",\"distance\":343},{\"source\":\"Szczecin\",\"destination\":\"Bialystok\",\"distance\":662},{\"source\":\"Szczecin\",\"destination\":\"Warszawa\",\"distance\":530},{\"source\":\"Szczecin\",\"destination\":\"Poznan\",\"distance\":234},{\"source\":\"Szczecin\",\"destination\":\"Krakow\",\"distance\":617},{\"source\":\"Krakow\",\"destination\":\"Poznan\",\"distance\":394},{\"source\":\"Krakow\",\"destination\":\"Gdansk\",\"distance\":601},{\"source\":\"Krakow\",\"destination\":\"Warszawa\",\"distance\":295},{\"source\":\"Krakow\",\"destination\":\"Bialystok\",\"distance\":505},{\"source\":\"Bialystok\",\"destination\":\"Gdansk\",\"distance\":376},{\"source\":\"Bialystok\",\"destination\":\"Poznan\",\"distance\":498},{\"source\":\"Bialystok\",\"destination\":\"Warszawa\",\"distance\":206},{\"source\":\"Gdansk\",\"destination\":\"Poznan\",\"distance\":292},{\"source\":\"Gdansk\",\"destination\":\"Warszawa\",\"distance\":662},{\"source\":\"Warszawa\",\"destination\":\"Poznan\",\"distance\":326}]}";
        jsonTransportationDataWithType = "{\"@type\": \"transportation\", \"timetable\":[{\"source\":\"Szczecin\",\"destination\":\"Gdansk\",\"travels\":[{\"departureTime\":\"09:14\",\"destinationTime\":\"13:18\"}]},{\"source\":\"Szczecin\",\"destination\":\"Krakow\",\"travels\":[{\"departureTime\":\"15:19\",\"destinationTime\":\"22:06\"}]},{\"source\":\"Szczecin\",\"destination\":\"Warszawa\",\"travels\":[{\"departureTime\":\"17:36\",\"destinationTime\":\"23:42\"}]},{\"source\":\"Warszawa\",\"destination\":\"Szczecin\",\"travels\":[{\"departureTime\":\"09:07\",\"destinationTime\":\"15:13\"},{\"departureTime\":\"17:12\",\"destinationTime\":\"23:18\"}]},{\"source\":\"Poznan\",\"destination\":\"Szczecin\",\"travels\":[{\"departureTime\":\"12:07\",\"destinationTime\":\"15:09\"},{\"departureTime\":\"17:13\",\"destinationTime\":\"20:15\"}]},{\"source\":\"Szczecin\",\"destination\":\"Poznan\",\"travels\":[{\"departureTime\":\"09:23\",\"destinationTime\":\"12:25\"},{\"departureTime\":\"18:29\",\"destinationTime\":\"21:31\"}]},{\"source\":\"Bialystok\",\"destination\":\"Krakow\",\"travels\":[{\"departureTime\":\"09:07\",\"destinationTime\":\"14:37\"},{\"departureTime\":\"13:23\",\"destinationTime\":\"18:53\"}]},{\"source\":\"Warszawa\",\"destination\":\"Krakow\",\"travels\":[{\"departureTime\":\"10:10\",\"destinationTime\":\"13:43\"},{\"departureTime\":\"13:15\",\"destinationTime\":\"16:48\"}]},{\"source\":\"Krakow\",\"destination\":\"Warszawa\",\"travels\":[{\"departureTime\":\"07:13\",\"destinationTime\":\"10:46\"}]},{\"source\":\"Gdansk\",\"destination\":\"Bialystok\",\"travels\":[{\"departureTime\":\"16:34\",\"destinationTime\":\"20:68\"}]},{\"source\":\"Bialystok\",\"destination\":\"Gdansk\",\"travels\":[{\"departureTime\":\"12:19\",\"destinationTime\":\"16:53\"},{\"departureTime\":\"13:16\",\"destinationTime\":\"17:50\"}]},{\"source\":\"Krakow\",\"destination\":\"Poznan\",\"travels\":[{\"departureTime\":\"08:03\",\"destinationTime\":\"13:06\"},{\"departureTime\":\"14:23\",\"destinationTime\":\"19:26\"}]},{\"source\":\"Poznan\",\"destination\":\"Warszawa\",\"travels\":[{\"departureTime\":\"11:22\",\"destinationTime\":\"17:28\"},{\"departureTime\":\"13:37\",\"destinationTime\":\"19:43\"}]},{\"source\":\"Gdansk\",\"destination\":\"Warszawa\",\"travels\":[{\"departureTime\":\"09:17\",\"destinationTime\":\"12:56\"},{\"departureTime\":\"18:05\",\"destinationTime\":\"21:44\"}]},{\"source\":\"Warszawa\",\"destination\":\"Gdansk\",\"travels\":[{\"departureTime\":\"07:36\",\"destinationTime\":\"11:15\"},{\"departureTime\":\"14:15\",\"destinationTime\":\"17:54\"}]},{\"source\":\"Poznan\",\"destination\":\"Gdansk\",\"travels\":[{\"departureTime\":\"11:09\",\"destinationTime\":\"14:42\"},{\"departureTime\":\"14:13\",\"destinationTime\":\"17:46\"}]},{\"source\":\"Gdansk\",\"destination\":\"Poznan\",\"travels\":[{\"departureTime\":\"08:36\",\"destinationTime\":\"12:09\"},{\"departureTime\":\"18:02\",\"destinationTime\":\"21:35\"}]},{\"source\":\"Szczecin\",\"destination\":\"Bialystok\",\"travels\":[{\"departureTime\":\"12:12\",\"destinationTime\":\"19:12\"},{\"departureTime\":\"15:12\",\"destinationTime\":\"22:12\"}]},{\"source\":\"Warszawa\",\"destination\":\"Bialystok\",\"travels\":[{\"departureTime\":\"08:34\",\"destinationTime\":\"10:36\"},{\"departureTime\":\"14:34\",\"destinationTime\":\"16:36\"}]},{\"source\":\"Bialystok\",\"destination\":\"Warszawa\",\"travels\":[{\"departureTime\":\"18:21\",\"destinationTime\":\"20:23\"}]}]}";
        jsonDistanceCollectionDataWithType = "{\"@type\": \"week\", \"days\":[{\"day\":\"monday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":159,\"temperature\":2,\"probabilityOfPrecipitation\":60}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":5,\"temperature\":-26,\"probabilityOfPrecipitation\":71}},{\"city\":\"Krakow\",\"weather\":{\"wind\":171,\"temperature\":13,\"probabilityOfPrecipitation\":6}},{\"city\":\"Lublin\",\"weather\":{\"wind\":126,\"temperature\":8,\"probabilityOfPrecipitation\":75}},{\"city\":\"Poznan\",\"weather\":{\"wind\":14,\"temperature\":1,\"probabilityOfPrecipitation\":43}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":189,\"temperature\":-28,\"probabilityOfPrecipitation\":13}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":196,\"temperature\":-27,\"probabilityOfPrecipitation\":64}}]},{\"day\":\"tuesday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":68,\"temperature\":-26,\"probabilityOfPrecipitation\":25}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":21,\"temperature\":-21,\"probabilityOfPrecipitation\":20}},{\"city\":\"Krakow\",\"weather\":{\"wind\":71,\"temperature\":4,\"probabilityOfPrecipitation\":67}},{\"city\":\"Lublin\",\"weather\":{\"wind\":44,\"temperature\":-3,\"probabilityOfPrecipitation\":36}},{\"city\":\"Poznan\",\"weather\":{\"wind\":63,\"temperature\":11,\"probabilityOfPrecipitation\":97}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":187,\"temperature\":16,\"probabilityOfPrecipitation\":57}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":152,\"temperature\":-13,\"probabilityOfPrecipitation\":25}}]},{\"day\":\"wednesday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":197,\"temperature\":-13,\"probabilityOfPrecipitation\":53}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":138,\"temperature\":0,\"probabilityOfPrecipitation\":88}},{\"city\":\"Krakow\",\"weather\":{\"wind\":135,\"temperature\":-15,\"probabilityOfPrecipitation\":4}},{\"city\":\"Lublin\",\"weather\":{\"wind\":176,\"temperature\":-6,\"probabilityOfPrecipitation\":67}},{\"city\":\"Poznan\",\"weather\":{\"wind\":54,\"temperature\":27,\"probabilityOfPrecipitation\":94}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":90,\"temperature\":-16,\"probabilityOfPrecipitation\":34}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":124,\"temperature\":-15,\"probabilityOfPrecipitation\":99}}]},{\"day\":\"thursday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":75,\"temperature\":-20,\"probabilityOfPrecipitation\":55}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":16,\"temperature\":7,\"probabilityOfPrecipitation\":6}},{\"city\":\"Krakow\",\"weather\":{\"wind\":125,\"temperature\":13,\"probabilityOfPrecipitation\":9}},{\"city\":\"Lublin\",\"weather\":{\"wind\":192,\"temperature\":8,\"probabilityOfPrecipitation\":44}},{\"city\":\"Poznan\",\"weather\":{\"wind\":103,\"temperature\":25,\"probabilityOfPrecipitation\":88}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":33,\"temperature\":27,\"probabilityOfPrecipitation\":0}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":187,\"temperature\":10,\"probabilityOfPrecipitation\":87}}]},{\"day\":\"friday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":183,\"temperature\":20,\"probabilityOfPrecipitation\":87}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":15,\"temperature\":-17,\"probabilityOfPrecipitation\":57}},{\"city\":\"Krakow\",\"weather\":{\"wind\":20,\"temperature\":-13,\"probabilityOfPrecipitation\":51}},{\"city\":\"Lublin\",\"weather\":{\"wind\":101,\"temperature\":4,\"probabilityOfPrecipitation\":1}},{\"city\":\"Poznan\",\"weather\":{\"wind\":60,\"temperature\":-23,\"probabilityOfPrecipitation\":82}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":49,\"temperature\":0,\"probabilityOfPrecipitation\":62}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":73,\"temperature\":-27,\"probabilityOfPrecipitation\":57}}]},{\"day\":\"saturday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":172,\"temperature\":-11,\"probabilityOfPrecipitation\":61}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":22,\"temperature\":19,\"probabilityOfPrecipitation\":16}},{\"city\":\"Krakow\",\"weather\":{\"wind\":163,\"temperature\":10,\"probabilityOfPrecipitation\":30}},{\"city\":\"Lublin\",\"weather\":{\"wind\":151,\"temperature\":10,\"probabilityOfPrecipitation\":7}},{\"city\":\"Poznan\",\"weather\":{\"wind\":122,\"temperature\":-7,\"probabilityOfPrecipitation\":4}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":6,\"temperature\":20,\"probabilityOfPrecipitation\":57}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":104,\"temperature\":-10,\"probabilityOfPrecipitation\":73}}]},{\"day\":\"sunday\",\"cities\":[{\"city\":\"Bialystok\",\"weather\":{\"wind\":36,\"temperature\":-29,\"probabilityOfPrecipitation\":59}},{\"city\":\"Gdansk\",\"weather\":{\"wind\":66,\"temperature\":11,\"probabilityOfPrecipitation\":83}},{\"city\":\"Krakow\",\"weather\":{\"wind\":135,\"temperature\":-8,\"probabilityOfPrecipitation\":44}},{\"city\":\"Lublin\",\"weather\":{\"wind\":43,\"temperature\":-29,\"probabilityOfPrecipitation\":35}},{\"city\":\"Poznan\",\"weather\":{\"wind\":48,\"temperature\":-22,\"probabilityOfPrecipitation\":98}},{\"city\":\"Szczecin\",\"weather\":{\"wind\":37,\"temperature\":25,\"probabilityOfPrecipitation\":63}},{\"city\":\"Warszawa\",\"weather\":{\"wind\":26,\"temperature\":26,\"probabilityOfPrecipitation\":28}}]}]}";
    }

    @Test
    public static void whenJsonStringAndObjectGiven_thenMapItToObjectOfGivenType(){
        //TODO
    }

    @Test
    public static void whenJsonMappingToObjectGoesWrong_thenReturnException(){
        //TODO
    }

    @Test
    public void whenNameAndValueGiven_thenMapItToHashMap(){
        String name = "time";
        int value = 30;
        Map<String, Integer> expectedResult = new HashMap<String, Integer>();
        expectedResult.put(name, value);

        Map<String, Integer> result = JsonMapper.mapVariableToHashMap(name, value);

        Assertions.assertEquals(expectedResult.get(name), result.get(name));
    }
}
