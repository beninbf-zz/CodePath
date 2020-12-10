package main.java.com.codepath.sorting;

import main.java.com.codepath.objects.Meeting;

import java.util.*;

public class SortingProblems {

    Sort sort = new Sort();
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
    public int[] mergeKSortedArrays(int[][] arr) {
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

    /**
     * You are given an integer array arr, of size n. Group and rearrange them
     * (in-place) into evens and odds in such a way that group of all even integers appears
     * on the left side and group of all odd integers appears on the right side.
     *
     * Ths problem is simply using the partition method from Quick sort. We partition the array
     * in such a way that all of the even numbers are on the left side and all of the even
     * numbers are on the right.
     *
     * RUNTIME: O(n)
     *
     * SPACE: O(1)
     * @param array int[] array
     */
    public void rearrange(int[] array) {
        if (array == null) {
            return;
        }

        if (array.length == 0 || array.length == 1) {
            return;
        }

        int i = 0;
        int j = array.length - 1;

        while (i <= j) {
            while (i < array.length && array[i] % 2 == 0) {
                i++;
            }

            while (j >= 0 && array[j] % 2 == 1) {
                j--;
            }

            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Given an integer array arr of size N, find all magical triplets in it. Magical triplet
     * is the group of 3 integers, whose sum is zero. Note that magical triplets may or may
     * not be made of consecutive numbers in arr.
     *
     * There are a few ways to do this problem. One brute force way is to generate all subsets
     * of size 3 and check there sum, but that run time is 2^N.
     *
     * Another way is to simply use 3 nested for loops, which is O(n^3).
     *
     * However there is a way to improve the algorithm to a run time O(n^2 + n log n)
     *
     * @param array int[]
     * @return String[]
     */
    public String[] magicalTriplets(int[] array) {
        Set<String> results = new HashSet<>();
        Integer[] subset = new Integer[3];

        if (array.length == 3) {
            for (int i = 0; i < array.length; i++) {
                subset[i] = array[i];
            }
            results.add(getString(subset));
            return results.toArray(new String[0]);
        }
        magicalTriplets(array, 0, subset, 0, results);
        return results.toArray(new String[0]);
    }

    public String[] magicalTripletsEff(int[] array) {
        Arrays.sort(array);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (i == j) {
                    continue;
                }
                for (int k = j; k < array.length; k++) {
                   if (i == k || k == j) {
                       continue;
                   }
                   String str = array[i] + "," + array[j] + "," + array[k];
                   if (!set.contains(str)) {
                       int sum = array[i] + array[j] + array[k];
                       if (sum == 0) {
                           set.add(str);
                       }
                   }
               }
           }
        }
        return set.toArray(new String[0]);
    }

    /**
     * This represents the most efficient way to perform the 3 sum to zero
     * check. We use sorting to help us make this more efficients.
     *
     * We first sort the array. We then loop over the entire array, computing
     * 3 way sums.
     *
     * We do this, by putting a while loop inside of the for loop. The while
     * has one pointer from the left and the other from the right. Take care
     * to avoid duplicates it checks to see if the sum of the three numbers,
     * the number at i, the number at left, and the number at right all sum to zero.
     *
     * if so, we make a string out of the 3 numbers and add them to a set of strings.
     * The important part is that, if the sum of the three numbers is > 0, we move the
     * j pointer to the left. We are essentially moving the right pointer to the left,
     * until we can make our 3 numbers sum to 0.
     *
     * If the sum is < 0, then we move our left pointer to the right, again trying to find
     * 3 numbers, with our i being fixed for one iteration of the outer for loop, we are moving
     * left and right until we find 3 numbers that equal sum.
     *
     * The neat trick is to the fact that the array is sorted to avoid duplicate 3 sum calculations.
     * Once we find a 3 sum that is equal to zero, we check that while left + 1, is still less than
     * right, if left is the same as the number to the right (a[left] == a[left + 1]) then we should move
     * left to the right by 1, essentially skipping the duplicate.
     *
     * The same goes for the right pointer. If the number to the left of right, (while right + 1
     * is still less than left) is the same (a[right] == a[right - 1]), then its a duplicate that
     * we should be able to skip. So we move right to the left by one.
     *
     * After we move left and right to the right and left by one, do it once more, and continue
     * looking for 3 sum-ways to zero.
     *
     * RUNTIME: O(n^2 + n log n)
     * SPACE: O(n^3) for storing the strings
     * @param array
     * @return
     */
    public String[] magicalTripletsEff1(int[] array) {

        if (array == null) {
            return null;
        }

        if (array.length < 3) {
            return null;
        }
        Arrays.sort(array);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && array[i] == array[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = array.length - 1;
            while (left < right) {
                int sum = array[i] + array[left] + array[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    String str = array[i] + "," + array[left] + "," + array[right];
                    set.add(str);
                    while (left + 1 < right && array[left] == array[left+1]) {
                        left += 1;
                    }
                    while (right - 1 > left && array[right] == array[right - 1]) {
                        right -= 1;
                    }
                    left += 1;
                    right -= 1;
                }
            }
        }
        return set.toArray(new String[0]);
    }


    public List<String> generateAllSubsetsOfSizeN(int[] array, int k) {
        Integer[] subset = new Integer[k];
        List<String> result = new ArrayList<>();
        gen(array, subset, 0, 0, result);
        return result;
    }

    private void gen(int[] array, Integer[] subset, int index, int subsetindex, List<String> result) {
        if (subsetindex == subset.length) {
            if (subset[0] != null && subset[1] != null && subset[2] != null ) {
                result.add(getString(subset));
                return;
            }
        }

        if (index == array.length) {
            return;
        }

        // include
        subset[subsetindex] = array[index];
        gen(array, subset, index + 1, subsetindex + 1, result);

        // exclude
        subset[subsetindex] = null;
        gen(array, subset, index + 1, subsetindex, result);
    }

    public List<String> genUsingBoolean(int[] array, int k) {
        boolean[] used = new boolean[array.length];
        List<String> result = new ArrayList<>();
        genUsingBoolean(array, used, 0, 0, k, result);
        return result;
    }

    private void genUsingBoolean(int[] array, boolean[] used, int index, int subsetindex, int k, List<String> result) {

        if (subsetindex == k) {
            for (int i = 0; i < array.length; i++) {
                if (used[i] == true) {
                    result.add(array[i] + "");
                }
            }
            return;
        }

        if (index == array.length) {
            return;
        }

        // include
        used[index] = true;
        genUsingBoolean(array, used, index + 1, subsetindex + 1, k, result);

        // exclude
        used[index] = false;
        genUsingBoolean(array, used, index + 1, subsetindex, k, result);
    }

    private void magicalTriplets(int[] array, int index, Integer[] subset, int subsetindex, Set<String> triplets) {
        if (subsetindex == subset.length) {
            if (sum(subset) == 0) {
                String str = getString(subset);
                if (!triplets.contains(str)) {
                    triplets.add(str);
                }
            }
            return;
        }

        if (index == array.length) {
            return;
        }

        // exclude
        magicalTriplets(array, index + 1, subset, subsetindex, triplets);

        subset[subsetindex] = array[index];

        // include
        magicalTriplets(array, index + 1, subset, subsetindex + 1, triplets);

        subset[subsetindex] = null;
    }

    private int sum(Integer[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                result += array[i].intValue();
            }
        }
        return result;
    }

