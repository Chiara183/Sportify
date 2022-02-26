package com.example.sportify;


import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxRobot;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

public class MenuTest extends StartingTest{

    private FxRobot robot;
    private static final String SIGNIN = "#signIn";
    private static final String HOME = "#home";
    private static final String FINDGYM = "#findGym";
    private static final String GYMFINDER = "Gym Finder";
    private static final String SPORTQUIZ = "#sportQuiz";
    private static final String SPORT = "Sport Quiz";


    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Desktop' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
    }

    /** Test the menu buttons without being logged in */
    @Test
    public void hasButtonTest(){
        robot.clickOn(SIGNIN);
        assertThat(robot.lookup(HOME).queryButton()).hasText("Home");
        assertThat(robot.lookup(SIGNIN).queryButton()).hasText("Login");
        assertThat(robot.lookup(FINDGYM).queryButton()).hasText(GYMFINDER);
        assertThat(robot.lookup(SPORTQUIZ).queryButton()).hasText(SPORT);
        assertThat(robot.lookup("#signUp").queryButton()).hasText("Sign Up");
    }

    /** Test the menu buttons being logged in as user */
    @Test
    public void hasButtonLogUserTest(){
        helpMethod();
        assertEquals("Name", robot.lookup("#username").queryLabeled().getText());
    }

    public void helpMethod(){
        robot.clickOn(SIGNIN);
        robot.clickOn("#user").write("Name");
        robot.clickOn("#password").write("name");
        robot.clickOn("#eye");
        robot.clickOn("#submit");
        robot.clickOn(SPORTQUIZ);
        assertThat(robot.lookup(HOME).queryButton()).hasText("Home");
        assertThat(robot.lookup(FINDGYM).queryButton()).hasText(GYMFINDER);
        assertThat(robot.lookup(SPORTQUIZ).queryButton()).hasText(SPORT);
        assertThat(robot.lookup("#signOut").queryButton()).hasText("Sign Out");
    }

    /** Test the menu buttons being logged in as gym */
    @Test
    public void hasButtonLogGymTest(){
        helpMethod();
        assertThat(robot.lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertEquals("Prova", robot.lookup("#username").queryLabeled().getText());
    }

}
