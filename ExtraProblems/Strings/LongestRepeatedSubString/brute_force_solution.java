package LongestRepeatedSubstring.solutions; /**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given a string str of length n, find the longest repeated substring in it.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class brute_force {

    // -------------------- START ----------------------

    public static String getLongestRepeatedSubstring(String inputStr) {
        int n = inputStr.length();
        int j;
        String curr;
        // Iterate over all possible substrings in descending order of their length
        for (int len = n - 1; len > 0; len--) {
            for (int i = 0; i + len - 1 < n; i++) {
                j = i + len - 1;
                curr = inputStr.substring(i, j + 1);
                // Check if substring inputStr.substring(i, j + 1) occurs anywhere other than at index i
                // or not
                if (isSubstring(inputStr.substring(i + 1), curr)) {
                    // Found at least one occurrence of inputStr.substring(i, j + 1) at some index ind >= i+1
                    return curr;
                }
                if (isSubstring(inputStr.substring(0, j), curr)) {
                    // Found at least one occurrence of inputStr.substring(i, j + 1) at some index ind <= i-1
                    return curr;
                }
            }
        }
        return "";
    }

    public static boolean isSubstring(String text, String pattern) {
        return text.contains(pattern);
    }

    // -------------------- END ----------------------

    private static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String inputStr = br.readLine().trim();

        String result = getLongestRepeatedSubstring(inputStr);
        bufferedWriter.write(result);
        bufferedWriter.newLine();
        bufferedWriter.close();

        br.close();
    }

}

/**
 * Time complexity: O(n^4)
 * Space complexity: O(n)
 */
