/**
 * *********************** PROBLEM DESCRIPTION ***************************
 * You are given a sorted 2D array arr of size r*c, where all the numbers (integers) in the array are in increasing
 * order from left to right and top to bottom i.e. arr[i][j]<=arr[i+1][j] and arr[i][j]<=arr[i][j+1].
 * Check if a given number x exists in it or not.
 */
import java.io.*;
import java.util.*;

class Result {

    // -------------------- START ----------------------
    static String isPresent(int[][] arr, int x) {
        int r = arr.length;
        int c = arr[0].length;
        // Iterate over entire input array to check if x is present or not
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (arr[i][j] == x) {
                    return "present";
                }
            }
        }
        return "not present";
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
        int arrRows = Integer.parseInt(bufferedReader.readLine().trim());
        int arrColumns = Integer.parseInt(bufferedReader.readLine().trim());
        
        int[][] arr = new int[arrRows][arrColumns];

        for (int arrRowItr = 0; arrRowItr < arrRows; arrRowItr++) {
            String[] arrRowItems = bufferedReader.readLine().split(" ");

            for (int arrColumnItr = 0; arrColumnItr < arrColumns; arrColumnItr++) {
                int arrItem = Integer.parseInt(arrRowItems[arrColumnItr].trim());
                arr[arrRowItr][arrColumnItr] = arrItem;
            }
        }
        
        int q = Integer.parseInt(bufferedReader.readLine().trim());
        int x;
        String res;
        for (int i=0 ; i<q ; i++){
            x = Integer.parseInt(bufferedReader.readLine().trim());
            res = Result.isPresent(arr, x);
            bufferedWriter.write(res+"\n");
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
}


/**
 * Time complexity: O((r*c)*q)
 * Space complexity: O(1)
 */