package com.example.sportify;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;
import java.util.concurrent.TimeoutException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;


class MainAppTest extends FxRobot {

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
    public void hasButtonTest() {
        assertThat(lookup("#signIn").queryButton()).hasText("Log in");
        assertThat(lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertThat(lookup("#findGym").queryButton()).hasText("Find gym");
        assertThat(lookup("#sportQuiz").queryButton()).hasText("Sport quiz");
    }

    @Test
    public void titleTest(){
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Home", title);
    }


}