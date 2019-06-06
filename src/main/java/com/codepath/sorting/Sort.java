package main.java.com.codepath.sorting;

import main.java.com.codepath.util.Util;

public class Sort  {

    /**
     * Quick Sort:
     *
     * A sorting algorithm that works by choosing a pivot, in my implementation its
     * the middle element.
     *
     * We then partition the array around the pivot point, such that all elements to
     * the left are less than or equal to the element, and all elements to the right
     * are greater than the pivot.
     *
     * We then recursively call Quick sort, on both halves, start to pivot - 1,
     * and pivot + 1 to end.
     *
     * QUICK SORT IS NOT STABLE: where stability is the property that the algorithm
     * will preserve the ordering of duplicate keys. This property is important
     * when performing multiway sorts.
     *
     * RUNTIME COMPLEXITY: best case (n log n), worst case O(n^2) if list is already sorted
     * or if list is in reverse order. Arrays.sort() implementation in java uses Quicksort.
     * Why does it do that? Because we are only using primitives, and hence don't have to
     * worry about having a stable sort. Why doesn't it use heap sort. Well, heap sort
     * has much worse locality of reference than quick sort, because with heap sort
     * we might have to look into a left or right half of the array, and that portion
     * might not be pulled into the L1 cache.
     *
     * When Quick sort runs we have much better locality of reference, and amortized
     * time of n log n. Also for  heap sort, we have to build a heap, which give us
     * n + n log n. The first n, takes time.
     *
     * SPACE COMPLEXITY: Some say constant, but its actually not because of the recursive call
     * stack. so its best case SPACE complexity is actually (log n) if counting the stack
     * @param array int[]
     */
    public void quickSort(int[] array) {
        quickSortHelper(array, 0, array.length - 1);
    }

    private void quickSortHelper(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = choosePivot(array, start, end);
        int index = hoaresPartition(array, start, end, array[pivot]);
        quickSortHelper(array, start, index - 1);
        quickSortHelper(array, index + 1, end);
    }

    private int choosePivot(int[] array, int start, int end) {
        return (end + start) / 2;
    }

    /**
     * One way of partitioning the array. This is kind of slick.
     *
     * It works by immediately taking the pivot element and swapping it
     * with the element at the end of the array.
     *
     * We then create a int pointer to hold a value index, this index pointer
     * will be important after the completion of a for loop.
     *
     * In the for loop, we start at the beginning of the array
     * and move to the end, each time checking to see if the current
     * element is less than or equal to the pivot.
     * If it is, then we know that element should be placed to the left
     * of the pivot (which is currently at the end...don't get confused).
     * So in this case, we swap, the current element with the element at
     * our index pointer.
     *
     * When the loop is completed, what we have is all of the elements
     * that are lower than our pivot, to the left of our index pointer.
     *
     * So to complete, we just have to swap our pivot element, which we
     * placed at the end of the array, with our index pointer, which
     * essentially is the boundary between the elements less than or equal
     * to the pivot, and the elements greater than the pivot.
     *
     * We then return index, our pointer, which remember is now the index
     * of the pivot element at the completion of the routine.
     *
     * @param array int[]
     * @param start int
     * @param end int
     * @return int;
     */
    public int lomutosPartition(int[] array, int start, int end) {
        int pivotValue = array[end];
        int index = start;

        for (int current = start; current < end; current++) {
            if (array[current] <= pivotValue) {
                swap(array, current, index);
                index++;
            }
        }
        swap(array, index, end);
        return index;
    }

