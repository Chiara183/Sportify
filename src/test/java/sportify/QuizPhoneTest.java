package sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/* Chiara D'Ambrogio */
@ExtendWith(ApplicationExtension.class)
class QuizPhoneTest extends StartingTest {

    private static final String COMBOBOX = "#comboActivity";
    private static final String QUIZ = "Take sport quiz";
    private static final String FOOTBALL = "Football";

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "mobile");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @Test
    void SportQuizTest(){
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
    void infoSportQuizTest(){
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
        Boolean contains = robot.lookup("#sportDescription")
                .queryLabeled().getText()
                .contains("Il calcio è uno sport di squadra giocato all'aperto");
        assertThat(contains).isTrue();
    }

    @Test
    void wrongInputQuiz(){
        robot.clickOn(COMBOBOX).write(QUIZ).press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#age").write("abc");
        robot.clickOn("#ok");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        Boolean contains = registeredStage.getTitle().contains("Warning");
        assertThat(contains).isTrue();
    }
}
