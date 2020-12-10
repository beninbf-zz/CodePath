package main.java.com.codepath.techscreens.masterclass;

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
     * Tech screen from Master class. I thought this went well, they at first
     * gave a simply question and then, continuously provided more difficult
     * use cases such that my code would have to change in order to solve the problem.
     *
     * It was essentially writing an algorithm to translate a word into pig latin.
     * The 6th use case forced me to change my algorithm altogether look at translate1.
     * @param word input
     * @return pig latin version
     */
    public String pigLatinTranslate(String word) {
        char[] charArray = word.toCharArray();

        if (!vowels.contains(Character.toLowerCase(word.charAt(0)))) {

            boolean isFirstUpper = isUpperCase(word.charAt(0));
            for (int i = charArray.length - 1; i > 0; i--) {
                swap(charArray, 0, i);
            }

            if (isFirstUpper) {
                transformUpper(charArray, 0, charArray.length - 1);
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append(charArray);
        sb.append("ay");

        return sb.toString();
    }

    /**
     * The correct implementation for a pig latin algorithm is below.
     * @param word input
     * @return pig latin translation
     */
    public String pigLatinTranslateImproved(String word) {
        char[] charArray = word.toCharArray();
        StringBuffer sb = new StringBuffer();
        boolean isFirstUpper = isUpperCase(word.charAt(0));
        if (isConsonant(word.charAt(0))) {
            int vowelLocation = -1;
            for (int i = 0; i < charArray.length; i++) {
                if (!isConsonant(charArray[i])) {
                    vowelLocation = i;
                    break;
                }
            }

            String subString = word.substring(0, vowelLocation);
            String restAfterVowel = word.substring(vowelLocation + 1, word.length());

            sb.append(word.charAt(vowelLocation));
            sb.append(restAfterVowel);
            sb.append(subString);
            sb.append("ay");

            if (isFirstUpper) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                sb.setCharAt(vowelLocation, Character.toLowerCase(sb.charAt(vowelLocation)));
            }
        }
        return sb.toString();
    }

    private boolean isConsonant(char ch) {
        return !vowels.contains(Character.toLowerCase(ch));
    }

    public void transformUpper(char[] charArray, int toUpperCase, int toLowerCase) {
        charArray[toUpperCase] = Character.toUpperCase(charArray[toUpperCase]);
        charArray[toLowerCase] = Character.toLowerCase(charArray[toLowerCase]);
    }

    public boolean isUpperCase(char ch) {
        return  !Character.isLowerCase(ch);
    }

    public void swap(char[] array, int x, int y) {
        char temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}
