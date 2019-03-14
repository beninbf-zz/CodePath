package main.java.com.codepath.recursion;

import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * <code>SubSets</code> is a class containing different algorithms for printing and generating powersets.
 *
 * A "subset" is a subset if all of its elements are contained within some superset. For example if our
 * super set is {1, 2}, then a subset of {1, 2} is {}, its also {1}, and {2}, and {1, 2}.
 *
 * All of these sets are subsets of {1, 2} because all of their elements are contained within {1, 2}
 * By default, the empty set, is also a set of {1, 2}. The empty set is a subset of everything.
 *
 * The key thing to remember with a power set, which is the set of all subsets of a set, is that we are
 * making a decision, for each member of the set, on whether or not to include it.
 *
 * so for the set [1, 2]. The power set will consist of a series of decisions to whether include 1 or not,
 * and 2 or not.
 *
 *                                       [1, 2]
 *                        (exclude 1) /        \ (include 1)
 *                         [2]                 [1, 2]
 *                 (ex 2) /   \(in 2)    (ex 2)/   \in 2
 *                     []     [2]         [1]      [1, 2] <---- all of the subsets of [1,2] are in this row
 *
 * Thats 2 decisions per character or 2 ^ n.
 *
 * @author Benin Bryant
 * @since Mar 4, 2019
 */
public class SubSets {
    /**
     * Print sub sets
     * <p>
     * This code take in a string, and then prints all of the possible subsets of characters made up
     * of the characters in our input.
     * <p>
     * This code calls for merely printing all of the subsets. Therefore, we don't necessarily need to
     * store each subset, we just need to be able to have it long enough to print it.
     * <p>
     * As such we will use an array, that's the same size as our input. The indice in our auxilary array
     * will represent a "subset" of our input. For example if we print {null,null,null} then that is essentially
     * the empty set. When we print the subset {t}, the array shoudl look like {t,null,null}. This strategy
     * will allow us to reuse the same storage to print every subset.
     * <p>
     * To do this, we are making a 2 decisions for every character in our input. That decision is to either include
     * the given character in a given subset, or exclude it.
     * <p>
     * For an input of "tdc", that means we have 2 choices to make for every character. The total number of subsets
     * is essentially 2*2*2, so that means 2^n possible choices.
     * <p>
     * Because we have to print the subsets, and our subset is stored in an array, that means we also have to traverse
     * our auxilary array when we want to print.
     * <p>
     * The total running time is therefor O(2^n * n) where the n is for printing every character of the subset.
     *
     * @param word
     */
    public void printSubSets(String word) {
        String subject = word.trim();
        if (subject == null || subject.isEmpty()) {
            System.out.println("Word has no content");
            return;
        }

        char[] array = word.toCharArray();
        char[] subSets = new char[array.length];
        printSubSetsHelper(array, subSets, 0);
    }

    private void printSubSetsHelper(char[] array, char[] subSets, int i) {
        if (i == array.length) {
            Util.print(subSets);
            return;
        }

        // setting the index to a psuedo "null" value, to indicate that we want a subset that doesn't
        // include array[i]
        subSets[i] = '\u0000';

        // This recursive call here is saying generate the subsets that do NOT include array[i]
        printSubSetsHelper(array, subSets, i + 1);

        // The code below is generating subsets that include array[i]
        subSets[i] = array[i];
        printSubSetsHelper(array, subSets, i + 1);
    }

    /**
     * Ik solution to return all subsets of a string
     * @param s string
     * @return String[] array of all subsets
     */
    public String[] generate_all_subsets(String s) {
        String subject = s.trim();
        if (subject == null || subject.isEmpty()) {
            return new String[]{};
        }

        char[] array = s.toCharArray();
        char[] subSets = new char[array.length];
        Double numberOfSubSets = Math.pow(array.length, 2);
        List<String> results = new ArrayList<>(numberOfSubSets.intValue());
        generate_all_subsetsHelper(array, subSets, 0, results);
        return results.toArray(new String[0]);
    }

