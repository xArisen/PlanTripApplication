package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;


@SpringBootTest
public class ExternalDataReaderTest {

    private static final String CORRECT_TEST_JSON_API_URL = "https://jsonplaceholder.typicode.com/todos/1";
    private static final String WRONG_TEST_JSON_API_URL = "";
    private Logger externalDataReaderLogger;
    private List<ILoggingEvent> logsList;

    @Mock
    HttpURLConnection connection;

    @Test
    public void whenUrlGiven_thenReturnHttpURLConnection() throws IOException {
        int expectedResponseCode = 200;

        try(MockedStatic<ExternalDataReader> externalDataReader = mockStatic(ExternalDataReader.class)) {
            externalDataReader.when(() -> ExternalDataReader.getHttpURLConnection(CORRECT_TEST_JSON_API_URL))
                    .thenReturn(connection);

            externalDataReader.when(() -> connection
                    .getResponseCode()).thenReturn(expectedResponseCode);

            Assertions.assertEquals(expectedResponseCode, connection.getResponseCode());
        }
    }

    @Test
    public void whenUrlGiven_thenReturnResponseString(){
        String expectedResponseString = "{  \"userId\": 1,  \"id\": 1,  \"title\": \"delectus aut autem\",  \"completed\": false}";

        try(MockedStatic<ExternalDataReader> externalDataReader = mockStatic(ExternalDataReader.class)) {
            externalDataReader.when(() -> ExternalDataReader.readStringFromUrl(CORRECT_TEST_JSON_API_URL))
                    .thenReturn(expectedResponseString);

            Assertions.assertEquals(expectedResponseString, ExternalDataReader.readStringFromUrl(CORRECT_TEST_JSON_API_URL));
        }
    }

    @Test
    public void whenReadingStringFromUrlGoesWrong_thenLogError(){
        createExternalDataReaderLogger();
        attachLogsListToExternalDataReaderLogger();

        ExternalDataReader.readStringFromUrl(WRONG_TEST_JSON_API_URL);

        assertIfMapJsonToObjectMethodLoggedCorrectMessage();
    }

    private void createExternalDataReaderLogger() {
        externalDataReaderLogger = (Logger) LoggerFactory.getLogger(ExternalDataReader.class);
    }

    private void attachLogsListToExternalDataReaderLogger(){
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        logsList = listAppender.list;
        externalDataReaderLogger.addAppender(listAppender);

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
}
