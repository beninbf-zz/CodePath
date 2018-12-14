package main.java.com.codepath.recursion;

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
}