    private void generate_all_subsetsHelper(char[] array, char[] subSets, int i, List<String> results) {
        if (i == array.length) {
            results.add(Util.getString(subSets));
            return;
        }

        // setting the index to a psuedo "null" value, to indicate that we want a subset that doesn't
        // include array[i]
        subSets[i] = '\u0000';

        // This recursive call here is saying generate the subsets that do NOT include array[i]
        generate_all_subsetsHelper(array, subSets, i + 1, results);

        // The code below is generating subsets that include array[i]
        subSets[i] = array[i];
        generate_all_subsetsHelper(array, subSets, i + 1, results);
    }

    /**
     * This problem is a natural extension of the sub set problem.
     *
     * Given an array of integers and a sum, print all of the subsets of the array
     * that sum to the given number.
     *
     * The currently implementation is simply print all of the subsets and then, at the base case
     * summing up the subset array to check its sum.
     *
     * While it works, it is not the efficient. While the running time is still O(2^n * 2n),
     * and after taking the 2 out, its the same,O(2^n * n), we don't necessarily have to
     * print every subset. If the sum of the subset is already greater than the sum, then don't
     * bother looking for more subsets.
     *
     * @param array
     * @param sum
     */
    public void printSubSetSum(Integer[] array, int sum) {
        if (array == null || array.length == 0) {
            return;
        }
        Integer[] subsets = new Integer[array.length];
        printSubSetSumHelper(array, subsets, sum, 0);
    }

    private void printSubSetSumHelper(Integer[] array, Integer[] subsets, int sum, int i) {
        if (i == array.length) {
            if (Util.sum(subsets) == sum) {
                Util.print(subsets);
            }
            return;
        }

        printSubSetSumHelper(array, subsets, sum, i + 1);
        // include array[i] in the subset
        subsets[i] = array[i];

        printSubSetSumHelper(array, subsets, sum, i + 1);
        // remove array[i] from the subset
        // because we are removing the a[i] element from the subset, there is nothing
        // we have to update regarding the sum
        subsets[i] = null;
    }

    /**
     * This implementation is more efficient than the one above as we keep track of the sum.
     * We subtract values that we add to our subset from our sum. if and when it gets to 0, we return.
     * If the sum is already less than 0, then we know to stop looking.
     * @param array
     * @param sum
     */
    public void printSubSetSubtractToZero(Integer[] array, int sum) {
        if (array == null || array.length == 0) {
            return;
        }
        Integer[] subsets = new Integer[array.length];
        printSubSetSubtractToZeroHelper(array, subsets, sum, 0);
    }

    private void printSubSetSubtractToZeroHelper(Integer[] array, Integer[] subsets, int sum, int i) {
        if (sum == 0) {
            Util.print(subsets);
            return;
        }

        if (sum < 0) {
            return;
        }

        if (i == array.length) {
            return;
        }

        printSubSetSubtractToZeroHelper(array, subsets, sum, i + 1);
        // include array[i] in the subset
        subsets[i] = array[i];
        if(array[i] != null) {
            sum -= array[i].intValue();
        }

        printSubSetSubtractToZeroHelper(array, subsets, sum, i + 1);
        // remove array[i] from the subset
        // because we are removing the a[i] element from the subset, there is nothing
        // we have to update regarding the sum
        subsets[i] = null;
    }

    /**
     * This implementation is another efficient way of testing the sum. As opposed to subtract,
     * we add up the values in the subset as we recurse, when our sum so far equals our sum,
     * we simply return.
     *
     * In this implementation, we wont wait for the recursion to get the leaf node before
     * we start to add/substract from our sum.
     * @param array
     * @param sum
     */
    public void printSubSetSumAddSoFar(Integer[] array, int sum) {
        if (array == null || array.length == 0) {
            return;
        }
        Integer[] subsets = new Integer[array.length];
        printSubSetSumAddSoFarHelper(array, subsets, sum, 0, 0);
    }

