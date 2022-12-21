package com.example.sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class QuizPhoneTest extends StartingTest {

    private FxRobot robot;
    private static final String COMBOBOX = "#comboActivity";
    private static final String QUIZ = "Take sport quiz";
    private static final String FOOTBALL = "Football";


    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Mobile' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
    }




    @Test
    public void SportQuizTest(){
        robot.clickOn(COMBOBOX).write(QUIZ).press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#age").write("14");
        robot.clickOn("#ok");
        robot.clickOn("#environment").write("outdoor");
        robot.clickOn("#ok");
        robot.clickOn("#type").write("group");
        robot.clickOn("#ok");
        assertEquals(FOOTBALL, robot.lookup("#sportName").queryLabeled().getText());
    }

    @Test
    public void infoSportQuizTest(){
        robot.clickOn(COMBOBOX).write(QUIZ).press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#age").write("14");
        robot.clickOn("#ok");
        robot.clickOn("#environment").write("outdoor");
        robot.clickOn("#ok");
        robot.clickOn("#type").write("group");
        robot.clickOn("#ok");
        assertEquals(FOOTBALL, robot.lookup("#sportName").queryLabeled().getText());
        robot.clickOn("#info");
        assertEquals(FOOTBALL, robot.lookup("#sport").queryLabeled().getText());
        assertThat(robot.lookup("#sportDescription").queryLabeled().getText().contains("Il calcio è uno sport di squadra giocato all'aperto"));
    }

    @Test
    public void wrongInputQuiz(){
        robot.clickOn(COMBOBOX).write(QUIZ).press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#age").write("abc");
        robot.clickOn("#ok");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        assertThat(registeredStage.getTitle().contains("Warning"));
    }
}
