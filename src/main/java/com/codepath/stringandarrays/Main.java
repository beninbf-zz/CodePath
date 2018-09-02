package main.java.com.codepath.stringandarrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");

        int[] array1 = {6, 5, 4, 2};
        Sorting sorting = new Sorting();
        sorting.recursiveMergeSort(array1);
        sorting.print(array1);

        /* MUST FIX */
        int[] array2 = {9, 1, 8, 5, 2, 17, 3, 6, 5};
        sorting.iterativeMergeSort(array2);
        sorting.print(array2);
    }
}
