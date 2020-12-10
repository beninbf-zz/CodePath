package main.java.com.codepath.techscreens.uber;

public class TechScreen {

    /**
     *
     * Uber tech screen.
     *
     * Get the next number. Write a function that rearrange input elements into to generate the immediate next value,
     * if is not possible (is be biggest one) return the smaller one.
     *
     * Examples:
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     *
     *
     * 1, 2, 7, 6, 5, 4, 3, 1 => 1, 3, 1, 2, 4, 5, 6, 7
     *
     * This problem, I found to be tricky. In the end i was able to get a working solution, however
     * i needed to be given some hints.
     *
     * The problem goes as follows, if the numbers are such that, every digit, moving from right
     * to left, is in ascending order, then there is no next largest number to generate
     * and we infact have to wrap around, but reversion the number, look at example
     *
     * 5 1 1 => 1, 1, 5...there is no next larget number to make for the array 5, 1, 1, so the
     * smallest number to be made with this digits is infact the reverse of the array.
     *
     * For other cases, we essentially do, is move from right to left, looking for an inflection
     * point.
     *
     * When we find that point, we record that index. We then move from that inflection point
     * to the right, looking for the smallest number that is larger that our inflection point.
     *
     * Those are the numbers we should swap to make the next largest number. At this point,
     * everything to the right of the inflection point should be sorted, so then we can
     * simply reverse that portion of the array to generate the next largest number.
     *
     * @param input in[] array
     */
    public void findNextLargest(int[] input) {
        int inflectionPoint = -1;
        for (int i = input.length - 1; i >= 0; i--) {
            if (i - 1 >= 0) {
                if (input[i - 1] < input[i]) {
                    inflectionPoint = i - 1;
                    break;
                }
            }
        }

        if (inflectionPoint != -1) {
            int toSwap = getNextLargest(input[inflectionPoint], inflectionPoint + 1, input);
            swap(inflectionPoint, toSwap, input);
            reverse(input, inflectionPoint + 1);
        } else {
            reverse(input, inflectionPoint + 1);
        }
    }

    public void reverse(int[] input, int start) {
        int steps = (input.length - start) / 2;
        int j = 0;
        for (int i = start; i < start + steps; i++) {
            swap(i, input.length - j - 1, input);
            j++;
        }
    }
    public void swap(int i, int j, int[] input) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

    public int getNextLargest(int value, int start, int[] input) {
        int nextLargest = Integer.MAX_VALUE;
        int nextLargestIndex = -1;
        for (int i = start; i < input.length; i++) {
            if (input[i] > value && input[i] < nextLargest) {
                nextLargest = input[i];
                nextLargestIndex = i;
            }
        }
        return nextLargestIndex;
    }

}
