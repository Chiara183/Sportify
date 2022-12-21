package com.example.sportify;

import javafx.application.Application;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ApplicationTest extends FxRobot {

    private static final Logger LOGGER = Logger.getLogger(ApplicationTest.class.getName());

    public static void launch(Class<? extends Application> appClass, String... appArgs){
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.setupApplication(appClass, appArgs);
        } catch (TimeoutException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }

    }
}
