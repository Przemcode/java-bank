package exception;

import java.io.Serializable;

public class NoSuchOptionException extends Exception {
    public NoSuchOptionException(String message) {
        super(message);
    }
}
