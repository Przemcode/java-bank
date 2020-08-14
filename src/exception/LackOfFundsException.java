package exception;

import java.io.Serializable;

public class LackOfFundsException extends RuntimeException{
    public LackOfFundsException(String message) {
        super(message);
    }
}
