package LongestRepeatedSubstring.solutions; /**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given a string str of length n, find the longest repeated substring in it.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class optimal_solution {

    // -------------------- START ----------------------

    static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz\1";

    public static class Node {
        int begin;
        int end;
        int depth; // distance in characters from root to this node
        Node parent;
        Map<Byte, Node> children;
        Node suffixLink;

        Node(int begin, int end, int depth, Node parent) {
            this.begin = begin;
            this.end = end;
            this.parent = parent;
            this.depth = depth;
            children = new HashMap();
        }
    }

    public static Node buildSuffixTree(CharSequence s) {
        int n = s.length();
        byte[] a = new byte[n];
        Map<Character, Byte> map = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            map.put(ALPHABET.charAt(i), (byte) i);
        }
        // Converting CharSequence s to byte array a, using index of that char in universal string ALPHABET
        for (int i = 0; i < n; i++) a[i] = map.get(s.charAt(i));
        Node root = new Node(0, 0, 0, null);
        Node node = root;
        for (int i = 0, tail = 0; i < n; i++, tail++) {
            Node last = null;
            while (tail >= 0) {
                Node ch = node.children.get(a[i - tail]);
                while (ch != null && tail >= ch.end - ch.begin) {
                    tail -= ch.end - ch.begin;
                    node = ch;
                    ch = ch.children.get(a[i - tail]);
                }
                if (ch == null) {
                    node.children.put(a[i], new Node(i, n, node.depth + node.end - node.begin, node));
                    if (last != null) last.suffixLink = node;
                    last = null;
                } else {
                    byte t = a[ch.begin + tail];
                    if (t == a[i]) {
                        if (last != null) last.suffixLink = node;
                        break;
                    } else {
                        Node splitNode = new Node(ch.begin, ch.begin + tail,
                                node.depth + node.end - node.begin, node);
                        splitNode.children.put(a[i], new Node(i, n, ch.depth + tail, splitNode));
                        splitNode.children.put(t, ch);
                        ch.begin += tail;
                        ch.depth += tail;
                        ch.parent = splitNode;
                        node.children.put(a[i - tail], splitNode);
                        if (last != null) last.suffixLink = splitNode;
                        last = splitNode;
                    }
                }
                if (node == root) {
                    --tail;
                } else {
                    node = node.suffixLink;
                }
            }
        }
        return root;
    }

    static int lrsLength;
    static int lrsSuffixIndex;
    static String s;

    static void findLRS(Node node) {
        boolean isLeaf = true;
        for (Byte b : node.children.keySet()) {
            isLeaf = false;
            findLRS(node.children.get(b));
        }
        // As name suggests, isLeaf will be true at this point, if it has no child

        // If node is a leaf, then suffix string formed by path from root to it's parent node is a
        // repeated substring
        // and a candidate for longest repeated substring
        if (isLeaf) {
            int currLength = node.depth;
            // currLength here denotes path length of node.parent. i.e. node.parent.pathLength == node.depth
            if (currLength > lrsLength) {
                lrsLength = currLength;
                lrsSuffixIndex = s.length() - (node.depth + node.end - node.begin);
                // For a leaf node, suffixIndex(i.e. index of suffix that ends here) is nothing but (
                // (length of given
                // string s) - path length of current node)
            }
        }
    }

    public static String getLongestRepeatedSubstring(String inputStr) {
        // Adding a special character('\1' here) in the end of string to make it explicit suffix tree.
        // In explicit suffix tree, all the suffixes ends at a leaf
        s = inputStr + "\1";
        Node tree = buildSuffixTree(s);
        lrsLength = 0;
        lrsSuffixIndex = -1;
        // findLRS will find the LRS and will populate the results in lrsLength and lrsSuffixIndex
        findLRS(tree);
        String lrs = "";
        if (lrsLength > 0) {
            lrs = s.substring(lrsSuffixIndex, lrsSuffixIndex + lrsLength);
        }
        return lrs;
    }

    // -------------------- END ----------------------

    private static BufferedReader br;

    public static void main(String args[]) {
        // Launching a thread with larger allowed stack memory to avoid StackOverflowException
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new optimal_solution();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (StackOverflowError e) {
                    System.out.println("RTE");
                }
            }
        }, "1", 1 << 26).start();
    }

    optimal_solution() throws IOException {
        solve();
    }

    static void solve() throws IOException {
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String inputStr = br.readLine().trim();

        String result = getLongestRepeatedSubstring(inputStr);
        bufferedWriter.write("" + result.length());
        bufferedWriter.newLine();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println((afterUsedMem - beforeUsedMem) / 1000000);
        bufferedWriter.close();

        br.close();

    }

}

/**
 * Time complexity: O(n)
 * Space complexity: O(n)
 * Reference : https://sites.google.com/site/indy256/algo/suffix_tree
 */
