package epiprot.services.ssp;

public class JPredSequenceTooLongException extends Exception {

	private String message = null;
	 
    public JPredSequenceTooLongException() {
        super();
    }
 
    public JPredSequenceTooLongException(String message) {
        super(message);
        this.message = message;
    }
 
    public JPredSequenceTooLongException(Throwable cause) {
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