    private String getString(Integer[] subset) {
        StringBuilder sb = new StringBuilder();
        String after = "";
        for (Integer n: subset) {
            sb.append(after);
            sb.append(n);
            after = ",";
        }
        return sb.toString();
    }

    public String[] nutsAndsBolts(int[] nuts, int[] bolts) {
        List<String> results = new ArrayList<String>();
        for (Integer n: nuts) {
            for (Integer m: bolts) {
                if (n.intValue() == m.intValue()) {
                    results.add(n + " " + m);
                }
            }
        }
        return results.toArray(new String[0]);
    }

    public String[] nutsAndBoltsNoSorting(int[] nuts, int[] bolts) {

        String[] results = new String[nuts.length];
        nutsAndBoltsNoSortingHelper(nuts, bolts, 0, nuts.length - 1);

        for (int i = 0; i < results.length; i++) {
            results[i] = nuts[i] + " " + bolts[i];
         }
        return results;
    }

    private void nutsAndBoltsNoSortingHelper(int[] nuts, int[] bolts, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivotIdx = (end - start) / 2;
        int partitionIdx = sort.hoaresPartition(nuts, start, end, bolts[pivotIdx]);
        sort.hoaresPartition(bolts, start, end, nuts[partitionIdx]);

        nutsAndBoltsNoSortingHelper(nuts, bolts, start, partitionIdx - 1);
        nutsAndBoltsNoSortingHelper(nuts, bolts,  partitionIdx + 1, end);
    }

