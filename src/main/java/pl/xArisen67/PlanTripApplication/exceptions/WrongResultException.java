package pl.xArisen67.PlanTripApplication.exceptions;

public class WrongResultException extends RuntimeException{
    public WrongResultException(String errorMessage) {
        super(errorMessage);
    }
}
