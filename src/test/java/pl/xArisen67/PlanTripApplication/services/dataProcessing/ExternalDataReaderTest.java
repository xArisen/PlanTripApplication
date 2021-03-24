package pl.xArisen67.PlanTripApplication.services.dataProcessing;

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
    @Mock
    HttpURLConnection connection;

    @Test
    public void whenHttpUrlConnectionGiven_thenReturnResponseString() throws GettingDataFromUrlException {
        String expectedResponseString = "{  \"userId\": 1,  \"id\": 1,  \"title\": \"delectus aut autem\",  \"completed\": false}";

        try(MockedStatic<ExternalApiStringReader> externalApiStringReaderMockedStatic = mockStatic(ExternalApiStringReader.class)) {
            externalApiStringReaderMockedStatic.when(() -> ExternalApiStringReader.readStringFromConnection(connection))
                    .thenReturn(expectedResponseString);

            assertEquals(expectedResponseString, ExternalApiStringReader.readStringFromConnection(connection));
        }
    }

    @Test
    public void whenReadingStringFromUrlGoesWrong_thenThrowGettingDataFromUrlException(){
        try(MockedStatic<ExternalApiStringReader> externalApiStringReaderMockedStatic = mockStatic(ExternalApiStringReader.class)) {
            externalApiStringReaderMockedStatic.when(() -> ExternalApiStringReader.readStringFromConnection(connection))
                    .thenThrow(new GettingDataFromUrlException("Error during getting data from Url connection.", new IOException()));


            Exception exception = assertThrows(GettingDataFromUrlException.class, () -> {
                ExternalApiStringReader.readStringFromConnection(connection);
            });

            String expectedMessage = "Error during getting data from Url connection.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
    }
}
