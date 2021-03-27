package pl.xArisen67.PlanTripApplication.exceptions;

public class UpdateDataException extends Exception{
    public UpdateDataException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
