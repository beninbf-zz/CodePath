package test.java.com.codepath.dydx;

import main.java.com.codepath.techscreens.dydx.Person;
import main.java.com.codepath.techscreens.dydx.TechScreen;
import org.junit.Before;
import org.junit.Test;

public class DyDxTechScreenTest {

    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testDyDx_GetStringFromField() {
        Person parent = new Person("Johns Dad", "Doe Dad", 65, null);
        Person person = new Person("John", "doe", 30, parent);

        String result = testObj.getJsonString(person);
        System.out.println(result);
    }
}
