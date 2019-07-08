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

class Result {

    /*
    * Complete the 'find_words' function below.
    *
    * The function accepts STRING and STRING ARRAY as parameter.
    * Return 2D INTEGER ARRAY.
    */
    // ============================ Start ============================
    public static ArrayList<ArrayList<Integer>> find_words(String text, List<String> words){
        // To store indexes for the given words
        ArrayList<ArrayList<Integer>> answer = new ArrayList<ArrayList<Integer>>();
        String words_in_file[] = text.split(" ");
        for(String word: words) {
            ArrayList<Integer> indexes = new ArrayList<Integer>();
            // Iterating over the complete list of words in file
            int sum_index = 0;
            for(int i=0; i<words_in_file.length; i++) {
                // If word matches with word of file then we add index of first character of that word in list
                if(words_in_file[i].compareTo(word)==0) {
                    indexes.add(sum_index);
                }
                sum_index += words_in_file[i].length() + 1;
            }
            // If we found no word matching with what we were looking for then we add -1 in list of indexes
            if(indexes.size()==0) {
                indexes.add(-1);
            }
            answer.add(indexes);
        }
        return answer;
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

        String text = bufferedReader.readLine().trim();
        int q = Integer.parseInt(bufferedReader.readLine().trim());
        List<String> words = new ArrayList<String>();
        
        for(int i=0;i<q;i++){
            words.add(bufferedReader.readLine().trim());
        }
        
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        ArrayList<ArrayList<Integer>> result = Result.find_words(text, words);
        result.stream()
            .map(
                r -> r.stream()
                    .map(Object::toString)
                    .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
                try {
                    bufferedWriter.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }); 
        bufferedWriter.close();
    }
}
