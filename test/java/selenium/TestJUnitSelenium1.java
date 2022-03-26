package selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJUnitSelenium1 {
    @Test
    public void TestNumCitation() throws InterruptedException {
        String str = testSelenium3.Translation();
        String[] split = str.split(" ");
        int ResultA = split.length;
        assertTrue(ResultA > 3);
    }
}
