package com.example.sportify;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import javax.swing.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;


class MainAppTest extends FxRobot {

    private static final Logger LOGGER = Logger.getLogger(ApplicationTest.class.getName());


    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Desktop' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
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

    /**Test that the home screen has the correct button*/
    @Test
    public void hasButtonTest() {
        assertThat(lookup("#signIn").queryButton()).hasText("Log in");
        assertThat(lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertThat(lookup("#findGym").queryButton()).hasText("Find gym");
        assertThat(lookup("#sportQuiz").queryButton()).hasText("Sport quiz");
    }

    /**Test that the application is launched correctly*/
    @Test
    public void titleTest(){
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Home", title);
    }


}