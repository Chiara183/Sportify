package selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSeleniumAPITranslation {
    @Test
    void TestTranslation() throws InterruptedException {
        String str = testSelenium.Translation();
        String[] split = str.split(" ");
        int ResultA = split.length;
        assertTrue(ResultA > 3);
    }
}
