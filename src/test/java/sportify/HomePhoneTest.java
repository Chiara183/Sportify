package sportify;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author Matteo La Gioia
 */
@ExtendWith(ApplicationExtension.class)
class HomePhoneTest extends StartingTest{

    private static final String COMBOBOX = "#comboActivity";

    @BeforeEach
    public void setUp() {
        ApplicationTest.launch(MainAppLauncher.class, "mobile");
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @ParameterizedTest
    @CsvSource({
            "Take sport quiz, Sportify - Sport Quiz",
            "Login, Sportify - Login",
            "Find gym, Sportify - Find Gym",
    })
    void openSceneTest(String wr, String t) {
        ROBOT.clickOn(COMBOBOX).write(wr).press(KeyCode.ENTER).release(KeyCode.ENTER);
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage();
        String title = stage.getTitle();
        assertEquals(t, title);
    }
}