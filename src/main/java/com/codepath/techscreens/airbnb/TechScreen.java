package main.java.com.codepath.techscreens.airbnb;

import java.util.ArrayList;
import java.util.List;

public class TechScreen {
    /**
     * AirBnb 2nd tech screen, I will need to complete this.
     *
     *
     // isPalindrome('foo') // False
     // isPalindrome('bob') // True
     // isPalindrome('gig') // True
     // isPalindrome('abba') // True

     // words = ['gab', 'cat', 'alpha', 'bag']
     // pairs = [
     //   ['gab', 'bag'],    // gab | bag
     //   ['bag', 'gab'],    // bag | gab
     // ]

     //   words = ['gab', 'cat', 'alpha', 'bag', 'race', 'car', 'ag']
     //   pairs = [
     //     ['gab', 'bag'],    // gab | bag
     //     ['bag', 'gab'],    // bag | gab
     //     ['race', 'car'],   // race | car     ybag
     // car  race
     //     ['racee', 'car']   // racee | car
     //     ['nurses', 'run'], // nurses | run
     //      nur ses run
     //       run  nurses
     // rac ebn car
     // racecar....carrace
     //

     // 'nurses',       // 'sesrun'        (nurses | sesrun)
     //                 // 'esrun'         (nurse | s | esrun)
     //                 // 'run'           (nur | ses | run)

     *
     * @param words
     * @return
     */
    public List<List<String>> getPairs(List<String> words) {

        List<List<String>> results = new ArrayList<>();

        return null;
    }

    public boolean isPalindrome(String input) {
        if (input == null) {
            return true;
        }

        if (input.isEmpty()) {
            return true;
        }

        if (input.length() == 1) {
            return true;
        }

        int length = input.length();
        int mid = length / 2;
        int j = mid - 1;
        int start = length % 2 == 0 ? mid : mid + 1;
        for (int i = start; i < length; i++) {
            if (input.charAt(i) != input.charAt(j)) {
                return false;
            }
            j--;
        }
        return true;
    }
}
