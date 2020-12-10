package test.java.com.codepath.flexport;

import main.java.com.codepath.techscreens.flexport.TechScreen;
import org.junit.Before;
import org.junit.Test;

public class FlexportTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testPrintZigZag() {
        testObj.printZigZag("ABCDEFGHIJKLMNOPQ", 4);

    }

    @Test
    public void testPrintZigZag1() {
        testObj.printZigZag("ABCDEFGHIJKLMNOPQ", 4);
    }
}
