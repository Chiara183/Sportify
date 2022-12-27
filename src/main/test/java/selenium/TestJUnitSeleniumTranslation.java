package java.selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJUnitSeleniumTranslation {
    @Test
    public void TestTranslation() throws InterruptedException {
        String str = testSelenium.Translation();
        String[] split = str.split(" ");
        int ResultA = split.length;
        assertTrue(ResultA > 3);
    }
}
