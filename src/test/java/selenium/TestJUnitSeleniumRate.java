package selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestJUnitSeleniumRate {
    @Test
    void TestRate() {
        Double ResultA = testSelenium.RateValue();
        assertTrue(ResultA < 5.0);
    }
}
