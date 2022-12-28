package sportify;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;


class MainAppTest extends StartingTest{


    private final FxRobot robot;

    MainAppTest(FxRobot robot) {
        this.robot = robot;
    }

    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Desktop' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
    }


    /**Test that the home screen has the correct button*/
    @Test
    public void hasButtonTest() {
        assertThat(robot.lookup("#signIn").queryButton()).hasText("Log in");
        assertThat(robot.lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertThat(robot.lookup("#findGym").queryButton()).hasText("Find gym");
        assertThat(robot.lookup("#sportQuiz").queryButton()).hasText("Sport quiz");
    }

    /**Test that the application is launched correctly*/
    @Test
    public void titleTest(){
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Home", title);
    }


}