/**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given a set of time intervals in any order, merge all overlapping intervals into one and output the result which
 * should have only mutually exclusive intervals.
 *
 * e.g. for this input: {{1,3}, {2,4}, {5,7}, {6,8} }.
 * The intervals {1,3} and {2,4} overlap with each other, so they should be merged and become {1, 4}.
 * Similarly {5, 7} and {6, 8} should be merged and become {5, 8}.
 *
 * Write a function which produces the set of merged intervals for the given set of intervals.
 */

import java.io.*;
import java.util.*;

class Result {

    // -------------------- START ----------------------
    static int[][] getMergedIntervals(int[][] inputArray) {
        for (int i = 0; i < inputArray.length; i++) {
            // Check if it is an invalid interval
            if (isInvalidInterval(inputArray[i])) {
                continue;
            }

            for (int j = 0; j < inputArray.length; j++) {
                // Check if it is an invalid interval
                if (i == j || isInvalidInterval(inputArray[j])) {
                    continue;
                }
                // Check if interval[i] and interval[j] are overlapping
                if (is_overlapping(inputArray[i], inputArray[j])) {
                    inputArray[i][0] = Math.min(inputArray[i][0], inputArray[j][0]);
                    inputArray[i][1] = Math.max(inputArray[i][1], inputArray[j][1]);
                    invalidateInterval(inputArray[j]);
                    // Here, for removing an interval from inputArray, we will make it an invalid interval
                    // (i.e. interval.start > interval.end) as actually removing it from array will be an inefficient way.
                }
            }
        }
        int outputArraySize = 0;
        for (int i = 0; i < inputArray.length; i++) {
            // Check if it is an invalid interval.
            // Invalid intervals are the ones which are removed(merged into some other intervals).
            if (!isInvalidInterval(inputArray[i])) {
                outputArraySize++;
            }
        }

        int[][] outputArray = new int[outputArraySize][2];
        int ptr = 0;
        for (int i = 0; i < inputArray.length; i++) {
            if (!isInvalidInterval(inputArray[i])) {
                outputArray[ptr++] = inputArray[i];
            }
        }

        return outputArray;
    }

    private static boolean isInvalidInterval(int[] inputArray) {
        return (inputArray[0] > inputArray[1]);
    }

    private static void invalidateInterval(int[] interval) {
        interval[0] = 1;
        interval[1] = 0;
    }

    private static boolean is_overlapping(int[] interval1, int[] interval2) {
        return !isInvalidInterval(interval1) && !isInvalidInterval(interval2) &&
                !(interval1[1] < interval2[0] || interval2[1] < interval1[0]);
    }
    // -------------------- END ----------------------
}

class Solution{
    public static void main(String args[]) {
        /*
        This function is used to increase the size of recursion stack. It makes the size of stack
        2^26 ~= 10^8
        */
        new Thread(null, new Runnable() {
            public void run() {
                try{
                    solve();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 26).start();
    }
    public static void solve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());
        int m = Integer.parseInt(bufferedReader.readLine().trim());

        int[][] inputArray = new int[n][m];

        for (int i = 0; i < n; i++) {
            String[] str = bufferedReader.readLine().split(" ");
            for(int j = 0; j < m; j++){
                inputArray[i][j] = Integer.parseInt(str[j].trim());
            }
        }

        int[][] res = Result.getMergedIntervals(inputArray);

        for (int i = 0; i < res.length; i++) {
            bufferedWriter.write(res[i][0]+" "+res[i][1]+"\n");
        }
        
        bufferedReader.close();
        bufferedWriter.close();
    }

}

/**
 * Time complexity: O(N*N)
 * Auxiliary Space complexity: O(N)
 */