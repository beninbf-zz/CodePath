package test.java.com.codepath.dynammicprograming;

import main.java.com.codepath.dynamicprogramming.DynamicProgramming;
import main.java.com.codepath.recursion.Palindrome.Palindrome;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class DynamicProgrammingTest {

    private DynamicProgramming testObj = null;

    @Before
    public void setUp() {
        this.testObj = new DynamicProgramming();
    }

    @Test
    public void findMinPath() {
    }

    @Test
    public void findMinPathDP() {
    }

    @Test
    public void print2DArray() {
    }

    @Test
    public void findMaxScoreRec() {
    }

    @Test
    public void findMaxScoreDP() {
    }

    @Test
    public void findMaxScoreBottomUp() {
    }

    @Test
    public void wordBreak() {
    }

    @Test
    public void wordBreakRec() {
    }

    @Test
    public void printSubString() {
    }

    @Test
    public void numberOfCoins() {
        int[] d = {2, 3, 5};
        List<List<Integer>> lists = testObj.getCoins(8, d);

        assertEquals("Should be 3", 3, lists.size());
        for (List<Integer> coins : lists) {
            assertEquals("Should be equal to 8", 8, sum(coins));
        }
    }

    @Test
    public void numberOfCoins1() {
        int[] d = {1, 5, 10};
        List<List<Integer>> lists = testObj.getCoins(8, d);

        assertEquals("Should be 2", 2, lists.size());
        for (List<Integer> coins : lists) {
            assertEquals("Should be equal to 8", 8, sum(coins));
        }
    }

    @Test
    public void numberOfDifferentWaystoGetChange() {
        int[] d = {1, 2};
        assertEquals("Should be 3", 3, testObj.numberOfDifferentWaystoGetChange(4, d, 0));
        assertEquals("Should be 3", 3, testObj.numberOfDiffChangeDp(4, d));
    }

    @Test
    public void numberOfDifferentWaystoGetChange1() {
        int[] d = {2, 3, 5};
        assertEquals("Should be 3", 3, testObj.numberOfDifferentWaystoGetChange(8, d, 0));
        assertEquals("Should be 3", 3, testObj.numberOfDiffChangeDp(8, d));
    }

    private int sum(List<Integer> list) {
        int result = 0;
        for (Integer n : list) {
            result += n.intValue();
        }
        return result;
    }

    @Test
    public void getMinNumberCoins() {
        int[] denom = {2, 3, 5};
        assertEquals("should be 2", 2, testObj.minNumberCoinsToMakeChange(8, denom));
    }

    @Test
    public void testCoinsDPsolution() {
        int[] denominations = {2, 3, 5};
        assertEquals("Should be 2", 2, testObj.coinChangeDP(8, denominations));
    }

    @Test
    public void testMaxPathRec1() {
        int[][] grid = {{5, 10},
            {35, 4},
        };

        assertEquals("Should be 44", 44, testObj.maxPathRec(grid));
        assertEquals("Should be 44", 44, testObj.maxPathDP(grid));
        assertEquals("Recursive and DP solution should be the same", testObj.maxPathRec(grid), testObj.maxPathDP(grid));
    }

    @Test
    public void testMaxPathRec2() {
        int[][] grid = {{1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 100, 1, 1},
            {1, 1, 100, 1}
        };

        assertEquals("Should be 205", 205, testObj.maxPathRec(grid));
        assertEquals("Should be 205", 205, testObj.maxPathDP(grid));
        assertEquals("Recursive and DP solution should be the same", testObj.maxPathRec(grid), testObj.maxPathDP(grid));
    }

    @Test
    public void testMaxPathRec3() {
        int[][] grid = {{5, 10, 20, 30},
            {35, 4, 25, 15},
            {92, 80, 61, 72},
            {12, 16, 70, 2},
            {6, 18, 41, 3}};

        assertEquals("Should be 387", 387, testObj.maxPathRec(grid));
        assertEquals("Should be 387", 387, testObj.maxPathDP(grid));
        assertEquals("Recursive and DP solution should be the same", testObj.maxPathRec(grid), testObj.maxPathDP(grid));
    }

    @Test
    public void testCountPaths() {
        int[][] grid = new int[2][2];
        assertEquals("Should be 2", 2, testObj.countPaths(grid));
        assertEquals("Should be 2", 2, testObj.countPathsOptimal(grid));
        assertEquals("Should be 2", 2, testObj.countPathsDP(grid));
    }

    @Test
    public void testCountPaths1() {
        int[][] grid = new int[3][3];
        assertEquals("Should be 6", 6, testObj.countPaths(grid));
        assertEquals("Should be 6", 6, testObj.countPathsOptimal(grid));
        assertEquals("Should be 6", 6, testObj.countPathsDP(grid));
    }

    @Test
    public void ropeCuttingMaxDP() {
        int[] prices = {0, 5, 1, 1, 1, 1};
        assertEquals("Should be 25", 25, testObj.ropeCuttingMax(prices.length - 1, prices));
        assertEquals("Should be 25", 25, testObj.ropeCuttingMaxDP(prices.length - 1, prices));
    }

    @Test
    public void testForTie() {
        int[] array = {5, 2, 7};
        assertTrue("Should be true", testObj.testForTie(array));
        assertTrue("Should be true", testObj.testForTieEff(array));
        assertTrue("Should be true", testObj.testForTieDP(array));
    }

    @Test
    public void testForTieEff() {
        int[] array = {5, 2, 7, 3, 3};
        assertTrue("Should be true", testObj.testForTie(array));
        assertTrue("Should be true", testObj.testForTieEff(array));
        assertTrue("Should be true", testObj.testForTieDP(array));

    }

    @Test
    public void subSetSizeNSum() {
        int[] array = {1, -1, 0, -2, 2, 3};
        testObj.subSetOfSizeNSumtoM(array, 3, 0);
    }

    @Test
    public void testEditDistanceRec() {
        assertEquals("should be one", 1, testObj.minimumEditDistance("bat", "cat"));
        assertEquals("should be one", 1, testObj.minEditDistanceDp("bat", "cat"));
    }

    @Test
    public void testEditDistanceDP() {
        assertEquals("should be 17", 17, testObj.minEditDistanceDp("masilanidbny", "zwujtimkexcgvxrgkp"));
        assertEquals("should be 5", 5, testObj.minEditDistanceDp("pizza", "yolo"));
        assertEquals("should be 15", 15, testObj.minEditDistanceDp("fifsmivvlq", "fpypvzeidrssnwlxss"));
        assertEquals("should be 9", 9, testObj.minEditDistanceDp("e", "eqqqqqqqqq"));
    }

    @Test
    public void testCoinGame() {
        int[] array = {8, 15, 3, 7};
        assertEquals("The max value should be 22", 22, testObj.coinGame(array));
        assertEquals("The max value should be 22", 22, testObj.coinGameDp(array));
    }

    @Test
    public void testCoinGame1() {
        int[] array = {5, 3, 7, 10};
        assertEquals("The max value should be 15", 15, testObj.coinGameDp(array));
    }

    @Test
    public void testCoinGame2() {
        int[] array = {149, 154, 63, 242, 12, 72, 65};
        assertEquals("The max value should be 289", 289, testObj.coinGame(array));
        assertEquals("The max value should be 289", 289, testObj.coinGameDp(array));
    }

    @Test
    public void testWordBreak() {
        String[] list = {"kick", "start", "kickstart", "is", "awe", "some", "awesome"};
        Set<String> dictionary = new HashSet<>(Arrays.asList(list));
        String text = "kickstartisawesome";
        List<String> results = testObj.wordBreak(dictionary, text);
        System.out.println(results);
    }

    @Test
    public void testCoinChange() {
        int amount = 4;
        int[] coins = {1, 2, 3};

        List<List<Integer>> results = testObj.coinCombinationsToMakeChange(4, coins);
        System.out.println(results);
        System.out.println(testObj.minCoins(amount, coins));
    }

    @Test
    public void maxRobberyValue() {
        int[] array = {6, 1, 2, 7};
        assertEquals("result should be 13", 13, testObj.maxRobberyValue(array));
        assertEquals("result should be 13", 13, testObj.maxRobberyDP(array));

        int[] array1 = {1, 2, 4, 5, 1};
        assertEquals("result should be 7", 7, testObj.maxRobberyValue(array1));
        assertEquals("result should be 7", 7, testObj.maxRobberyDP(array1));

        int[] array4 = {2, 7, 3};
        assertEquals("result should be 7", 7, testObj.maxRobberyValue(array4));
        assertEquals("result should be 7", 7, testObj.maxRobberyDP(array4));



        int[] array2 = {6, 1, 6, 1, 1, 10, 1, 8, 3, 2, 7, 3};
        assertEquals("result should be 37", 37, testObj.maxRobberyValue(array2));
        assertEquals("result should be 37", 37, testObj.maxRobberyDP(array2));


    }
}