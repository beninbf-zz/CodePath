/**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given a string s of length n, find the length of the longest substring t that contains exactly two distinct
 * characters.
 */
import java.io.*;
import java.util.*;

class Result {

    // -------------------- START ----------------------
    static int getLongestSubstringLengthExactly2DistinctChars(String s) {
        int max_len = 0;
        HashSet<Character> temp = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            temp.clear();
            // Considering substring starting from i and ending at j
            for (int j = i; j < s.length(); j++) {
                temp.add(s.charAt(j));
                // If size of temp set is more than 2 means substring s[i, j] 
                // have more than 2 distinct characters
                if (temp.size() > 2){
                    break;
                }
                // If size of temp set is equal to 2 means substring s[i, j] 
                // is composed of exactly 2 distinct characters hence we compare and store max_len
                if (temp.size() == 2){
                    max_len = Math.max(max_len, j - i + 1);
                }
            }
        }
        // Return maximum possible length of longest substring having exactly 2 distinct characters
        return max_len;
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

        int t = Integer.parseInt(bufferedReader.readLine().trim());
        while (t-- > 0) {
            String s = bufferedReader.readLine().trim();
            int l = Result.getLongestSubstringLengthExactly2DistinctChars(s);

            bufferedWriter.write(String.valueOf(l));
            bufferedWriter.newLine();
        }
        
        bufferedWriter.close();

        bufferedReader.close();
    }

}

/**
 * Time complexity: O(n^2)
 * Space complexity: O(n)
 */
