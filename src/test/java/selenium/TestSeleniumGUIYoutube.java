package selenium;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
public class TestSeleniumGUIYoutube extends testBaseSelenium{
  private WebDriver driver;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testSeleniumGUI() {
    // Test name: Untitled
    // Step # | name | target | value
    // 1 | open | https://www.youtube.com/watch?v=bAO0ZNin7qM | 
    driver.get("https://www.youtube.com/watch?v=bAO0ZNin7qM");
    sleep(1000);
    // 2 | setWindowSize | 1920x1080 |
    driver.manage().window().setSize(new Dimension(1920, 1080));
    sleep(1000);
    // 3 | runScript | window.scrollTo(0,400) |
    sleep(10, "//*[@id=\"dialog\"]", driver);
    sleep(10, "//*[@id=\"content\"]/div[2]/div[6]/div[1]/ytd-button-renderer[2]/yt-button-shape/button", driver);
    driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[6]/div[1]/ytd-button-renderer[2]/yt-button-shape/button")).click();
    sleep(1000);
    // 4 | click | linkText=Gilby1385 |
    sleepText(10, "Gilby1385", driver);
    driver.findElement(By.linkText("Gilby1385")).click();
    sleep(2000);
    // 5 | runScript | window.scrollTo(0,0) | 
    js.executeScript("window.scrollTo(0,0)");
    sleep(100);
    // 6 | runScript | window.scrollTo(0,359) | 
    js.executeScript("window.scrollTo(0,359)");
    sleep(10, "//*[@id=\"right-arrow\"]/ytd-button-renderer/yt-button-shape/button", driver);
    // 7 | click | css=.ytd-shelf-renderer > #right-arrow #button > #button > .style-scope | 
    driver.findElement(By.xpath("//*[@id=\"right-arrow\"]/ytd-button-renderer/yt-button-shape/button")).click();
    sleepText(10, "Fake Logo - Dingo Pictures (2000)", driver);
    // 8 | click | linkText=McDonald's commercial (1980) |
    driver.findElement(By.linkText("Fake Logo - Dingo Pictures (2000)")).click();
    sleep(4000);
    // 9 | runScript | window.scrollTo(0,0) |
    js.executeScript("window.scrollTo(0,0)");
    sleepCss(10, ".ytp-play-button", driver);
    // 10 | click | css=.ytp-play-button|
    driver.findElement(By.cssSelector(".ytp-play-button")).click();
    sleep(100);
    // 12 | close |  |
    driver.close();
  }
}
