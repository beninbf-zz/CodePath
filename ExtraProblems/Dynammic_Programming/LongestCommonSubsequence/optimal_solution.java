import java.io.*;
import java.lang.reflect.Member;
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

    // ============================ Start ============================
    // Returns String of LCS for a[0..m-1], b[0..n-1] 
    public static String lcs(String a, String b) {
        int len_a = a.length(), len_b = b.length();
        int[][] L = new int[len_a+1][len_b+1];

        // Following steps build L[m+1][n+1] in bottom up fashion. Note
        // that L[i][j] contains length of LCS of a[0..i-1] and b[0..j-1]
        for (int i=0; i<=len_a; i++) { 
            for (int j=0; j<=len_b; j++) { 
                if (i == 0 || j == 0) {
                    L[i][j] = 0;
                }
                else if (a.charAt(i-1) == b.charAt(j-1)) {
                    L[i][j] = L[i-1][j-1] + 1;
                }
                else {
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]);
                }
            }
        }

        // Following code is used to print LCS
        int index = L[len_a][len_b];
        int temp = index;

        // Create a character array to store the lcs string
        char[] lcs = new char[index+1];
        lcs[index] = ' '; // Set the terminating character

        // Start from the right-most-bottom-most corner and
        // one by one store characters in lcs[]
        int i = len_a, j = len_b;
        while (i > 0 && j > 0) {
            // If current character in a and b are same, then
            // current character is part of LCS
            if (a.charAt(i-1) == b.charAt(j-1)) {
                // Put current character in result
                lcs[index-1] = a.charAt(i-1);

                // reduce values of i, j and index
                i--;
                j--;
                index--;
            }

            // If not same, then find the larger of two and
            // go in the direction of larger value
            else if (L[i-1][j] > L[i][j-1]) {
                i--;
            }
            else {
                j--;
            }
        }
        String answer = new String(lcs);
        answer = answer.trim();
        return answer.length() == 0 ? "-1" : answer;
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
    
    public static int MAX_LENGTH = 100000;

    public static void solve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String a = bufferedReader.readLine().trim();
        String b = bufferedReader.readLine().trim();

        String result = Result.lcs(a, b);
        bufferedWriter.write(result+"\n");
        bufferedWriter.close();
    }
}
