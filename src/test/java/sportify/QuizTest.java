package sportify;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class QuizTest extends StartingTest{

    private static final String QUIZ = "#sportQuiz";
    private final FxRobot robot;

    public QuizTest(FxRobot robot) {
        this.robot = robot;
    }

    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Desktop' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
    }


    /** Test that sport information are correct */
    @Test
    public void testSportInfo(){
        robot.clickOn(QUIZ);
        robot.clickOn("#age1");
        robot.clickOn("#outdoor");
        robot.clickOn("#group");
        assertEquals("Football", robot.lookup("#sportName").queryLabeled().getText());
        robot.clickOn("#info");
        assertEquals("Football", robot.lookup("#sport").queryLabeled().getText());
        assertEquals("Football is a game in which two teams of 11 players, using any part of their bodies except their hands and arms, try to maneuver the ball into the opposing team’s goal. Only the goalkeeper is permitted to handle the ball and may do so only within the penalty area surrounding the goal. The team that scores more goals wins. Football is the world’s most popular ball game in the number of participants and spectators.", robot.lookup("#sportDescription").queryLabeled().getText());
    }


    /**
     * Test that pressing the button combination "age1,indoor,group" lead to volleyball as answer of the quiz
     */
    @Test
    public void testVolleyball() {
        robot.clickOn(QUIZ);
        robot.clickOn("#age2");
        robot.clickOn("#outdoor");
        robot.clickOn("#single");
        assertEquals("Tennis", robot.lookup("#sportName").queryLabeled().getText());
    }

    /**
     * Test that pressing next quiz button without pressing an age button show a warning message
     */
    @Test
    public void testWarning() {
        robot.clickOn(QUIZ);
        robot.clickOn("#nextQuiz");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        assertThat(registeredStage.getTitle().contains("Warning"));
    }

}