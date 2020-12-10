package test.java.com.codepath.udemy;

import main.java.com.codepath.techscreens.udemy.NameNumberElement;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UdemyTechScreenTest {

    @Test
    public void testUdemy() {
        String[] input = {
                "John one-billion-two-hundred-thousand",
                "Alex five",
                "Alex one",
                "John two-hundred-thirty-four-thousand-five-hundred-sixty-seven",
                "John thirty-three",
                "John four-million-eight-thousand"
        };

        List<NameNumberElement> elements = new ArrayList<>();
        for (String item : input) {
            String[] values = item.split(" ");
            NameNumberElement e = new NameNumberElement(values[0], values[1]);
            elements.add(e);
        }
        Collections.sort(elements);
        System.out.println(elements);
    }

    @Test
    public void testUdemyGetNumber() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 234567", 234567, e.getNumber("two-hundred-thirty-four-thousand-five-hundred-sixty-seven"));
    }

    @Test
    public void testUdemyGetNumber1() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 4500", 4500, e.getNumber("four-thousand-five-hundred"));
    }

    @Test
    public void testUdemyGetNumber2() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1000000", 1000000, e.getNumber("one-million"));
    }

    @Test
    public void testUdemyGetNumber3() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 10000", 10000, e.getNumber("ten-thousand"));
    }

    @Test
    public void testUdemyGetNumber4() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 10050", 10050, e.getNumber("ten-thousand-fifty"));
    }

    @Test
    public void testUdemyGetNumber5() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1550", 1550, e.getNumber("fifteen-hundred-fifty"));
    }

    @Test
    public void testUdemyGetNumber6() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 50001", 50001, e.getNumber("fifty-thousand-one"));
    }

    @Test
    public void testUdemyGetNumber7() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 450001", 450001, e.getNumber("four-hundred-fifty-thousand-one"));
    }

    @Test
    public void testUdemyGetNumber8() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 450000", 450000, e.getNumber("four-hundred-fifty-thousand"));
    }

    @Test
    public void testUdemyGetNumber9() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 892", 892, e.getNumber("eight-hundred-ninety-two"));
    }

    @Test
    public void testUdemyGetNumber10() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1400000", 1400000, e.getNumber("one-million-four-hundred-thousand"));
    }

    @Test
    public void testUdemyGetNumber11() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1462500", 1462500, e.getNumber("one-million-four-hundred-sixty-two-thousand-five-hundred"));
    }

    @Test
    public void testUdemyGetNumber12() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 5", 5, e.getNumber("five"));
    }
}
