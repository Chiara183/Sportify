package it.uniroma2.dicii.ispw.selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author Chiara D'Ambrogio
 */
class TestSeleniumAPITranslation {
    @Test
    void TestTranslation() throws InterruptedException {
        String str = testSelenium.Translation();
        String[] split = str.split(" ");
        int ResultA = split.length;
        assertTrue(ResultA > 3);
    }
}
