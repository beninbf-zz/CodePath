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

    @Test
    public void testNumPhoneNumbers() {
        List<List<Integer>> result = testObj.numPhoneNumbersRec(1, 2);
        assertEquals("should be 2", 2, result.size());
        result = testObj.numPhoneNumbersRec(1, 3);
        assertEquals("should be 5", 5, result.size());
        assertEquals("should be 2", 2, testObj.numPhoneNumbersOptimal(1, 2));
        assertEquals("should be 2", 2, testObj.numPhoneNumbersMemo(1, 2));
        assertEquals("should be 5 memo", 5, testObj.numPhoneNumbersMemo(1, 3));
        assertEquals("should be 10 memo", 10, testObj.numPhoneNumbersMemo(1, 4));
        assertEquals("should be 26 memo", 26, testObj.numPhoneNumbersMemo(1, 5));
        assertEquals("should be 26 memo", 13632143360l, testObj.numPhoneNumbersMemo(0, 29));
    }

    @Test
    public void testCountStairs() {
        int[] steps = {2, 3};
        assertEquals("Should be 3", 3, (testObj.countWaysToReachNthStair(steps, 7)));

        int[] steps1 = {1, 2};
        assertEquals("Should be 2", 2, testObj.countNthStairDP(steps1, 2));
        assertEquals("Should be 1", 1, testObj.countNthStairDP(steps1, 1));

        int[] steps2 = {1};
        assertEquals("Should be 1", 1, testObj.countNthStairDP(steps2, 10));

        int[] steps3 = {1, 2, 3, 4, 5};
        assertEquals("Should be 16", 16, testObj.countNthStairDP(steps3, 5));

        int[] steps4 = {2, 3, 5};
        assertEquals("Should be 14", 14, testObj.countNthStairDP(steps4, 10));

        int[] steps5 = {3, 5};
        assertEquals("Should be 0", 0, testObj.countNthStairDP(steps5, 7));

        int[] steps6 = {47, 25, 40, 8, 3, 22, 12, 2, 28, 23, 33, 26};
        assertEquals("Should be 29782190164", 29782190164l, testObj.countNthStairDP(steps6, 78));

    }

    @Test
    public void testMaxCut() {
        assertEquals("Should be 1", 1, testObj.maxProductFromCuts(1));
        assertEquals("Should be 2", 1, testObj.maxProductFromCuts(2));
        assertEquals("Should be 2", 2, testObj.maxProductFromCuts(3));
        assertEquals("Should be 4", 4, testObj.maxProductFromCuts(4));
        assertEquals("Should be 6", 6, testObj.maxProductFromCuts(5));
        assertEquals("Should be 9", 9, testObj.maxProductFromCuts(6));
        assertEquals("Should be 54", 54, testObj.maxProductFromCuts(11));
        assertEquals("Should be 81", 81, testObj.maxProductFromCuts(12));
        assertEquals("Should be 108", 108, testObj.maxProductFromCuts(13));

        assertEquals("Should be 1", 1, testObj.maxProductFromCutsDp(1));
        assertEquals("Should be 2", 1, testObj.maxProductFromCutsDp(2));
        assertEquals("Should be 2", 2, testObj.maxProductFromCutsDp(3));
        assertEquals("Should be 4", 4, testObj.maxProductFromCutsDp(4));
        assertEquals("Should be 6", 6, testObj.maxProductFromCutsDp(5));
        assertEquals("Should be 9", 9, testObj.maxProductFromCutsDp(6));
        assertEquals("Should be 54", 54, testObj.maxProductFromCutsDp(11));
        assertEquals("Should be 81", 81, testObj.maxProductFromCutsDp(12));
        assertEquals("Should be 108", 108, testObj.maxProductFromCutsDp(13));
        assertEquals("Should be 4374", 4374, testObj.maxProductFromCutsDp(23));
    }

    @Test
    public void testLongestCommonSubstring() {
        assertEquals("longest common substring should be ello", "ello", testObj.longestCommongSubstring("hello", "bellodfd"));
        assertEquals("longest common substring should be fooxtex", "fooxtex", testObj.longestCommongSubstring("fooxtex", "fooxtex"));
        assertEquals("longest common substring should be oxte", "oxte", testObj.longestCommongSubstring("fooxtex", "ewoxteg"));
        assertEquals("longest common substring should be football", "football", testObj.longestCommongSubstring("football", "footballd"));
        assertEquals("longest common substring should be ootball", "ootball", testObj.longestCommongSubstring("pootball", "football"));
    }

    @Test
    public void testLongestCommonSubSequence() {
        assertEquals("lcs should be 4", 4, testObj.longestCommonSubsequence("abdace", "babce"));
        assertEquals("lcs should be 4", 4, testObj.lcsDp("abdace", "babce"));
    }

    @Test
    public void testCountPathsWithObstruction() {
        int[][] test = {{1, 1, 0, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 0, 1, 0, 1},
                    {1, 1, 1, 1, 1},
                    {1, 0, 1, 1, 1}};


        assertEquals("should be 11", 11, testObj.numberOfPathsWithObstructions(test));
        assertEquals("should be 11", 11, testObj.numberOfPathsDp(test));

        int[][] test1 = {{1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}};

        assertEquals("should be 10", 10, testObj.numberOfPathsWithObstructions(test1));
        assertEquals("should be 10", 10, testObj.numberOfPathsDp(test1));

        int[][] test2 = {{1, 1},
            {0, 1}};

        assertEquals("should be 1", 1, testObj.numberOfPathsWithObstructions(test2));
        assertEquals("should be 1", 1, testObj.numberOfPathsDp(test2));

        int[][] test3 = {{1, 0, 0},
            {1, 1, 1},
            {1, 1, 1}};

        assertEquals("should be 3", 3, testObj.numberOfPathsWithObstructions(test3));
        assertEquals("should be 3", 3, testObj.numberOfPathsDp(test3));

        int[][] test4 = {
            {1 ,1, 1, 1, 1, 0, 0, 1, 0, 1},
        {1, 1, 1, 1, 0, 0, 1, 0, 1, 0},
        {1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
        {1, 1, 0, 1, 1, 1, 1, 0, 1, 0},
        {1, 1, 0, 1, 0, 1, 0, 1, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 0, 1, 1, 0, 1, 1, 0, 1},
        {1, 1, 0, 0, 1, 1, 0, 1, 0, 1},
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        assertEquals("should be 606", 606, testObj.numberOfPathsWithObstructions(test4));
        assertEquals("should be 606", 606, testObj.numberOfPathsDp(test4));
    }

    @Test
    public void testLongestIncreasingSubsequence() {
        //int[] A = {10, 22, 9, 33, 21, 50, 41, 60, 80};
        //List<List<Integer>> results = testObj.findAllIncreasingSubSequences(A);
        //System.out.println(results);

        int[] A1 ={4,10,4,3,8,9};
        assertEquals("LIS for {4,10,4,3,8,9} should be 3", 3, testObj.findLongestIncreasingSubSequence(A1));
        assertEquals("LIS for {4,10,4,3,8,9} should be 3", 3, testObj.lengthOfLIS(A1));
        assertEquals("LIS for {4,10,4,3,8,9} should be 3", 3, testObj.lengthOfLISMemo(A1));
        assertEquals("LIS for {4,10,4,3,8,9} should be 3", 3, testObj.lengthOfLisRec(A1));


        int[] A2 = {10,9,2,5,3,7,101,18};
        assertEquals("LIS for {10,9,2,5,3,7,101,18} should be 4", 4, testObj.findLongestIncreasingSubSequence(A2));
        assertEquals("LIS for {10,9,2,5,3,7,101,18} should be 4", 4, testObj.lengthOfLIS(A2));
        assertEquals("LIS for {10,9,2,5,3,7,101,18} should be 4", 4, testObj.lengthOfLISMemo(A2));
        assertEquals("LIS for {10,9,2,5,3,7,101,18} should be 4", 4, testObj.lengthOfLisRec(A2));

        int[] A4 = {10,19,20};
        assertEquals("LIS for {10,19,20} should be 3", 3, testObj.findLongestIncreasingSubSequence(A4));
        assertEquals("LIS for {10,19,20} should be 3", 3, testObj.lengthOfLIS(A4));
        assertEquals("LIS for {10,19,20} should be 3", 3, testObj.lengthOfLISMemo(A4));
        assertEquals("LIS for {10,19,20} should be 3", 3, testObj.lengthOfLisRec(A4));

        int[] A5 = {10,19};
        assertEquals("LIS for {10,19} should be 2", 2, testObj.findLongestIncreasingSubSequence(A5));
        assertEquals("LIS for {10,19} should be 2", 2, testObj.lengthOfLIS(A5));
        assertEquals("LIS for {10,19} should be 2", 2, testObj.lengthOfLISMemo(A5));
        assertEquals("LIS for {10,19} should be 2", 2, testObj.lengthOfLisRec(A5));

        int[] A3 = {-2, -1};
        assertEquals("LIS for {-2, -1} should be 2", 2, testObj.findLongestIncreasingSubSequence(A3));
        assertEquals("LIS for {-2, -1} should be 2", 2, testObj.lengthOfLIS(A3));
        assertEquals("LIS for {-2, -1} should be 2", 2, testObj.lengthOfLISMemo(A3));
        assertEquals("LIS for {-2, -1} should be 2", 2, testObj.lengthOfLisRec(A3));

        int[] A6 = {1,3,6,7,9,4,10,5,6};
        assertEquals("LIS for {1,3,6,7,9,4,10,5,6} should be 6", 6, testObj.findLongestIncreasingSubSequence(A6));
        assertEquals("LIS for {1,3,6,7,9,4,10,5,6} should be 6", 6, testObj.lengthOfLIS(A6));
        assertEquals("LIS for {1,3,6,7,9,4,10,5,6} should be 6", 6, testObj.lengthOfLISMemo(A6));
        assertEquals("LIS for {1,3,6,7,9,4,10,5,6} should be 6", 6, testObj.lengthOfLisRec(A6));
    }

    @Test
    public void testSquareMatix() {
        int[][] input = {
            {0, 1, 1},
            {0, 1, 1},
            {0, 0, 0}};
        assertEquals("Should be 2", 2, testObj.largestSquareMatrixOptimal(input));
        assertEquals("Should be 2", 2, testObj.largestSubMatrixOfOnes(input));
        assertEquals("Should be 2", 2, testObj.largestSubMatrixOfOnesDp(input));
        assertEquals("Should be 2", 2, testObj.largestSquareSubMatrixDp(input));


        int[][] input1 = {
            {0, 1, 1, 1},
            {0, 1, 1, 1},
            {0, 1, 1, 1},
            {0, 1, 1, 1}};
        assertEquals("Should be 3", 3, testObj.largestSquareMatrixOptimal(input1));
        assertEquals("Should be 3", 3, testObj.largestSubMatrixOfOnes(input1));
        assertEquals("Should be 3", 3, testObj.largestSubMatrixOfOnesDp(input1));
        assertEquals("Should be 3", 3, testObj.largestSquareSubMatrixDp(input1));
    }
}