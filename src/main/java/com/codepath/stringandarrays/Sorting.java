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

    public void print(int[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]+"]");
        System.out.println();
    }

    public int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }

        int value = a % b;
        return gcd(b, value);
    }

    public int gcdSubtraction(int a, int b) {
        if (a == b) {
            return a;
        }

        if (a > b) {
            return gcdSubtraction( a - b, b);
        } else {
            return gcdSubtraction(a, b - a);
        }
    }
}
