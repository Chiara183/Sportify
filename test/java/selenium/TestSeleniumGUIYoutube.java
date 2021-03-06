package selenium;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
public class TestSeleniumGUIYoutube {
  private WebDriver driver;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "trunk/drivers/chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testSeleniumGUI() throws InterruptedException {
    // Test name: Untitled
    // Step # | name | target | value
    // 1 | open | https://www.youtube.com/watch?v=bAO0ZNin7qM | 
    driver.get("https://www.youtube.com/watch?v=bAO0ZNin7qM");
    Thread.sleep(1000);
    // 2 | setWindowSize | 1382x784 | 
    driver.manage().window().setSize(new Dimension(1382, 784));
    Thread.sleep(100);
    // 3 | runScript | window.scrollTo(0,400) | 
    js.executeScript("window.scrollTo(0,400)");
    Thread.sleep(100);
    driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[5]/div[2]/ytd-button-renderer[2]")).click();
    Thread.sleep(1000);
    // 4 | click | linkText=Gilby1385 | 
    driver.findElement(By.linkText("Gilby1385")).click();
    Thread.sleep(2000);
    // 5 | runScript | window.scrollTo(0,0) | 
    js.executeScript("window.scrollTo(0,0)");
    Thread.sleep(100);
    // 6 | runScript | window.scrollTo(0,359) | 
    js.executeScript("window.scrollTo(0,359)");
    Thread.sleep(100);
    // 7 | click | css=.ytd-shelf-renderer > #right-arrow #button > #button > .style-scope | 
    driver.findElement(By.cssSelector(".ytd-shelf-renderer > #right-arrow #button > #button > .style-scope")).click();
    Thread.sleep(100);
    // 8 | click | css=.ytd-shelf-renderer > #right-arrow #button > #button > .style-scope | 
    driver.findElement(By.cssSelector(".ytd-shelf-renderer > #right-arrow #button > #button > .style-scope")).click();
    Thread.sleep(100);
    // 9 | click | linkText=McDonald's commercial (1980) | 
    driver.findElement(By.linkText("McDonald's commercial (1980)")).click();
    Thread.sleep(4000);
    // 10 | runScript | window.scrollTo(0,0) | 
    js.executeScript("window.scrollTo(0,0)");
    Thread.sleep(100);
    // 11 | click | css=.ytp-play-button|
    driver.findElement(By.cssSelector(".ytp-play-button")).click();
    Thread.sleep(100);
    // 12 | click | css=.ytp-chrome-bottom:nth-child(29) .ytp-progress-bar | 
    driver.findElement(By.cssSelector(".ytp-chrome-bottom:nth-child(29) .ytp-progress-bar")).click();
    Thread.sleep(100);
    // 13 | close |  | 
    driver.close();
  }
}
