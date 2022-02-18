package com.example.sportify;



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
import java.util.concurrent.TimeoutException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class LoginTest extends FxRobot {

    @BeforeAll
    static void versionControl(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You have to click 'Desktop' and then 'Next' everytime the next pop up window appears during the test to execute the correct testing");
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

    /**Test that login is successful with valid username and password*/
    @Test
    public void loginTest() {
        clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        clickOn("#user").write("Pluto");
        clickOn("#password").write("pluto");
        clickOn("#eye");
        clickOn("#submit");
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        String title1 = stage1.getTitle();
        assertEquals("Sportify - Home", title1);
    }

    /**Test that login is NOT successful with invalid username and password*/
    @Test
    public void notLoginTest() {
        clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        clickOn("#user").write("Ciao");
        clickOn("#password").write("prova");
        clickOn("#eye");
        clickOn("#submit");
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        assertThat(stage1.getTitle().contains("Wrong Username or Password"));
    }


}
