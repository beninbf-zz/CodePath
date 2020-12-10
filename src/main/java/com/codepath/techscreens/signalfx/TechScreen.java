package main.java.com.codepath.techscreens.signalfx;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TechScreen {
    /**
     * SignalFx
     *
     * This tech screen was given by signal Fx. It wasn't that hard, but it exposed a limitation
     * of mine regarding find duplicates, without using extra memory as well as re-organizing
     * information in array.
     *
     * Given array [3, 6, 2, 3, 6, 5, 2]
     * return only unique values [3, 6, 2, 5]
     *
     * Iteration of this problem is to return
     *
     * [3, 6, 2, 5, -1, -1, -1, -1]
     *
     * While only looping through the array once. To do this we still need a set for
     * storing duplicates but that's ok, its no extra memory. We also need to remember
     * the location of the lastUnique occurrence. Because we want to rearrange the array,
     * with all of the unique occurrences toward the beginning, then if we find another unique value
     * and the position of the last occurrence of a unique value it to its left, as in
     *
     * [3, 6, 2, -1, -1, -1, 5, -1], the position of 2 is i=2, and i=2, is less than i=7.
     *
     * That means the last occurrence of a unique number occurred at i=2, and if, 2 is too
     * the left of 7, that means thare are -1's in between. So, just look to the very right of
     * i=2 (our last occurrence of a unique number), and there should be a -1. Swap the two values,
     * effectively moving 5, to the sopt of the -1, i=3.
     *
     * @param input List of integers
     * @return List<Integer> of unique values
     */
    public List<Integer> getUniqueValuesEff(List<Integer> input) {
        Set<Integer> results = new HashSet(input.size());

        int length = input.size();
        int lastUniqueOccurence = 0;
        for (int i = 0; i < length; i++) {
            if(!results.contains(input.get(i))) {
                results.add(input.get(i));
                if (lastUniqueOccurence < i) {
                    swap(input, i, lastUniqueOccurence + 1);
                }
                lastUniqueOccurence = i;
            } else {
                Integer repeat = new Integer(-1);
                input.set(i, repeat);
            }
        }
        return input;
    }

    /**
     * Swaps to Integer values in a list
     * @param input the list
     * @param index index to swap
     * @param afterLastNonRepeat index to swap
     */
    public void swap(List<Integer> input, int index, int afterLastNonRepeat) {
        Integer negativeValue = input.get(index);
        Integer after = input.get(afterLastNonRepeat);
        input.set(afterLastNonRepeat, negativeValue);
        input.set(index,  after);
    }
}
