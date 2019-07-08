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
        String words_in_file[] = text.split(" ");
        
        // For storing all the words in file along with list of indices of there occurring
        HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
        int sum_index = 0;
        for(int i=0; i<words_in_file.length; i++) {
            String word = words_in_file[i];
            ArrayList<Integer> indexes = new ArrayList<Integer>();
            if(map.containsKey(word)) {
                indexes = map.get(word);
            }
            indexes.add(sum_index);
            sum_index += word.length() + 1;
            map.put(word, indexes);
        }

        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
        for(String word: words){
            ArrayList<Integer> indexes = new ArrayList<Integer>();
            if(map.containsKey(word)){
                indexes = map.get(word);
            }
            else{
                indexes.add(-1);
            }
            ans.add(indexes);
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
