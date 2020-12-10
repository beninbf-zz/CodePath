package main.java.com.codepath.techscreens.apple;

import java.util.*;

public class TechScreen {
    /**
     * Apple tech screen. Secret santa.
     *
     * Given a list of names, return a map, that has a name randomnly mapped to another
     * name.
     *
     * One edge case to account for
     *
     * // A, B, C
     * // A -> B
     * // B -> A
     * // C ? // solve for this edge case, only thing left to map is C
     *
     *
     * @param names
     * @return Map
     */
    public Map<String, String> getRandomMap(List<String> names) {
        Map<String, String> result = new HashMap<>();
        if (names == null) {
            return result;
        }

        if (names.isEmpty() || names.size() == 1) {
            return result;
        }

        int length = names.size();
        List<String> namesToMapTo = new ArrayList<>(names);

        int index = 0;
        boolean lastNameCollision = false;
        while (!namesToMapTo.isEmpty() && index < length) {
            int mapIndex = setRandom(index, names, namesToMapTo, result);
            if (mapIndex == names.size() - 1) {
                break;
            } else if (mapIndex == -1) {
                if (namesToMapTo.size() == 1) {
                    lastNameCollision = true;
                    break;
                }
                // collision
            } else {
                index++;
            }
        }

        if (lastNameCollision) {
            String parent = result.get(names.get(0));
            result.put(names.get(0), names.get(index));
            result.put(names.get(index), parent);
        }
        return result;
    }



    public int setRandom(int i, List<String> names, List<String> namesToMapTo, Map<String, String> result) {
        int random = getRandom(namesToMapTo.size());
        String parent = names.get(i);
        String child = namesToMapTo.get(random);
        if (!parent.equals(child)) {
            if (!result.containsKey(parent)) {
                result.put(parent, child);
                swap(namesToMapTo, random);
                namesToMapTo.remove(namesToMapTo.size() - 1);
                return i;
            }
        }
        return -1;
    }

    public int getRandom(int size) {
        Random r = new Random();
        int randomIndex = r.nextInt(size);
        return randomIndex;
    }

    public void swap(List<String> namesToMapTo, int index) {
        String temp = namesToMapTo.get(index);
        String last = namesToMapTo.get(namesToMapTo.size() - 1);
        namesToMapTo.set(index, last);
        namesToMapTo.set(namesToMapTo.size() - 1, temp);
    }

    /**
     * Apple tech screen
     *
     * Given a sorted array of integers, return the sorted squares of those integers
     * Input: [-4,-2,1,3,5]
     *
     * [-5, -3 -2, -1]
     * [1, 4, 9, 25]
     *
     * 16, 4.....1 , 9 , 25
     *
     * input: [1, 4, 9, 16, 25]
     * Output:[1,4,9,16,25]
     *
     * If you need more classes, simply define them inline.
     *
     * This problem essentially required one to use the merge portion of merge
     * sort. I did quite well on this problem!
     *
     * @param array int[] array
     * @return int[] array
     */
    public int[] sortedSquares(int[] array) {

        if (array == null || array.length == 0) {
            return null;
        }

        if (array[0] >= 0) {
            square(array);
            return array;
        }

        if (array[0] < 0 && array[array.length - 1] < 0) {
            square(array);
            return reverse(array);
        }

        if (array[0] < 0 && array[array.length -1] > 0) {
            int inflectionIndex = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] > 0) {
                    inflectionIndex = i;
                    break;
                }
            }

            square(array);
            return merge(array, inflectionIndex);
        }

        return null;
    }

    private void square(int[] array) {
        for (int i =0; i < array.length; i++) {
            array[i] = array[i] * array[i];
        }
    }

    private int[] reverse(int[] array) {
        int[] temp = new int[array.length];
        int j =0;
        for (int i = array.length - 1; i >=0; i--) {
            temp[j] = array[i];
            j++;
        }
        return temp;
    }

    private int[] merge(int[] array, int inflectionIndex) {

        int begLeft = 0;
        int endLeft = inflectionIndex - 1;

        int begRight = inflectionIndex;
        int endRight = array.length - 1;

        int[] temp = new int[array.length];
        int i = 0;

        while (endLeft >= begLeft && begRight <= endRight) {
            if (array[endLeft] <= array[begRight]) {
                temp[i] = array[endLeft];
                endLeft--;
                i++;
            } else {
                temp[i] = array[begRight];
                begRight++;
                i++;
            }
        }

        while (endLeft >= begLeft) {
            temp[i] = array[endLeft];
            i++;
            endLeft--;
        }

        while (begRight <= endRight) {
            temp[i] = array[begRight];
            i++;
            begRight++;
        }

        return temp;
    }
}
