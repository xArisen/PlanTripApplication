package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
public class ExternalApiConnectorTest {

    private static final String CORRECT_TEST_JSON_API_URL_AS_STRING = "https://jsonplaceholder.typicode.com/todos/1";
    private static final String WRONG_TEST_JSON_API_URL_AS_STRING = "htt/jsonplaceholder.typicode.com/todos/1";

    @Mock
    URL url;

    @Mock
    HttpURLConnection connection;

//    @Test
//    public void whenCorrectUrlStringGiven_thenReturnUrl() throws MalformedURLException {
//        URL expectedUrl = new URL(CORRECT_TEST_JSON_API_URL_AS_STRING);
//        URL returnedUrl = ExternalApiConnector.createUrlFromString(CORRECT_TEST_JSON_API_URL_AS_STRING);
//        assertEquals(expectedUrl, returnedUrl);
//    }
//
//    @Test
//    public void whenWrongUrlStringGiven_thenThrowIllegalArgumentException() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            ExternalApiConnector.createUrlFromString(WRONG_TEST_JSON_API_URL_AS_STRING);
//        });
//
//        String expectedMessage = "Url cannot be created from passed string.";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }

    @Test
    public void whenUrlGiven_thenReturnHttpURLConnection() throws IOException {
        int expectedResponseCode = 200;

        try(MockedStatic<ExternalApiConnector> externalApiConnectorMockedStatic = mockStatic(ExternalApiConnector.class)) {
            externalApiConnectorMockedStatic.when(() -> ExternalApiConnector.getHttpUrlConnection(
                    new URL(CORRECT_TEST_JSON_API_URL_AS_STRING)
            )).thenReturn(connection);

            externalApiConnectorMockedStatic.when(() -> connection
                    .getResponseCode()).thenReturn(expectedResponseCode);

            assertEquals(expectedResponseCode, connection.getResponseCode());
        }
    }

    @Test
    public void whenCreatingHttpUrlConnectionFails_thenReturnIOException() {
        try(MockedStatic<ExternalApiConnector> externalApiConnectorMockedStatic = mockStatic(ExternalApiConnector.class)) {
            externalApiConnectorMockedStatic.when(() -> ExternalApiConnector.getHttpUrlConnection(url))
                    .thenThrow(new IOException("Error during creating connection."));
            String expectedMessage = "Error during creating connection.";

            Exception exception = assertThrows(IOException.class, () -> {
                ExternalApiConnector.getHttpUrlConnection(url);
            });
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
    }
}
