package com.example.sportify;

import javafx.application.Application;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;

public abstract class ApplicationTest extends FxRobot {
    public static void launch(Class<? extends Application> appClass, String... appArgs){
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.setupApplication(appClass, appArgs);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
