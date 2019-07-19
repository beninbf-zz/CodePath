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
        int minimum = Integer.MAX_VALUE;
        // Iterating over all the values of arr to find minimum value
        for(int num: arr){
            minimum = Math.min(minimum, num);
        }
        return minimum;
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
