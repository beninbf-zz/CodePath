package main.java.com.codepath.techscreens.stackrox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechScreen {
    /**
     *
     * Tech Screen is from StackRox
     *
     * Input:
     * 1 => [A B C]
     * 2 =>[A B]
     * 3 =>[B]
     * 4 =>[A B C D]
     *
     * Output:
     * 1 [2 C]
     * 2 [A B]
     * 3 [B]
     * 4 [1 D]
     *
     * Find away to return the output with the appropriate prefixes in each map. The key to this problem is just
     * organizing the input in such a way as to properly check which map value is a prefix of the other.
     */
    public Map<String, String> getUniquePrefixes(List<StackRoxNode> input) {

        int length = input.size();
        StackRoxNode current = input.get(0);
        Map<String, String> results = new HashMap<>();
        for (int i = 1; i < length; i++) {
            StackRoxNode next = input.get(i);
            if (current.value.startsWith(next.value)) {
                String leftOver = current.value.substring(next.value.length(), current.value.length());
                String newValue = next.key + leftOver;
                results.put(current.key, newValue);
            } else {
                results.put(current.key, current.value);
            }
            current = next;
        }

        StackRoxNode last = input.get(input.size() - 1);
        results.put(last.key, last.value);
        return results;
    }
}
