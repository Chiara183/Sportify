package selenium;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class testBaseSelenium {

    private static final Logger LOGGER = Logger.getLogger(testBaseSelenium.class.getName());

    protected static void sleep(long duration) {
        try {
            TimeUnit.MILLISECONDS.sleep(duration);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Error in Thread sleep!");
        }
    }

}
