package test.java.com.codepath.objects;

import main.java.com.codepath.objects.Trie;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrieTest {

    private Trie testObj;

    @Before
    public void setUp() {
         testObj = new Trie(null);
    }

    @Test
    public void testAddWord() {
        testObj.addWord("boom");
        testObj.addWord("book");
        testObj.addWord("booked");
        testObj.addWord("bag");

        assertTrue("Trie should contain boom", testObj.contains("boom"));
        assertTrue("Trie should contain book", testObj.contains("book"));
        assertTrue("Trie should contain book", testObj.contains("booked"));

        assertFalse("Trie should NOT contain bool", testObj.contains("bool"));

        assertTrue("boo should be a prefix", testObj.isPrefix("boo"));
        assertFalse("bao should be a prefix", testObj.isPrefix("bao"));

        List<String> results = testObj.getWordsThatBeginWithPrefix("bo");
        System.out.println(results);

        List<String> results1 = testObj.getWordsThatBeginWithPrefix("b");
        System.out.println(results1);
    }
}
