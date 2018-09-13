package main.java.com.codepath.stringandarrays;

public class Main {
    public static void main(String[] args) {
        Sorting sorting = new Sorting();

//        System.out.println("Hello world");
//
//        int[] array1 = {6, 5, 4, 2};
//        sorting.recursiveMergeSort(array1);
//        sorting.print(array1);

        /* MUST FIX */
        //int[] array2 = {9, 1, 8, 5, 2, 17, 3, 6, 5};
        int[] array2 = {8, 5, 22, 5, 13};
        int[] array3 = {9, 1, 8, 5, 12, 2};
        int[] array4 = {9, 1, 8, 5, 2, 17, 3, 6, 5};

        int[] array5 = {8, 5, 2, 5, 6};




        sorting.iterativeMergeSort3(array4);
        sorting.print(array4);

        System.out.println(sorting.gcd(30, 12));
        System.out.println(sorting.gcdSubtraction(30, 12));

    }
}
