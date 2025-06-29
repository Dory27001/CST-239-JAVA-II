package app;


public class CustomFileException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomFileException(String message) {
        super(message);  // Pass the error message to the parent Exception class
    }
}
