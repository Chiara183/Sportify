package com.example.sportify;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import javax.swing.*;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

public class MenuTest extends FxRobot {

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
            e.printStackTrace();
        }
    }

    /** Test the menu buttons without being logged in */
    @Test
    public void hasButtonTest(){
        clickOn("#signIn");
        assertThat(lookup("#home").queryButton()).hasText("Home");
        assertThat(lookup("#signIn").queryButton()).hasText("Login");
        assertThat(lookup("#findGym").queryButton()).hasText("Gym Finder");
        assertThat(lookup("#sportQuiz").queryButton()).hasText("Sport Quiz");
        assertThat(lookup("#signUp").queryButton()).hasText("Sign Up");
    }

    /** Test the menu buttons being logged in as user */
    @Test
    public void hasButtonLogUserTest(){
        clickOn("#signIn");
        clickOn("#user").write("Name");
        clickOn("#password").write("name");
        clickOn("#eye");
        clickOn("#submit");
        clickOn("#sportQuiz");
        assertThat(lookup("#home").queryButton()).hasText("Home");
        assertThat(lookup("#findGym").queryButton()).hasText("Gym Finder");
        assertThat(lookup("#sportQuiz").queryButton()).hasText("Sport Quiz");
        assertThat(lookup("#signOut").queryButton()).hasText("Sign Out");
        assertEquals("Name", lookup("#username").queryLabeled().getText());
    }

    /** Test the menu buttons being logged in as gym */
    @Test
    public void hasButtonLogGymTest(){
        clickOn("#signIn");
        clickOn("#user").write("Prova");
        clickOn("#password").write("prova");
        clickOn("#eye");
        clickOn("#submit");
        clickOn("#sportQuiz");
        assertThat(lookup("#home").queryButton()).hasText("Home");
        assertThat(lookup("#findGym").queryButton()).hasText("Gym Finder");
        assertThat(lookup("#sportQuiz").queryButton()).hasText("Sport Quiz");
        assertThat(lookup("#signOut").queryButton()).hasText("Sign Out");
        assertThat(lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertEquals("Prova", lookup("#username").queryLabeled().getText());
    }

}
