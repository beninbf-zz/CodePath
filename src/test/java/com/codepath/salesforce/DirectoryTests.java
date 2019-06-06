package test.java.com.codepath.salesforce;

import main.java.com.codepath.salesforce.Directory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectoryTests {
    private Directory testObj;

    @Before
    public void setUp() {
        this.testObj = new Directory(null, null);
    }

    @Test
    public void testMkdir() {
        String directory = "sub601";
        String response = this.testObj.mkdir(directory);
        assertEquals("Response is incorrect", "Command: mkdir   " + directory, response);
    }

    @Test
    public void testDir() {
        String response = this.testObj.dir();
        System.out.println(response);
    }

    @Test
    public void testDir1() {
        String[] dirs = {"sub601", "sub602", "sub603", "sub604"};

        for (String dir: dirs) {
            this.testObj.mkdir(dir);
        }
        String response = this.testObj.dir();
        System.out.println(response);
    }

    @Test
    public void testMkdir1() {
        String directory = "sub601\\sub603";
        String response = this.testObj.mkdir(directory);
        System.out.println(response);
        assertEquals("Response is incorrect", "Command: mkdir   " + directory, response);
    }

    @Test
    public void testMkdirDirectoryAlreadyExists() {
        String[] dirs = {"sub601", "sub601"};

        for (String dir: dirs) {
            this.testObj.mkdir(dir);
        }
        String response = this.testObj.dir();
        System.out.println(response);
    }
}
