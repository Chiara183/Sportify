package com.example.sportify;

import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import javax.swing.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class QuizTest extends FxRobot {

    private static final Logger LOGGER = Logger.getLogger(QuizTest.class.getName());


    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Desktop' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
    }

    @Before
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

    /** Test that sport information are correct */
    @Test
    public void testSportInfo(){
        clickOn("#sportQuiz");
        clickOn("#age1");
        clickOn("#outdoor");
        clickOn("#group");
        assertEquals("Football", lookup("#sportName").queryLabeled().getText());
        clickOn("#info");
        assertEquals("Football", lookup("#sport").queryLabeled().getText());
        assertEquals("Football is a game in which two teams of 11 players, using any part of their bodies except their hands and arms, try to maneuver the ball into the opposing team’s goal. Only the goalkeeper is permitted to handle the ball and may do so only within the penalty area surrounding the goal. The team that scores more goals wins. Football is the world’s most popular ball game in the number of participants and spectators.", lookup("#sportDescription").queryLabeled().getText());
    }


    /**
     * Test that pressing the button combination "age1,indoor,group" lead to volleyball as answer of the quiz
     */
    @Test
    public void testVolleyball() {
        clickOn("#sportQuiz");
        clickOn("#age2");
        clickOn("#outdoor");
        clickOn("#single");
        assertEquals("Tennis", lookup("#sportName").queryLabeled().getText());
    }

    /**
     * Test that pressing next quiz button without pressing an age button show a warning message
     */
    @Test
    public void testWarning() {
        clickOn("#sportQuiz");
        clickOn("#nextQuiz");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        assertThat(registeredStage.getTitle().contains("Warning"));
    }

}