package main.java.com.codepath.techscreens.glassdoor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TechScreen {
    /*
     * Tech screen from Glass door.
     *
     * Q1 - For the given input string, return a map of case insensitive
     * words and their counts. Do not worry about punctuation or special
     * characters.
     */
    public Map<String, Integer> countWords(String input) {
        Map<String, Integer> results = new HashMap<>();
        if (input == null || input.isEmpty()) {
            return results;
        }
        String[] inputArray = input.split(" ");
        for (String word: inputArray) {
            if (!results.containsKey(word.toLowerCase())) {
                results.put(word.toLowerCase(), 1);
            } else {
                Integer count = results.get(word.toLowerCase());
                results.put(word.toLowerCase(), count.intValue() + 1);
            }
        }
        return results;
    }

    public Map<String, Integer> countWordsWithSynonyms(Map<String, Integer> wordMap, String[][] synonyms) {
        Map<String, String> adjMap = new HashMap<>();
        for (int i = 0; i < synonyms.length; i++) {
            adjMap.put(synonyms[i][0], synonyms[i][1]);
        }

        Set<String> keys = wordMap.keySet();
        Set<String> seen = new HashSet<>();
        Set<String> toRemove = new HashSet<>();

        for (String key: keys) {
            if (!seen.contains(key)) {
                exploreGetCount(adjMap, wordMap, key, seen, toRemove);
            }
        }

        for (String key: toRemove) {
            if (wordMap.containsKey(key)) {
                wordMap.remove(key);
            }
        }
        return wordMap;
    }

    public int exploreGetCount(Map<String, String> adjMap, Map<String, Integer> wordMap, String key, Set<String> seen, Set<String> toRemove) {
        String neighbor = adjMap.get(key);
        int resultingCount = 0;
        while(neighbor != null) {
            if (!seen.contains(neighbor)) {
                seen.add(neighbor);
                resultingCount = exploreGetCount(adjMap, wordMap, neighbor, seen, toRemove);
                toRemove.add(neighbor);
            }
            neighbor = adjMap.get(neighbor);
        }

        Integer count = wordMap.get(key);
        int total = count.intValue() + resultingCount;
        wordMap.put(key, count.intValue() + resultingCount);
        return total;
    }

    /**
     * --- Question ---
     *
     *   A queue of people are waiting to buy ice cream from you.
     *   Each person buys one ice cream, which sells for $5.
     *   Each customer is holding a bill of $5, $10 or $20.
     *   Your initial balance is 0.
     *   Find whether you will be able to make change for every customer in the queue.
     *   You must serve customers in the order they come in.
     *
     *
     *   - Simple Use Cases -
     *   5,   5,   5,  10,  10      -> true,
     *   10,  10,   5                -> false,
     *   5,   5,  20,   5           -> false,
     *
     *  - Other/Edge Use Cases -
     *   5,   5,   5,  20,   5,  10 -> true,
     *   5,   5,  10,  10,  20      -> false,
     *   5,  10,   5,  20           -> true,
     *   5,   5,   5,  10,  10,  20 -> true,
     *   5,   5,   5,   5,  20      -> true
     *
     *
     * @param queue queue of ice cream order
     * @return boolean
     */
    public boolean sellIceCream(int[] queue) {
        Map<Integer, Integer> changeMap = new HashMap<>();
        int total = 0;
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == 5) {
                if (!changeMap.containsKey(queue[i])) {
                    changeMap.put(queue[i], 1);
                    total = 5;
                } else {
                    incrementDenomination(changeMap, queue[i]);
                    total += queue[i];
                }
            } else {
                total = updateChangeMap(changeMap, queue[i], total);
                if (total == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private int updateChangeMap(Map<Integer, Integer> changeMap, int denomination, int total) {
        int changeRequired = denomination - 5;
        if (total < changeRequired) {
            return -1;
        }
        if (!changeMap.containsKey(denomination)) {
            changeMap.put(denomination, 1);
            total += denomination;
        } else {
            incrementDenomination(changeMap, denomination);
            total += denomination;
        }

        if (changeMap.containsKey(changeRequired) && changeMap.get(changeRequired).intValue() != 0) {
            decrementDenomination(changeMap, changeRequired);
            total -= changeRequired;
            return total;
        }
        return remainingChange(changeMap, changeRequired, total);
    }

    private int remainingChange(Map<Integer, Integer> changeMap, int changeRequired, int total) {
        int[] denominations = {20, 10, 5};
        for (int i = 0; i < denominations.length; i++) {
            while (changeRequired > 0) {
                // our remaining change can't be taken from the current denomination
                if (changeRequired / denominations[i] == 0) {
                    break;
                } else if (changeRequired / denominations[i] >= 1) {
                    if (changeMap.containsKey(denominations[i]) && changeMap.get(denominations[i]).intValue() != 0) {
                        decrementDenomination(changeMap, denominations[i]);
                        total -= denominations[i];
                        changeRequired -= denominations[i];
                    } else {
                        break;
                    }
                }
            }
        }
        return changeRequired != 0 ? -1 : total;
    }

    private void decrementDenomination(Map<Integer, Integer> changeMap, int denomiation) {
        int value = changeMap.get(denomiation);
        changeMap.put(denomiation, value - 1);
    }

    private void incrementDenomination(Map<Integer, Integer> changeMap, int denomiation) {
        int numberOfDenomination = changeMap.get(denomiation);
        changeMap.put(denomiation, numberOfDenomination + 1);
    }
}
