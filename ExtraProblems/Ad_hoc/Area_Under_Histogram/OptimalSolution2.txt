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
        // Create an empty stack. The stack holds indexes of arr[] array which can be from l to r. 
        // The bars stored in stack are always in increasing order of their heights. 
        Stack<Integer> stack = new Stack<>();
        
        long max_area = 0; // Initialize max area 
        int tp;  // To store top of stack 
        long area_with_top; // To store area with top bar as the smallest bar 
       
        // Run through all bars of given histogram 
        int i = l; 
        while (i <= r) 
        { 
            // If this bar is higher than the bar on top stack, push it to stack 
            if (stack.empty() || arr[stack.peek()] <= arr[i]) 
                stack.push(i++); 
       
            // If this bar is lower than top of stack, then calculate area of rectangle  
            // with stack top as the smallest (or minimum height) bar. 'i' is  
            // 'right index' for the top and element before top in stack is 'left index' 
            else
            { 
                tp = stack.peek();  // store the top index 
                stack.pop();  // pop the top 
       
                // Calculate the area with arr[tp] stack as smallest bar 
                area_with_top = arr[tp] * 1l * (stack.empty() ? i-l : i - stack.peek() - 1); 
       
                // update max area, if needed 
                if (max_area < area_with_top) 
                    max_area = area_with_top; 
            } 
        } 
       
        // Now pop the remaining bars from stack and calculate area with every 
        // popped bar as the smallest bar 
        while (stack.empty() == false) 
        { 
            tp = stack.peek(); 
            stack.pop(); 
            area_with_top = arr[tp] * 1l * (stack.empty() ? i-l : i - stack.peek() - 1); 
       
            if (max_area < area_with_top) 
                max_area = area_with_top; 
        } 
       
        return max_area;
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