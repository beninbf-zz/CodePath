package main.java.com.codepath.sorting;

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
     * or if list is in reverse order
     *
     * SPACE COMPLEXITY: Some say constant, but its actually not because of the recursive call
     * stack. so its best case SPACE complexity is (log n)
     * @param array int[]
     */
    public void quickSort(int[] array) {
        quickSortHelper(array, 0, array.length - 1);
    }

    private void quickSortHelper(int[] array, int start, int end) {
        if (start >= end) {
            return;
        };
        int pivot = choosePivot(array, start, end);
        int index = partition2(array, start, end, pivot);
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
     * @param pivot int
     * @return int;
     */
    private int partition1(int[] array, int start, int end, int pivot) {
        swap(array, end, pivot);
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
     * @param array int[]
     * @param start int
     * @param end int
     * @param pivot int
     * @return i
     */
    private int partition2(int[] array, int start, int end, int pivot) {
        int i = start;
        int j = end;

        while (i <= j) {
            while (array[i] < array[pivot]) {
                i++;
            }

            while (array[j] > array[pivot]) {
                j--;
            }

            // must be <= so that j can cross j and the while loop can exit
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        return i;
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
     *
     * SPACE: O(1)
     * @param array int[]
     */
    public void heapSortMax(int[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            maxHeapify(array, array.length, i);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            swap(array, i, 0);
            maxHeapify(array, i, 0);
        }
    }

    public void heapSortMin(int[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            minHeapify(array, array.length, i);
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
     * @param array int[]
     * @param n right boundary of array
     * @param i the element we are assuming is our largest element
     */
    private void maxHeapify(int[] array, int n, int i) {
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
    private void minHeapify(int[] array, int n, int i) {
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
}
