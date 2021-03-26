package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.xArisen67.PlanTripApplication.exceptions.JsonToObjectMappingException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.ExternalDataObject;

import java.util.HashMap;
import java.util.Map;

public class JsonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ExternalDataObject mapJsonToObject(String json, ExternalDataObject object) throws JsonToObjectMappingException{
        try {
            object = objectMapper.readValue(json, ExternalDataObject.class);
        }catch (JsonProcessingException e){
            throw new JsonToObjectMappingException("Error during mapping Json string to object.", e);
        }
        return object;
    }

    public static Map<String, Integer> mapVariableToHashMap(String name, int value){
        Map<String, Integer> data = new HashMap<String, Integer>();
        data.put(name, value);
        return data;
    }
}
