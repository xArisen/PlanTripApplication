package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JsonFormatterTest {


    private static String correctJsonInString;
    private static String wrongJsonInString;
    private static String emptyJsonInString;
    private static String typeName = "person";

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
        String exampleJson = correctJsonInString;
        String changedExampleJson = JsonFormatter.addTypeToJsonDataInTheBeginning(exampleJson, typeName);
        String addedString = "\"@type\": \""+typeName+"\", ";

        assertTrue(changedExampleJson.startsWith("{"+addedString));
        assertEquals(changedExampleJson.replace(addedString, ""), exampleJson);
    }

    @Test
    public void whenWrongJsonWithTypeGiven_thenReturnException(){
        String exampleJson = wrongJsonInString;
        try{
            JsonFormatter.addTypeToJsonDataInTheBeginning(exampleJson, typeName);
        }catch (IllegalArgumentException e){
            String message = "Entered string is not a json.";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void whenEmptyJsonWithTypeGiven_thenAddTypeVariableInTheBeginning(){
        String exampleJson = emptyJsonInString;
        String changedExampleJson = JsonFormatter.addTypeToJsonDataInTheBeginning(exampleJson, typeName);
        String addedString = "\"@type\": \""+typeName+"\"";

        assertTrue(changedExampleJson.startsWith("{"+addedString));
        assertEquals(changedExampleJson.replace(addedString, ""), exampleJson);
    }
}
