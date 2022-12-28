package sportify;


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
import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class LoginTest extends StartingTest{

    private final FxRobot robot = new FxRobot();

    @Override
    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "desktop");
        WaitForAsyncUtils.waitForFxEvents(100);
    }


    /**Test that login is successful with valid username and password*/
    @Test
    public void loginTest() {
        robot.clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        robot.clickOn("#user").write("Pluto");
        robot.clickOn("#password").write("pluto");
        robot.clickOn("#eye");
        robot.clickOn("#submit");
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        String title1 = stage1.getTitle();
        assertEquals("Sportify - Home", title1);
    }

    /**Test that login is NOT successful with invalid username and password*/
    @Test
    public void notLoginTest() {
        robot.clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        robot.clickOn("#user").write("Ciao");
        robot.clickOn("#password").write("prova");
        robot.clickOn("#eye");
        robot.clickOn("#submit");
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        assertThat(stage1.getTitle().contains("Wrong Username or Password"));
    }


}
