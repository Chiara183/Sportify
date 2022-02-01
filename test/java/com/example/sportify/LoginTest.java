package com.example.sportify;



import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class LoginTest extends FxRobot {


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
    public void loginTest() throws InterruptedException {
        clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        clickOn("#user").write("Prova");
        clickOn("#password").write("prova");
        clickOn("#eye");
        clickOn("#submit");
        TimeUnit.SECONDS.sleep(5);
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        String title1 = stage1.getTitle();
        assertEquals("Sportify - Home", title1);
    }

    @Test
    public void notLoginTest() throws InterruptedException {
        clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        clickOn("#user").write("Ciao");
        clickOn("#password").write("prova");
        clickOn("#eye");
        clickOn("#submit");
        TimeUnit.SECONDS.sleep(5);
        Stage stage1= FxToolkit.toolkitContext().getRegisteredStage();
        assertThat(stage1.getTitle().contains("Wrong Username or Password"));
    }


}
