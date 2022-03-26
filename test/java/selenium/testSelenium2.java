package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class testSelenium2 {
    public static Integer NumCitation (){
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://scholar.google.com/citations?user=PQWZTB8AAAAJ&hl=it");

        String TxtBoxContent = driver.findElement(By.xpath("//*[@id=\"gsc_rsb_st\"]/tbody/tr[1]/td[2]")).getText();
        Integer citation = Integer.parseInt(TxtBoxContent);

        System.out.println("There's " + citation + " citation");

        driver.close();

        return citation;
    }
}
