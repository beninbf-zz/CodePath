import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.Collectors;


class Result {
    //------------------------------------START------------------------------------
    /*
     * Complete the solver function below.
     *
     * The function accepts STRING_ARRAY dictionary as parameter. and string array
     * mat as input matrix. The function returns the list of all possible words from
     * dictionary found in the matrix mat
     */
    public static boolean check_validity(int x, int y, List<String> mat, TrieNode rt) {
        int n = mat.size();
        int m = mat.get(0).length();
        return x>=0 && y>=0 && x<n && y<m && rt != null && rt.child[mat.get(x).charAt(y)-'a']!=null;
    }
    public static class TrieNode {
        // stores presence of current node in trie
        int cnt;
        // marks true if current node is end of word in trie
        boolean isEnd;
        // stores references to all its children
        TrieNode child[];
        // paramtrized constructor
        public TrieNode() {
            cnt = 1;
            child = new TrieNode[26];
            isEnd = false;
            for(int i=0;i<26;i++) {
                child[i] = null;
            }
        }
    }
    public static void insert_in_trie(TrieNode root, String str) {
        TrieNode tmp = root;
        int l = str.length();
        for (int i = 0; i < l; i++) {
            int idx = str.charAt(i) - 'a';
            if (tmp.child[idx] == null) {
                tmp.child[idx] = new TrieNode();
            }
            else {
                tmp.child[idx].cnt++;
            }
            tmp = tmp.child[idx];
        }
        tmp.isEnd = true;
    }
    public static void remove_from_trie(String str, TrieNode root) {
        int l = str.length();
        TrieNode curr = root;
        for (int i = 0; i < l; i++) {
            int idx = str.charAt(i) - 'a';
            if (curr == null || curr.child[idx] == null){
                break;
            }
            TrieNode tmp = curr.child[idx];
            tmp.cnt--;
            if (tmp.cnt == 0) {
                curr.child[idx] = null;
            }
            curr = tmp;
        }
    }
    public static void traverse(int x, int y, List<String> mat, boolean vis[][], 
        TrieNode root, TrieNode rt, List<String> result, String res) {
        // check if rt node is end of any word in trie
        if (rt !=null && rt.isEnd) {
            // unmark the end node
            rt.isEnd = false;
            // push the current word in the result array
            result.add(res);
            // remove the current word from the trie
            remove_from_trie(res, root);
        }

        // mark current node as visited
        vis[x][y] = true;

        // iterate in all 8 - directions from current cell
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                // if this cell(x+i, y+j) is valid and not visited
                if (check_validity(x + i, y + j, mat, rt) && !vis[x + i][y + j]) {
                    int idx = mat.get(x + i).charAt(y + j) - 'a';
                    // building the word further
                    res += (mat.get(x + i).charAt(y + j));
                    // extend dfs traversal to cell(x+i,y+j)
                    traverse(x + i, y + j, mat, vis, root, rt.child[idx], result, res);
                    // pop current char from the end of current word
                    res = res.substring(0, res.length()-1);
                }
            }
        }
        // mark current cell as non - visited
        vis[x][y] = false;
    }
    public static List<String> boggle_solver(List<String> dictionary, List<String> mat) {
        // Write your code here
        TrieNode root = new TrieNode();
        for (String word: dictionary) {
            insert_in_trie(root, word);
        }
        int n = mat.size();
        int m = mat.get(0).length();

        List<String> answer = new ArrayList<String>();
        boolean visited[][] = new boolean[n][m];
        for(int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                int idx = mat.get(i).charAt(j) - 'a';

                if (root.child[idx]!=null) {
                    // stores words found with first char as mat[i][j]
                    List<String> foundWords = new ArrayList<String>();
                    String word = "";
                    // initializing word's first char
                    word = word + mat.get(i).charAt(j);
                    // do a dfs traversal at location (i,j)
                    traverse(i, j, mat, visited, root, root.child[idx], foundWords, word);

                    // insert all words found in current dfs to overall result
                    for (String w: foundWords) {
                        answer.add(w);
                    }
                }
            }
        }
        return answer;
    }
    //-------------------------------------END----------------------------------------
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
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            int dictionaryCount = Integer.parseInt(bufferedReader.readLine().trim());

            List<String> dictionary = new ArrayList<String>();

            for (int i = 0; i < dictionaryCount; i++) {
                String word = bufferedReader.readLine().trim();
                dictionary.add(word);
            }

            int n = Integer.parseInt(bufferedReader.readLine().trim());
            int m = Integer.parseInt(bufferedReader.readLine().trim());
            List<String> mat = new ArrayList<String>();

            for (int i = 0; i < n; i++) {
                String word = bufferedReader.readLine().trim();
                mat.add(word);
            }
            bufferedReader.close();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

            List<String> result = Result.boggle_solver(dictionary, mat);

            bufferedWriter.write(result.stream().collect(Collectors.joining("\n")) + "\n");
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
