package main.java.com.codepath.stringandarrays;

import main.java.com.codepath.stringandarrays.Sorting;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");

        int[] array = {6, 5, 4, 2};
        Sorting sorting = new Sorting();

        sorting.recursiveMergeSort(array);

        sorting.print(array);

    }
}
