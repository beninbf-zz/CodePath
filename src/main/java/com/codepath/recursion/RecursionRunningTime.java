package main.java.com.codepath.recursion;

import java.util.Arrays;


public class RecursionRunningTime {

    public int[][] memo = new int[101][101];

    public int findMinPath(int[][] V, int r, int c) {
        System.out.println(String.format("r=%s, c=%s", r, c));
        int R = V.length;
        int C = V[0].length;
        if (r >= R || c >= C) return 100000000; // Infinity
        if (r == R - 1 && c == C - 1) return 0;
        if (memo[r][c] != -1) return memo[r][c];
        memo[r][c] =  V[r][c] + Math.min(findMinPath(V, r + 1, c), findMinPath(V, r, c + 1));
        return memo[r][c];
    }

    public void callFindMinPath() {
        for (int i = 0; i < memo.length; i++) {
            Arrays.fill(memo[i], -1);
        }
        int[][] V = new int[3][3];
        findMinPath(V, 0, 0);
    }
}
