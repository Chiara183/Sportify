package sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class HomePhoneTest  extends StartingTest{

    private static final String COMBOBOX = "#comboActivity";
    private FxRobot robot;

    @BeforeAll
    static void versionControl1() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Mobile' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
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