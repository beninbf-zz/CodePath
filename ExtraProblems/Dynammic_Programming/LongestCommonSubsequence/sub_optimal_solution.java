import java.io.*;
import java.util.*;

class Result {

    // ============================ Start ============================

    public static String lcs(String a, String b) {
        // dp is to store the values that are used again while calculating the LCS;
        //dp[i][j] tells the LCS for String A starting from 'i' to the end & 
        // String B, starting from 'j' to the end
        String dp[][] = new String[a.length()][b.length()];
        String answer = lcs_util(a, b, 0, 0, dp);
        answer = answer.trim();
        return answer.length() == 0 ? "-1" : answer;
    }
    // This method returns the LCS of a, starting from 'i' to the end & b, starting from 'j' to the end
    private static String lcs_util(String a, String b, int i, int j, String dp[][]) {
        // if the index of either of the string is outside the bound of the respective string,
        // then there cant be a common substring, so return empty string
        if(i >= a.length() || j >= b.length()) {
            return "";
        }

        if(dp[i][j] == null) {
            // Find LCS by excluding the ith and jth string respectively, and the longer of either can be a possible answer.
            String excluding_ith = lcs_util(a, b, i + 1, j, dp);
            String excluding_jth = lcs_util(a, b, i, j + 1, dp);
            dp[i][j] = excluding_ith.length() > excluding_jth.length() ? excluding_ith : excluding_jth;

            if (a.charAt(i) == b.charAt(j)) {
                String excluding_both = lcs_util(a, b, i + 1, j + 1, dp);
                dp[i][j] = ( 1 + excluding_both.length()) > dp[i][j].length() ? a.charAt(i) + excluding_both : dp[i][j];
            }
        }

        return dp[i][j];
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
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String a = bufferedReader.readLine().trim();
        String b = bufferedReader.readLine().trim();

        String result = Result.lcs(a, b);
        bufferedWriter.write(result+"\n");
        bufferedWriter.close();
    }
}
