package main.java.com.codepath.techscreens.creditkarma;

import java.util.ArrayList;
import java.util.List;

public class TechScreen {
    /**
     * Credit Karma tech screen.
     *
     * Given a list of colors and a string, find the colors that have the String "input"
     * as a subsequence.
     *
     * Example:
     *
     *
     * console.log(findColor('uqi'))
     * [ 'darkturquoise', 'mediumaquamarine', 'mediumturquoise', 'paleturquoise', 'turquoise' ]
     *
     * console.log(findColor('zre'))
     * [ 'azure' ]
     *
     * console.log(findColor('gold'))
     * [ 'darkgoldenrod', 'gold', 'goldenrod', 'lightgoldenrodyellow', 'palegoldenrod' ]
     *
     *
     * @param input
     * @param colors
     * @return
     */
    public List<String> findColors(String input, String[] colors) {
        if (input == null) {
            return null;
        }

        if (input.isEmpty()) {
            return null;
        }
        List<String> results = new ArrayList<>();

        int inputLength = input.length();
        for (String color : colors) {
            List<Integer> positions = getPositions(input, color);
            if (positions != null) {
                if (positions.size() == inputLength && isSorted(positions)) {
                    results.add(color);
                }
            }
        }

        return results;
    }

    public List<Integer> getPositions(String input, String color) {
        List<Integer> pos = new ArrayList<>();
        int length = input.length();
        int colorLength = color.length();
        int lastPos = 0;
        for (int i = 0; i < length; i++) {
            char inputChar = input.charAt(i);
            for (int j = 0; j < colorLength; j++) {
                char colorChar = color.charAt(j);
                if (inputChar == colorChar && j >= lastPos) {
                    pos.add(j);
                    lastPos = j;
                    break;
                } else {
                    if (j == colorLength - 1) {
                        return null;
                    }
                }
            }
        }
        return pos;
    }

    public boolean isSorted(List<Integer> positions) {
        if (positions == null) {
            return true;
        }

        if (positions.size() == 0) {
            return true;
        }
        int length = positions.size();
        for (int i = 0; i < length - 1; i++) {
            if (positions.get(i) > positions.get(i + 1)) {
                return false;
            }
        }

        return true;
    }
}
