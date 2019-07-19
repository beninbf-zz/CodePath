import java.io.*;
import java.util.*;

class Result {

    // ============================ Start ============================
    static int calculateHammingWeight(List<Long> s){
        int total_sum = 0;
        for(Long num: s){
            total_sum += countSetBits(num);
        }
        return total_sum;
    }
    static int countSetBits(long n) { 
        if (n == 0l) {
            return 0; 
        }
        else {
            return 1 + countSetBits(n & (n - 1l)); 
        }
    } 
    // ============================ End =============================
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

        List<Long> s = new ArrayList<Long>();

        for (int i = 0; i < n; i++) {
            Long s_item = Long.parseLong(bufferedReader.readLine().trim());
            s.add(s_item);
        }

        bufferedReader.close();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        int result = Result.calculateHammingWeight(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();
        bufferedWriter.close();
    }
}