    private void printSubSetSumAddSoFarHelper(Integer[] array, Integer[] subsets, int sum, int sumSoFar, int i) {
        if (sumSoFar == sum) {
            Util.print(subsets);
            return;
        }

        if (sumSoFar > sum) {
            return;
        }

        if (i == array.length) {
            return;
        }

        printSubSetSumAddSoFarHelper(array, subsets, sum, sumSoFar, i + 1);
        // include array[i] in the subset
        subsets[i] = array[i];
        if(array[i] != null) {
            sumSoFar += array[i].intValue();
        }

        printSubSetSumAddSoFarHelper(array, subsets, sum, sumSoFar, i + 1);
        // remove array[i] from the subset
        subsets[i] = null;
    }

    /**
     * In this implementation I am generating all of the subsets and returning them.
     * This implementation represents a different approach. Because I want to return all subsets
     * I need to pass an accumulator to each recursive call as I build this powerSet up.
     * <p>
     * The important thing to remember is that with every new character we introduce, we must include
     * in the power set all of the previous subsets that did NOT include this character, and then
     * create all of the subsets that contain this character.
     * <p>
     * So, start with the empty set.
     * <p>
     * []
     * <p>
     * Then generate all subsets for [1]. This means including all of the subsets that DON'T include 1.
     * Which in this case is [], and then generate all of the subsets that include [1]. in total thats.
     * <p>
     * [], [1]
     * <p>
     * What happens if we want to generate all subsets of [1, 2].
     * <p>
     * Well, look at all subsets for [1]. Lets keep those subsets and then create all subsets that contain
     * 2. We do this by simply adding 2, to each existing subset.
     * <p>
     * So, those that don't have 2
     * <p>
     * [], [1]
     * <p>
     * Those that do have 2...just add 2 to each set of the powerset of [1]
     * <p>
     * [2], [1, 2].
     * <p>
     * Now the power set of [1, 2] is
     * <p>
     * [], [1], [2], [1,2];
     * <p>
     * Look at [1, 2, 3]..first maintain all of the subsets that don't have 3 which is above, and then
     * generate all of those that do.
     * <p>
     * So thats [3], [1,3], [2,3], [1,2,3]. So all of the subsets of [1, 2, 3]
     * are [], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]
     * <p>
     * This is essentially the same rationale as the class recursive solution.
     *
     * @param input
     * @return
     */
    public List<List<Character>> generatePowerSet(String input) {
        List<List<Character>> powerSet = new ArrayList<>();
        powerSet.add(new ArrayList<>());
        powerSet = generatePowerSetHelper(input.toCharArray(), powerSet, 0);
        return powerSet;
    }

    private List<List<Character>> generatePowerSetHelper(char[] input, List<List<Character>> powerSet, int i) {
        if (i == input.length) {
            return powerSet;
        }
        List<List<Character>> set = new ArrayList<>();
        set.addAll(powerSet);
        for (List l : powerSet) {
            Character c = input[i];
            List<Character> newSet = new ArrayList<>();
            newSet.addAll(l);
            newSet.add(c);
            set.add(newSet);
        }
        return generatePowerSetHelper(input, set, i + 1);
    }

    /**
     * Power set generation via an interative solution. It looks just like
     * my implemenation in generatePowerSetHelper. Its just using 2 for loops
     * as opposed to recursion.
     *
     * @param input
     * @return
     */
    public List<List<Character>> powerSetIterative(String input) {
        List<List<Character>> powerSet = new ArrayList<>();

        // set start with the empty set, which is contained in every subset
        powerSet.add(new ArrayList<>());

        // We need this variable so that we can accumulate all of the subset that
        // contain our new character. Remember that all of the sets in the power set
        // BEFORE we introduce our new characters are the ones that DON'T have the
        // new character.
        List<List<Character>> newSubSets;
        char[] array = input.toCharArray();

        for (Character c : array) {
            newSubSets = new ArrayList<>();
            for (List set : powerSet) {
                List<Character> newSet = new ArrayList<>();
                newSet.addAll(set);
                newSet.add(c);
                newSubSets.add(newSet);
            }
            powerSet.addAll(newSubSets);
        }
        return powerSet;
    }

