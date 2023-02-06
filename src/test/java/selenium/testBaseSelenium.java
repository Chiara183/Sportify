package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The `testBaseSelenium` abstract class provides a static
 * method for sleeping the execution of the program for a
 * specified duration.
 *
 * @author Matteo La Gioia
 */
public abstract class testBaseSelenium {

    /**
     * Sleeps the execution of the program for the specified duration.
     *
     * @param duration the duration to sleep in milliseconds
     */
    protected static void sleep(long duration) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {}
        }, duration);
    }

    /**
     * Sleeps the execution of the program for the specified duration.
     *
     * @param duration the duration to sleep in milliseconds
     */
    protected static void sleep(long duration, String xpath, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, duration);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(xpath)));
    }
}
