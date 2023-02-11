package it.uniroma2.dicii.ispw.sportify;


import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

/**
 * @author Matteo La Gioia
 */
@ExtendWith(ApplicationExtension.class)
class LoginTest extends StartingTest{

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "desktop");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    /**Test that login is successful with valid username and password*/
    @Test
    void loginTest() {
        ROBOT.clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        ROBOT.clickOn("#user").write("Pluto");
        ROBOT.clickOn("#password").write("pluto");
        ROBOT.clickOn("#eye");
        ROBOT.clickOn("#submit");
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        String title1 = stage1.getTitle();
        assertEquals("Sportify - Home", title1);
    }

    /**Test that login is NOT successful with invalid username and password*/
    @Test
    void notLoginTest() {
        ROBOT.clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        ROBOT.clickOn("#user").write("Ciao");
        ROBOT.clickOn("#password").write("prova");
        ROBOT.clickOn("#eye");
        ROBOT.clickOn("#submit");
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        Boolean contains = stage1.getTitle().contains("Sportify - Login");
        assertThat(contains).isTrue();
    }


}
