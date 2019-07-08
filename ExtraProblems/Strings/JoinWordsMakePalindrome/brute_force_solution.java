import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Result {

    // -------------------- START ----------------------
    static String[] join_words_to_make_a_palindrome(String words[]) {
        String result[] = new String[2];
        result[0] = "NOTFOUND";
        result[1] = "DNUOFTON";

        int n = words.length;
        // Iterating over all possible pair (i,j), 0<=i<j<n
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // check if (wordsList.get(i) + wordsList.get(j)) is palindrome or not
                if (isPalindrome(words[i] + words[j])) {
                    result[0] = words[i].toString();
                    result[1] = words[j].toString();
                    return result;
                }
                // check if (wordsList.get(i) + wordsList.get(j)) is palindrome or not
                if (isPalindrome(words[j] + words[i])) {
                    result[0] = words[j].toString();
                    result[1] = words[i].toString();
                    return result;
                }
            }
        }
        return result;
    }

    // Check if input string str is palindrome or not
    static boolean isPalindrome(String str) {
        int index = 0;
        int n = str.length();
        while(index<n-index) {
            if(str.charAt(index)!=str.charAt(n-index-1)) {
                return false;
            }
            index++;
        }
        return true;
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
        String words[] = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = bufferedReader.readLine().trim();
        }
        
        String result[] = Result.join_words_to_make_a_palindrome(words);
        for(String str: result){
            bufferedWriter.write(str+"\n");
        }
        bufferedWriter.close();
        bufferedReader.close();
    }
}
