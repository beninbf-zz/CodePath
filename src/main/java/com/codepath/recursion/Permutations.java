package main.java.com.codepath.recursion;

import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <code>Permutations</code> is a class containing different algorithms for generating permutations.
 *
 * Assume the String we are permuting has all unique characters.
 *
 * What is a permutation?
 *
 * A permutation is nothing more than the re-arranging the members of a set into a sequence or order.
 *
 * For the problem of finding all permutations of a String. It means finding all of the ways
 * to re-order the characters in that string.
 *
 * For the example of "abc". The permutations are
 *
 * abc
 * bac
 * cba
 * acb
 * cab
 * bca
 *
 * Notice the number of permutations is 6. Why is it 6?
 *
 * If a permutation is a reordering of members of a set, then to find out how many different possible
 * combinations there are, is as straight forward as asking yourself, how many different possible
 * choices are for characters to be in the 1st position? How many choices for the 2nd position, the 3rd?
 *
 * For the string abc, we have 3 positions
 *
 *   number of choices      _3_  _2_  _1_
 *   indices                 0    1    2
 *
 * For the first position at the 0 index, there are 3 choices, as we have all three characters, 'a', 'b', and 'c' to
 * choose from.
 *
 * At position 1, since we have already chosen 1 character, we only have 2 left. Once we choose that one,
 * that leaves one more choice.
 *
 * We wind up with 3 * 2 * 1, or n! factorial, where n is the number of characters in the string.
 *
 *
 *
 * @author Benin Bryant
 * @since Feb 28, 2019
 */
public class Permutations {

    /**
     * This represents the most efficient way to perform permutations. Turn the input, if it is
     * a string of characters into a char array.
     *
     * We essentially choose which character we want to put in the 1st position, and then find
     * all the permutations that begin with that character in the recursive calls.
     *
     * At every level of the recursion tree, we are choosing a character to put in the 'ith' index
     * and then finding all of the subsequent permutations.
     *
     * One caveat using the array, is that when using recursion, when a recursive call returns, we
     * need to maintain state. In other words, when we return from a recursive call, we need to return
     * to the same state that the call was made with. In order to do that, we need to "un-choose" the choice
     * that we previously made, so that we can make other choices for characters in the ith spot.
     *
     * In this problem, that simply means calling swap again, to return the characters to the state they were
     * previously in before the recursive call.
     *
     * We stop permuting when we have every character has been chosen to be in every position. Which is to say
     * when i is equal to the length of the array.
     *
     * We know that the running time is at least O(n!), however we have the added cost of printing the array.
     * So we need to count the cost of iterating through the array to print it.
     *
     * So the total cost is O(n! * n).
     * @param array
     */
    public void permutations(int[] array) {
        if (array.length == 0) {
            System.out.println("Array must have length greater than 0");
            return;
        } else if (array.length == 1)  {
            Util.print(array);
            return;
        }
        permutationsHelper(array, 0);
    }

    private void permutationsHelper(int[] array, int i) {
        if (i == array.length - 1) {
            Util.print(array);
        }

        for (int j = i; j < array.length; j++) {
            Util.swap(array, i, j);
            permutationsHelper(array, i + 1);
            Util.swap(array, i, j);
        }
    }

    public void uniquePermutations(int[] array) {
        uniquePermutationsHelper(array, 0);
    }

    private void uniquePermutationsHelper(int[] array, int i) {
        if (i == array.length - 1) {
            Util.print(array);
        }

        for (int j = i; j < array.length; j++) {
            if (shouldSwap(array, i, j)) {
                Util.swap(array, i, j);
                uniquePermutationsHelper(array, i + 1);
                Util.swap(array, i, j);
            }

        }
    }

    public void uniquePermutationsChar(char[] array) {
        uniquePermutationsHelperChar(array, 0);
    }

