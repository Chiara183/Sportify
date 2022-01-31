package com.example.sportify;


import javafx.scene.Parent;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class LoginTest extends FxRobot {

    private static GuiTest controller;

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class);
        WaitForAsyncUtils.waitForFxEvents(100);
        /*controller = new GuiTest() {
            @Override
            protected Parent getRootNode() {
                return null;
            }
        };*/
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
    public void logInTest(FxRobot robot){
        robot.clickOn("#signIn");
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Login", title);
        /*controller.click("#username").type("Name");
        controller.click("#password").type("name");
        controller.click("#submit");
        Stage stage1 = FxToolkit.toolkitContext().getRegisteredStage();
        String title1 = stage1.getTitle();
        assertEquals("Sportify - Home", title1);*/
    }


}
