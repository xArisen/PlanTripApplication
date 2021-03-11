package pl.xArisen67.PlanTripApplication.services.dataProcessing;

public class JsonFormatter {
    public static String addTypeToJsonData(String json, String typeName){
        String type = String.format("\"@type\": \"%s\", ", typeName);
        StringBuffer resString = new StringBuffer(json);
        resString.insert(1, type);

        return resString.toString();
    }
}
