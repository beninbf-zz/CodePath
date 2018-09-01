package main.java.com.codepath.stringandarrays;

public class Sorting {

    public Sorting() {};

    public void recursiveMergeSort(int[] array) {
        if (array.length == 0 || array.length == 1) {
            return;
        }
        int[] temp = new int[array.length];
        mergeSort(array, 0, array.length - 1, temp);
    }

    private void mergeSort(int[] array, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid, temp);
            mergeSort(array, mid + 1, right, temp);
            merge(array, left, mid, right, temp);
        }
    }

    private void merge(int[] array, int left, int mid, int right, int[] temp) {
        int counter = left;
        int leftBegArray = left;
        int leftEndArray = mid;
        int rightBegArray = mid + 1;
        int rightEndArray = right;
        int totalNums = (right - left) + 1;

        // We need to compare the last element of the array, so use less than or equal
        while(leftBegArray <= leftEndArray && rightBegArray <= rightEndArray) {
            if (array[leftBegArray] <= array[rightBegArray]) {
                temp[counter] = array[leftBegArray];
                counter++;
                leftBegArray++;
            } else {
                temp[counter] = array[rightBegArray];
                counter++;
                rightBegArray++;
            }
        }

        // We need to compare the last element of the array, so use less than or equal
        while(leftBegArray <= leftEndArray) {
            temp[counter] = array[leftBegArray];
            counter++;
            leftBegArray++;
        }

        // We need to compare the last element of the array, so use less than or equal
        while(rightBegArray <= rightEndArray) {
            temp[counter] = array[rightEndArray];
            counter++;
            rightBegArray++;
        }

        // copy number from temp back to array, starting from the end
        for (int i = 0; i < totalNums; i++) {
            array[rightEndArray] = temp[rightEndArray];
            rightEndArray--;
        }
    }

    public void print(int[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]+"]");
        System.out.println();
    }

    /**
     * Iterative mergeSort
     *
     * Works on the same principle as the recursive merge sort, the only difference is its using a loop
     * instead of recursion.
     *
     * In the recursive solution we use the call stack to pass array indices that are continually
     * halved until we get n sub arrays (where n is the number of elements in the array) of length
     * one. We then move up the call stack, sorting arrays of doubling size.
     *
     * We can do a similar thing with a loop. Our variable "groups" is the number of groups we will divide
     * our array into. We will then proceed to sort each group of elements. In order to do that, we need to know
     * how many elements are going to be in each group of elements. Initially we want to sort groups
     * that have 2 elements.
     *
     * i.e. that means for the following array [9, 8, 7, 6, 5, 4, 3, 2] which has 8 elements, want to have
     * groups of size 2. We find out the number of groups, by calculating 8 / 2, which is 4, so initially we have 4
     * groups.
     *
     * Considering we have 4 groups, we need to come up with intervals for our array indices, so that we can sort the
     * 4 intervals. To get the intervals of 4 groups, divide the number of elements, by the number of groups.
     *
     * In this case, that is 8 / 4. So the dimension of the interval is 2, which makes sense because, initially
     * each interval will have 2 elements. So this the initial array index boundaries of the first group is
     *
     *  left = 0, right = 1 (because arrays are 0 based, this is right=(0 + 2) - 1 = 1, or right = (left + interval_size) - 1
     *
     * Using loop we will continue to sort groups of elements, until we are the array is sorted.
     * First 8/2 = 4 groups
     * then 4/2 = 2 groups
     * then 2/2 = 1 groups
     *
     * Until we have no more groups to sort. The running time if n log n. We have to merge at most n elements, log n times
     * becaus we continually cut the array in half.
     *
     * CAVEAT: We always want to sort an even number of groups. So if the number of elements is odd, we need to round up.
     * Same thing would go for the interval size, the interal size should always be even. For odd length arrays
     * be sure to check your right bound. When its equal to the length, just subtract one so that you are on the
     * last element of the array.
     *
     * Our merge operation is the same, regardless of recursive mergeSort or iterative mergeSort.
     *
     * Remember with recurisve merge sort, we recurse until we get elements of size one. With iterative merge sort,
     * we start of arrays of size 2.
     *
     * @param array
     */
    public void iterativeMergeSort(int[] array) {
        int numElements = array.length;
        int groups = returnRoundedUpInt(numElements, 2);
        int[] temp = new int[array.length];
        while (groups > 0) {
            int intervalSize = returnRoundedUpInt(numElements, groups);
            int left = 0;
            // Subtract one because arrays are zero based;
            int right = intervalSize - 1;
            for (int i = 0; i < groups; i++) {
                int mid = (left + right) / 2;
                //System.out.println("left=" +left + "mid="+mid+ "right=" +right);
                merge(array, left, mid, right, temp);
                left += intervalSize;
                right += intervalSize;

                // if we have an odd number of elements check the right boundary
                if (right == array.length) {
                    right--;
                }
            }
            groups = groups / 2;
        }
    }

    public int returnRoundedUpInt(int number, int divisor) {
        int result = 0;
        if (number % divisor == 0) {
            result = number / divisor;
        } else {
            result = (number / divisor) + 1;
        }
        return result;
    }
}
