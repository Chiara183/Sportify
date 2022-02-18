package com.example.sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class QuizPhoneTest extends FxRobot {

    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Mobile' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
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
            e.printStackTrace();
        }
    }

    @Test
    public void openSportQuizTest(){
        clickOn("#comboActivity").write("Take sport quiz").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Sport Quiz", title);
    }

    @Test
    public void SportQuizTest(){
        clickOn("#comboActivity").write("Take sport quiz").press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#age").write("14");
        clickOn("#ok");
        clickOn("#environment").write("outdoor");
        clickOn("#ok");
        clickOn("#type").write("group");
        clickOn("#ok");
        assertEquals("Football", lookup("#sportName").queryLabeled().getText());
    }

    @Test
    public void infoSportQuizTest(){
        clickOn("#comboActivity").write("Take sport quiz").press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#age").write("14");
        clickOn("#ok");
        clickOn("#environment").write("outdoor");
        clickOn("#ok");
        clickOn("#type").write("group");
        clickOn("#ok");
        assertEquals("Football", lookup("#sportName").queryLabeled().getText());
        clickOn("#info");
        assertEquals("Football", lookup("#sport").queryLabeled().getText());
        assertThat(lookup("#sportDescription").queryLabeled().getText().contains("Il calcio è uno sport di squadra giocato all'aperto"));
    }

    @Test
    public void wrongInputQuiz(){
        clickOn("#comboActivity").write("Take sport quiz").press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#age").write("abc");
        clickOn("#ok");
        Stage registeredStage = FxToolkit.toolkitContext().getRegisteredStage();
        assertThat(registeredStage.getTitle().contains("Warning"));
    }
}
