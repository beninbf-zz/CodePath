import java.io.*;
import java.util.*;

class Result {

    /*
    * Complete the 'solveBalancedLineBreaks' function below.
    *
    * The function accepts INTEGER ARRAY and INTEGER as parameter.
    * Return LONG.
    */
    // ============================ Start ============================
    static long solveBalancedLineBreaks(List<String> words, int limit) {
        long infinite = Long.MAX_VALUE;
        int n = words.size();
        long[] dp = new long[n + 1];
        // dp[i] = x denotes that the total minimized cost is x if we had sequence of words
        // [i,i+1,...,n-1] and we put line breaks in a most balanced way

        int currentLineNonSpaceChars, noOfWordsInCurrentLine, currentLineTotalChars;
        long currentLineCost;

        for (int i = n - 1; i >= 0; i--) {
            // currentLineNonSpaceChars denotes, as name suggests, sum of no of characters in current line
            // excluding spaces present between each consecutive pair of words
            currentLineNonSpaceChars = 0;
            noOfWordsInCurrentLine = 0;
            dp[i] = infinite;
            for (int j = i; j < n; j++) {
                // Here, current line means the first line having sequence of words [i,i+1,...,j]
                currentLineNonSpaceChars += words.get(j).length();
                noOfWordsInCurrentLine++;

                // currentLineTotalChars denotes, as name suggests, sum of no of characters in current line
                // including spaces present between each consecutive pair of words
                currentLineTotalChars = currentLineNonSpaceChars + noOfWordsInCurrentLine - 1;
                if (currentLineTotalChars > limit){
                    break;
                }

                currentLineCost = (j == n-1) ? 0 : (limit - currentLineTotalChars);
                currentLineCost = currentLineCost * currentLineCost * currentLineCost;

                dp[i] = Math.min(currentLineCost + dp[j + 1], dp[i]);
            }
        }

        return dp[0];
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

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> words = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String word = bufferedReader.readLine().trim();
            words.add(word);
        }

        int limit = Integer.parseInt(bufferedReader.readLine().trim());
        
        long result = Result.solveBalancedLineBreaks(words, limit);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();
        
        bufferedWriter.close();
        bufferedReader.close();
    }
}
