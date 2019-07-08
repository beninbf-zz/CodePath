import java.io.*;
import java.util.*;

class Result {

    // -------------------- START ----------------------
    static String[] join_words_to_make_a_palindrome(String words[]) {
        String result[] = new String[2];
        result[0] = "NOTFOUND";
        result[1] = "DNUOFTON";

        HashMap<String, Integer> count = new HashMap<String, Integer>();
        for(String word: words){
            if(count.containsKey(word)){
                count.put(word, count.get(word)+1);
            }else{
                count.put(word, 1);
            }
        }
        // To find (left_word + right_word) exist which form palindrome where 
        // left_word and right_word in given list of words
        String current = "";
        
        for(String left_word: words){
            
            current = "";
            // Two cases are possible here:
            // 
            // CASE 1: words[x].length() >= words[y].length()
            
            // Iterating over words, considering the word in the current iteration as xth word in words.
            // Task is to find out if there exists some yth word, such that 
            // words[x] + words[y] is a palindrome. 
            // Now, if such y exists, it must be of the form
            // stringReverse(words[x].substring(0, k)), for some 0 <= k < words[x].length(). 
            // So, now we only need to find such k,
            // such that (words[y] == stringReverse(words[x].substring(0, k))) and 
            // (words[x].substring(k+1,words[x].length())) is a palindrome, if (k+1<words[x].length())
            
            for(int j=0;j<left_word.length();j++){
                // Here, current string denotes stringreverse(left_word.substring(0, j))
                // Check if current string is present in words or not
                current = left_word.charAt(j) + current;
                
                if(count.containsKey(current)){
                    // Handles that case so that same string itself doesn't get picked up as other string in pair to
                    // form a palindrome
                    if(current.equals(left_word)){
                        if(count.get(current)>1){
                            result[0] = left_word;
                            result[1] = current;
                            return result;
                        }
                    }
                    // Check if left_word.substring(j+1, len(left_word)) is a palindrome or not
                    else if(isPalindrome(left_word.substring(j+1))){
                        result[0] = left_word;
                        result[1] = current;
                        return result;
                    }
                }
            }
            
            current = "";
            
            // CASE 2: words[x].length() < words[y].length()
            
            // Iterating over words, considering the word in the current iteration as xth word in words.
            // Task is to find out if there exists some yth word, such that 
            // words[y] + words[x] is a palindrome. 
            // Now, if such y exists, it must be of the form
            // stringReverse(words[x].substring(k, words[x].length())), for some 0 <= k < words[x].length(). 
            // So, now we only need to find such k,
            // such that (words[y] == stringReverse(words[x].substring(k, words[x].length()))) and 
            // (words[x].substring(0, k)) is a palindrome, if (k>0)
            
            for(int j=left_word.length()-1;j>=0;j--){
                // Here, current string denotes stringreverse(left_word.substring(j+1, len(left_word)))
                // Check if current string is present in words or not
                current = current + left_word.charAt(j);
                
                if(count.containsKey(current)){
                    // Handles that case so that same string itself doesn't get picked 
                    // up as other string in pair to form a palindrome
                    if(current.equals(left_word)){
                        if(count.get(current)>1){
                            result[0] = current;
                            result[1] = left_word;
                            return result;
                        }
                    }
                    // Check if left_word.substring(0, j) is a palindrome or not
                    else if(isPalindrome(left_word.substring(0, j))){
                        result[0] = current;
                        result[1] = left_word;
                        return result;
                    }
                }
            }
        }
        return result;
    }
    
    // Check if string str is palindrome or not
    static boolean isPalindrome(String str) {
        int l = 0;
        int n = str.length();
        while(l<n-l){
            if(str.charAt(l)!=str.charAt(n-1-l)){
                return false;
            }
            l++;
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