    private void uniquePermutationsHelperChar(char[] array, int i) {
        if (i == array.length - 1) {
            Util.print(array);
        }

        for (int j = i; j < array.length; j++) {
            if (shouldSwap(array, i, j)) {
                Util.swap(array, i, j);
                uniquePermutationsHelperChar(array, i + 1);
                Util.swap(array, i, j);
            }

        }
    }

    /**
     * This method is used when we are looking for permutations that are unique,
     * when we have duplicate characters in our input array.
     *
     * The rational is that, if we are about to swap with a character that is a duplicate...then don't!
     * If we are going to swap with a character is different from the current one, then go ahead, swap,
     * and continue to recurse.
     *
     * The trick to remember is how to exactly perform the swap. Its not enough to merely check
     * if the two characters you Wish to swap the array[i] and array[j] are different.
     *
     * No, what you must do is check every character from i to j - 1. If you want to swap array[i] and
     * array[j], then you must check to see if array[j] matches any of the characters from
     * array[i...j - 1]. If array[j] doesn't match any of the characters from array[i...j - 1], then that
     * means we haven't generated any permutations that have the jth character in the ith spot.
     *
     * If array[j] does match with a character in between array[i....j - 1], that means we have already
     * generated a permutation with the character (duplicate) array[j] in the ith spot. Take the following example
     *
     * A B C E B D
     *
     * Lets assume that i is at 0, so array[0] = A, and j is at 4, so array[4] = B. We want to see if we can swap
     * array[i] and array[j], or swap, A and B.
     *
     * In order to do this, we will check every character between array[i] and array[j - 1], i.e. A B C E. If there is a match
     * with array[4], or B, in that range then we know we shouldn't swap. Why do we know this?
     *
     * We know this because, the character at array[1] is equal to B. What that means is, in a previous recursive call, array[0],
     * and array[1] were already swapped, and the character B has already been choosen to be at the 0th spot,
     * so we don't need to put B back into the 0th position again. Because the B at index = 1, was previously swapped already
     * with that position!
     *
     * If we did go ahead and swap array[0] with array[4], then we would be putting B back in the 0th index, which would create
     * duplicate permutations.
     *
     *
     * @param array
     * @param i
     * @param j
     * @return
     */
    private boolean shouldSwap(int array[], int i, int j) {
        for (int index = i; index < j; index++) {
            if (array[index] == array[j]) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldSwap(char array[], int i, int j) {
        for (int index = i; index < j; index++) {
            if (array[index] == array[j]) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * This algorithm represents my first inclination at solving this problem. Thought of this
     * problem purely in terms of strings. I didn't break it down into a character array.
     *
     * When I drew the recursion tree, I thought of using two strings as input. One string representing
     * work to do, and the other string representing work that was done. When the "work to do" string
     * was done, then I knew to stop recursing and print the "work done" string. Below is an example
     * recursion tree if our input was the string "abc". We have to "choose" which character we will
     * fix as the first in our permutation, and then continue recursing.
     *
     *    Level                        work to do,  work done
     *                                      \       /
     *      0                               "abc", ""
     *                              /            |
     *      1                  "bc","a"       "ac", "b"
     *                      /       \          /
     *      2          "c","ab"  "b", "ac"    "c","ba"
     *               /               \           |
     *          "", "abc"         "","acb"     "bac"
     *
     *
     * Every time we return from a recursive call, the decision we made regarding the first character we are choosing
     * has been undone given the call stack, and the fact that we are using immutable Strings.
     *
     * All we are doing is saying, first we will put 'a' in the first spot, and then find all permutations
     * that begin with the character a.
     *
     * Then we will put 'b' in the first spot, and find all permutations that start with 'b'. Then we will put
     * 'c' in the first spot and find all permutations that start with c.
     *
     * As you can see, given each level of the tree, we are essentially iterating through each of the remaining
     * characters in our "work to do" string.
     *
     * When "work to do" is empty, we know there are no more permutations left, so we can stop, and print
     * the "work done" string.
     *
     * At the 0 level of the tree, our root node as 3 children representing the 3 possible choices for the first position
     * of the permutation.
     *
     * At the 1 level of the tree, our nodes have only 2 children, representing the number of possible choices in the
     * second position.
     *
     * At the 2 level of the tree, our nodes only have 1 child, as there is only one choice left to make. The out degree,
     * the number of edges of a node, decreases each time. Essentially being (n - 1) * (n - 2) * (n - 3)...until we get
     * to zero, essentially giving us our running time of O(n!). Remember that the leaf nodes, as well as the
     * make up 1/2 of all the nodes in the tree, so the leaf nodes and the total number of nodes in the tree are
     * both bound by n!.
     *
     * The problem with this algorithm is the use of a StringBuilder as opposed to an array. First of all
     * StringBuilder is not synchronized (unlike StringBuffer). Secondly it has a backing array of characters.
     * The cost of a deleting character is O(n), of appending one character is constant.
     *
     * So the running time of this algorithm is n for the for loop times, n for the # of chars in the available
     * string for the delete operation.
     *
     * We know that at the very least, it will be executed O(n!) times. So the time complexity is
     * O(n! * n^2).
     *
     * @param word
     */
    public void permutationsUsingStrings(String word) {
        permutationsUsingStringsHelper(word, "");
    }

    private void permutationsUsingStringsHelper(String workToDo, String workDone) {
        if (workToDo.equals("")) {
            System.out.println(workDone);
            return;
        } else {
            int length = workToDo.length();
            for (int i = 0; i < length; i++) {

                // choose
                StringBuilder availableBuilder = new StringBuilder(workToDo);
                Character choice = workToDo.charAt(i);
                String workRemaining = availableBuilder.deleteCharAt(i).toString();

                StringBuilder chosenBuilder = new StringBuilder(workDone);
                chosenBuilder.append(choice);
                String workCompleted = chosenBuilder.toString();

                // explore
                permutationsUsingStringsHelper(workRemaining, workCompleted);

                /**
                 * after returning from the recursion, we have implicitly unchosen our "choice"
                 * because at the top we are re-instantiating our builders, with the original
                 * arguments to the function, "workToDo" and "workDone".
                 *
                 * Moving the counter forward, will move the counter forward in our initial
                 * arguments of "workToDo". We have there by maintained state, and returned
                 * to the state that we were previously at.
                 */
            }
        }
    }

    /**
     * permutationAlternateEvenOddNumber - The problem statement is the following:
     * Given an array of size n, where n is even, and half of the numbers are odd and the other have is even,
     * print all of the permutations of numbers such that the permutations begin with an odd number, and such that
     * all of the numbers in the permutations alternate between even and odd.
     *
     * For this problem, for every "choice" (the number at the 'i'th index), that we chose for a given position,
     * we need to pay attention to the number to the left of our choice (the number "left" of 'i' in the
     * array), in order to ensure that we only permute in cases were even and odd alternate.
     *
     * Remember that when we are swapping numbers in the array, we are essentially choosing to have a given number in
     * the 'i'th position. so when we call swap(a, i, j). We are saying, take the number in the 'j'th position in the
     * array, and put it in the 'i'th spot.
     *
     * In our recursive calls, j is the iterator in the array, and i is the index that we are "choosing" to put
     * our choices in. So for the array [10, 12, 4, 7], if i = 0, and j = 1, that means, take the number at index 1
     * which is 12 (our choice), and put it in the 0th spot. So choose 12, to be in the ith spot, or the zero-th spot.
     *
     * If the number we have choosen is even, then we should only permute if the number to the left was odd.
     * If the number we have chosen is odd, then we should only permute if the number to left was even.
     * There is no need to check the number to the right, because, when we recurse, we are recursing to the
     * i + 1 position, and evaluating that in every subsequent recursive call.
     *
     * if we are at index = 0 (which means i - 1 < 0), then we just need to make sure that
     * the current number in the array is odd.
     */

    public void permutationAltEvenOddNumber(int[] array) {
        if (array.length % 2 != 0) {
            System.out.println("Array length must be even");
            return;
        }

        int numOfOdd = 0;
        for (int i = 0; i < array.length; i++) {
            if (isOdd(array, i)) {
                numOfOdd++;
            };
        }

        if (numOfOdd != array.length/2) {
            System.out.println("Half of the numbers should be odd, and the other half even!");
            return;
        }

        permutationAltEvenOddNumberHelpler(array, 0);
    }

    private void permutationAltEvenOddNumberHelpler(int[] array, int i) {
        if (i == array.length - 1) {
            Util.print(array);
        }

        for (int j = i; j < array.length; j++) {
            Util.swap(array, i, j);
            boolean isCurrentOdd =  isOdd(array, i);
            boolean isPreviousOdd = isOdd(array, i - 1);
            if ((i - 1 < 0) && isCurrentOdd || isCurrentOdd && !isPreviousOdd || !isCurrentOdd && isPreviousOdd) {
                permutationAltEvenOddNumberHelpler(array, i + 1);
            }
            Util.swap(array, i, j);
         }
    }

    private boolean isOdd(int[] a, int index) {
        if (index < 0 || index == a.length) {
            return false;
        }
        return a[index] % 2 == 1;
    }

    public String[] generate_all_expressions(String s, long target) {
        if (s == null || s.trim().isEmpty()) {
            return new String[]{};
        }

        int length = s.length();
        List<String> results = new ArrayList<>(length * length);
        if (Long.valueOf(s) == target) {
            results.add(s);
        }

        StringBuffer additionBuffer = new StringBuffer();
        additionBuffer.append("+");
        additionBuffer.append(s);

        StringBuffer multiplicationBuffer = new StringBuffer();
        multiplicationBuffer.append("*");
        multiplicationBuffer.append(s);


        String additionString = additionBuffer.toString();
        generate_all_expressionsHelper(additionString.toCharArray(), target, 0, results);

        String multiplicationString = multiplicationBuffer.toString();
        generate_all_expressionsHelper(multiplicationString.toCharArray(), target, 0, results);
        return results.toArray(new String[0]);
    }

    private void generate_all_expressionsHelper(char[] array, long target, int i, List<String> results) {
        if (i == array.length - 1) {
             if (array[0] != '+' && array[0] != '*' && array[array.length - 1] != '+' && array[array.length - 1] != '*') {
                int operatorLocation = 1;
                StringBuffer firstOperandBuffer = new StringBuffer();
                firstOperandBuffer.append(array[0]);
                for (int j = 1; j < array.length; j++) {
                    if (array[j] == '+' || array[j] == '*') {
                        operatorLocation = j;
                        break;
                    } else {
                        firstOperandBuffer.append(array[0]);
                    }
                }
                StringBuffer secondOperandBuffer = new StringBuffer();
                for (int j = operatorLocation + 1; j < array.length;  j++) {
                    secondOperandBuffer.append(array[j]);
                }

                Long firstOperand = Long.valueOf(firstOperandBuffer.toString());
                Long secondOperand = Long.valueOf(secondOperandBuffer.toString());

                if (array[operatorLocation] == '*') {
                    Long value = firstOperand * secondOperand;
                    if (value == target) {
                        results.add(Util.getString(array));
                    }
                }


                if (array[operatorLocation] == '+') {
                    Long value = firstOperand + secondOperand;
                    if (value == target) {
                        results.add(Util.getString(array));
                    }
                }
                return;
            }
        }

        for (int j = i; j < array.length; j++) {
            if (shouldSwap(array, i, j)) {
                Util.swap(array, i, j);
                generate_all_expressionsHelper(array, target, i + 1, results);
                Util.swap(array, i, j);
            }
        }
    }


}
