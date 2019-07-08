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
        root = new TrieNode();
        String words_in_file[] = text.split(" ");
        int sum_index = 0;
        for(int i=0;i<words_in_file.length;i++) {
            // Inserting all the words of file in trie
            insert_trie(words_in_file[i], sum_index);
            sum_index += words_in_file[i].length() + 1;
        }
        ArrayList<ArrayList<Integer>> answer = new ArrayList<ArrayList<Integer>>();
        for(String word: words) {
            // Searching for word in trie
            ArrayList<Integer> indexes = search_trie(word);
            // If no word found in file matching with our word then list of indices will have -1
            if(indexes.size()==0) {
                indexes.add(-1);
            }
            answer.add(indexes);
        }
        return answer;
    }
    // Root is for maintaining the root of trie
    public static TrieNode root;
    public static class TrieNode{
        // To maintain data, children information, indices and information that any word ending at this node or not
        char data;
        HashMap<Character, TrieNode> children;
        ArrayList<Integer> indexes;
        
        public TrieNode() {
            children = new HashMap<Character, TrieNode>();
            indexes = new ArrayList<Integer>();
        }
    }
    public static void insert_trie(String word, int index) {
        // To insert word in the trie
        TrieNode head = root;
        for(char c: word.toCharArray()) {
            if(head.children.containsKey(c)) {
                // If child of current trie node have data matching with the character then we will make that child as current node
                head = head.children.get(c);
            }else {
                // If current trie does not have any child matching to the character then we create new child and make that as current node
                TrieNode new_node = new TrieNode();
                new_node.data = c;
                head.children.put(c, new_node);
                head = new_node;
            }
        }
        head.indexes.add(index);
    }
    public static ArrayList<Integer> search_trie(String word) {
        // To search for the word in the trie
        TrieNode head = root;
        for(char c: word.toCharArray()) {
            if(head.children.containsKey(c)) {
                // If child of current trie node's data matching with the character then we will make that child as current node 
                head = head.children.get(c);
            }else {
                // If child of current trie node's data not matching then will return blank list of indices
                return new ArrayList<Integer>();
            }
        }
        return head.indexes;
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
