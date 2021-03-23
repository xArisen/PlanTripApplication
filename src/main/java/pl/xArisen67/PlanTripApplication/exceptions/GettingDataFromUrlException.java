package pl.xArisen67.PlanTripApplication.exceptions;
/**
 * @throws GettingDataFromUrlException if getting data from created url connection fails.
 */
public class GettingDataFromUrlException extends Exception{
    public GettingDataFromUrlException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
