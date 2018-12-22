package main.java.com.codepath.recursion;

import main.java.com.codepath.util.Util;

import java.util.HashSet;
import java.util.Set;

public class Permutations {

    // Recursive function to generate all permutations of a String
    public void permutationsInt(char[] ch, int currentIndex)
    {
        if (currentIndex == ch.length - 1) {
            System.out.println(String.valueOf(ch));
        }

        for (int i = currentIndex; i < ch.length; i++)
        {
            swap(ch, currentIndex, i);
            permutationsInt(ch, currentIndex + 1);
            swap(ch, currentIndex, i);
        }
    }

    public void permutationsMyWay(String word) {
        int[] count = new int[1];
        Set<String> perms = new HashSet<>();
        permutationsMyWayHelper(word, "", count, perms);
        System.out.println(count[0]);
        System.out.println(perms + " of size " + perms.size());
    }

    private void permutationsMyWayHelper(String available, String chosen, int[] count, Set<String> perms) {
        if (available.equals("")) {
            count[0]++;
            perms.add(chosen);
            System.out.println(chosen);
            return;
        } else {
            int length = available.length();
            for (int i = 0; i < length; i++) {

                // choose
                StringBuilder availableBuilder = new StringBuilder(available);
                Character choice = available.charAt(i);
                String restOfAvailable = availableBuilder.deleteCharAt(i).toString();

                StringBuilder chosenBuilder = new StringBuilder(chosen);
                chosenBuilder.append(choice);
                String chosenStuff = chosenBuilder.toString();

                // explore
                permutationsMyWayHelper(restOfAvailable, chosenStuff, count, perms);

                // implicitly unchoosen, because then the recursion returns right here,
                // AND at the top of each loop we are reinstating the string builders, effectively
                // un choosing our prior choice. When the counter i, moves forward, it will
                // chose the next character
            }
        }
    }

    public void permutations(String word) {
        int[] count = new int[1];
        Set<String> perms = new HashSet<>();
        permutations(word.toCharArray(), 0, count, perms);
        //System.out.println(count[0]);
        System.out.println(perms + " of size " + perms.size());
    }

    private void permutations(char[] word, int i, int[] count, Set<String> perms) {
        if (i == word.length) {
           // count[0]++;
            //System.out.print("Ans:");
            perms.add(String.valueOf(word));
            print(word);
            return;
        } else {
            for (int k = i; k < word.length; k++) {

                // choose
                swap(word, i, k);

                // explore
                permutations(word, i + 1, count, perms);

                //unchoose
                System.out.print("word to unchoose: ");
                print(word);
                swap(word, i, k);
                System.out.print("unchoosen: ");
                print(word);
            }
        }
    }

    public void swap(char[] word, int i, int j) {
        char temp = word[i];
        word[i] = word[j];
        word[j] =  temp;
    }

    public void print(char[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]+"]");
        System.out.println();
    }

    /**
     * permutationAlternateEvenOddNumber - The problem statement is the following.
     * Given an array of size n, where n is even, and half of the numbers are odd
     * and the other have is even, print all of the permutations of numbers that alternate
     * between even and odd, that start with an odd number.
     *
     * This problem is an iteration of the permutations problem. What is required is to remember
     * if the calling a[j] is even or odd. We do this by passing a boolean. It isn't enough to
     * simply not print permutations for the array, when a[j] is even. If we did that for the array
     * {1, 2, 3, 4} we would miss out on some of the valid permutations that start with
     * 1, such as {1 4 3 2}. The thing to do, is to ONLY permute in certain cases. If the current
     * a[j] that we are choosing to start our permutation with is odd, then we should only permute
     * if the calling a[j] was even. If the current a[j] that we are choosing to permute is even,
     * then we should only permute if the parent was odd.
     *
     * If the parent is null, as in the its the first call of the recursion, then only permute if the
     * a[j] that we are choosing is odd.
     */
    public void permutationAlternateEvenOddNumber(int[] array) {
        if (array.length % 2 != 0) {
            System.out.println("Array length must be even");
            return;
        }

        permutationAlternateNumberHelper(array, 0, null);
    }

    private void permutationAlternateNumberHelper(int[] array, int i, Boolean isParentOdd) {
        if (i == array.length - 1) {
            Util.print(array);
        }

        for (int j = i; j < array.length; j++) {
            if (isParentOdd == null && array[j] % 2 == 1 ||
                Boolean.TRUE.equals(isParentOdd) && array[j] % 2 == 0 ||
                Boolean.FALSE.equals(isParentOdd) && array[j] % 2 == 1
            ) {
                Util.swap(array, i, j);
                permutationAlternateNumberHelper(array, i + 1, array[j] % 2 == 1);
                Util.swap(array, i, j);
            }
        }
    }
}
