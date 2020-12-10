package test.java.com.codepath.sorting;

import main.java.com.codepath.objects.Meeting;
import main.java.com.codepath.sorting.Sort;
import main.java.com.codepath.sorting.SortingProblems;
import main.java.com.codepath.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SortingProblemsTest {

    private SortingProblems testObj;
    @Before
    public void setUp() {
        this.testObj = new SortingProblems();
    }

    @Test
    public void hoaresQuickSort() {
        int[] array = {8, 7, 6, 5, 4};
        int[] result = {4, 5, 6, 7, 8};
        Sort sorter = new Sort();
        sorter.hoaresQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }


    @Test
    public void hoaresQuickSort1() {
        int[] array = {8, 7, 6, 5, 8};
        int[] result = {5, 6, 7, 8, 8};
        Sort sorter = new Sort();
        sorter.hoaresQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void hoaresQuickSort2() {
        int[] array = {19, 27, 4, 6, 30, 1, 9};
        int[] result = {1, 4, 6, 9, 19, 27, 30};
        Sort sorter = new Sort();
        sorter.hoaresQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void hoaresQuickSort3() {
        int[] array = {9, 8, 7, 6, 5, 4 ,3 ,2, 1};
        int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Sort sorter = new Sort();
        sorter.hoaresQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void myQuickSort() {
        int[] array = {19, 27, 4, 6, 30, 1, 9};
        int[] result = {1, 4, 6, 9, 19, 27, 30};
        Sort sorter = new Sort();
        Util.print(array);
        sorter.myQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void myQuickSort1() {
        int[] array = {9, 8, 7, 6, 5, 4 ,3 ,2, 1};
        int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Sort sorter = new Sort();
        Util.print(array);
        sorter.myQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void myQuickSort2() {
        int[] array = {9, 8, 8, 8, 5, 4, 44, 44, 6};
        int[] result = {4, 5, 6, 8, 8, 8, 9, 44, 44};
        Sort sorter = new Sort();
        Util.print(array);
        sorter.myQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void lomutosQuickSort() {
        int[] array = {19, 27, 4, 6, 30, 1, 9};
        int[] result = {1, 4, 6, 9, 19, 27, 30};
        Sort sorter = new Sort();
        Util.print(array);
        sorter.lomutosQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void lomutosQuickSort1() {
        int[] array = {9, 8, 7, 6, 5, 4 ,3 ,2, 1};
        int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Sort sorter = new Sort();
        Util.print(array);
        sorter.lomutosQuickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void mergeSort() {
        int[] array = {8, 7, 6, 5, 4};
        int[] result = {4, 5, 6, 7, 8};
        Sort sorter = new Sort();
        sorter.mergeSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void heapSortMax() {
        int[] array = {4, 10, 3, 5 ,1};
        int[] result = {1, 3, 4, 5, 10};
        Sort sorter = new Sort();
        sorter.heapSortMax(array);
        System.out.println("array");
        Util.print(array);
        testEquality(array, result);

//        int[] array1 = {8, 7, 6, 5, 4};
//        int[] result1 = {4, 5, 6, 7, 8};
//        sorter.heapSortMax(array1);
//        Util.print(array1);
//        testEquality(array1, result1);
    }

    @Test
    public void heapSortMax1() {
        int[] array = {19, 27, 4, 6, 30, 1, 9};
        int[] result = {1, 4, 6, 9, 19, 27, 30};
        Sort sorter = new Sort();
        sorter.heapSortMax(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void heapSortMin() {
        int[] array = {4, 10, 3, 5 ,1};
        int[] result = {1, 3, 4, 5, 10};
        Sort sorter = new Sort();
        sorter.heapSortMin(array);
        Util.print(array);
        testEquality(array, result);

        int[] array1 = {8, 7, 6, 5, 4};
        int[] result1 = {4, 5, 6, 7, 8};
        sorter.heapSortMin(array1);
        Util.print(array1);
        testEquality(array1, result1);
    }

    @Test
    public void testMergeArraysAsc() {
        int[] array = {1, 4, 9};
        int[] array1 = {3, 7, 8};
        int[] array2 = {2, 8, 12};

        int[][] input = {array, array1, array2};

        int[] answer = {1, 2, 3, 4, 7, 8, 8, 9, 12};

        int[] result = testObj.mergeKSortedArrays(input);
        Util.print(result);
        testEquality(result, answer);
    }

    @Test
    public void testMergeArraysDesc() {
        int[] array = {9, 4, 1};
        int[] array1 = {8, 7, 3};
        int[] array2 = {12, 8, 2};

        int[][] input = {array, array1, array2};

        int[] answer = {12, 9, 8, 8, 7, 4, 3, 2, 1};

        int[] result = testObj.mergeKSortedArrays(input);
        Util.print(result);
        testEquality(result, answer);
    }

    @Test
    public void testMergeArraysAsc1() {
        int[][] input = {
            {3, 3, 12, 20, 22, 25, 34},
            {4, 10, 12, 20, 28, 32, 36},
            {5, 6, 10, 19, 22, 28, 34},
            {8, 17, 17, 25, 34, 34, 42},
            {6, 7, 14, 17, 18, 25, 26},
            {8, 10, 15, 19, 28, 32, 40},
            {5, 13, 17, 19, 25, 26, 27},
            {1, 9, 12, 20, 26, 28, 30},
            {0, 8, 13, 19, 21, 25, 28},
            {3, 12, 18, 21, 27, 32, 32}
        };

        int[] result = testObj.mergeKSortedArrays(input);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue (result[i] <= result[i+1]);
        }
    }

    @Test
    public void testTopK1() {
        int[] input = {1, 5, 4, 4, 2, 2};

        int[] result = testObj.topK(input, 2);
        int[] answer = {5, 4};

        Set<Integer> setResult = new HashSet<>();
        for (Integer n: result) {
            setResult.add(n);
        }
        Util.print(result);
        for (Integer n: answer) {
            if (setResult.contains(n)) {
                setResult.remove(n);
            }
        }
        assertTrue("set should be empty", setResult.isEmpty());
    }

    @Test
    public void testTopK2() {
      int[] input =  {4, 8, 9, 6, 6, 2, 10, 2, 8, 1, 2};
      int[] result = testObj.topK(input, 9);
      int[] answer = {1, 2, 4, 6, 8, 9, 10};
      Util.print(result);
        Set<Integer> setResult = new HashSet<>();
        for (Integer n: result) {
            setResult.add(n);
        }
        Util.print(result);
        for (Integer n: answer) {
            if (setResult.contains(n)) {
                setResult.remove(n);
            }
        }
        assertTrue("set should be empty", setResult.isEmpty());
    }

    @Test
    public void testRearrage() {
        int[] array = {1, 2, 3, 4, 6};

        testObj.rearrange(array);
        int[] answer = {6, 2, 4, 3, 1};
        testEquality(array, answer);
    }

    @Test
    public void testRearrange1() {
        int[] array = {3, 5};
        int[] answer = {3, 5};

        testObj.rearrange(array);
        testEquality(array, answer);

        int[] array1 = {1, 3, 5};
        int[] answer1 = {1, 3, 5};

        testObj.rearrange(array1);
        testEquality(array1, answer1);

        int[] array2 = {2, 4, 6};
        int[] answer2 = {2, 4, 6};

        testObj.rearrange(array2);
        testEquality(array2, answer2);
    }

    @Test
    public void getMagicalTriplets() {
        int[] array = {10, 3, -4, 1, -6, 9};
        String[] result = testObj.magicalTriplets(array);
        Util.print(result);

        String[] arr = testObj.magicalTripletsEff(array);
        String[] arr1 = testObj.magicalTripletsEff1(array);

        Util.print(arr);
        Util.print(arr1);


        int[] array1 = {0, 0, 0};
        String[] arr2 = testObj.magicalTripletsEff1(array1);
        Util.print(arr2);
    }

    @Test
    public void getMagicalTriplets1() {
        int[] array = {12, 34, -46};
        String[] result = testObj.magicalTriplets(array);

        Util.print(result);

        List<String> items = testObj.generateAllSubsetsOfSizeN(array, 3);
        System.out.println(items);
    }

    @Test
    public void getMagicalTriplets2() {
        int[] array = {-2, 2, 0, -1 , 1};
        String[] result = testObj.magicalTriplets(array);
        Util.print(result);
        List<String> items1 = testObj.generateAllSubsetsOfSizeN(array, 3);
        System.out.println(items1);
    }

    @Test
    public void testNutsAndBolts() {
        int[] nuts = {4, 32, 5, 7, 78, 21};
        int[] bolts = {32, 7, 5, 4, 21, 78};

        String[] result = testObj.nutsAndBoltsNoSorting(nuts, bolts);
        Util.print(result);
    }

    @Test
    public void testNutsAndBolts1() {
        int[] nuts = {38, 32, 35, 9, 19, 21};
        int[] bolts = {32, 35, 21, 38, 19, 9};

        String[] result = testObj.nutsAndBoltsNoSorting(nuts, bolts);
        Util.print(result);
    }

    @Test
    public void testPartitionAlgorithms() {
        Sort sort = new Sort();

        int[] nuts =  {38, 32, 35, 9, 19, 21};
        int[] nuts2 = {38, 32, 35, 9, 19, 21};

        sort.lomutosPartition(nuts, 0, nuts.length - 1);
        sort.hoaresPartition(nuts2, 0, nuts2.length - 1, 21);

        Util.print(nuts);
        Util.print(nuts2);
    }

    @Test
    public void testKeysAndValues() {
        String[] input = {"key1 abcd", "key2 zzz", "key1 hello", "key3 world", "key1 hello"};
        String[] answer = this.testObj.keysAndValues(input);
        Util.print(answer);
    }

    @Test
    public void testMerge() {
        int[] arr1 = {1, 3, 5};
        int[] arr2 = {2, 4, 6, 0, 0, 0};

        testObj.merger_first_into_second(arr1, arr2);
        Util.print(arr2);
    }

    @Test
    public void testMerge1() {
        int[] arr1 = {1};
        int[] arr2 = {2, 0};
        testObj.merger_first_into_second(arr1, arr2);
    }

    @Test
    public void testDutchFlag() {
        char[] balls =  {'G', 'B', 'G', 'G', 'R', 'B', 'R', 'G'};
        testObj.dutch_flag_sort(balls);
        Util.print(balls);
    }

    @Test
    public void testDutchFlag1() {
        char[] balls1 = {'G', 'R'};
        testObj.dutch_flag_sort(balls1);
        Util.print(balls1);
    }

    @Test
    public void testDutchFlag2() {
        char[] balls2 = {'B', 'R', 'G'}; // R, G, B
        testObj.dutch_flag_sort(balls2);
        Util.print(balls2);
    }

    @Test
    public void testFindKthElement() {

//        int[] array = {3, 5, 8, 9, 1, 34};
//        //assertEquals("Should be 5", 5, testObj.findKthSmallest(array, 3));
//        System.out.println("pivotIndex: " + 2 + " value: " + array[2]);
//        int pivot = testObj.hoaresPlusOnePartition(array, 0, array.length - 1, array[2]);
//        System.out.println(pivot);
//        Util.print(array);
//
//        int[] array1 = {3, 5, 8, 9, 1, 34};
//        int pivot1 = testObj.hoaresStandardPartition(array1, 0, array1.length - 1, array1[2]);
//        System.out.println(pivot1);
//        Util.print(array1);
//
        int[] array2 = {3, 5, 8, 9, 1, 34};
        assertEquals("2nd smallest element should be 5", 5, testObj.findKthSmallest(array2, 3));

        assertEquals("2nd smallest element should be 5", 5, testObj.findKthSmallest(array2, 3));


        int[] array3 = {3, 5, 8, 9, 1, 34};

        //assertEquals("2nd smallest element should be 5", 5, testObj.findKthElementByQuickSelect(array3, 3));


    }

    @Test
    public void testKthSmallestElement1() {
        int[] array2 = {3, 5, 8, 9, 1, 34};
        assertEquals("2nd smallest element should be 1", 1, testObj.findKthSmallest(array2, 1));
    }

    @Test
    public void testKthSmallestElement2() {
        int[] array2 = {3, 5, 8, 9, 1, 34};
        assertEquals("1nd smallest element should be 3", 3, testObj.findKthSmallest(array2, 2));
    }

    @Test
    public void testKthSmallestElement3() {
        int[] array2 = {3, 5, 8, 9, 1, 34};
        assertEquals("2nd smallest element should be 5", 5, testObj.findKthSmallest(array2, 3));
    }

    @Test
    public void testKthSmallestElement4() {
        int[] array2 = {3, 5, 8, 9, 1, 34};
        assertEquals("3rd smallest element should be 8", 8, testObj.findKthSmallest(array2, 4));
    }

    @Test
    public void testKthSmallestElement5() {
        int[] array2 = {3, 5, 8, 9, 1, 34};
        assertEquals("5th smallest element should be 9", 9, testObj.findKthSmallest(array2, 5));
    }

    @Test
    public void testKthSmallestElement6() {
        int[] array2 = {3, 5, 8, 9, 1, 34};
        assertEquals("6nd smallest element should be 34", 34, testObj.findKthSmallest(array2, 6));
    }

    @Test
    public void testIsSubset() {

        int[] array1 = {1, 2, 3, 4};
        int[] array2 = {-2, -1, 0, 1, 2, 3, 4, 5, 6};

        assertTrue("should be true", testObj.isSubsetOfAnother(array1, array2));
    }

    @Test
    public void testIsSubset1() {

        int[] array1 = {1, 2, 3, 4};
        int[] array2 = {-2, -1, 0, 100, 104, 678};

        assertFalse("should be true", testObj.isSubsetOfAnother(array1, array2));
    }

    @Test
    public void testIsSubset2() {

        int[] array1 = {0, 2, 3, 4};
        int[] array2 = {-2, -1, 0, 1, 3, 4};

        assertFalse("should be false", testObj.isSubsetOfAnother(array1, array2));
    }

    private void testEquality(int[] input, int[] answer) {
        if (input == null || answer == null) {
            assertTrue("Invalid input", false);
        }

        if (input.length != answer.length) {
            assertTrue("Arrays are of different lengths", false);
        }

        for (int i = 0; i < answer.length; i++) {
            assertEquals("values should be equal", answer[i], input[i]);
        }
    }

    // tests
    @Test
    public void meetingsBasic() {
        final List<Meeting> meetings = Arrays.asList(new Meeting(0, 1), new Meeting(3, 5), new Meeting(4, 8),
                new Meeting(10, 12), new Meeting(9, 10));
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(new Meeting(0, 1), new Meeting(3, 8), new Meeting(9, 12));
        assertEquals(expected, actual);
    }

    @Test
    public void meetingsOverlapTest() {
        final List<Meeting> meetings = Arrays.asList(new Meeting(1, 3), new Meeting(2, 4));
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(new Meeting(1, 4));
        assertEquals(expected, actual);
    }

    @Test
    public void  meetingsTouchTest() {
        final List<Meeting> meetings = Arrays.asList(new Meeting(5, 6), new Meeting(6, 8));
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(new Meeting(5, 8));
        assertEquals(expected, actual);
    }

    @Test
    public void meetingContainsOtherMeetingTest() {
        final List<Meeting> meetings = Arrays.asList(new Meeting(1, 8), new Meeting(2, 5));
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(new Meeting(1, 8));
        assertEquals(expected, actual);
    }

    @Test
    public void meetingsStaySeparateTest() {
        final List<Meeting> meetings = Arrays.asList(new Meeting(1, 3), new Meeting(4, 8));
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(
                new Meeting(1, 3), new Meeting(4, 8)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void multipleMergedMeetingsTest() {
        final List<Meeting> meetings = Arrays.asList(
                new Meeting(1, 4), new Meeting(2, 5), new Meeting (5, 8));
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(new Meeting(1, 8));
        assertEquals(expected, actual);
    }

    @Test
    public void meetingsNotSortedTest() {
        final List<Meeting> meetings = Arrays.asList(
                new Meeting(5, 8), new Meeting(1, 4), new Meeting(6, 8));
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(
                new Meeting(1, 4), new Meeting(5, 8)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void oneLongMeetingContainsSmallerMeetingsTest() {
        final List<Meeting> meetings = Arrays.asList(
                new Meeting(1, 10), new Meeting(2, 5), new Meeting(6, 8),
                new Meeting(9, 10), new Meeting(10, 12)
        );
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(
                new Meeting(1, 12)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void sampleInputTest() {
        final List<Meeting> meetings = Arrays.asList(
                new Meeting(0, 1), new Meeting(3, 5), new Meeting(4, 8),
                new Meeting(10, 12), new Meeting(9, 10)
        );
        final List<Meeting> actual = testObj.mergeRanges(meetings);
        final List<Meeting> expected = Arrays.asList(
                new Meeting(0, 1), new Meeting(3, 8), new Meeting(9, 12)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findKthLargest() {
        int[] array = {19, 27, 4, 6, 30, 1, 9};
        assertEquals("30 should be the 1st largest", 30, testObj.findKthLargest(array, 1));
        assertEquals("27 should be the 1st largest", 27, testObj.findKthLargest(array, 2));
        assertEquals("19 should be the 1st largest", 19, testObj.findKthLargest(array, 3));
        assertEquals("9 should be the 1st largest", 9, testObj.findKthLargest(array, 4));
        assertEquals("6 should be the 1st largest", 6, testObj.findKthLargest(array, 5));
        assertEquals("4 should be the 1st largest", 4, testObj.findKthLargest(array, 6));
        assertEquals("1 should be the 1st largest", 1, testObj.findKthLargest(array, 7));
    }
}
