package pl.xArisen67.PlanTripApplication.exceptions;

/**
 * @throws WrongResultException if result of function is different, from which it supposed to be
 * (but still proper in terms of returned function type).
 */
public class WrongResultException extends RuntimeException{
    public WrongResultException(String errorMessage) {
        super(errorMessage);
    }
}
