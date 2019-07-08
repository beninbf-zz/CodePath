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


/* This is a solution which uses memoization. 
 * You can read more about it here: https://www.geeksforgeeks.org/memoization-1d-2d-and-3d/ */
class Result {

    /*
     * Complete the 'lcs' function below.
     *
     * The function accepts STRING a and STRING b as parameter.
     * The function is expected to return a STRING.
     */
    
    public static String lcs(String a, String b) {
        // Write your code here
        //computing maximum of size of strings a and b.
        int n = (a.length()>b.length()?a.length():b.length());
        String dp[][] = new String[n][n];
        return recur(a,b,0,0,dp);
    
    }

    static String recur(String s, String t, int i, int j, String[][] dp) {
        if(i>=s.length()) return "";
        if(j>=t.length()) return "";
        // we return the string if we already visited this state.
        if(dp[i][j]!= null) return dp[i][j];
        String a = recur(s, t, i+1, j, dp);
        String b = recur(s, t, i, j+1, dp);
        // we choose the string whose length is more
        String ans = ((a.length()>b.length())?a:b);
        //if character matchces
        if(s.charAt(i)==t.charAt(j)) {
            //we increase index of both and recur
            String c = s.charAt(i) + recur(s, t, i+1, j+1, dp);
            //we choose the stirng whose length is more from String c and String ans
            return dp[i][j] = ((c.length()>ans.length())?c:ans);
        }
        else return dp[i][j] =ans;
    }
}


class Solution {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String a = bufferedReader.readLine();

        String b = bufferedReader.readLine();

        String result = Result.lcs(a, b);
        
        if(result == null || result.length()==0) {
            result = "-1";
        }
        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();
        bufferedReader.close();
    }
    
}