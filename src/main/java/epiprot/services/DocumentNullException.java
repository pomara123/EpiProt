package epiprot.services;

public class DocumentNullException extends Exception {
	private String message = null;
	 
    public DocumentNullException() {
        super();
    }
 
    public DocumentNullException(String message) {
        super(message);
        this.message = message;
    }
 
    public DocumentNullException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