    /**
     * This is another way of partitioning the array, which is more intuitive for me
     * than the prior method.
     *
     * Essentially we have two pointers, one pointing at the beginning of the array (i)
     * the other pointing at the end (j).
     *
     * While arr[i] < array[pivot], we move i to the right, because the element at i is already
     * correctly partitioned, so there is no need to be concerned with it.
     *
     * While array[j] > array[pivot] we move j to the left, because the element at j is already
     * correctly partitioned.
     *
     * We are essentially trying to find an i element, that is on the left, but greater than the
     * pivot, then we stop moving i. We are also trying to find a j element, that is less than
     * the pivot but on the right.
     *
     * When we find these elements, we check that i <= j, then we swap then, and move i to the right
     * one step, and j to the left one step.
     *
     * We exit the loop when i had crossed j, or i is no longer <= to j.
     *
     * We then return our i pointer.
     *
     * TO BE USED WITH QUICK like such
     *
     * quicksort(arr, start, index - 1)
     * quicksort(arr, index, end)
     * @param array int[]
     * @param start int
     * @param end int
     * @param pivotValue int
     * @return i
     */
    public int hoaresPartition(int[] array, int start, int end, int pivotValue) {
        int i = start - 1;
        int j = end + 1;
        while(true) {
            do {
                i++;
            } while (array[i] < pivotValue);

            do {
                j--;
            } while (array[j] > pivotValue);


            if (i >= j) {
                return j;
            }
            swap(array, i, j);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Merge sort
     *
     * Classic sorting algorithm that for me is the most intuitive to understand.
     * We just continue cutting the array into halves until we get two one element arrays,
     * then we merge those arrays, but putting less than or equal to elements in left
     * half of the array, and then the greater elements in the right half.
     *
     * Most of the logic is in the merge portion.
     *
     * MERGE SORT IS STABLE: because of the <= in the merge portion.
     *
     * RUNTIME: O(n log n), n for the merge portion, and log (n) for the recursive calls
     * Collections.sort() uses an implementation of Merge sort, because we are sorting
     * objects and might have to do a multi dimension sort, and hence we might
     * require a stable sort. The actual implementation is called timsort.
     *
     * SPACE: O(n) for the memory for the auxiliary array in the merge portion
     * @param array int[]
     */
    public void mergeSort(int[] array) {
        mergeSortHelper(array, 0, array.length - 1);
    }

    private void mergeSortHelper(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = start + (end - start)/2;
        mergeSortHelper(array, start, mid);
        mergeSortHelper(array, mid + 1, end);
        merge(array, start, mid, end);
    }

    private void merge(int[] array, int start, int mid, int end) {
        int leftBeg = start;
        int leftEnd = mid;
        int rightBeg = mid + 1;
        int rightEnd = end;
        int itemsToCopy = end - start + 1;

        int[] temp = new int[itemsToCopy];
        int index = 0;

        while (leftBeg <= leftEnd && rightBeg <= rightEnd) {
            if (array[leftBeg] <= array[rightBeg]) {
                temp[index] = array[leftBeg];
                index++;
                leftBeg++;
            } else {
                temp[index] = array[rightBeg];
                index++;
                rightBeg++;
            }
        }

        while (leftBeg <= leftEnd) {
            temp[index] = array[leftBeg];
            index++;
            leftBeg++;
        }

        while (rightBeg <= rightEnd) {
            temp[index] = array[rightBeg];
            index++;
            rightBeg++;
        }

        int j = start;
        for (int i = 0; i < itemsToCopy; i++) {
            array[j] = temp[i];
            j++;
        }
    }

    /**
     * Heap sort.
     *
     * Heap sort makes use of a max heap to sort an array. The following implementation
     * is using a max heap.
     *
     * It does this by first building a heap. This means, starting at the middle
     * element, and building the heap from there.
     *
     * Why start from the middle element? Because the left and right children of the middle
     * element will be looking to the right half of the array. All we want to do, is get the
     * maximum element to the root of the array, or the 0 index.
     *
     * After building the heap, we then swap the max element, putting at the end of the array
     * (where it should be for sorting).
     *
     * We then call heapify again, on the remaining portion of the array, which is now one element smaller
     * considering the largest element is now at the end.
     *
     * HEAP SORT IS NOT STABLE: The extraction of elements can not be guaranteed to preserve the order.
     *
     * RUNTIME: n log n, n for running through the array and log n for heapifying, so n * log n
     * run time, although O(n log n), its actually n + n log n, because we need to first build
     * the heap, which is n.
     *
     * SPACE: O(1)
     * @param array int[]
     */
    public void heapSortMax(int[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            maxHeapify(array, i, array.length);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            swap(array, i, 0);
            maxHeapify(array, i, 0);
        }
    }

    public void heapSortMin(int[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            minHeapify(array, i, array.length);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            swap(array, i, 0);
            minHeapify(array, i, 0);
        }
        reverse(array);
    }

    private void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            swap(array, i, array.length - i - 1);
        }
    }

