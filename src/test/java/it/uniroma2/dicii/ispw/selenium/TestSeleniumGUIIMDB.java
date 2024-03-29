package it.uniroma2.dicii.ispw.selenium;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Chiara D'Ambrogio
 */
public class TestSeleniumGUIIMDB {
  private WebDriver driver;
  JavascriptExecutor js;

  /**
   * This method sets up the driver and javascript executor for the test
   */
  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
  }

  /**
   * This method closes the driver after the test is completed
   */
  @After
  public void tearDown() {
    driver.quit();
  }

  /**
   * This method tests the GUI of IMDB website
   */
  @Test
  public void testSeleniumGUI() {
    // Test name: TestSeleniumGUI
    // Step # | name | target | value
    // 1 | open | / | 
    driver.get("https://www.imdb.com/");
    // 2 | setWindowSize | 1382x784 | 
    driver.manage().window().setSize(new Dimension(1382, 784));
    // 3 | click | id=iconContext-list | 
    driver.findElement(By.xpath("/html/body/div[2]/main/div/div[3]/div[1]/div/div/div[1]/div/div[3]")).click();
    // 4 | Find the next button and click it multiple times | css=.sc-9422afe0-12 |
    WebElement nextButton = driver.findElement(By.cssSelector(".gYVOWZ.swiper-button-next"));
    for (int i = 0; i < 6; i++) {
      nextButton.click();
    }

    // 10 | Find the previous button and click it multiple times | css=.sc-fefd0a83-3 |
    WebElement prevButton = driver.findElement(By.cssSelector(".ecgJWH.swiper-button-prev"));
    for (int i = 0; i < 2; i++) {
      prevButton.click();
    }

    // 12 | runScript | window.scrollTo(0,209) |
    js.executeScript("window.scrollTo(0,209)");
    // 13 | close |  |
    driver.close();
  }
}
