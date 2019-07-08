import java.io.*;
import java.util.*;

class Result {

    // ============================ Start ============================
    static String lcs(String a, String b) {
        // Calling function lcs_util with index pointing to starting of both strings
        String answer = lcs_util(a, b, 0, 0);
        answer = answer.trim();
        return answer.length() == 0 ? "-1" : answer;
    }

    // This method returns the LCS of str1, starting from 'i' to the end & str2, starting from 'j' to the end
    static String lcs_util(String str1, String str2, int i, int j) {

        if(i >= str1.length() || j >= str2.length()){
            return "";
        }

        // excluding_ith gives the LCS with str1 starting at (i+1), which means excluding 'i' whereas,
        // excluding_jth gives the LCS with str2 starting at (j+1), which means excluding 'j' and str1 starting at 'i'.
        String excluding_ith = lcs_util(str1, str2, i + 1, j);
        String excluding_jth = lcs_util(str1, str2, i, j + 1);

        // longer among excluding_ith and excluding_jth can be a possible answer.
        String ans = (excluding_ith.length() > excluding_jth.length()) ? excluding_ith : excluding_jth;

        // if char at 'i' in str1 == char at 'j' in str2, then this character can be included in the LCS,
        // if by excluding (i.e. 'ans' calculated above) it we dont get the LCS.
        if (str1.charAt(i) == str2.charAt(j)) {
            String excluding_both = lcs_util(str1, str2, i + 1, j + 1);
            return (1 + excluding_both.length()) > ans.length() ? (str1.charAt(i) + excluding_both) : ans;
        }
        return ans;
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