    public List<List<Character>> recursiveGeneratePS(String word) {
        List<List<Character>> powerSet = new ArrayList<>();
        if (word == null || word.trim().isEmpty()) {
            return powerSet;
        }

        List<Character> candidateSet = new ArrayList<>();
        recursiveGeneratePsHelper(word.toCharArray(), powerSet, candidateSet, 0);
        return powerSet;
    }

    public void recursiveGeneratePsHelper(char[] array, List<List<Character>> powerSet, List<Character> candidateSet, int i) {
        if (i == array.length) {
            powerSet.add(new ArrayList(candidateSet));
            return;
        }
        recursiveGeneratePsHelper(array, powerSet, candidateSet, i+1);
        candidateSet.add(array[i]);
        recursiveGeneratePsHelper(array, powerSet, candidateSet, i+1);
        candidateSet.remove(candidateSet.size() - 1);
    }

    /**
     * Given an array of positive integers, return true if there exists a subset of the array
     * that sums to the given number.
     *
     * Because we are just return true or false to find "any" subset that sums to our given sum
     * we don't need to store all of the generated subsets
     *
     * @param array array of integers
     * @param sum the number to sum to.
     * @return boolean
     */
    public boolean mySubSetSumSubtraction(int[] array, int sum) {
        if (array.length == 1) {
            return array[0] == sum;
        }
        return mySubSetSumSubTractionHelper(array, 0, sum);
    }

    private boolean mySubSetSumSubTractionHelper(int[] array, int i, int sum) {
        if (sum == 0) {
            return true;
        } else if (i == array.length) {
            return false;
        }

        // Again, this is doing the same as all the other problems. There are two recursive calls here
        // representing making a decision on whether to include the array[i] element or not.
        boolean withoutElement = mySubSetSumSubTractionHelper(array, i + 1, sum);
        sum -= array[i];
        boolean withElement = mySubSetSumSubTractionHelper(array, i + 1 , sum);
        return withoutElement || withElement;
    }

    public boolean mySubSetSumAddition(int[] array, int sum) {
        if (array.length == 1) {
            return array[0] == sum;
        }
        return mySubSetSumAdditionHelper(array, 0, sum, 0);
    }

    private boolean mySubSetSumAdditionHelper(int[] array, int i, int sum, int sumSoFar) {
        if (sumSoFar == sum) {
            return true;
        } else if (i == array.length) {
            return false;
        }

        boolean withoutElement = mySubSetSumAdditionHelper(array, i + 1, sum, sumSoFar);
        sumSoFar += array[i];
        boolean withElement = mySubSetSumAdditionHelper(array, i + 1 , sum, sumSoFar);
        return withoutElement || withElement;
    }

    public String decimalToBinaryStr(int n) {
        int result = n;
        StringBuffer sb = new StringBuffer();
        while (result > 0) {
            if (result % 2 == 0) {
                sb.append("0");
            } else {
                sb.append("1");
            }
            result = result / 2;
        }

        return sb.reverse().toString();
    }

    public void decimalToBinary(int n) {
        int result = n;
        int index = 0;
        int[] array = new int[50];
        StringBuffer sb = new StringBuffer();
        while (result > 0) {
            array[index] = result % 2;
            result = result / 2;
            index++;
        }

        for (int i = index; i >= 0; i--) {
            System.out.print(array[i]);
        }
    }

    public Integer[] decimalToBinaryArray(int n) {
        int result = n;
        int index = 0;
        Integer[] array = new Integer[50];
        StringBuffer sb = new StringBuffer();
        while (result > 0) {
            array[index] = result % 2;
            result = result / 2;
            index++;
        }

        return array;
    }
}