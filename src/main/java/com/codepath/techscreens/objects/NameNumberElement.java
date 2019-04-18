package main.java.com.codepath.techscreens.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * Tech screen from Udemy
 *
 * This tech screen wasn't that bad, but again I was running out of time and had troubles
 * coming up with a clean solution.
 *
 * While the problem states, to take in a list of strings and sort them like so
 *
 * ['Alex five', 'John thirty-three', 'Alex one'] return => ['Alex one', 'Alex five', 'John thirty-three']
 *
 * That essentially entails making a class, making it comparable, and then determining the sort.
 * The difficult part of the problem, stems around taking in a string and converting it into a number
 * correctly.
 *
 * For example, take 'two-hundred-thirty-four-thousand-five-hundred-sixty-seven' and convert it
 * into 234567.
 *
 * Again I ran into problems, and got nervous as I knew I was running out of time. As always the crux of the
 * problem is to understand how exactly we would build a number of out this string, OR how would we add it.
 * The number 234567 is actually = ((2 * 100) + 30 + 4)) * 1000 + (5 * 100) + 60 + 7
 *
 * How about the number 1462500? = (1 * 1000000) + ((4 * 100) + 60 + 2) * 1000) + (5 * 100)
 *
 * We should begin to see a pattern emerge. for 234567, what we did is sum up the 234, and then multiply it by a 1000.
 * Then we added the 567. At first our algorithm was wrong because what we in effect did was take 234000, then we add
 * 5, so we got 234005, then attempted to add 100, for 234105. We can see that this is wrong.
 *
 * We need to add numbers in groups, which is to say, we should add everything up BEFORE the 1000. Then after we
 * are done, simply multiply that number by 1000. After that, we should just add the remaining numbers in the string
 * to 234000, so we get (234 * 1000) + 567.
 *
 * This same patten scales as the numbers get bigger. If we have 1462500 or one-million-four-hundred-sixty-two-thousand-five-hundred
 * then we simply first sum everything before we see the "million". Then when we do that, that the result and multiple it by 1000000.
 * This then becomes the next number we will add to the summation of everything before "thousand". So we will have
 * 1000000 + 462000 + 500.
 *
 * The key to getting the algorithm right is simply understanding how to add numbers in this fashion. We make use of maps
 * to store the locations of the "billion", "million", and "thousand" strings, adding everything up until those strings,
 * and then adding them to a summation.
 *
 */
public class NameNumberElement implements Comparable<NameNumberElement> {

    public static Map<String, Integer> numberMap;
    {
        numberMap = new HashMap<>();
        numberMap.put("one", 1);
        numberMap.put("two", 2);
        numberMap.put("three", 3);
        numberMap.put("four", 4);
        numberMap.put("five", 5);
        numberMap.put("six", 6);
        numberMap.put("seven", 7);
        numberMap.put("eight", 8);
        numberMap.put("nine", 9);
        numberMap.put("ten", 10);
        numberMap.put("eleven", 11);
        numberMap.put("twelve", 12);
        numberMap.put("thirteen", 13);
        numberMap.put("fourteen", 14);
        numberMap.put("fifteen", 15);
        numberMap.put("sixteen", 16);
        numberMap.put("seventeen", 17);
        numberMap.put("eighteen", 18);
        numberMap.put("nineteen", 19);
        numberMap.put("twenty", 20);
        numberMap.put("thirty", 30);
        numberMap.put("forty", 40);
        numberMap.put("fifty", 50);
        numberMap.put("sixty", 60);
        numberMap.put("seventy", 70);
        numberMap.put("eighty", 80);
        numberMap.put("ninety", 90);
        numberMap.put(HUNDRED, 100);
        numberMap.put(THOUSAND, 1000);
        numberMap.put(MILLION, 1000000);
        numberMap.put(BILLION, 1000000000);

    }

    public static final String HUNDRED = "hundred";
    public static final String THOUSAND = "thousand";
    public static final String MILLION = "million";
    public static final String BILLION = "billion";


    public String name;
    public String number;

    public NameNumberElement(String name, String number) {
       this.name = name;
       this.number = number;
    }

    public int compareTo(NameNumberElement other) {
        if (this.name.compareTo(other.name) < 0) {
            return -1;
        } else if (this.name.compareTo(other.name) > 0) {
            return 1;
        } else {
            int thisNumber = getNumber(this.number);
            int otherNumber = getNumber(other.number);
            return thisNumber - otherNumber;
        }
    }

    /**
     * getNumber - Takes a string and returns its numerical equivalent.
     *
     * @param numberStr String
     * @return int
     */
    public int getNumber(String numberStr) {
        String[] numberArray = numberStr.split("-");
        int result = 0;
        Map<String, Integer> positions = getIndexMap(numberArray);
        String[] ordersOfMagnitude = {BILLION, MILLION, THOUSAND};

        int start = 0;
        for (int i = 0; i < ordersOfMagnitude.length; i++) {
            if (positions.containsKey(ordersOfMagnitude[i])) {
                int end = positions.get(ordersOfMagnitude[i]);
                int summation = add(numberArray, start, end);
                result += summation * numberMap.get(ordersOfMagnitude[i]);
                start = end + 1;
            }
        }

        /**
         * If we still have strings in our input to sum up after we have checked and summed
         * for BILLION, MILLION, and THOUSAND strings in our input
         */
        if (start < numberArray.length) {
            result += add(numberArray, start, numberArray.length);
        }

        return result;
    }

    /**
     * Add - for adding all numbers up to a scalar of million, billion,
     * or thousand
     * @param numberArray input of strings
     * @param start start index
     * @param end end index
     * @return
     */
    private int add(String[] numberArray, int start, int end) {
        int result = 0;
        for (int i = start; i < end; i++) {
            Integer number = numberMap.get(numberArray[i]);
            if (number.intValue() == 100) {
                result *= 100;
            } else {
                result += number;
            }
        }
        return result;
    }

    /**
     * getIndexMap - Populates a position with the indices of "billion", "million",
     * or "thousand" in our number array.
     *
     * @param numberArray input array of string
     * @return map of strings to integer
     */
    private Map<String, Integer> getIndexMap(String[] numberArray) {
        Map<String, Integer> positions = new HashMap<>();
        for (int i = 0; i < numberArray.length; i++) {
            if (numberArray[i].equals(THOUSAND)) {
                positions.put(THOUSAND, i);
            } else if (numberArray[i].equals(MILLION)) {
                positions.put(MILLION, i);
            } else if (numberArray[i].equals(BILLION)) {
                positions.put(BILLION, i);
            }
        }
        return positions;
    }

    public int getNumberValue() {
        return getNumber(this.number);
    }

    public String toString() {
        return String.format("%s %s", name, number);
    }
}
