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
        // Sorting the input interval array by their starting points in increasing order
        Arrays.sort(inputArray, (int object1[], int object2[]) -> { 
            if (object1[0] != object2[0]){
                return object1[0] - object2[0];
            }
            return object1[1] - object2[1]; }
        );

        Stack<int[]> stack = new Stack<>();
        int[] popped;
        for (int[] interval : inputArray) {
            if (!stack.isEmpty()) {
                // Checking if interval at top of stack is overlapping with current interval
                if (stack.peek()[1] >= interval[0]) {
                    // Checking if interval at top of stack has ending point less than that of current interval
                    if (stack.peek()[1] < interval[1]) {
                        // Updating ending point of interval at top of stack with that of current interval
                        popped = stack.pop();
                        popped[1] = interval[1];
                        stack.push(popped);
                    }
                } else {      
                    // if not overlapping
                    stack.push(interval);
                }
            } else {        
                // if stack was empty i.e. it was the first interval
                stack.push(interval);
            }
        }

        int[][] outputArray = new int[stack.size()][2];
        int ptr = outputArray.length - 1;
        // Popping out all found non overlapping intervals
        while (!stack.isEmpty()) {
            popped = stack.pop();
            outputArray[ptr] = popped;
            ptr--;
        }
        return outputArray;
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
 * Time complexity: O(N*log(N))
 * Auxiliary Space complexity: O(N)
 */