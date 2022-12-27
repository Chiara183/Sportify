package java.selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJUnitSeleniumRate {
    @Test
    public void TestRate() {
        Double ResultA = testSelenium.RateValue();
        assertTrue(ResultA < 5.0);
    }
}
