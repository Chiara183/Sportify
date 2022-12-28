package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class testSelenium {
    public static Double RateValue () {
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com/title/tt0273255/");

        String TxtBoxContent = driver.findElement(By.xpath("/html/body/div[2]/main/div/section[1]/section/div[3]/section/section/div[2]/div[2]/div/div[1]/a/div/div/div[2]/div[1]/span[1]")).getAttribute("innerText");
        String rateS = TxtBoxContent.replace(",",".");
        Double rate = Double.parseDouble(rateS);

        System.out.println("The rate of the film is " + rate + "/10");

        driver.close();

        return rate;
    }

    public static String Translation () throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://translate.google.it/?hl=it&sl=it&tl=en&op=translate");
        driver.findElement(By.xpath("/html/body/c-wiz/div/div/div/div[2]/div[1]/div[4]/div[1]/div[1]/form[2]/div/div/button")).click();
        driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[3]/c-wiz[1]/span/span/div/textarea")).sendKeys("La pizza margherita è buona");
        Thread.sleep(1000);
        String translation = driver.findElement(By.xpath("/html/body/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[3]/c-wiz[2]/div/div[8]/div/div[1]/span[1]/span/span")).getAttribute("innerText");

        System.out.println("The translation is: " + translation);

        driver.close();

        return translation;
    }
}
