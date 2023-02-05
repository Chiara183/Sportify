package sportify.errorlogic;

/**
 * An exception class to handle exceptions
 * in the data access layer.
 *
 * @see Exception
 */
public class DAOException extends Exception {

    /**
     * Constructs a new exception with
     * {@code null} as its detail message.
     * The cause is not initialized, and
     * may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DAOException() {
        super();
    }

    /**
     * Constructs a new exception with
     * the specified detail message and cause.
     *
     * @param message the detail message. The
     *               detail message is saved for
     *                later retrieval by the
     *                {@link #getMessage()} method.
     * @param cause the cause (which is saved for
     *              later retrieval by the
     *              {@link #getCause()} method). A
     *              {@code null} value is permitted,
     *              and indicates that the cause is
     *              nonexistent or unknown.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with
     * the specified detail message.
     *
     * @param message the detail message.
     *                The detail message is
     *                saved for later retrieval
     *                by the {@link #getMessage()}
     *                method.
     */
    public DAOException(String message) {
        super(message);
    }
}
