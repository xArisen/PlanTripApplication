package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormatter {
    private  static final ObjectMapper objectMapper = new ObjectMapper();

    public static String addTypeToJsonDataInTheBeginning(String jsonInString, String typeName){
        if(checkIfStringIsValidJson(jsonInString)){
            String type = jsonInString.equals("{}") ? String.format("\"@type\": \"%s\"", typeName) : String.format("\"@type\": \"%s\", ", typeName);
            StringBuilder resString = new StringBuilder(jsonInString);
            resString.insert(1, type);
            return resString.toString();
        }else {
            throw new IllegalArgumentException("Entered string is not a json.");
        }
    }

    public static boolean checkIfStringIsValidJson(String jsonInString){
        try {
            objectMapper.readTree(jsonInString);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
