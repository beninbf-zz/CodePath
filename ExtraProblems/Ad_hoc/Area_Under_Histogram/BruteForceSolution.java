/**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Find the largest rectangular area possible in a given histogram, where 
 * the largest rectangle can be made of a number of contiguous bars. 
 * For simplicity, assume that all bars have same width and the width is 1 unit. 
 * You will be given an array arr of height of bars of size n.
 */

import java.io.*;
import java.util.*;

class Result {

    // ============================ Start ============================
    static long findMaxPossibleArea(long[] arr, int l, int r) {
        long maxArea = 0;
        long currentMaxPossibleHeight, currentMaxArea;
        //Iterating over all rectangles which starts from ith bar
        for (int i = l; i <= r; i++) {
            currentMaxPossibleHeight = Long.MAX_VALUE;
            // Finding area of rectangle which starts from ith bar and ends at jth bar
            for (int j = i; j <= r; j++) {
                // Height of rectangle which starts from ith bar and ends at jth bar 
                // can be minimum of all the heights (arr[k], i<=k<=j) between i and j.
                currentMaxPossibleHeight = Math.min(currentMaxPossibleHeight, arr[j]);
                currentMaxArea = currentMaxPossibleHeight * (j - i + 1);
                maxArea = Math.max(currentMaxArea, maxArea);
            }
        }
        return maxArea;
    }
    // ============================= End ==============================
}

class Solution {
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
        int heightsCount = Integer.parseInt(bufferedReader.readLine().trim());

        long[] heights = new long[heightsCount];

        for (int heightsItr = 0; heightsItr < heightsCount; heightsItr++) {
            heights[heightsItr] = Long.parseLong(bufferedReader.readLine().trim());
        }

        int q = Integer.parseInt(bufferedReader.readLine().trim());
        for (int i = 0; i < q; i++) {
            int l = Integer.parseInt(bufferedReader.readLine().trim());

            int r = Integer.parseInt(bufferedReader.readLine().trim());

            long res = Result.findMaxPossibleArea(heights, l, r);
            bufferedWriter.write(res+"\n");
        }
        bufferedWriter.close();
    }
}
/**
 * Time complexity: O(n*n)
 * Space complexity: O(n)
 */