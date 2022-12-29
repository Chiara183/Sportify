package sportify;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
        robot.clickOn(QUIZ);
        robot.clickOn("#age1");
        robot.clickOn("#outdoor");
        robot.clickOn("#group");
        assertEquals("Football", robot.lookup("#sportName").queryLabeled().getText());
        robot.clickOn("#info");
        assertEquals("Football", robot.lookup("#sport").queryLabeled().getText());
        assertEquals("Association football, more commonly known as simply football or soccer, is a team sport played with a spherical ball between two teams of 11 players. The game is played on a rectangular field called a pitch with a goal at each end. The object of the game is to score more goals than the opposition by moving the ball beyond the goal line into the opposing goal, usually within a time frame of 90 or more minutes.", robot.lookup("#sportDescription").queryLabeled().getText());
    }


    /** Test that pressing the button combination "age1,indoor,group" lead to volleyball as answer of the quiz */
    @Test
    void testVolleyball() {
        robot.clickOn(QUIZ);
        robot.clickOn("#age2");
        robot.clickOn("#outdoor");
        robot.clickOn("#alone");
        assertEquals("Tennis", robot.lookup("#sportName").queryLabeled().getText());
    }

    /** Test that pressing next quiz button without pressing an age button show a warning message */
    @Test
    void testWarning() {
        robot.clickOn(QUIZ);
        robot.clickOn("#nextQuiz");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        Boolean contains = registeredStage.getTitle().contains("Warning");
        assertThat(contains).isTrue();
    }

}