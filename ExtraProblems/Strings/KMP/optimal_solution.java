package KMP.solutions; /**
 * *********************** PROBLEM DESCRIPTION ***************************
 * Given a text string t of length n and a pattern string p of length m, return start indices of all
 * occurrences of p
 * in t.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class optimal_solution {

    // -------------------- START ----------------------

    public static int[] KMP(String t, String p) {
        if (t.length() < p.length()) {
            return new int[]{-1};
        }
        List<Integer> l = new ArrayList<>();

        // For each substring p[0..i] where i = 0 to m-1, lps[i] stores length of the maximum matching
        // proper prefix which is
        // also a suffix of the substring pat[0..i]. A proper prefix is prefix with whole string not allowed.

        int[] lps = getLPSArray(p);

        // We use a value from lps[] to decide the next characters to be matched. The idea is to not match
        // a character
        // that we know will anyway match.
        int ti = 0, pi = 0;
        while (ti < t.length() && pi < p.length()) {
            if (t.charAt(ti) == p.charAt(pi)) {
                ti++;
                pi++;

                // If pi is equal to length of p, then it can be said that there is an occurrence of p
                // starting at
                // position ti-pi, as we know that pi starts with 0 and get incremented only when there is
                // a match
                if (pi == p.length()) {
                    l.add(ti - pi);
                    pi = lps[pi - 1];
                }
            } else {
                // When we see a mismatch, we know that characters p[0,1,..,pi-1] match with t[ti-pi,..,ti-1]
                // We also know that lps[j-1] is count of characters of p[0,1,..,j-1] that are both proper
                // prefix and
                // suffix. From above two points, we can conclude that we do not need to match these
                // lps[pi-1]
                // characters with txt[ti-pi,..,ti-1] because we know that these characters will anyway
                // match.
                if (pi > 0) {
                    pi = lps[pi - 1];
                } else {
                    // If mismatch on first character of p
                    ti++;
                }
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

    public static int[] getLPSArray(String p) {
        // To build lps array, for lps[i], we keep track of the length of the longest proper prefix suffix
        // value for the previous
        // index.
        int[] lps = new int[p.length()];
        // We initialize lps[0] and len as 0
        lps[0] = 0;
        int len = 0;
        for (int i = 1; i < lps.length; ) {
            if (p.charAt(len) == p.charAt(i)) {
                // If p[len] and p[i] matches, we increment len by 1 and assign the incremented value to
                // lps[i]
                len++;
                lps[i] = len;
                i++;
            } else {
                // If p[i] and p[len] do not match and len is not 0, we update len to lps[len-1] if len>0
                if (len > 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
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
//            bufferedWriter.write("\n");
        }

        bufferedWriter.close();

        br.close();
    }

}

/**
 * Time Complexity: O(n) for each query
 * Over all time complexity: O(q*n)
 * Auxiliary Space Used (excluding output space): O(m)
 * Space Complexity: O(n+m)
 */
