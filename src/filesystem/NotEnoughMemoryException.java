package filesystem;

public class NotEnoughMemoryException extends Exception {
    private static final long serialVersionUID = 7535404394698873255L;

    public NotEnoughMemoryException(String message) {
	super(message);
    }
}
