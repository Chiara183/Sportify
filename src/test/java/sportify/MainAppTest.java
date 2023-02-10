package sportify;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

/**
 * @author Matteo La Gioia
 */
class MainAppTest extends StartingTest{

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "desktop");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    /**Test that the home screen has the correct button*/
    @Test
    void hasButtonTest() {
        assertThat(ROBOT.lookup("#signIn").queryButton()).hasText("Log in");
        assertThat(ROBOT.lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertThat(ROBOT.lookup("#findGym").queryButton()).hasText("Find gym");
        assertThat(ROBOT.lookup("#sportQuiz").queryButton()).hasText("Sport quiz");
    }

    /**Test that the application is launched correctly*/
    @Test
    void titleTest(){
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals("Sportify - Home", title);
    }


}