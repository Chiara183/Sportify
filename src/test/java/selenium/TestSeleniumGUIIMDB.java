package selenium;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSeleniumGUIIMDB {
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
    // Test name: TestSeleniumGUI
    // Step # | name | target | value
    // 1 | open | / | 
    driver.get("https://www.imdb.com/");
    // 2 | setWindowSize | 1382x784 | 
    driver.manage().window().setSize(new Dimension(1382, 784));
    // 3 | click | id=iconContext-list | 
    driver.findElement(By.xpath("/html/body/div[2]/main/div/div[3]/div[1]/div/div/div[1]/div/div[3]")).click();
    // 4 | click | css=.sc-9422afe0-12 | 
    driver.findElement(By.cssSelector(".huQJXp.swiper-button-next")).click();
    // 5 | click | css=.sc-fefd0a83-3 | 
    driver.findElement(By.cssSelector(".huQJXp.swiper-button-next")).click();
    // 6 | click | css=.sc-fefd0a83-3 | 
    driver.findElement(By.cssSelector(".huQJXp.swiper-button-next")).click();
    // 7 | click | css=.sc-fefd0a83-3 | 
    driver.findElement(By.cssSelector(".huQJXp.swiper-button-next")).click();
    // 8 | click | css=.sc-fefd0a83-3 | 
    driver.findElement(By.cssSelector(".huQJXp.swiper-button-next")).click();
    // 9 | click | css=.sc-fefd0a83-3 | 
    driver.findElement(By.cssSelector(".huQJXp.swiper-button-next")).click();
    // 10 | click | css=.sc-fefd0a83-3 | 
    driver.findElement(By.cssSelector(".hBQMwK.swiper-button-prev")).click();
    // 11 | click | css=.sc-fefd0a83-5 | 
    driver.findElement(By.cssSelector(".hBQMwK.swiper-button-prev")).click();
    // 12 | click | linkText=The Batman | 
    driver.findElement(By.xpath("/html/body/div[2]/main/div/div[3]/div[1]/div/div/div[1]/div/div[1]/div[4]/figure/div/div[2]/div[2]/div[3]/figcaption/div[2]/div[1]/span[1]")).click();
    // 13 | runScript | window.scrollTo(0,209) | 
    js.executeScript("window.scrollTo(0,209)");
    // 14 | close |  | 
    driver.close();
  }
}
