import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    // ============================ Start ============================
    static long MOD = 7l + (long)Math.pow(10, 9);

    static int calculate_power(long a, long b) {
        // To store resultant value
        long answer = 1;

        // To store a^(incremental power of 2). Initial value a^(2^0) = a
        long powerOfTwo = a % MOD;

        while (b > 0) {

            // For set bit in b
            if ((b & 1l) > 0) {
                answer = (answer * powerOfTwo) % MOD;
            }
            
            // To increase value of powerOfTwo by powerOfTwo
            // For example to make a^(2^2 + 2^2) from a^(2^2) as a^(2^2 + 2^2) = a^(2^2) * a(2^2).
            powerOfTwo = (powerOfTwo * powerOfTwo) % MOD;

            // Divide b by 2
            b = b >> 1;
        }
        return (int)answer;
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
    
    static long MAX_VALUE = (long)Math.pow(10, 18);
    static long MOD = 7l + (long)Math.pow(10, 9);

    public static void solve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        long a = Long.parseLong(bufferedReader.readLine().trim());
        long b = Long.parseLong(bufferedReader.readLine().trim());

        assert (0 <= a && a <= MAX_VALUE) : "Invalid value of a";
        assert (0 <= b && b <= MAX_VALUE) : "Invalid value of b";
        assert (!(a==0 && b==0)) : "Invalid value of a and b";

        int result = Result.calculate_power(a, b);
        assert (0<=result && result<(int)MOD) : "Invalid value of result";

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();
        bufferedReader.close();
        bufferedWriter.close();
    }
}