    private int lomutosPartitionForNutsAndBolts(int[] array, int[] array1, int start, int end, int pivot) {
        swap(array1, end, pivot);
        int pivotValue = array1[end];
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

    public int lomutosPartition(int[] array, int start, int end, int pivot) {
        swap(array, end, pivot);
        int pivotValue = array[end];
        int indexOfItemsSmallerThanPivot = start;
        for (int current = start; current < end; current++) {
            if (array[current] <= pivotValue) {
                swap(array, current, indexOfItemsSmallerThanPivot);
                indexOfItemsSmallerThanPivot++;
            }
        }
        swap(array, indexOfItemsSmallerThanPivot, end);
        return indexOfItemsSmallerThanPivot;
    }

    /**
     * You are given a string array named arr, of size N, containing KEYS and VALUES
     * separated by a space.
     *
     * Your task is to find, for each unique KEY, the number of VALUES with that key
     * and the VALUE with the highest lexicographical order (also called alphabetical
     * order OR dictionary order).
     *
     * Return a string array res, with an entry for each unique key. Each entry should
     * contain key, number of values corresponding to that key and value with the
     * highest lexicographical order in the below format:
     *
     * "<KEY>:<COUNT>,<HIGHEST_LEXICOGRAPHICAL_VALUE>"
     *
     * For input: arr = [“key1 abcd”, “key2 zzz”, “key1 hello”, “key3 world”, "key1 hello"]
     *
     * output: ["key3:1,world", "key2:1,zzz", "key1:3,hello"]
     *
     * This problem is straightforward. We break down the strings and use a map to store
     * the keys, mapped to a PriorityQueue of their values. We are using the priority
     * queue as a MaxHeap. That way when we want to find the largest string that a key
     * maps to, we only need to look at the top of the priority queue.
     *
     * RUNTIME: N LOG N, we run through every string in the input array, and the
     * insertion into the Priority Queue takes log n time. So n * log n. We then
     * have to move through every entry in the map, and build a string, but
     * the building of the string should be constant.
     *
     * SPACE: O(N) because of the map and priority queue
     *
     *
     *
     * @param input String[]
     * @return String[]
     */
    public String[] keysAndValues(String[] input) {
        Map<String, Queue<String>> map = new HashMap<>();
        for (String s: input) {
            String[] valuePair = s.split(" ", 2);
            if (!map.containsKey(valuePair[0])) {
                Queue<String> queue = new PriorityQueue<>(input.length, Collections.reverseOrder());
                queue.add(valuePair[1]);
                map.put(valuePair[0], queue);
            } else {
                Queue<String> queue = map.get(valuePair[0]);
                queue.add(valuePair[1]);
            }
        }

        String[] results = new String[map.size()];
        int i = 0;
        for (Map.Entry<String, Queue<String>> entry: map.entrySet()) {
            String key = entry.getKey();
            Queue<String> queue = entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append(":");
            sb.append(String.valueOf(queue.size()));
            sb.append(",");
            sb.append(queue.peek());
            results[i] = sb.toString();
            i++;
        }
        return results;
    }

    /**
     * Given two arrays:
     *
     * 1) arr1 of size n, which contains n positive integers sorted in increasing order.
     *
     * 2) arr2 of size (2*n) (twice the size of first), which contains n positive integers
     * sorted in increasing order in its first half. Second half of this array arr2 is empty.
     * (Empty elements are marked by 0).
     *
     * Write a function that takes these two arrays, and merges the first one into second one,
     * resulting in an increasingly sorted array of (2*n) positive integers.
     *
     * If n = 3, arr1 = [1, 3, 5] and arr2 = [2, 4, 6, 0, 0, 0], then output should be:
     *
     * arr2 = [1, 2, 3, 4, 5, 6]
     *
     * NO EXTRA SPACE
     *
     * I got confused with the no extra space requirment and made this problem much more
     * complicated then it had to be, by essentially using the arr1 and storage
     * and repeatedly merging portions of the array, then copying them back to
     * arr2.
     *
     * This can be done with a relatively straight forward merge. We don't actually need
     * extra space because once we have done a comparison, then the winning number
     * will be placed at its final location and our pointer will be moved passed,
     * essentially meaning we can do what we want with that space.
     *
     * RUNTIME: O(N)
     *
     * SPACE: O(1)
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public int[] merger_first_into_second(int[] arr1, int[] arr2) {
        int endArr1 = arr1.length - 1;
        int endArr2 = arr1.length - 1;
        int finalEnd = arr2.length - 1;
        while (endArr1 >= 0 && endArr2 >= 0) {
            if (arr2[endArr2] >= arr1[endArr1]) {
                arr2[finalEnd] = arr2[endArr2];
                endArr2--;
                finalEnd--;
            } else {
                arr2[finalEnd] = arr1[endArr1];
                endArr1--;
                finalEnd--;
            }
        }

        while (endArr1 >= 0) {
            arr2[finalEnd] = arr1[endArr1];
            endArr1--;
            finalEnd--;
        }

        while (endArr2 >= 0) {
            arr2[finalEnd] = arr2[endArr2];
            endArr2--;
            finalEnd--;
        }
        return arr2;
    }

    public void dutch_flag_sort(char[] balls) {
        int start = 0; // red pointer
        int mid = 0; // green pointer
        int end = balls.length - 1; // blue pointer

        char pivot = 'G';
        while (mid <= end) {
            if (compare(balls[mid]) < compare(pivot)) {
                swap(balls, start, mid);
                start++;
                mid++;
            } else if (compare(balls[mid]) > compare(pivot)) {
                swap(balls, mid, end);
                end--;
            } else {
                mid++;
            }
        }
    }

    private void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private int compare(char ch) {
        if (ch == 'R') {
            return 0;
        } else if (ch == 'G') {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * findKthSmallest - This problem can be solved a number of ways.
     * Probably the most intuitive although not the most optimal, is to use a heap.
     * If using a heap the trick is to remember the inverse relationship between
     * finding the kth smallest element and the use of a max heap. Or conversely
     * finding the kth largest element and using a min heap.
     *
     * If we wanted to find the 2nd smallest element, we should use a max heap.
     * Look at the following example.
     *
     * {3, 5, 8, 1, 4} the 2nd smallest element is 3. Lets have a max heap, that
     * can only hold 2 elements. Once it has more than two elements, considering its a max heap,
     * we should eject the largest element from the heap. Starting from the left in the array,
     * lets populate the array.
     *
     * Start at index = 0, so we have heap[3], now go to index 1, heap[5, 3], now to go index = 2. add 8 to the heap,
     * heap[8, 5, 3], well, we have more than k elements, so lets remove the largest from the
     * max heap => heap[5, 3], Now lets move to index = 3. index 3 heap[8, 3, 1], we have more than k elements,
     * so bump out 8. heap[3, 1]. Now index = 4, and add 4 to the heap, [4, 3, 1]. We have more than k elements
     * so remove the max, which is 4.
     *
     * The remaining element on the top of the max heap, 3, is the 2nd smallest. We wanted the
     * 2nd smallest element in this list, so we used a max heap. Using a max heap, allowed us
     * access to the largest values in constant time. The max heap, essentially maintained
     * a property of having the nth smallest (where n is the number of elements in the input)
     * at the top, we don't want the 5th smallest, which is in fact larger than the 2nd smallest
     * so what data structure could allow us to remove larger elements most quickly...a max heap,
     * because the largest elements are at the top of the heap.
     *
     * So we keep ejecting the largest elements, until we only have k elements in our heap,
     * and the element on top will be the kth smallest.
     *
     * The run time for this is n * log k, the log k being for the insertions and deletions
     * from the heap.
     *
     * The more optimal way to do this problem is by using partitioning from Quicksort.
     * we must remember that when we partition an array, we are selection a pivot point,
     * and then putting everything lesser than the pivot to the left, and everything greater
     * than the pivot to the right. By doing this, we are essentially putting the value, at
     * index = pivot, in its final sorted location.
     *
     * So if we want the element that is kth smallest, we should simply keep running the
     * partition algorithm until, until the index returned by partition is equal to k.
     *
     * For finding the kth largest element is the same process, only we would be looking
     * for the partition index that is equal to n - k.
     *
     * Remember that arrays are 0 based, so looking for the kth smallest element is equivalent
     * to looking for kth - 1 index.
     *
     * We run the standard partition algorithm, checking for the partition index == k - 1.
     *
     * If our partition index is greater than k, then we should recurse on the left half
     * to find the kth smallest element.
     *
     * If our partition index is less than k, then we should recurse on the right half. When
     * recursing to the right, we must make sure to understand that, if looking for the kth
     * smallest element in an array of [0......n], then looking at the right half, would be
     * looking for the k - partitionIndex + start - 1 element or
     * [0....left.k....end], as an example. if k == 6, and our partition index was = 3, and our array
     * would be broken up like so [3, 5, 2, 6, 8, 1, 9] =>
     * left half: [3, 5, 2], partitionIndex[6], right half: [8, 1, 9], if we recurse
     * to the right, we are actually looking for the element at the "2nd" index in the right half,
     * which is 6 - 3 + 0 - 1 = 2, that means when recursing to the right we look for,
     * k - partitionIndex + start - 1 (the - 1 for the zero based property).
     *
     * RUNTIME: The best running time of this algorithm is O(n), in case we find the kth element
     * on the first try. The worst case is still O(n^2).
     *
     * @param array int[]
     * @param k int
     * @return int
     */
    public int findKthSmallest(int[] array, int k) {
        return findKthSmallestHelper(array, 0, array.length - 1, k);
    }

    private int findKthSmallestHelper(int[] array, int start, int end, int k) {
        if (k > 0 && k <= end - start + 1) {
            int pivot = start + (end - start) / 2;
            int partitionedIndex = sort.hoaresPartition(array, start, end, array[pivot]);

            if (partitionedIndex == k + start - 1) {
                return array[partitionedIndex];
            }

            if (partitionedIndex > k + start - 1) {
                return findKthSmallestHelper(array, start, partitionedIndex - 1, k);
            }
            // Using this example [3, 5, 2, 6, 8, 1, 9], find the 5th smallest
            // if we are looking at the right half of the array, we don't want to find the element of
            // rank k in the right half. if the rank we were looking for was k = 5
            // then looking for an element of rank = 5 in the right half, wouldn't make sense. We've already excluded
            // the left half, so if the left half ended at [6], and the rank == 5, then we would be looking
            // for the 5th element minus the index of [6], in the right half. 6 - 5, is the magnitude of difference from
            // the start however, and rank is usually one based, so that's why its k - partitionedIndex + start - 1
            return findKthSmallestHelper(array, partitionedIndex + 1, end, k - partitionedIndex + start - 1);
        }
        return 0;
    }

    /**
     * findKthLargest - find the kth largest element of an array.
     *
     * This implementation is making use of a heap. Specifically a routine to heapfify an array.
     *
     * Everytime we "heapify" an array (for a max heap) we're finding the largest value in the array.
     * In this case, we just want to heapify kth times.
     *
     * We first build a heap out of the array, if k == 1, i.e. find the first largest element, then that element,
     * after first heapifying will be at the root index, index == 0.
     *
     * If kth is not equal to 1, then we simply need to keep heapifying, and stop, when we have heapified kth times.
     *
     * While repeatedly heapifying, starting with the end of the array, we only need to heapify kth times. In the
     * context of the loop that means, when n - i == k. Lets assume n == 10. If we are are looking for the
     * kth largest element, and k == 3, then we stop heapifying when 10 - i == 3, and return array[i], which would be
     * the kth largest element.
     *
     * If we are trying to find the kth largest element, start with an example, say the 3rd largest element. Well,
     * in an array like so [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], that would be 8, or to think of it another way, because
     * arrays are 0 based, its the n - kth index, or 10 - 3, the 7th index, which is 8 in this case.
     *
     * @param array - int array
     * @param kth - int the kth largest element
     * @return in the kth largest
     */
    public int findKthLargest(int[] array, int kth) {
        int n = array.length;
        // first lets build a heap
        for (int i = n/2; i >= 0; i--) {
            sort.maxHeapify(array, i, n - 1);
        }

         if (kth == 1) {
             return array[0];
         }

        for (int i = n - 1; i >= 0; i--) {
            swap(array, 0, i);
            sort.maxHeapify(array, 0, i);
            if (n - i == kth) {
                return array[i];
            }
        }
        return -1;
    }


    /**
     * Is one array a subset of another.
     *
     * This problem depends on both arrays being already sorted.
     * If they are then we essentially run the merge portion of merge sort.
     * If we manage to increment our pointer of the shorter array, to the end,
     * then that means we found it in the larger array.
     *
     * If it gets to the end, then we return true.
     * @param array1 int[]
     * @param array2 int[]
     * @return boolean
     */
    public boolean isSubsetOfAnother(int[] array1, int[] array2) {
        if (array1 == null) {
            return true;
        }

        if (array2 == null) {
            return true;
        }

        if (array1.length >= array2.length) {
            return mergeSubsetCheck(array1, array2);
        } else {
            return mergeSubsetCheck(array2, array1);
        }
    }

    public boolean mergeSubsetCheck(int[] longer, int[] shorter) {
        int shorterBeg = 0;
        int shorterEnd = shorter.length - 1;
        int longerBeg = 0;
        int longerEnd = longer.length - 1;

        while (shorterBeg <= shorterEnd && longerBeg <= longerEnd) {
            if (shorter[shorterBeg] == longer[longerBeg]) {
                shorterBeg++;
                longerBeg++;
            } else {
                longerBeg++;
            }
        }
        return shorterBeg == shorter.length;
    }

    /**
     * Your company built an in-house calendar tool called HiCal.
     * You want to add a feature to see the times in a day when everyone is available. To do this, you’ll need to
     * know when any team is having a meeting. In HiCal, a meeting is stored as a tuple ↴ of integers
     * (start_time, end_time). These integers represent the number of 30-minute blocks past 9:00am.
     *
     * For Example:
     *
     * (2, 3)  # Meeting from 10:00 – 10:30 am
     * (6, 9)  # Meeting from 12:00 – 1:30 pm
     *
     * Write a function merge_ranges() that takes a list of multiple meeting time ranges and returns a list
     * of condensed ranges.
     *
     * For example, given:
     *
     *  [(0, 1), (3, 5), (4, 8), (10, 12), (9, 10)]
     *
     *  your function would return:
     *
     * [(0, 1), (3, 8), (9, 12)]
     *  Do not assume the meetings are in order. The meeting times are coming from multiple teams.
     */
    public List<Meeting> mergeRanges(List<Meeting> meetings) {

        Collections.sort(meetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
               return m1.getStartTime() - m2.getStartTime();
            }
        });

