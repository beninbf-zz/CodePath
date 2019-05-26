package main.java.com.codepath.sorting;

import main.java.com.codepath.recursion.Palindrome.Palindrome;

import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class SortingProblems {

    /**
     * This problem can be solved with an implementation of heapsort or merge sort.
     *
     * Given the arrays are already sorted I choose to use an implementation of merge,
     * repeatedly merging each two arrays until all values were merged. the only thing
     * to do, was to check to see whether or not the order was increasing or descreasing.
     *
     * Given K sorted arrays arr, of size N each, merge them into a new array res, such
     * that res is a sorted array. Assume N is very large compared to K. N may not even
     * be known. The arrays could be just sorted streams, for instance, timestamp streams.
     *
     * All arrays might be sorted in increasing manner or decreasing manner. Sort all of
     * them in the manner they appear in input.
     *
     * @param arr int[]
     * @return int[]
     */
    public int[] mergeArrays(int[][] arr) {
        boolean isAsc = isAsc(arr[0]);
        int[] result = arr[0];

        if (isAsc) {
            for (int i = 1; i < arr.length; i++) {
                result = mergeAsc(result, arr[i]);
            }
        } else {
            for (int i = 1; i < arr.length; i++) {
                result = mergeDesc(result, arr[i]);
            }
        }
        return result;
    }

    private boolean isAsc(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1]) {
                continue;
            } else {
                if (arr[i] < arr[i + 1]) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private int[] mergeAsc(int[] array1, int[] array2) {
        int leftBeg = 0;
        int leftEnd = array1.length - 1;
        int rightBeg = 0;
        int rightEnd = array2.length - 1;
        int itemsToCopy = array1.length + array2.length;

        int[] result = new int[itemsToCopy];
        int index = 0;

        while (leftBeg <= leftEnd && rightBeg <= rightEnd) {
            if (array1[leftBeg] <= array2[rightBeg]) {
                result[index] = array1[leftBeg];
                index++;
                leftBeg++;
            } else {
                result[index] = array2[rightBeg];
                index++;
                rightBeg++;
            }
        }

        while (leftBeg <= leftEnd) {
            result[index] = array1[leftBeg];
            index++;
            leftBeg++;
        }

        while (rightBeg <= rightEnd) {
            result[index] = array2[rightBeg];
            index++;
            rightBeg++;
        }

        return result;
    }

    private int[] mergeDesc(int[] array1, int[] array2) {
        int leftBeg = 0;
        int leftEnd = array1.length - 1;
        int rightBeg = 0;
        int rightEnd = array2.length - 1;
        int itemsToCopy = array1.length + array2.length;

        int[] result = new int[itemsToCopy];
        int index = 0;

        while (leftBeg <= leftEnd && rightBeg <= rightEnd) {
            if (array1[leftBeg] >= array2[rightBeg]) {
                result[index] = array1[leftBeg];
                index++;
                leftBeg++;
            } else {
                result[index] = array2[rightBeg];
                index++;
                rightBeg++;
            }
        }

        while (leftBeg <= leftEnd) {
            result[index] = array1[leftBeg];
            index++;
            leftBeg++;
        }

        while (rightBeg <= rightEnd) {
            result[index] = array2[rightBeg];
            index++;
            rightBeg++;
        }

        return result;
    }

    /**
     *
     * This problem requires using a heap of some sort. In this case, we are using a PriorityQueue
     * that has been modified to be a MaxHeap via the use of Collections.reverseOrder()
     *
     * We put all of the elements of the array into the maxHeap. We also create a set
     * for our result as the problem states that it doesn't want duplicates.
     *
     * If our set doesn't contain the max element, then we extract it from the heap
     * and add it to our set, and increment our counter i, which will count up to i.
     *
     * While the queue is not empty and while i < k, we will keep doing this. In the
     * end we just copy our Integer set to an integer array so that we can return
     * an int[]. The Arrays.asList doesn't do auto boxing for ints so we must
     * copy it manually to an array.
     *
     * You are given an array of integers arr, of size n, which is analogous to a continuous stream of
     * integers input. Your task is to find K largest elements from a given stream of numbers.
     *
     * By definition, we don't know the size of the input stream. Hence, produce K largest elements seen so far,
     * at any given time. For repeated numbers, return them only once.
     *
     * If there are less than K distinct elements in arr, return all of them.
     * Note:
     *
     * Don't rely on size of input array arr.
     * Feel free to use built-in functions if you need a specific data-structure.
     *
     * RUNTIME: n log n. We move over the entire array and the insert
     * and extract operations on the Queue are log n.
     *
     * SPACE: O(K) because we are storing the elements in a heap
     *
     * @param arr int[]
     * @param k k largest elements to capture
     * @return int[]
     */
    public int[] topK(int[] arr, int k) {
        Queue<Integer> queue = new PriorityQueue<Integer>(arr.length, Collections.reverseOrder());

        for (Integer n: arr) {
            queue.add(n);
        }

        Set<Integer> result = new HashSet<>();

        int i = 0;
        while (!queue.isEmpty() && i < k) {
            if (!result.contains(queue.peek())) {
                result.add(queue.poll());
                i++;
            } else {
                queue.poll();
            }
        }

        int j = 0;
        int[] toReturn = new int[result.size()];
        for (Integer n: result) {
            toReturn[j] = n.intValue();
            j++;
        }
        return toReturn;
    }
}
