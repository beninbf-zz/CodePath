/**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given an array of numbers, nums, return an array of numbers products, where products[i]is the product of all nums[j],
 * j != i.
 * 
 * Input : [1, 2, 3, 4, 5]
 * Output: [(2*3*4*5), (1*3*4*5), (1*2*4*5), (1*2*3*5), (1*2*3*4)]
 * = [120, 60, 40, 30, 24]
 * You must do this in O(n) time, and constant space, without using division. Usage of products array is not considered
 * extra space.
 * 
 * Without using division is the key constraint to remember.
 */

import java.io.*;
import java.util.*;

class Result {

    // ============================ Start ============================
    static int mod = (int)Math.pow(10, 9) + 7;
    static int[] getProductArray(int[] nums) {
        // Size of output array is same as that of input array
        int[] products = new int[nums.length];

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            products[currentIndex] = 1;
            for (int iterator = 0; iterator < nums.length; iterator++) {
                if (iterator != currentIndex) {
                    nums[iterator] = nums[iterator]>0?(nums[iterator]%mod):((mod+nums[iterator])%mod);
                    products[currentIndex] = (int)((products[currentIndex] * 1l * nums[iterator])%mod);
                }
            }
        }

        return products;
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
        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int nums[] = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(bufferedReader.readLine().trim());
        }

        int products[] = Result.getProductArray(nums);
        for(int i=0; i<products.length; i++){
            bufferedWriter.write(products[i]+"\n");
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
}
/**
 * Time complexity: O(n*n)
 * Auxiliary space used: O(1)
 * Space complexity: O(n)
 */