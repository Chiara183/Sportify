package com.example.sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javax.swing.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class HomePhoneTest  extends FxRobot {

    private static final Logger LOGGER = Logger.getLogger(HomePhoneTest.class.getName());


    @BeforeAll
    static void versionControl() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Mobile' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
    }

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class);
        WaitForAsyncUtils.waitForFxEvents(100);

    }

    @AfterEach
    public void tearDown() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Test
    public void openSportQuizTest() {
        clickOn("#comboActivity").write("Take sport quiz").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Sport Quiz", title);
    }

    @Test
    public void openLoginWindow() {
        clickOn("#comboActivity").write("Login").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
    }

    @Test
    public void openGymFinderWindow() {
        clickOn("#comboActivity").write("Find gym").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Find Gym", title);
    }
}