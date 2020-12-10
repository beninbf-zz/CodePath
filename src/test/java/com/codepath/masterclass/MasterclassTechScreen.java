package test.java.com.codepath.masterclass;

import main.java.com.codepath.techscreens.masterclass.TechScreen;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MasterclassTechScreen {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }
    @Test
    public void testMasterClass() {
        assertEquals("Should be equal to igpay", "igpay", testObj.pigLatinTranslate("pig"));
        assertEquals("Should be equal to eggay", "eggay", testObj.pigLatinTranslate("egg"));
        assertEquals("Should be equal to Ellohay", "Ellohay", testObj.pigLatinTranslate("Hello"));
        assertEquals("Should be equal to Orangeay", "Orangeay", testObj.pigLatinTranslate("Orange"));
        assertEquals("Should be equal to Uckstray", "Uckstray", testObj.pigLatinTranslateImproved("Struck"));
    }
}
