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
        HashMap<Character, Integer> countMap = new HashMap<>();
        int left = 0, right = 0;
        while (right < s.length()) {
            // We are maintaining character along with it's frequency
            if (countMap.containsKey(s.charAt(right))) {
                countMap.put(s.charAt(right), countMap.get(s.charAt(right))+1);
            }
            else{
                countMap.put(s.charAt(right), 1);
            }
            // If size of countMap is more than 2 means substring s[left, right] 
            // have more than 2 distinct characters so, we remove characters from left
            // while countMap size is more than 2
            while (countMap.size() > 2) {
                countMap.put(s.charAt(left), countMap.get(s.charAt(left)) - 1);
                if (countMap.get(s.charAt(left)) == 0){
                    countMap.remove(s.charAt(left));
                }
                left++;
            }
            // If size of countMap is equal to 2 means substring s[left, right] 
            // is composed of exactly 2 distinct characters hence we compare and store max_len
            if (countMap.size() == 2) {
                max_len = Math.max(max_len, right - left + 1);
            }
            right++;
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
 * Time complexity: O(n)
 * Space complexity: O(n)
*/
