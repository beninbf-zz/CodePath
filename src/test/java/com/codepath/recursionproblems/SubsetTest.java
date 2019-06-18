package test.java.com.codepath.recursionproblems;

import main.java.com.codepath.recursion.Subsets;
import main.java.com.codepath.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SubsetTest {

    private Subsets testObj;

    @Before
    public void setUp() {
        testObj = new Subsets();
    }

    @Test
    public void testSubset() {
        String[] array = testObj.generate_all_subsets("1234");
        Util.print(array);
    }

    @Test
    public void findSubsetsOfSizeK() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<List<Integer>> result = testObj.findSubsetsOfSizek(array, 4);
        System.out.println(result.size());
        System.out.println(result);
        System.out.println("----------");

        List<List<Integer>> otherResult =  testObj.combine(9, 4);
        System.out.println(otherResult);
        System.out.println(otherResult.size());
    }

}
