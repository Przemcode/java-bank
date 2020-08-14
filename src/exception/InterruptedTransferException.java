package exception;

import java.io.Serializable;

public class InterruptedTransferException extends LackOfFundsException {
    public InterruptedTransferException(String message) {
        super(message);
    }
}
