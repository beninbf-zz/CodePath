import java.util.TreeSet;

public class OptimalSolution {
    // ==================start==================
    
    /*
     * Space Complexity: O(len(a) * len(b))
     * Time Complexity: O(len(i) * (len(a) + len(b)))
     */
    public static boolean doStringsInterleave(String a, String b, String i) {
        // Write your code here
        int n = a.length();
        int m = b.length();
        
        // This cannot be an interleaving
        if(n + m != i.length()){
            return false;
        }
        
        // dp[lenA][lenB] determines if lenA from string a and lenB from string b makes interleaving of
        // length lenA+lenB in i
        boolean dp[][] = new boolean[n + 1][m + 1];
        
        // Empty string can be formed by combining empty string from a and b.
        dp[0][0] = true;
        
        for (int indexI = 0; indexI < i.length(); indexI++) {
            for (int indexA = 0; indexA < a.length(); indexA++) {
                // if char at indexA in a matches char at indexI in i, check if previous state was valid
                // state
                if (a.charAt(indexA) == i.charAt(indexI)) {
                    int aLen = indexA;
                    int bLen = indexI - indexA;
                    // Check if bLen is in range
                    if (bLen >= 0 && bLen <= b.length()) {
                        if (bLen >= 0 && dp[aLen][bLen]) {
                            // If the previous state was an interleaving, new state will be an interleaving
                            // as characters match
                            dp[aLen + 1][bLen] = true;
                        }
                    }
                }
            }
            
            for (int indexB = 0; indexB < b.length(); indexB++) {
                // if char at indexB in B matches char at indexI in i, check if previous state was valid
                // state
                if (b.charAt(indexB) == i.charAt(indexI)) {
                    int aLen = indexI - indexB;
                    int bLen = indexB;
                    // Check if aLen is in range
                    if (aLen >= 0 && aLen <= a.length()) {
                        if (dp[aLen][bLen]) {
                            // If the previous state was an interleaving, new state will be an interleaving
                            // as characters match
                            dp[aLen][bLen + 1] = true;
                        }
                    }
                }
            }
        }
        
        // Return if the new string interleaving.
        return dp[n][m];
    }
    
    // ==================end==================
}
