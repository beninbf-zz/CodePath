import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    // -------------------- START ----------------------
    /*
    * Complete the 'findPascalTriangle' function below.
    *
    * The function accepts INTEGER as parameter.
    * Return 2D INTEGER ARRAY.
    */
    static List<List<Integer>> findPascalTriangle(int n) {
        long res = 0;
        int mod = 1000000007;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j <= i; j++) {
                // nCr(i,j) is nothing but jth value in ith line of Pascal's Triangle (0-based indexing)
                row.add((int)(nCr(i, j, mod)%mod));
            }
            result.add(row);
        }
        return result;
    }

    private static long nCr(int n, int r, int mod) {
        long num = 1;
        long den = 1;
        for (long i = 1; i <= n; i++) {
            num *= i;
            num %= mod;
        }

        for (long i = 1; i <= (n - r); i++) {
            den *= i;
            den %= mod;
        }

        for (long i = 1; i <= r; i++) {
            den *= i;
            den %= mod;
        }

        long nCr = (num * modInverse((int) den, mod)) % mod;
        return nCr;
    }

    // Works only for prime value of modulo
    static long modInverse(int a, int mod) {
        return modPower(a, mod - 2, mod);
    }

    // To compute x^y under modulo m
    static long modPower(int x, int y, int m) {
        if (y == 0)
            return 1;

        long p = modPower(x, y / 2, m) % m;
        p = (p * p) % m;

        if (y % 2 == 0)
            return p;
        else
            return (p * x) % m;
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
        List<List<Integer>> result = Result.findPascalTriangle(n);

        result.stream()
            .map(
                r -> r.stream()
                    .map(Object::toString)
                    .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
                try {
                    bufferedWriter.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        bufferedWriter.close();
        bufferedReader.close();
    }

}
/**
 * Time complexity: O(n^3)
 * Space complexity: O(n^2)
 */
