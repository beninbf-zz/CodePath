package main.java.com.codepath.dynamicprogramming;

import main.java.com.codepath.util.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        int[][] grid = {{1, 3, 1}, {2, 4, 5}, {1, 1, 1}};
        int[][] A = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};


        //DynamicProgramming dynamicProgramming = new DynamicProgramming();
//
      // dynamicProgramming.print2DArray(grid);
//        System.out.println(dynamicProgramming.findMinPath(grid));
//        System.out.println(dynamicProgramming.findMinPathDP(grid));
//
//        System.out.println("rows="+grid.length);
//        System.out.println("cols="+grid[0].length);
//
//        System.out.println(dynamicProgramming.findMaxScoreRec(A));
//        System.out.println(dynamicProgramming.findMaxScoreDP(A));

        String test ="myinterviewtrainer";
        Set<String> dictionary = new LinkedHashSet<>(Arrays.asList("my", "interview", "trainer"));

        //System.out.println(dynamicProgramming.wordBreak1(test, dictionary));

        int[][] array = {{1, 1}, {2, 2}};

        int[][] arrayClone = array.clone();

        array[1][1] = 98;

        Util.print2DArray(array);
        System.out.println("Different array");
        Util.print2DArray(arrayClone);



    }
}
