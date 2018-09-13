package main.java.com.codepath.recursion;

import java.util.ArrayList;
import java.util.Arrays;

public class RecursionProblems {

    public ArrayList<String> getSubStrings(String s) {
        ArrayList<String> results = new ArrayList<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j <= length; j++) {
                results.add(s.substring(i, j));
            }
        }
        return results;
    }

    public ArrayList<String> getSubStringRecursive(String s) {
        ArrayList<String> results = new ArrayList<>();
        return getSubStringRecursive(s, results);
    }

    private ArrayList<String> getSubStringRecursive(String s, ArrayList<String> results) {
       if (s.isEmpty()) {
           return results;
       }
       int length = s.length();
        for (int i = 1; i <= length; i++) {
            results.add(s.substring(0, i));
        }
        String remaining = s.substring(1, length);
        return getSubStringRecursive(remaining, results);
    }

    public int nChooseK(int n, int k) {
        if (k == 1) {
            return n;
        } else if (n == k) {
           return 1;
        } else {
            System.out.println("Non memoziation recursive call");
            return nChooseK(n - 1, k)+ nChooseK(n - 1, k - 1);
        }
    }

    public int nChooseKMemoization(int n, int k) {
        int[][] array = new int[n+1][k+1];
        return nChooseKMemoization(n, k, array);

    }

    public int nChooseKMemoization(int n, int k, int[][] array) {
        if (k == 1) {
            if (array[n][k] != 0) {
                System.out.println("Memoization array return");
                return array[n][k];
            }
            array[n][k] = n;
            return array[n][k];
        } else if (n == k) {
            if (array[n][k] != 0) {
                System.out.println("Memoization array return");

                return array[n][k];
            }
            array[n][k] = 1;
            return array[n][k];
        } else {
            if (array[n][k] != 0) {
                //System.out.println("memo time!");
                System.out.println("Memoization array return");

                return array[n][k];
            }
            System.out.println("Memoization recursive call");
            array[n][k] = nChooseKMemoization(n - 1, k, array)+ nChooseKMemoization(n - 1, k - 1, array);
            return array[n][k];
        }
    }

    public int nChooseKWithCount(int n, int k, CountingObject counter) {
        if (k == 1) {
            return n;
        } else if (n == k) {
            return 1;
        } else {
            counter.incrementCount();
            return nChooseKWithCount(n - 1, k, counter) + nChooseKWithCount(n - 1, k - 1, counter);
        }
    }

    public int nChooseKMemoizationWithCount(int n, int k, CountingObject counter) {
        int[][] array = new int[n+1][k+1];
        return nChooseKMemoizationWithCount(n, k, array, counter);
    }

    public int nChooseKMemoizationWithCount(int n, int k, int[][] array, CountingObject counter) {
        if (k == 1) {
            if (array[n][k] != 0) {
                return array[n][k];
            }
            array[n][k] = n;
            return array[n][k];
        } else if (n == k) {
            if (array[n][k] != 0) {
                return array[n][k];
            }
            array[n][k] = 1;
            return array[n][k];
        } else {
            if (array[n][k] != 0) {
                return array[n][k];
            }
            counter.incrementCount();
            array[n][k] = nChooseKMemoizationWithCount(n - 1, k, array, counter)+ nChooseKMemoizationWithCount(n - 1, k - 1, array, counter);
            return array[n][k];
        }
    }

    /**
     * PrintBinary - Write a function, that given an integer n, print all possible binary numbers that
     * contain n digits. i.e. n = 1, {0, 1}, n = 2, {00, 01, 10, 11}
     *
     */
    public ArrayList<String> returnBinaryNumbers(int n) {
        String[] digits = {"0", "1"};
        ArrayList<String> results = new ArrayList<String>();
        results.add("0");
        results.add("1");
        return returnBinaryUtil(n, digits, results);
    }

    private ArrayList<String> returnBinaryUtil(int n, String[] digits, ArrayList<String> results) {
        if (n == 1) {
            return results;
        }

        ArrayList<String> newCombos = new ArrayList<>();
        for (String digit: digits) {
            for (String s : results) {
                StringBuilder sb = new StringBuilder(s);
                sb.insert(0, digit);
                s = sb.toString();
                newCombos.add(s);
            }
        }

        return returnBinaryUtil(n - 1, digits, newCombos);
    }

    /**
     *
     * For the case of n = 1 we have
     * 0
     * 1
     *
     * n = 2
     * 00
     * 01
     * 10
     * 11
     *
     * n = 3
     * 000
     * 001
     * 010
     * 011
     * 100
     * 101
     * 110
     * 111
     *
     * If we look at these cases closely, we see that the case for n = 2, is simply the case for n = 1, with a 0
     * and a 1 put in front of each binary number. The same thing for n = 3, its simply the case of n=2, with 0 put
     * in front of every 3 digit number, and then a 1 put in front of every three digit number.
     *
     * The way we solve this problem is to remember the work we have already done (the choices we have made) with a prefix
     * and then build the other strings by back tracking. Each call is a decision made by the algorithm.
     *
     * Print Binary - When considering the number of digits in conjunction with a prefix, we must
     * ask ourselves what these two variables actually mean. When digits == 3, then digits represents the
     * number of digits (or choices) left to account for. As we have made no decisions, then our prefix will
     * be "".
     *
     * When digits == 2, then our prefix is "0", which means we have made a "choice" that the first digit is 0.
     *
     * If digits == 0, then we have no digits left to account for, then our prefix should be a 3 digit binary number.
     *
     * The prefix in this case, is being used to remember the work that we have done from prior calls.
     * So when digits == 0, there is no more work to do.
     *
     * Our base case, is there fore when digits is 0 because there are no more choices to make, and we
     * must then print our prefix. If I'm at my base case of digits == 0, and prefix = "000", then I have
     * no digits left to account for, and the prefix contains all of the necessary digits and there are
     * exactly 0 of them that still need to be picked.
     *
     * DIGITS == 0 DOES NOT MEAN WE ARE PRINTING 0 DIGIT NUMBERS.
     * @param n
     */
    public void printBinaryNumbers(int n) {
        String prefix="";
        printBinaryUtil(n, prefix);
    }

    private void printBinaryUtil(int n, String prefix) {
        if (n == 0) {
            System.out.println(prefix);
        } else {
            printBinaryUtil(n - 1, prefix + "0");
            printBinaryUtil(n - 1, prefix + "1");
        }
    }

    public void printDecimalNumbers(int n) {
        String prefix="";
        printDecimalNumbers(n, prefix);
    }

    private void printDecimalNumbers(int n, String prefix) {
        if (n == 0) {
            System.out.println(prefix);
        } else {
//            printDecimalNumbers(n - 1, prefix + "0");
//            printDecimalNumbers(n - 1, prefix + "1");
//            printDecimalNumbers(n - 1, prefix + "2");
//            printDecimalNumbers(n - 1, prefix + "3");
//            printDecimalNumbers(n - 1, prefix + "4");
//            printDecimalNumbers(n - 1, prefix + "5");
//            printDecimalNumbers(n - 1, prefix + "6");
//            printDecimalNumbers(n - 1, prefix + "7");
//            printDecimalNumbers(n - 1, prefix + "8");
//            printDecimalNumbers(n - 1, prefix + "9");

            for (int i = 0; i < 9; i++) {
                printDecimalNumbers(n - 1, prefix + String.valueOf(i));
            }
        }
    }

    public int fibonnaci(int n) {
        if (n == 0) {
            return n;
        }

        if (n == 1) {
            return n;
        }

        return fibonnaci(n - 1) + fibonnaci(n - 2);
    }

    public int fibonnaciIter(int n) {

        int f1 = 1;
        int f2 = 1;
        int result = 0;

        for (int i = 2; i < n; i++) {
            result = f1 + f2;
            f1 = f2;
            f2 = result;
        }
        return result;
    }
}