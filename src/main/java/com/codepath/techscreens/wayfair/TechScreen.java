package main.java.com.codepath.techscreens.wayfair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TechScreen {
    /**
     * Test from Codility for Wayfair
     * Given an array of integers, return the smallest non negative
     * integer, not in the array. Can't return 0, but must be at least 1
     *
     * input: [1, 3, 4, 2, 1, 6]
     * output: 5
     *
     * This is an interesting problem with a number of different solutions.
     * The simplest is to search through the array, storing numbers that are larger
     * greater than 0. Store the integers in a Set
     *
     * If, the set is empty that means there were no positive integers so we can
     * simply return 1, as the largest non negative integer missing.
     *
     * If the set is not empty, then we simply check, from 1 to the max number in
     * the input. The first number the set doesn't contain, is the smallest missing number.
     *
     * If we make it through the entire set, and nothing is missing, then that means
     * we should return the max number + 1.
     *
     * RUNTIME: O(N)
     * SPACE: O(N)
     * @param A input array of integers
     * @return smallest missing positive integer
     */
    public int findSmallestMissingPositiveInteger(int[] A) {
        Set<Integer> integers = new HashSet<>(A.length);
        int max = Arrays.stream(A).max().getAsInt();
        for (int i = 0; i < A.length; i++) {
            if (A[i] > 0) {
                integers.add(A[i]);
            }
        }

        if (integers.isEmpty()) {
            return 1;
        }

        for (int i = 1; i < max; i++){
            if (!integers.contains(i)) {
                return i;
            }
        }

        return max + 1;
    }
}