        List<Meeting> results = new ArrayList<Meeting>();

        int length = meetings.size();
        int endingSpot = 0;
        int currPointer = 0;

        for (int i = 0; i < length - 1; i++) {
            Meeting meeting = meetings.get(currPointer);
            Meeting nextMeeting = meetings.get(i + 1);
            int meetingEndTime = meeting.getEndTime();
            if (isInBetween(meetingEndTime, nextMeeting)) {
                Meeting mergedMeeting = new Meeting(meeting.getStartTime(), nextMeeting.getEndTime());
                results.add(mergedMeeting);
                i++;
                currPointer = i + 1;
            } else if (meetingEndTime >= nextMeeting.getEndTime()) {
                //System.out.println("Current end time greater than next end time("+meeting+")");
            } else {
                //System.out.println("Adding current meeting(" + meeting +")");
                results.add(meeting);
                currPointer++;
            }
            endingSpot = i;
        }

        // Attempt to merge the last meeting in original meetings list with end of results
        // System.out.println("END: " + endingSpot);
        // System.out.println("RESULTS: " + results);

        if (endingSpot < length - 1) {
            // If we previously have results
            if (!results.isEmpty()) {
                Meeting meeting = results.get(results.size() - 1);
                Meeting lastMeeting = meetings.get(length - 1);
                if (isInBetween(meeting.getEndTime(), lastMeeting)) {
                    Meeting mergedMeeting = new Meeting(meeting.getStartTime(), lastMeeting.getEndTime());
                    results.set(results.size() - 1, mergedMeeting);
                } else {
                    results.add(lastMeeting);
                }
            } else {
                results.add(meetings.get(endingSpot));
            }
        }
        return results;
    }


    public boolean isInBetween(int meetingEndTime, Meeting nextMeeting) {
        return meetingEndTime >= nextMeeting.getStartTime() && meetingEndTime <= nextMeeting.getEndTime();
    }

    public List<Meeting> officialMergeRangesSolution(List<Meeting> meetings) {

        // make a copy so we don't destroy the input
        List<Meeting> sortedMeetings = new ArrayList<>();
        for (Meeting meeting: meetings) {
            Meeting meetingCopy = new Meeting(meeting.getStartTime(), meeting.getEndTime());
            sortedMeetings.add(meetingCopy);
        }

        // sort by start time
        Collections.sort(sortedMeetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                return m1.getStartTime() - m2.getStartTime();
            }
        });

        // initialize mergedMeetings with the earliest meeting
        List<Meeting> mergedMeetings = new ArrayList<>();
        mergedMeetings.add(sortedMeetings.get(0));

        for (Meeting currentMeeting : sortedMeetings) {

            Meeting lastMergedMeeting = mergedMeetings.get(mergedMeetings.size() - 1);

            // if the current meeting overlaps with the last merged meeting, use the
            // later end time of the two
            if (currentMeeting.getStartTime() <= lastMergedMeeting.getEndTime()) {
                lastMergedMeeting.setEndTime(Math.max(lastMergedMeeting.getEndTime(), currentMeeting.getEndTime()));

                // add the current meeting since it doesn't overlap
            } else {
                mergedMeetings.add(currentMeeting);
            }
        }

        return mergedMeetings;
    }
}