    /**
     * Heapifies the array;
     *
     * what does is mean to heapify an array. It simply means that given an array, and an index,
     * we need to enforce the heap property. In this case, that means, starting with the element
     * i, check to see if its larger than both of its children at indices 2(i) + 1 and 2(i) + 2,
     * if it is, then there is nothing left to do, and we can stop.
     *
     * If not, then we need to swap the element at i, with its child that is largest. Once we do that
     * we should recursively call heapify again, on this new largest index, to ensure, that it has
     * still maintained the heap property.
     *
     * This is how we build our heap out of an array.
     *
     * Build Heap is O(n)
     * @param array int[]
     * @param n right boundary of array
     * @param i the element we are assuming is our largest element
     */
    private void maxHeapify(int[] array, int i, int n) {
        int largestIndex = i;

        int leftChildIndex = 2 * i + 1;
        int rightChildIndex = 2 * i + 2;

        if (leftChildIndex < n && array[leftChildIndex] > array[largestIndex]) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < n && array[rightChildIndex] > array[largestIndex]) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != i) {
            swap(array, largestIndex, i);
            maxHeapify(array, n, largestIndex);
        }
    }

    /**
     * The same implementation as above, but finds the min stead. This method means
     * that we then have to reverse the array at the end, because it would have put
     * the smallest at the end of the array and so forth.
     *
     * @param array int[]
     * @param n right boundary of array
     * @param i the element we are assuming is our smallest element
     */
    private void minHeapify(int[] array, int i, int n) {
        int smallestIndex = i;

        int leftChildIndex = 2 * i + 1;
        int rightChildIndex = 2 * i + 2;

        if (leftChildIndex < n && array[leftChildIndex] < array[smallestIndex]) {
            smallestIndex = leftChildIndex;
        }

        if (rightChildIndex < n && array[rightChildIndex] < array[smallestIndex]) {
            smallestIndex = rightChildIndex;
        }

        if (smallestIndex != i) {
            swap(array, smallestIndex, i);
            minHeapify(array, n, smallestIndex);
        }
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
                System.out.println("[l=" +left + ", m="+mid+ ", r=" +right+"]");
                mergeIter(array, left, mid, right, temp);
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

    private void mergeIter(int[] array, int left, int mid, int right, int[] temp) {
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
            temp[counter] = array[rightBegArray];
            counter++;
            rightBegArray++;
        }

        // copy number from temp back to array, starting from the end
        for (int i = 0; i < totalNums; i++) {
            array[rightEndArray] = temp[rightEndArray];
            rightEndArray--;
        }
    }

    public void iterativeMergeSort2(int[] array) {
        int n = array.length;
        int groups = returnRoundedUpInt(n, 2);
        int[] temp = new int[array.length];
        for (int k = groups; k >= 1; k--) {
            System.out.println("GROUPS: " + k);
            int intervalSize = n / k;
            System.out.println("width: " + intervalSize);
            int left = 0;
            int right = Math.min(left + intervalSize, n - 1);
            int mid = (left + right) / 2;
            for (int i = 0; i < k; i++) {
                System.out.println("[l=" +left + ", m="+mid+ ", r=" +right+"]");
                mergeIter(array, left, mid, Math.min(right, n - 1), temp);
                left = Math.min( n - 1, Math.min(right, n - 1) + 1);
                right = Math.min(left + intervalSize, n - 1);
                mid = (left + right) / 2;
            }
            Util.print(array);
            //groups = returnRoundedUpInt(groups, 2);
        }
    }

    public void iterativeMergeSort3(int[] array) {
        int n = array.length;
        int groups = returnRoundedUpInt(n, 2);
        int[] temp = new int[array.length];
        // interval size, merging arrays of length 1, then length 2, then 4
        for (int intervalSize = 1; intervalSize < n; intervalSize = intervalSize * 2) {
            System.out.println("GROUPS: " + groups);

            System.out.println("width: " + intervalSize);
            int left = 0;
            // We add interval - 1 to the mid point because the array is zero based
            // We always take the minimum of mid + interval and the number of elements in the array
            int mid = Math.min(left + (intervalSize - 1), n - 1);
            int right = Math.min(mid + intervalSize, n - 1);
            for (int i = 0; i < groups; i++) {
                System.out.println("[l=" +left + ", m="+mid+ ", r=" +right+"]");
                mergeIter(array, left, mid, right, temp);
                left = Math.min(right + 1, n - 1);
                mid = Math.min(left + (intervalSize - 1), n - 1);
                right = Math.min(mid + intervalSize, n - 1);
            }
            Util.print(array);
            groups = returnRoundedUpInt(groups, 2);
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
