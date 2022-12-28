package sportify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class StartingTest {

    private static final Logger LOGGER = Logger.getLogger(StartingTest.class.getName());

    @BeforeEach
    abstract public void setUp();

    @AfterEach
    public void tearDown() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

}
