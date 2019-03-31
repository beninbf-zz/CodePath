package main.java.com.codepath.recursion;

import main.java.com.codepath.objects.Cell;
import main.java.com.codepath.recursion.Palindrome.Palindrome;
import main.java.com.codepath.recursion.nqueens.Nqueens;
import main.java.com.codepath.util.Util;

import java.util.HashSet;
import java.util.Set;

public class RecursionMain {
    public static void main(String[] args) {
        RecursionProblems recursionProblems = new RecursionProblems();
        SubSets subSets = new SubSets();
        Palindrome palindrome = new Palindrome();
//        System.out.println(palindrome.isPalindrome("addda"));
//        System.out.println(palindrome.isPalindromeWithPointers("a"));

        //palindrome.decomposePalindrome("a");

        long[] set = new long[]{10l, -10l};
        System.out.println(subSets.isSumInSubset(set, 0));


        Permutations permutations = new Permutations();

        //System.out.println(recursionProblems.howManyBsts(5));

//        String[] brackets = recursionProblems.findAllWellFormedBracketsInEff(2);
//        Util.print(brackets);

//        for (String s: brackets) {
//            System.out.print(s + "is balanced: ");
//            System.out.println(recursionProblems.areBracketsWellBalanced(s));
//        }

       // recursionProblems.areBracketsWellBalanced("(())");
        //recursionProblems.printSubSets("tdc");
        //System.out.println(recursionProblems.generatePowerSet("tdc"));
        //String[] answer = subSets.generate_all_subsets("tdc");
        //Util.print(answer);
        //System.out.println(subSets.recursiveGeneratePS("tdc"));

        //String[] result = permutations.generate_all_expressions("222", 24);
        //Util.print(result);

        Integer[] array = new Integer[] {1, 2, 3, 4};
        int[] arr = new int[] {1, 2, 3};
        int[] arr1 = new int[] {1, 3, 3, 3};
        char[] chars = new char[] {'+','A','A'};

        String test = "1*382+3+2*121+123";
        //String[] values = test.split("[*+]");
        //Util.print(values);

//        String test1 = "5";
//        String[] res = test1.split("/+");
//
//        String[] results = recursionProblems.printOperationCombinations("22", 4);
//        Util.print(results);
        //recursionProblems.evaluateAdditionMultiplicationExpression("3*5+4+2*1+6+2*8*9");

        //permutations.uniquePermutations(arr1);
        //permutations.uniquePermutationsChar(chars);
        //permutations.permutations(arr1);






        //subSets.printSubSetSumAddSoFar(array, 7);
        //System.out.println(subSets.mySubSetSumAddition(arr, 0));
        //System.out.println(subSets.mySubSetSumSubtraction(arr, 0));



        //int[] array = new int[]{4, 2, 3, 1};

        //String test = "td";


        //char[] nothing = new char[5];
        //Util.print(nothing);
        //StringBuilder sb = new StringBuilder(test);
        //System.out.println(sb.deleteCharAt(2).toString());
        //permutations.permutationAltEvenOddNumber(array);

        //permutations.perm(array);

//        String test = "test";
//
//        System.out.println(test.substring(0, 0));
//        System.out.println(test.substring(0, test.length()));
//
        //System.out.println(recursionProblems.getSubStrings("12345"));
//
        //System.out.println(recursionProblems.getSubStringRecursive("12345"));

        //System.out.println(recursionProblems.nChooseK(23, 3));
        //System.out.println(recursionProblems.nChooseKMemoization(23, 3));

//        int n = 123;
//        int k = 4;

//        CountingObject countingObject = new CountingObject();
//        CountingObject countingObject2 = new CountingObject();
//
//        System.out.println(String.format("Value of n choose k for n=%s and k=%s is [%s], recursive count is [%s]", n, k,
//            recursionProblems.nChooseKWithCount(n, k, countingObject), countingObject.getCount()));
//
//        System.out.println(String.format("Value of n choose k for n=%s and k=%s is [%s], memoziation recursive count is [%s]", n, k,
//            recursionProblems.nChooseKMemoizationWithCount(n, k, countingObject2), countingObject2.getCount()));
//
//
//        System.out.println(recursionProblems.returnBinaryNumbers(3));
//        recursionProblems.printBinaryNumbers(3);
//
//        System.out.println(recursionProblems.fibonnaci(10));
//        System.out.println(recursionProblems.fibonnaciIter(10));

        //recursionProblems.printDecimalNumbers(2);

        //recursionProblems.diceRoll(2);
        //recursionProblems.diceSum(3, 7);

        //recursionProblems.printBinary(3);
        //recursionProblems.rollingDiceChooseSum(3, 7);

        //String word = "abc";
        //int length = word.length();
        //word.substring(0, word.length() -1);
//        System.out.println(word.length());
//        System.out.println(word.substring(1, length));
//        System.out.println(word.substring(0, 1));

        //recursionProblems.permutationsMyWay("abc");
        //permutations.permutations(word);

        //int[] array = {2, 10, 20};

        //System.out.println(recursionProblems.findAllWellFormedBrackets(2));
        //System.out.println(recursionProblems.subSetSum(array, 0));

//        int[][] grid = {{10, 20},
//                        {25, 4}};
//
//        int[][] grid1 = {{10, 20, 5, 10, 450},
//                         {25, 4, 12, 17, 123},
//                        {25, 4, 14, 15, 345}};
//
//        int[][] grid2 = {{1, 1, 1, 1},
//            {1, 1, 1, 1},
//            {1, 1, 1, 1}};
//
//        System.out.println(recursionProblems.maxPath(grid));
//        System.out.println(recursionProblems.maxPathAlternate(grid));


//        Nqueens nqueens = new Nqueens();
//        Boolean[][] chessBoard = new Boolean[4][4];
//
//        //Boolean[][] answer = nqueens.solve(chessBoard);
//        String[][] answer = nqueens.find_all_arrangements(4);
//
//        Util.print2DArray(answer);
    }
}
