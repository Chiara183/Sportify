package it.uniroma2.dicii.ispw.sportify;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Chiara D'Ambrogio
 */
class QuizTest extends StartingTest{

    private static final String QUIZ = "#sportQuiz";

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "desktop");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    /** Test that sport information are correct */
    @Test
    void testSportInfo(){
        ROBOT.clickOn(QUIZ);
        ROBOT.clickOn("#age1");
        ROBOT.clickOn("#outdoor");
        ROBOT.clickOn("#group");
        assertEquals("Football", ROBOT.lookup("#sportName").queryLabeled().getText());
        ROBOT.clickOn("#info");
        assertEquals("Football", ROBOT.lookup("#sport").queryLabeled().getText());
        assertEquals("Association football, more commonly known as simply football or soccer, is a team sport played with a spherical ball between two teams of 11 players. The game is played on a rectangular field called a pitch with a goal at each end. The object of the game is to score more goals than the opposition by moving the ball beyond the goal line into the opposing goal, usually within a time frame of 90 or more minutes.", ROBOT.lookup("#sportDescription").queryLabeled().getText());
    }


    /** Test that pressing the button combination "age1,indoor,group" lead to volleyball as answer of the quiz */
    @Test
    void testVolleyball() {
        ROBOT.clickOn(QUIZ);
        ROBOT.clickOn("#age2");
        ROBOT.clickOn("#outdoor");
        ROBOT.clickOn("#alone");
        assertEquals("Tennis", ROBOT.lookup("#sportName").queryLabeled().getText());
    }

    /** Test that pressing next quiz button without pressing an age button show a warning message */
    @Test
    void testWarning() {
        ROBOT.clickOn(QUIZ);
        ROBOT.clickOn("#nextQuiz");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        Boolean contains = registeredStage.getTitle().contains("Sportify - Sport Quiz");
        assertThat(contains).isTrue();
    }

}