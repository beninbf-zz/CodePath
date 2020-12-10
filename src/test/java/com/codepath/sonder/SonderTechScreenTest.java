package test.java.com.codepath.sonder;

import main.java.com.codepath.techscreens.sonder.TechScreen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SonderTechScreenTest {

    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testSonder_GetFirstBooking() {
        String bookingsInput = "4:8 19:35 80:160";
        int current_date = 35;
        int stay_length = 46;
        assertEquals("first available date should be 180", 160, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "4:8 19:35 80:160";
        current_date = 50;
        stay_length = 15;
        assertEquals("first available date should be 50", 50, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "0:3 3:6 7:14";
        current_date = 14;
        stay_length = 46;
        assertEquals("first available date should be 14", 14, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "0:3 3:6 7:14";
        current_date = 3;
        stay_length = 1;
        assertEquals("first available date should be 6", 6, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "0:3 3:6 7:14";
        current_date = 3;
        stay_length = 2;
        assertEquals("first available date should be 14", 14, testObj.getDate(bookingsInput, current_date, stay_length));
    }
}
