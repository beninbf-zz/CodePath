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
        // To store prefix sum i.e. sum of subarray starting at index 0 and ending at index i
        // Key of hashmap will be sum and value will be index i for prefix sum
        HashMap<Long, Integer> map = new HashMap<>();
        // To check whether prefix sum it self is equal to zero
        map.put(0l, -1);
        // To store current sum
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            // To check if we encountered with value which itself is zero
            if(arr[i]==0){
                res[0]=i;
                res[1]=i;
                return res;
            }
            // Adding current value in current sum
            sum += arr[i];
            // If we found value sum in our hashmap means we have encountered with this sum before
            // means arr[0, map.get(sum)] = sum and
            // arr[0, map.get(sum)] + arr[map.get(sum)+1, i] = sum
            // which implies arr[map.get(sum)+1, i] = 0 and hence interval we are looking for is
            // start = map.get(sum)+1 and end = i
            if (map.containsKey(sum)) {
                res[0] = map.get(sum) + 1;
                res[1] = i;
                return res;
            } else {
                map.put(sum, i);
            }
        }
        // If no subarray having sum = 0 found then we will return [-1]
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
 * Time complexity: O(n)
 * Space complexity: O(n)
 */