package geocoder.exceptions;

public class GeolocationServerException extends RuntimeException {
    public GeolocationServerException() {
    }

    public GeolocationServerException(String message) {
        super(message);
    }

    public GeolocationServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeolocationServerException(Throwable cause) {
        super(cause);
    }
}
