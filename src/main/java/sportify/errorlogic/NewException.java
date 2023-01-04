package sportify.errorlogic;

public class NewException extends Exception{

        public NewException (String message){
            super("message : " + message);
        }

        public NewException (String message, Throwable cause) {
            super("Message: " + message + "\nCause: ", cause);
        }
}
