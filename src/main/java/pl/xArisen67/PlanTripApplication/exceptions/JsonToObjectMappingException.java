package pl.xArisen67.PlanTripApplication.exceptions;
/**
 * @throws JsonToObjectMappingException if mapping json string to object fails.
 */
public class JsonToObjectMappingException extends Exception{
    public JsonToObjectMappingException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
