package main.java.com.codepath.techscreens.thumbtack;

import java.util.HashSet;
import java.util.Set;

public class TechScreen {

    public static Set<Character> vowels;
    static {
        vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
    };

    /**
     * Thumbtack Tech screen, number 2.
     *
     * This tech screen was straight forward.
     *
     * You have a dictionary (set) of words. You need to implement a spellchecker that
     * returns the correct word for an input word.
     *
     * The spellchecker only handles the following two class of spelling mistakes -
     *
     * Capitalization
     * Example 1: <set: {yellow, radish}, input: yelloW, output: yellow>
     * Example 2: <set: {Yellow, radish}, input: yelloW, output: Yellow> // yolloW => Yellow
     *
     * Vowels (letters in the set {a,e,i,o,u}) mixed up - consonants are in the correct order,
     * but one or more vowels in the input word is/are not the same as the vowels in the
     * corresponding dictionary word.
     * Example 1: <set: {yellow, radish}, input: yollow, output: yellow //>
     * yellow is a valid suggestion for yollow
     * Example 2: <set: {yellow, radish}, input: redosh, output: radish> // redosx => radish -- NO_MATCH
     * In addition, the following cases should be handled -
     *
     * When there is no valid suggestion, your function should return the string "NO_MATCH".
     * When there is more than one valid suggestion, your function can return any one of them.
     * When there is no spelling mistake in the input (exact match found), your function should
     * return the same word back.
     *
     * What I messed up with this problem, was the follow up question, where the interview asked
     * what pre-processing could we do to make this faster. I struggled with this. The current run
     * time was O(n * c), where n is the number of words in the dictionary, and c is the number
     * of characters in the word we Wish to spell check.
     *
     * I think the thing I need to remember, is when asked these questions, how can I make them of
     * order of magnitude faster. So this is polynomial, so how could I make it linear? Making
     * it linear would be simply iterating over the dictionary, and making the check with our input
     * constant. How could we do that?
     *
     * Given that the spell checking is largely based on the positions of consonants, what I could
     * do is create a wild card for the input. So that the mispelled word "yollow", has wildcards
     * like "y*ll*w". The dictionary, could also be in this format, such that the key "y*ll*w",
     * would map to a set of valid words where all of the consonants are in the same format.
     *
     * @param dictionary set of words
     * @param input the word we Wish to return a suggestion for
     * @return String
     */
    public String spellChecker(Set<String> dictionary, String input) {
        if (input == null || input.isEmpty()) {
            return "NO_MATCH";
        }
        for (String s: dictionary) {
            if(isMatch(s, input)) {
                return s;
            }
        }
        return "NO_MATCH";
    }

    private boolean isMatch(String word1, String word2) {
        int lengthOne = word1.length();
        int lengthTwo = word2.length();
        if (lengthOne != lengthTwo) {
            return false;
        }

        for (int i = 0; i < lengthOne; i++) {
            char ch1 = Character.toLowerCase(word1.charAt(i));
            char ch2 = Character.toLowerCase(word2.charAt(i));
            if (isConsonant(ch1) && isConsonant(ch2)) {
                if (ch1 != ch2) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isConsonant(char ch) {
        return !vowels.contains(Character.toLowerCase(ch));
    }
}
