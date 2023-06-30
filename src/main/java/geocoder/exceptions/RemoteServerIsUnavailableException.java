package geocoder.exceptions;

public class RemoteServerIsUnavailableException extends RuntimeException {
    public RemoteServerIsUnavailableException() {
    }

    public RemoteServerIsUnavailableException(String message) {
        super(message);
    }

    public RemoteServerIsUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteServerIsUnavailableException(Throwable cause) {
        super(cause);
    }
}
