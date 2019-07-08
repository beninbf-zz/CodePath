package KMP.solutions; /**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given a text string t of length n and a pattern string p of length m, return start indices of all
 * occurrences of p
 * in t.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class brute_force {

    // -------------------- START ----------------------

    static int[] KMP(String t, String p) {
        // l will contain indices of text t where we find the occurrences of pattern p
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < t.length(); i++) {
            // In this loop, we are trying to find how many characters are matching between substring of
            // text t starting at i and pattern p. Let say no of character matched here is j. Then if j is
            // equal to length of p, then it can be said that there is an occurrence of p starting at
            // position
            // i of t.

            int j = 0;
            for (; j < p.length() && i + j < t.length(); j++) {
                if (t.charAt(i + j) != p.charAt(j)) {
                    break;
                }
            }

            // Checking if j, no of character matched, is equal to length of p or not.
            if (j == p.length()) {
                // p is occurring in t at index i
                l.add(i);
            }
        }

        // If no occurrence of p is found in t, then as asked in problem, return array having an element -1
        if (l.size() == 0) {
            l.add(-1);
        }
        int[] res = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            res[i] = l.get(i);
        }
        return res;
    }

    // -------------------- END ----------------------

    private static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String t = br.readLine().trim();

        int q = Integer.parseInt(br.readLine().trim());
        while (q-- > 0) {
            String p = br.readLine().trim();
            int[] res = KMP(t, p);

            for (int i = 0; i < res.length; i++) {
                bufferedWriter.write(String.valueOf(res[i]));

                if (i != res.length - 1) {
                    bufferedWriter.write("\n");
                }
            }
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        br.close();
    }

}

/**
 * Time Complexity: O(n*m) for each query
 * Over all time complexity: O(q*n*m)
 * Auxiliary Space Used (excluding output space): O(1)
 * Space Complexity: O(n+m)
 */
