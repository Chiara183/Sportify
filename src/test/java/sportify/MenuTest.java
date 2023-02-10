package sportify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

/**
 * @author Chiara D'Ambrogio
 */
class MenuTest extends StartingTest{
    private static final String SIGNIN = "#signIn";
    private static final String SIGNOUT = "#signOut";
    private static final String HOME = "#home";
    private static final String FINDGYM = "#findGym";
    private static final String GYMFINDER = "Gym Finder";
    private static final String SPORTQUIZ = "#sportQuiz";
    private static final String SPORT = "Sport Quiz";

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "desktop");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    /** Test the menu buttons without being logged in */
    @Test
    void hasButtonTest(){
        ROBOT.clickOn(SIGNIN);
        assertThat(ROBOT.lookup(HOME).queryButton()).hasText("Home");
        assertThat(ROBOT.lookup(SIGNIN).queryButton()).hasText("Login");
        assertThat(ROBOT.lookup(FINDGYM).queryButton()).hasText(GYMFINDER);
        assertThat(ROBOT.lookup(SPORTQUIZ).queryButton()).hasText(SPORT);
        assertThat(ROBOT.lookup("#signUp").queryButton()).hasText("Sign Up");
    }

    /** Test the menu buttons being logged in as user */
    @Test
    void hasButtonLogUserTest(){
        helpMethod();
        assertEquals("Name", ROBOT.lookup("#username").queryLabeled().getText());
    }

    private void helpMethod(){
        ROBOT.clickOn(SIGNIN);
        ROBOT.clickOn("#user").write("Name");
        ROBOT.clickOn("#password").write("name");
        ROBOT.clickOn("#eye");
        ROBOT.clickOn("#submit");
        ROBOT.clickOn(SPORTQUIZ);
        assertThat(ROBOT.lookup(HOME).queryButton()).hasText("Home");
        assertThat(ROBOT.lookup(FINDGYM).queryButton()).hasText(GYMFINDER);
        assertThat(ROBOT.lookup(SPORTQUIZ).queryButton()).hasText(SPORT);
        assertThat(ROBOT.lookup(SIGNOUT).queryButton()).hasText("Sign Out");
    }

    /** Test the menu buttons being logged in as gym */
    @Test
    void hasButtonLogGymTest(){
        helpMethod();
        assertThat(ROBOT.lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertEquals("Name", ROBOT.lookup("#username").queryLabeled().getText());
    }

}
