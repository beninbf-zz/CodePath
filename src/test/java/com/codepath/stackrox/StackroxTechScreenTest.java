package test.java.com.codepath.stackrox;

import main.java.com.codepath.techscreens.stackrox.TechScreen;
import main.java.com.codepath.techscreens.stackrox.StackRoxNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StackroxTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testStackRox_GetUniquePrefixes() {
        List<StackRoxNode> nodes = new ArrayList<>(Arrays.asList(
                new StackRoxNode("4", "ABCD"),
                new StackRoxNode("1", "ABC"),
                new StackRoxNode("2", "AB"),
                new StackRoxNode("3", "B")
        ));
        Map<String, String> results = testObj.getUniquePrefixes(nodes);
        for (String key : results.keySet()) {
            System.out.println(results.get(key));
        }
    }
}
