package selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJUnitSelenium {
    @Test
    public void TestNumCitation(){
        Integer ResultA = testSelenium2.NumCitation();
        assertTrue(ResultA > 1800);
    }
}
