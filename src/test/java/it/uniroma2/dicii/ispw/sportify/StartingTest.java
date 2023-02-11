package it.uniroma2.dicii.ispw.sportify;

import org.junit.jupiter.api.AfterEach;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class StartingTest {

    protected final FxRobot ROBOT = new FxRobot();
    private static final Logger LOGGER = Logger.getLogger(StartingTest.class.getName());

    @AfterEach
    public void tearDown() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

}
