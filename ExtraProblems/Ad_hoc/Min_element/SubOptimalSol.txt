import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;


class Result {

    /*
    * Complete the 'find_minimum' function below.
    *
    * The function accepts INTEGER ARRAY as parameter.
    * Return INTEGER.
    */
    // ============================ Start ============================
    public static int find_minimum(List<Integer> arr) {
        int n = arr.size();
        if(n==1) {
            return arr.get(0);
        }
        if(n==2) {
            return Math.min(arr.get(0), arr.get(1));
        }
        if(n==3) {
            return Math.min(arr.get(0), Math.min(arr.get(1), arr.get(2)));
        }
        /*
         * All numbers of array are unique as given in question so don't consider the cases when 
         * numbers are equal and get confuse.
         */
        /*
         * consider example [4, 7, 8, 10, 15].
         * for this given array was [4, 7, 8, 10, 15] which was sorted in ascending order
         * and rotated right by 0
         */
        if(arr.get(0)-arr.get(n-1)<0&&arr.get(0)-arr.get(1)<0&&arr.get(n-2)-arr.get(n-1)<0) {
            return arr.get(0);
        }
        /*
         * consider example [15, 10, 8, 7, 4].
         * for this given array was [15, 10, 8, 7, 4] which was sorted in ascending order
         * and rotated right by 0
         */
        if(arr.get(0)-arr.get(n-1)>0&&arr.get(0)-arr.get(1)>0&&arr.get(n-2)-arr.get(n-1)>0) {
            return arr.get(n-1);
        }
        if(arr.get(0)-arr.get(n-1) > 0) {
            /*
             * consider example [10, 13, 15, 4, 6, 8].
             * for this given array was [4, 6, 8, 10, 13, 15] which was sorted in ascending order
             * and rotated right by 3
             */
            return find_minimum_in_increasing(arr, 0, n-1);
        }
        else {
            /*
             * consider example [8, 6, 4, 15, 13, 10].
             * for this given array was [15, 13, 10, 8, 6, 4] which was sorted in descending order
             * and rotated right by 3
             */
            return find_minimum_in_decreasing(arr, 0, n-1);
            
        }
    }
    public static int find_minimum_in_increasing(List<Integer> arr, int low, int high) {
        if(low>high){
            return -1;
        }
        if(arr.get(low)-arr.get(high)<=0){
            return arr.get(low);
        }
        int mid = (low+high)/2;
        if(arr.get(mid)-arr.get(low)>=0){
            return find_minimum_in_increasing(arr, mid+1, high);
        }
        return find_minimum_in_increasing(arr, low, mid);
    }
    public static int find_minimum_in_decreasing(List<Integer> arr, int low, int high) {
        if(low>high){
            return -1;
        }
        if(arr.get(low)-arr.get(high)>=0) {
            return arr.get(high);
        }
        int mid = (low+high)/2;
        if(arr.get(mid)-arr.get(low)<0){
            return find_minimum_in_decreasing(arr, mid, high);
        }
        return find_minimum_in_decreasing(arr, low, mid);
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

        int n = Integer.parseInt(bufferedReader.readLine().trim());
        List<Integer> arr = new ArrayList<Integer>();
        
        for(int i=0;i<n;i++){
            arr.add(Integer.parseInt(bufferedReader.readLine().trim()));
        }
        
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        int result = Result.find_minimum(arr);
        bufferedWriter.write(result+"\n");
        bufferedWriter.close();
    }
}
