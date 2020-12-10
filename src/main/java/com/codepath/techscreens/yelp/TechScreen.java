package main.java.com.codepath.techscreens.yelp;

import main.java.com.codepath.objects.UserClick;

import java.util.*;

public class TechScreen {
    /**
     * Yelp tech screen
     *
     * Making a prefix matcher and then iterating on that requirement to return the
     * top 4 results.
     *
     * After prefix works, then we must alter the code to return not just prefix matches
     * but also if the search term is within the book mark.
     *
     * @param bizNames String[]
     * @param searchTerm string
     * @return List<String>
     */
    public List<String> getBookMarks(String[] bizNames, String searchTerm) {
        if (bizNames == null || bizNames.length == 0) {
            return new ArrayList<>();
        }

        if (searchTerm == null) {
            return Arrays.asList(bizNames);
        }

        List<String> results = new ArrayList<>();
        int prefixMatchCount = 0;
        Queue<Integer> nonPrefixLocations = new LinkedList<>();
        for (String biz: bizNames) {
            if (prefixMatch(biz, searchTerm)) {
                results.add(biz);
                prefixMatchCount++;
            } else {
                if (nonPreFixMatch(biz, searchTerm)) {
                    results.add(biz);
                    nonPrefixLocations.add(results.size() - 1);
                }
            }

            if (prefixMatchCount == 4) {
                break;
            }
        }

        if (prefixMatchCount == 4 && results.size() > 4) {
            while (!nonPrefixLocations.isEmpty()) {
                Integer position = nonPrefixLocations.poll();
                results.remove(position.intValue());
            }
        }
        return results;
    }

    private static boolean prefixMatch(String bookMark, String searchTerm) {
        if (bookMark == null || bookMark.isEmpty()) {
            return false;
        }

        if (searchTerm == null) {
            return false;
        }

        String[] bookMarks = bookMark.split(" ");
        for (String bookMarkItem: bookMarks) {
            if (bookMarkItem != null) {
                if (bookMarkItem.toLowerCase().startsWith(searchTerm.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean nonPreFixMatch(String bookMark, String searchTerm) {
        if (bookMark == null || bookMark.isEmpty()) {
            return false;
        }

        if (searchTerm == null) {
            return false;
        }

        String[] bookMarks = bookMark.split(" ");
        for (String bookMarkItem: bookMarks) {
            if (bookMarkItem != null) {
                if (bookMarkItem.toLowerCase().contains(searchTerm.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getFinalDestination(List<UserClick> clicks, String start) {
        Map<String, List<String>> clickMap = new HashMap<>();
        String[] result = new String[1];
        for (UserClick click: clicks) {
            if (!clickMap.containsKey(click.source)) {
                List<String> list = new ArrayList<>();
                list.add(click.destination);
                clickMap.put(click.source, list);
            } else {
                List<String> list = clickMap.get(click.source);
                list.add(click.destination);
            }
        }
        Set<String> seen = new HashSet<>();
        seen.add(start);
        explore(start, seen, clickMap, result);
        return result[0];
    }

    private void explore(String key, Set<String> seen, Map<String, List<String>> clickMap, String[] result) {
        result[0] = key;
        List<String> neighbors = clickMap.get(key);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                result[0] = neighbor;
                if (!seen.contains(neighbor)) {
                    seen.add(neighbor);
                    explore(neighbor, seen, clickMap, result);
                }
            }
        }
    }
}
