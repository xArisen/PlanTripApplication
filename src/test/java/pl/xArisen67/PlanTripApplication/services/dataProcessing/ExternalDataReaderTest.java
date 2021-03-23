package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


@SpringBootTest
public class ExternalDataReaderTest {

    private static final String CORRECT_TEST_JSON_API_URL = "https://jsonplaceholder.typicode.com/todos/1";
    private static final String WRONG_TEST_JSON_API_URL = "";

    @Mock
    HttpURLConnection connection;

    @Test
    public void whenUrlGiven_thenReturnHttpURLConnection() throws IOException {
        int expectedResponseCode = 200;

        try(MockedStatic<ExternalDataReader> externalDataReader = mockStatic(ExternalDataReader.class)) {
            externalDataReader.when(() -> ExternalDataReader.getHttpUrlConnection(CORRECT_TEST_JSON_API_URL))
                    .thenReturn(connection);

            externalDataReader.when(() -> connection
                    .getResponseCode()).thenReturn(expectedResponseCode);

            assertEquals(expectedResponseCode, connection.getResponseCode());
        }
    }

    @Test
    public void whenUrlGiven_thenReturnResponseString() throws GettingDataFromUrlException {
        String expectedResponseString = "{  \"userId\": 1,  \"id\": 1,  \"title\": \"delectus aut autem\",  \"completed\": false}";

        try(MockedStatic<ExternalDataReader> externalDataReader = mockStatic(ExternalDataReader.class)) {
            externalDataReader.when(() -> ExternalDataReader.readStringFromUrl(CORRECT_TEST_JSON_API_URL))
                    .thenReturn(expectedResponseString);

            assertEquals(expectedResponseString, ExternalDataReader.readStringFromUrl(CORRECT_TEST_JSON_API_URL));
        }
    }

    @Test
    public void whenReadingStringFromUrlGoesWrong_thenThrowGettingDataFromUrlException(){
        Exception exception = assertThrows(GettingDataFromUrlException.class, () -> {
            ExternalDataReader.readStringFromUrl(WRONG_TEST_JSON_API_URL);
        });

        String expectedMessage = "Error during getting data from Url connection.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
