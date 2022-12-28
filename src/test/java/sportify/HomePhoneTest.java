package sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class HomePhoneTest  extends StartingTest{

    private static final String COMBOBOX = "#comboActivity";
    private final FxRobot robot = new FxRobot();

    @Override
    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "mobile");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @Test
    public void openSportQuizTest() {
        robot.clickOn(COMBOBOX).write("Take sport quiz").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Sport Quiz", title);
    }

    @Test
    public void openLoginWindow() {
        robot.clickOn(COMBOBOX).write("Login").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
    }

    @Test
    public void openGymFinderWindow() {
        robot.clickOn(COMBOBOX).write("Find gym").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Find Gym", title);
    }
}