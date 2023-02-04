package sportify.errorlogic;

/**
 * The class that implement
 * the new exception
 */
public class NewException extends Exception{

    /**
     * This is a constructor that accepts an
     * argument of type String, representing
     * the error message. The constructor passes
     * the message to the parent class (Exception)
     * using super with a prefix "message: " for
     * the message string.
     *
     * @param message the error message
     */
    public NewException (String message){
        super("message : " + message);
    }

    /**
     * This is a constructor that accepts two
     * arguments: an error message of type String
     * and a cause of type Throwable. The constructor
     * passes the message to the parent class (Exception)
     * using super with a prefix "Message: " and a suffix
     * "\nCause: " for the message string, and also the
     * cause as the second argument. This provides additional
     * information about why the exception was thrown.
     *
     * @param message the error message
     * @param cause the cause of type Throwable
     */
    public NewException (String message, Throwable cause) {
        super("Message: " + message + "\nCause: ", cause);
    }
}
