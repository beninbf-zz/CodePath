package main.java.com.codepath.recursion.Palindrome;

import java.util.ArrayList;
import java.util.List;

public class Palindrome {

    public boolean isPalindrome(String word) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }
        StringBuffer sb = new StringBuffer(word);
        sb.reverse();
        String reversedWord = sb.toString();
        return reversedWord.equals(word);
    }

    public boolean isPalindromeWithPointers(String word) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }

        if (word.length() == 1) {
            return true;
        }
        int length = word.length();
        for (int i = 0; i < length; i++) {
            char first = word.charAt(i);
            char last = word.charAt(length - i - 1);
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    /**
     * The decomposition of a palindrome is a recursive problem.
     *
     * We essentially have to find all possible decompositions. We illustrate a palindromic composition as such
     * For a string s = "abba", the resulting decompositions are
     *
     * "a|b|b|a"
     * "a|bb|a"
     * "abba"
     *
     * The reason being is that any single character is a palindrome, so thats why there is a "|" after every character
     * because the strings "a" and "b" are valid palindromes.
     *
     * The next String, "a|bb|a" is because "a" is a valid palindrome, and so is "bb".
     *
     * Of course the entire input, "abba" is a palindrome, so there is no need to apply an "|", just to add it to the results.
     *
     * We know to use recursion because we have to find all palindrome decompositions, so we know the search is exhaustive.
     * We do this by essentially checking every possible substring of the string, beginning with each character.
     *
     * That means the following,
     *
     * SubString of "abba", starting at 0 and ending at 1, so the substring "a". if its a subString, then build up our
     * decompositions "a|", and recurse on the rest of the string "bba".
     *
     * Then so the same thing on "bba", find out of if the substring "0 to 1" is a palindrome, i.e. is "b" a palindrome.
     * It is so build up our decomposition, its now "a|b|", and we recurse on "ba".
     *
     * Do the same on "ba", and we see that "b" is a palindrome, so we get "a|b|b|", then recurse on "a". "a" will also be a
     * palindrome, but because its the last character, we don't append the "|".
     *
     * We notice there is nothing left to recurse on, so when we make the next recursive call we hit our base case, which is
     * when word is empty, then add our decomposition to our array.
     *
     * As we then begin to back track, we wind up in our for loop, now looking at substrings from 0 to 2, (where as the previous
     * stack frames look at 0 to 1). We will now proceed on checking if substrings from 0 to 2 are also palindromes.
     *
     * As this search is exhaustive, it will end up checking all possible substrings, starting at every character.
     *
     * Regarding the SPACE COMPLEXITY, we know the recursion tree will have a height at most of n, where n is the number of
     * characters in the String. We also have the extra space for the decompositions array where we store our results. With
     * regard to the number of decompositions, its hard to say how many there might be. its essentially the number of leaf nodes
     * because at every leaf node, we add the decomposition to our list. Its probably still on the order of O(n). So the space
     * from the recursive tree is O(n) and the space from the list is O(n), so overall it should be O(n).
     *
     * TIME COMPLEXITY: Considering we are looking at every substring of each string, that means our initial call will check
     * n characters, then n - 1, then n - 2, then n - 3, until we get to zero. So thats n!. We are also checking to see that
     * each substring is a palindrome, and thats at most O(n). Considering every recursive call has O(n) for the palindrome
     * check and there are n! of them, the running time is exponential. The exact running time, I'm not sure.
     *
     * @param word
     * @return
     */
    public String[] decomposePalindrome(String word) {
        if (word == null || word.trim().isEmpty()) {
            return new String[]{};
        }
        List<String> decompositions = new ArrayList<>();
        decomposePalindrome(word, word.length(), "", decompositions);
        return decompositions.toArray(new String[0]);
    }

    private void decomposePalindrome(String word, int length, String palindrome, List<String> decompositions) {
        if (word.isEmpty()) {
            decompositions.add(palindrome);
        }

        for (int i = 0; i < length; i++) {
            String decomposition = null;
            String candidatePalindrome = word.substring(0, i + 1);
            if (isPalindromeWithPointers(candidatePalindrome)) {
                String rest = word.substring(i + 1, length);
                if (rest.isEmpty() || length == 1) {
                    decomposition = palindrome + candidatePalindrome;
                } else {
                    decomposition = palindrome + candidatePalindrome + "|";
                }
                decomposePalindrome(rest, rest.length(), decomposition, decompositions);
            }
        }
    }
}
