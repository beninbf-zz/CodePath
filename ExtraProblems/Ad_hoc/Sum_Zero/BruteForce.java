/**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given an array of integers arr of size n, find a subarray whose sum is zero.
 */
import java.io.*;
import java.util.*;

class Result {

    // -------------------- START ----------------------
    static int[] sumZero(int[] arr) {
        // To store interval (start, end) for which sum is zero
        int[] res = new int[2];
        // To store current sum
        long sum = 0;
        // We are trying to find sum of each subarray[i, n-1] where 0<=i<=n-1.
        for (int i = 0; i < arr.length; i++) {
            sum = 0;
            // To calculate sum of subarray starting from i and ending at n-1 
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                // If sum == 0 means we found our subarray having sum as 0 with start index i 
                // and end index j
                if (sum == 0) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        // If no subarray as sum equal to zero found then we will return [-1]
        return new int[]{-1};
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

        int n = Integer.parseInt(bufferedReader.readLine());

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(bufferedReader.readLine());
        }

        int[] resSubArray = Result.sumZero(arr);

        for (int i = 0; i < resSubArray.length; i++) {
            bufferedWriter.write(String.valueOf(resSubArray[i])+"\n");
        }
        bufferedWriter.newLine();
        
        bufferedWriter.close();

        bufferedReader.close();
    }

}
/**
 * Time complexity: O(n*n)
 * Space complexity: O(n)
 */