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
        int n = r - l + 1;
        long maxArea = 0;
        
        // rightSmaller[i] = j, implies that j is smallest index greater than i 
        // such that arr[l+i]>arr[r+j] where 0<=i<(r-l+1) and 0<=j<(r-l+1)

        // To fill rightSmaller array
        int[] rightSmaller = new int[n];
        Stack<Integer> stack = new Stack<Integer>();
        
        for (int i = l; i <= r; i++) {
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                    int popped = stack.pop();
                    rightSmaller[popped - l] = i - l;
                }
                stack.push(i);
            }
        }
        while (!stack.isEmpty()) {
            int popped = stack.pop();
            rightSmaller[popped - l] = n;
        }

        // leftSmaller[i] = j, implies that j is smallest index greater than i 
        // such that arr[l+i]>arr[r+j] where 0<=i<(r-l+1) and 0<=j<(r-l+1)

        // To fill leftSmaller array
        int[] leftSmaller = new int[n];
        
        for (int i = r; i >= l; i--) {
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                    int popped = stack.pop();
                    leftSmaller[popped - l] = i - l;
                }
                stack.push(i);
            }
        }
        while (!stack.isEmpty()) {
            int popped = stack.pop();
            leftSmaller[popped - l] = -1;
        }
        // Notice that all elements in subarray arr[leftSmaller[i]+1], arr[leftSmaller[i]+2],...,
        // arr[rightSmaller[i]-1] are greater than or equal to arr[i]
        // So, the max area a rectangle can have such that it must contain ith bar is
        // (arr[i]*((rightSmaller[i]-1) - (leftSmaller[i]+1) + 1)

        // to calculate histogram area. here n = r - l + 1
        long currentMaxArea;
        for (int i = 0; i < n; i++) {
            currentMaxArea = arr[i + l] * (rightSmaller[i] - leftSmaller[i] - 1);
            maxArea = Math.max(currentMaxArea, maxArea);
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
 * Time complexity: O(n)
 * Space complexity: O(n)
 */
