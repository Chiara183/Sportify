package it.uniroma2.dicii.ispw.sportify;

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

/**
 * @author Chiara D'Ambrogio
 */
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
        ROBOT.clickOn(COMBOBOX).write(QUIZ).press(KeyCode.ENTER).release(KeyCode.ENTER);
        ROBOT.clickOn("#age").write("14");
        ROBOT.clickOn("#ok");
        ROBOT.clickOn("#environment").write("outdoor");
        ROBOT.clickOn("#ok");
        ROBOT.clickOn("#type").write("group");
        ROBOT.clickOn("#ok");
        assertEquals(FOOTBALL, ROBOT.lookup("#sportName").queryLabeled().getText());
    }

    @Test
    void infoSportQuizTest(){
        ROBOT.clickOn(COMBOBOX).write(QUIZ).press(KeyCode.ENTER).release(KeyCode.ENTER);
        ROBOT.clickOn("#age").write("14");
        ROBOT.clickOn("#ok");
        ROBOT.clickOn("#environment").write("outdoor");
        ROBOT.clickOn("#ok");
        ROBOT.clickOn("#type").write("group");
        ROBOT.clickOn("#ok");
        assertEquals(FOOTBALL, ROBOT.lookup("#sportName").queryLabeled().getText());
        ROBOT.clickOn("#info");
        assertEquals(FOOTBALL, ROBOT.lookup("#sport").queryLabeled().getText());
        Boolean contains = ROBOT.lookup("#sportDescription")
                .queryLabeled().getText()
                .contains("Association football, more commonly known as simply football or soccer");
        assertThat(contains).isTrue();
    }

    @Test
    void wrongInputQuiz(){
        ROBOT.clickOn(COMBOBOX).write(QUIZ).press(KeyCode.ENTER).release(KeyCode.ENTER);
        ROBOT.clickOn("#age").write("abc");
        ROBOT.clickOn("#ok");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        Boolean contains = registeredStage.getTitle().contains("Sportify - Sport Quiz");
        assertThat(contains).isTrue();
    }
}
