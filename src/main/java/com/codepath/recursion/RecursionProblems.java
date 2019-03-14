package main.java.com.codepath.recursion;

import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A class of recursion problems
 */
public class RecursionProblems {

    public void printBinary(int n) {
        printBinary(n, "");
    }

    private void printBinary(int n, String binaryNumber) {
        if (n == 0) {
            System.out.println(binaryNumber);
            return;
        }

        printBinary(n - 1, binaryNumber + "0");
        printBinary(n - 1, binaryNumber + "1");
    }

    public void printDecimal(int n) {
        printDecimal(n, "");
    }

    private void printDecimal(int n, String decimalNumber) {
        if (n == 0) {
            System.out.println(decimalNumber);
            return;
        }

        printDecimal(n - 1, decimalNumber + "0");
        printDecimal(n - 1, decimalNumber + "1");
        printDecimal(n - 1, decimalNumber + "2");
        printDecimal(n - 1, decimalNumber + "3");
        printDecimal(n - 1, decimalNumber + "4");
        printDecimal(n - 1, decimalNumber + "5");
        printDecimal(n - 1, decimalNumber + "6");
        printDecimal(n - 1, decimalNumber + "7");
        printDecimal(n - 1, decimalNumber + "8");
        printDecimal(n - 1, decimalNumber + "9");
    }

    public void rollingDice(int n) {
        rollingDiceChoose(n, "");
    }

    private void rollingDice(int n , String prefix) {
        if (n == 0) {
            System.out.println(prefix);
            return;
        }

        rollingDice(n - 1, prefix + "1");
        rollingDice(n - 1, prefix + "2");
        rollingDice(n - 1, prefix + "3");
        rollingDice(n - 1, prefix + "4");
        rollingDice(n - 1, prefix + "5");
        rollingDice(n - 1, prefix + "6");

    }

    private void rollingDiceChoose(int n , String prefix) {
        if (n == 0) {
            System.out.println(prefix);
            return;
        }

        for (int i = 1; i <= 6; i++) {
            //Choose
            prefix  = prefix + i;

            // explore
            rollingDiceChoose(n - 1, prefix);

            // un choose...we already explored the previous value, so remove it and keep exploring
            prefix = prefix.substring(0, prefix.length() - 1);
        }

    }

    public void rollingDiceChooseSum(int n, int sum) {
        ArrayList<Integer> prefix = new ArrayList<>();
        int sumSoFar = 0;
        rollingDiceChooseSum(n, prefix, sum, sumSoFar);
    }

    private void rollingDiceChooseSum(int dice , ArrayList<Integer> prefix, int sum, int sumSoFar) {
        if (dice == 0) {
            if (sumSoFar == sum) {
                System.out.println(prefix);
                return;
            }
            return;
        }

        for (int i = 1; i <= 6; i++) {
            //Choose
            prefix.add(i);

            // explore
            rollingDiceChooseSum(dice - 1, prefix, sum, sumSoFar + i);

            // un choose...we already explored the previous value, so remove it and keep exploring
            prefix.remove(prefix.size() - 1);

        }
    }

    private void rollingDiceChooseSumOptimized(int dice , ArrayList<Integer> prefix, int sum, int sumSoFar) {
        // since we are only rolling if the target is within the range of a given max and min
        // we no longer need to check the sumSoFar.
        if (dice == 0) {
            System.out.println(prefix);
            return;
        }

        for (int i = 1; i <= 6; i++) {

            // the minimum you could possibly roll, is your current sum fo far, plus what you just rolled, i.e. i
            // plus the minimum you could roll with the remaining dies. i.e. 1 * remaining dice
            // remaining dice is dice - 1, because i is your current roll (i.e. your current dice)
            int minYouCouldRoll = sumSoFar + i + 1 * (dice  - 1);

            // the maximum you could possibly roll, is your current sum so far, plus what you just rolled, i.e. i
            // plus the max you could roll with the remaining dice. i.e. 6 * remaining dice
            // remaining dice is dice - 1, because i is your current roll (i.e. your current dice)
            int maxYouCouldRoll = sumSoFar + i + 6 * (dice - 1);

            if (minYouCouldRoll <= sum && maxYouCouldRoll >= sum) {
                //Choose
                prefix.add(i);

                // explore
                rollingDiceChooseSum(dice - 1, prefix, sum, sumSoFar + i);

                // un choose...we already explored the previous value, so remove it and keep exploring
                prefix.remove(prefix.size() - 1);
            }
        }

    }

