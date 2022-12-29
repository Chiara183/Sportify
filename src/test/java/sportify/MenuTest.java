package sportify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

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
        robot.clickOn(SIGNIN);
        assertThat(robot.lookup(HOME).queryButton()).hasText("Home");
        assertThat(robot.lookup(SIGNIN).queryButton()).hasText("Login");
        assertThat(robot.lookup(FINDGYM).queryButton()).hasText(GYMFINDER);
        assertThat(robot.lookup(SPORTQUIZ).queryButton()).hasText(SPORT);
        assertThat(robot.lookup("#signUp").queryButton()).hasText("Sign Up");
    }

    /** Test the menu buttons being logged in as user */
    @Test
    void hasButtonLogUserTest(){
        helpMethod();
        assertEquals("Name", robot.lookup("#username").queryLabeled().getText());
    }

    private void helpMethod(){
        robot.clickOn(SIGNIN);
        robot.clickOn("#user").write("Name");
        robot.clickOn("#password").write("name");
        robot.clickOn("#eye");
        robot.clickOn("#submit");
        robot.clickOn(SPORTQUIZ);
        assertThat(robot.lookup(HOME).queryButton()).hasText("Home");
        assertThat(robot.lookup(FINDGYM).queryButton()).hasText(GYMFINDER);
        assertThat(robot.lookup(SPORTQUIZ).queryButton()).hasText(SPORT);
        assertThat(robot.lookup(SIGNOUT).queryButton()).hasText("Sign Out");
    }

    /** Test the menu buttons being logged in as gym */
    @Test
    void hasButtonLogGymTest(){
        helpMethod();
        assertThat(robot.lookup("#gymInfo").queryButton()).hasText("Gym Info");
        assertEquals("Name", robot.lookup("#username").queryLabeled().getText());
    }

}
