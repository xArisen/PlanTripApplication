package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.xArisen67.PlanTripApplication.models.ExternalDataObject;

import java.util.HashMap;
import java.util.Map;

public class JsonMapper {
    private  static final ObjectMapper objectMapper = new ObjectMapper();

    public static ExternalDataObject mapJsonToObject(String json, ExternalDataObject object){

        try {
            object = objectMapper.readValue(json, ExternalDataObject.class);
        }catch (Exception e){
            System.out.println(e);
        }

        return object;
    }

    public static Map<String, Integer> mapVariableToObject(String name, int value){
        Map<String, Integer> data = new HashMap<String, Integer>();
        data.put(name, value);
        return data;
    }
}
