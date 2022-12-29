package sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class HomePhoneTest  extends StartingTest{

    private static final String COMBOBOX = "#comboActivity";

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "mobile");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @Test
    void openSportQuizTest() {
        robot.clickOn(COMBOBOX).write("Take sport quiz").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Sport Quiz", title);
    }

    @Test
    void openLoginWindow() {
        robot.clickOn(COMBOBOX).write("Login").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
    }

    @Test
    void openGymFinderWindow() {
        robot.clickOn(COMBOBOX).write("Find gym").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Find Gym", title);
    }
}