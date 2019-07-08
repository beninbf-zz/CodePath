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
    static String isPresent(int arr[][], int x) {
        int r = arr.length;
        int c = arr[0].length;
        int rowIndex = 0;
        int colIndex = c - 1;
        // Starting from 0th row, find first element from right in current row, let say a[l][m], such
        // that a[l][m] <= x.
        while(rowIndex <= (r-1) && colIndex >= 0){
            // arr[rowIndex][colIndex] is the first element from right in current row rowIndex. 
            if (arr[rowIndex][colIndex] == x){
                return "present";
            }
            
            // As arr is sorted row wise and column wise in increasing order, 
            // we can say that x can't be present at arr[l][m], rowIndex<l and colIndex<m
            // Also, in current row rowIndex, x can't be present as arr[rowIndex][colIndex] < x and 
            // all elements to its left are even smaller than arr[rowIndex][colIndex] and 
            // we have already checked all elements to its right. So moving on to next row.
            // Notice that you can start to check at current column j (stored in colIndex) in next row as x can't
            // be present at arr[l][m], l>rowIndex and m>colIndex
            if (arr[rowIndex][colIndex] < x){
                rowIndex++;
            }
            // As arr is sorted row wise and column wise in increasing order, 
            // we can say that if x < arr[rowIndex][colIndex] means x can be present 
            // on left side of colIndex in same row rowIndex.
            else if(arr[rowIndex][colIndex] > x){
                colIndex--;
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
 * Time complexity: O((r+c)*q)
 * Space complexity: O(1)
 */
