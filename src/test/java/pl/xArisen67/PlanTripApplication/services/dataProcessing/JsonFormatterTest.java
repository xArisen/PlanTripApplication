package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JsonFormatterTest {
    private static String correctJsonInString;
    private static String wrongJsonInString;
    private static String emptyJsonInString;
    private static String typeName;

    private String exampleJson;
    private String typeInJson;
    private String changedExampleJson;

    @BeforeAll
    public static void setUp(){
        correctJsonInString = "{ \"name\": \"John\", \"age\": \"31\", \"city\": \"New York\" }";
        wrongJsonInString = "{ name: \"John\", \"age\": \"31\", \"city\": \"New York\" }";
        emptyJsonInString = "{}";
        typeName = "person";
    }

    @Test
    public void checkIfGivenStringIsJson(){
        assertTrue(JsonFormatter.checkIfStringIsValidJson(correctJsonInString));
        assertFalse(JsonFormatter.checkIfStringIsValidJson(wrongJsonInString));
        assertTrue(JsonFormatter.checkIfStringIsValidJson(emptyJsonInString));
    }

    @Test
    public void whenCorrectJsonWithTypeGiven_thenAddTypeVariableInTheBeginning(){
        exampleJson = correctJsonInString;
        changeTypeInJsonForNotEmptyJson();

        changedExampleJson = JsonFormatter.addTypeToJsonDataInTheBeginning(exampleJson, typeName);

        assertThatTypeIsAddedToJsonStringCorrectly();
    }

    @Test
    public void whenEmptyJsonWithTypeGiven_thenAddTypeVariableInTheBeginning(){
        exampleJson = emptyJsonInString;
        changeTypeInJsonForEmptyJson();

        changedExampleJson = JsonFormatter.addTypeToJsonDataInTheBeginning(exampleJson, typeName);

        assertThatTypeIsAddedToJsonStringCorrectly();
    }

    private void assertThatTypeIsAddedToJsonStringCorrectly() {
        assertAll(() -> {
            assertTrue(changedExampleJson.startsWith("{" + typeInJson));
            assertEquals(changedExampleJson.replace(typeInJson, ""), exampleJson);
        });
    }

    private void changeTypeInJsonForEmptyJson(){
        typeInJson = "\"@type\": \""+typeName+"\"";
    }

    private void changeTypeInJsonForNotEmptyJson(){
        typeInJson = "\"@type\": \""+typeName+"\", ";
    }

    @Test
    public void whenWrongJsonWithTypeGiven_thenReturnIllegalArgumentException(){
        exampleJson = wrongJsonInString;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            JsonFormatter.addTypeToJsonDataInTheBeginning(exampleJson, typeName);
        });

        String expectedMessage = "Entered string is not a json.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
