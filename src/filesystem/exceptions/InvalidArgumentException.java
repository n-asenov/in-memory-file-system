package filesystem.exceptions;

public class InvalidArgumentException extends Exception {
    private static final long serialVersionUID = -4457889502825529824L;

    public InvalidArgumentException(String message) {
	super(message);
    }
    
    public InvalidArgumentException(String message, Throwable cause) {
	super(message, cause);
    }
}