    public void print(char[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]+"]");
        System.out.println();
    }

    /**
     * Gets sub strings.
     *
     * @param s the s
     * @return the sub strings
     */
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
            /*
            printDecimalNumbers(n - 1, prefix + "0");
            printDecimalNumbers(n - 1, prefix + "1");
            printDecimalNumbers(n - 1, prefix + "2");
            printDecimalNumbers(n - 1, prefix + "3");
            printDecimalNumbers(n - 1, prefix + "4");
            printDecimalNumbers(n - 1, prefix + "5");
            printDecimalNumbers(n - 1, prefix + "6");
            printDecimalNumbers(n - 1, prefix + "7");
            printDecimalNumbers(n - 1, prefix + "8");
            printDecimalNumbers(n - 1, prefix + "9");
            */

            for (int i = 0; i < 10; i++) {
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

    public void diceRoll(int n) {
        String prefix = "";
        diceRoll(n, prefix);
    }

    private void diceRoll(int n, String prefix) {
        if (n == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 1; i <= 6; i++) {
                diceRoll(n - 1, prefix + String.valueOf(i));
            }
        }
    }

    public void diceSum(int n, int sum) {
        diceSum(n, "", 0, sum);
    }

    private void diceSum(int n, String prefix, int sumSoFar, int sum) {
        if (n == 0) {
            if (sum == sumSoFar) {
                System.out.println(prefix + "=" + sumSoFar);
                return;
            }
            return;
        } else {
            for (int i = 1; i <= 6; i++) {
                diceSum(n - 1, prefix + String.valueOf(i), i + sumSoFar, sum);
            }
        }
    }

    public void printCapitalVariations(String word) {
        String prefix = "";
        printCapitalVariations(word, prefix);
    }

    private void printCapitalVariations(String word, String prefix) {
        if (word.isEmpty()) {
            System.out.println(prefix);
        } else {

        }
    }


    public String[] findAllWellFormedBrackets(int n) {
        String s = "";
        ArrayList<String> results = new ArrayList<>();
        int leftBracket = n;
        int rightBracket = n;
        findAllWellFormedBrackets(leftBracket, rightBracket, s, results);
        String[] stringArray = Arrays.copyOf(results.toArray(), results.size(), String[].class);
        return stringArray;
    }

    public void findAllWellFormedBrackets(int leftBracket, int rightBracket, String s, ArrayList results) {

        if (leftBracket == 0 && rightBracket == 0) {
            results.add(s);
            return;
        }

        if (leftBracket > rightBracket) {
            return;
        }

        if (leftBracket > 0) {
            findAllWellFormedBrackets(leftBracket - 1, rightBracket, s + "(", results);
        }

        if (rightBracket > 0) {
            findAllWellFormedBrackets(leftBracket, rightBracket - 1, s + ")", results);
        }
    }

    /**
     * maxPath. Given a 2D grid with postive integers, find the maximum path through the grid starting at
     * the upper most left cell and ending at the bottom most right cell.
     *
     * The following implementation is correct, however unconventional. What this approach does, is declare
     * a 1-D array to store the value of the math path. This value is passed to the helper function
     * which traverses every possible path of the array.
     *
     * Every recursive call makes two decisions either go right, or down. What we end up with is an exhaustive
     * search, moving through every path of the grid.
     *
     * We also pass in another variable, maxSoFar. Everytime we reach our destination, we check to see if the
     * Current max is greater or less than the maxSoFar. If maxSoFar is greater, then we reset the max value.
     *
     * @param grid
     * @return
     */
    public int maxPath(int[][] grid) {
        int[] max = new int[1];
        max[0] = 0;
        int maxSoFar = 0;
        maxPathHelper(grid, 0, 0, maxSoFar, max);
        return max[0];
    }

    private void maxPathHelper(int[][] grid, int row, int col, int maxSoFar, int[] max) {
        if (row >= grid.length || col >= grid[0].length) {
            return;
        }

        if ((row == grid.length - 1) && (col == grid[0].length - 1)) {
            maxSoFar += grid[row][col];
            if (maxSoFar > max[0]) {
                max[0] = maxSoFar;
            }
        }

        maxPathHelper(grid, row + 1, col, maxSoFar + grid[row][col], max);
        maxPathHelper(grid, row, col + 1, maxSoFar + grid[row][col], max);
    }

    /**
     * This solution is the more optimal way of doing this problem. It uses less memory and the helper
     * function returns the max value as opposed to just updating an object with the max value.
     *
     * If we think about this carefully, the solution for this can be completely deduced by the base cases.
     * If we are outside the bound of the grid, well we obviously need to return and stop searching.
     *
     * But what value should we return? Well, if we don't want to consider a path that has taken us outside
     * of the grid, then just return zero.
     *
     * The other base case, just says, hey...when we have reached our destination, just return the value of that cell.
     *
     * Given that? how might we deduce the maximum value of the path? Well, there are only two ways to get to the
     * destination cell. Either from the upper cell, or the leftward cell. To find the max path, how about we just
     * take the max of those two cells, and add to the value of the cell we are at?
     *
     * Then, because we want the max, just keep returning those values. The max of the left, or from above, plus
     * the value of the cell we are currently at.
     *
     * This is the most optimal solution, and makes this problem a prime candidate for a Dynamic programming solution.
     *
     * The data set, the GRID, isn't changing, and we have many overlapping sub problems.
     *
     * Because we are making 2 decisions per cell, the running time is 2 ^ (n) were n is the number of cell.
     *
     * Technicall that means 2^(rows + cols). thats the running time.
     *
     * Space complexity is the size of the grip O(r + c).
     *
     * @param grid
     * @return int the value of the max path
     */
    public int maxPathAlternate(int[][] grid) {
        return maxPathAlternateHelper(grid, 0, 0);
    }

    private int maxPathAlternateHelper(int[][] grid, int row, int col) {

        if (row >= grid.length || col >= grid[0].length) {
            return 0;
        }

        if ((row == grid.length - 1) && (col == grid[0].length - 1)) {
            return grid[row][col];
        }

        /* The "leaf" most node calls, will hit the base case, and return 0 from the cells
         * just outside the bounds of the array.
         * Thats why its important to have the first base case, the first if statement, BEFORE
         * the base case where we return the value of the destination cell.
         * We need to return those zero's so that our little math.max equation properly returns
         * the max path at cell [grid.length - 1][grid[0].length - 1] as the actual value of that cell
         * essentially doing [grid.length - 1][grid[0].length - 1] + Math.Max(0, 0);
         */
        int maxDown = maxPathAlternateHelper(grid, row + 1, col);
        int maxRight = maxPathAlternateHelper(grid, row, col + 1);
        return grid[row][col] + Math.max(maxDown, maxRight);
    }

    /**
     * Given a number in the form of a string, print all combination of the operators in between each character.
     * "", "+", "*"
     *
     * For the string 22, the resulting strings should be [22, 2+2, 2*2]
     *
     * For the string 123, the resulting strings should be
     *
     * 123
     * 12+3
     * 12x3
     * 1+23
     * 1+2+3
     * 1+2x3
     * 1x23
     * 1x2+3
     * 1x2x3
     *
     * Because we must insert these operators in between each characters, that means there are n - 1
     * spots to insert characters at. And there are 3 options. So the max number of combinations are
     * 3 ^ (n - 1).
     *
     * @param number
     */
    public String[] printOperationCombinations(String number, long target) {
        if (number == null || number.isEmpty()) {
            return new String[]{};
        }
        Double exponent = new Double(number.length() - 1);
        Double numberOfExpressions = Math.pow(3d, exponent);
        List<String> expressions = new ArrayList<>(numberOfExpressions.intValue());
        printOperationCombinationsHelper(number, number.length(), target, 0,  0, expressions);
        return expressions.toArray(new String[0]);
    }

    private void printOperationCombinationsHelper(String expression, int length, long target, int i, int offset, List<String> numberOfExpressions) {
        if (i + offset == length - 1) {
            long value = evaluateAdditionMultiplicationExpression(expression);
            if (value == target) {
                numberOfExpressions.add(expression);
            }
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(expression);

        sb.insert(i + 1, "");
        printOperationCombinationsHelper(sb.toString(), length, target, i + 1, offset, numberOfExpressions);

        sb.insert(i + offset + 1, "+");
        String additionString = sb.toString();
        printOperationCombinationsHelper(additionString, additionString.length(), target, i + 1, offset + 1, numberOfExpressions);
        sb.deleteCharAt(i + offset + 1);

        sb.insert(i + offset + 1, "*");
        String multiString = sb.toString();
        printOperationCombinationsHelper(multiString, multiString.length(), target, i + 1, offset + 1, numberOfExpressions);
        sb.deleteCharAt(i + offset + 1);
    }

    public long evaluateAdditionMultiplicationExpression(String value) {
        long result = 0;
        String[] values = value.split("\\+");

        for (int i = 0; i < values.length; i++) {
            String[] multiplication = values[i].split("\\*");
            Long productVal = null;
            if (multiplication.length >= 1) {
                productVal = Long.valueOf(multiplication[0]);
            }
            for (int j = 1; j < multiplication.length; j++) {
                productVal = Math.multiplyExact(productVal, Long.valueOf(multiplication[j]));
            }
            result += productVal;
        }

        return result;
    }
}