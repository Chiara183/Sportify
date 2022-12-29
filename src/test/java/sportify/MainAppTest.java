package sportify;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;


class MainAppTest extends StartingTest{

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "desktop");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    /**Test that the home screen has the correct button*/
    @Test
    void hasButtonTest() {
        assertThat(robot.lookup("#signIn").queryButton()).hasText("Log in");
        assertThat(robot.lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertThat(robot.lookup("#findGym").queryButton()).hasText("Find gym");
        assertThat(robot.lookup("#sportQuiz").queryButton()).hasText("Sport quiz");
    }

    /**Test that the application is launched correctly*/
    @Test
    void titleTest(){
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Home", title);
    }


}