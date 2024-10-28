package stats;

public class NotEnoughDataException extends RuntimeException {
    public NotEnoughDataException(String message) {
        super(message);
    }
}
