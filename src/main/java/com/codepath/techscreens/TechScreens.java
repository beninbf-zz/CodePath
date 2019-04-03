package main.java.com.codepath.techscreens;

import main.java.com.codepath.objects.Person;
import main.java.com.codepath.techscreens.objects.Booking;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TechScreens {

    private final static String QUOTE = "\"";
    private final static String OPEN_BRACKET = "{";
    private final static String CLOSE_BRACKET = "}";
    private final static String OPEN_ARRAY_BRACKET = "[";
    private final static String CLOSE_ARRAY_BRACKET = "]";
    private final static String COLON = ":";
    private final static String COMMA = ",";
    private final static Set<String> protectedTypes = new HashSet<>(Arrays.asList("String", "Integer", "Double", "Float"));

    /**
     * This problem was given during a tech screening. The company was call Sonder.
     * An Airbnb type company. The problem statement below is given a series of bookings
     * in the form of "4:8 19:35 80:160", a current_date, i.e. 4, and stay_length, i.e. 1
     * return the first available day of booking.
     *
     * What gave me problems during this problem was the fact that I would have to map the current
     * date to the bookings. To do that, I would, in some cases, need to check the checkIn date
     * of the very next booking.
     *
     * During the tech screen I neglected to create a Booking Object, and instead just tried to
     * parse strings and string arrays which makes it difficult to see the checkIn Date of the
     * "next" booking.
     *
     * I was struggling describing my solution because I was doing so withing the constraints
     * of the String array, where the next booking, needed to first be split, before I could check
     * the checkIn date.
     *
     * The take away from this, is to bend the problem to my algorithm solution by transforming it
     * some how. I should have just made a Booking Object, and then create a list of bookings
     * to iterate through.
     *
     * @param bookingsInput string of bookings where integer is day "4:8 19:35 80:160"
     * @param current_date int
     * @param stay_length int
     * @return first available day
     */
    public int getDate(String bookingsInput, int current_date, int stay_length) {
        String[] array = bookingsInput.split(" ");
        Booking[] bookings = new Booking[array.length];
        for (int i = 0; i < array.length; i++) {
            String[] booking = array[i].split(":");
            bookings[i] = new Booking(booking[0], booking[1]);
        }

        int bookingToStart = getBookingToStartSearch(bookings, current_date);
        if (bookingToStart == bookings.length - 1) {
            return bookings[bookingToStart].checkOut;
        }

        return getFirstAvailableDay(bookings, bookingToStart, current_date, stay_length);
    }

    /**
     * We need to be able to find the booking date from which our start will commence.
     * That is exactly what this logic does.
     *
     * @param bookings string a booking, i.e. "0:2"
     * @param current_date the current date the customer is looking for bookings
     * @return index of the booking
     */
    private int getBookingToStartSearch(Booking[] bookings, int current_date) {
        for (int i = 0; i < bookings.length; i++) {
            Booking booking = bookings[i];
            /* if the current date is neatly within the bounds of a booking, then simply return that booking*/
            if (current_date >= booking.checkIn && current_date <= booking.checkOut) {
                return i;
            } else { /* if its not within a booking then find the booking that immediately preceeds the current date*/
                if (i + 1 < bookings.length && current_date >= booking.checkOut && current_date <= bookings[i + 1].checkIn) {
                    return i;
                }
            }
        }
        /* if we iterate through the entire array, then that means the current date is outside of the bounds of the bookings
        *  so we can just return the last booking.
        * */
        return bookings.length - 1;
    }

    /**
     * Now that we know the booking to start from, we can walk through the booking array, searching for the first
     * available date that will accommodate our stay.
     *
     * @param bookings array of Booking objects
     * @param bookingToStart the booking from which we are beginning or search
     * @param current_date the current date
     * @param stay_length the length of the stay
     * @return int the first available day that the customer can book
     */
    private int getFirstAvailableDay(Booking[] bookings, int bookingToStart, int current_date, int stay_length) {
        for (int i = bookingToStart; i < bookings.length; i++) {
            int entireStay = current_date + stay_length;
            // we need to be able to check the current booking and the next one, so this is a boundary check
            if ((i + 1) < bookings.length) {
                // if the entire stay is sandwiched between two bookings then we can just return the current date
                if (current_date >= bookings[i].checkOut && entireStay <= bookings[i + 1].checkIn) {
                    return current_date;
                } else if (entireStay >= bookings[i + 1].checkIn) { // if the entire stay overlaps into the booking then "walk" up to the checkout of the next booking
                    current_date = bookings[i + 1].checkOut;
                } else {
                    return bookings[i].checkOut;
                }
            }
        }
        return bookings[bookings.length  - 1].checkOut;
    }

    /*
    Casey has a square image made up of black and white pixels represented as 0 and 1 respectively.  As part of an image analysis process, Casey needs to determine the size of the largest square area of white pixels.  Given a 2-dimensional square matrix that represents the image, write a function to determine the length of a side of the largest square area made up of white pixels.



For example, the n x n = 5 x 5 matrix of pixels is represented as arr = [[1,1,1,1,1], [1,1,1,0,0], [1,1,1,0,0], [1,1,1,0,0], [1,1,1,1,1].  A diagram of the matrix is:

1 1 1 1 1
1 1 1 0 0
1 1 1 0 0
1 1 1 0 0
1 1 1 1 1
The largest square sub-matrix is 3 x 3 in size starting at position (0, 0), (1, 0) or (2, 0). The expected return value is 3.


Function Description

Complete the function largestMatrix in the editor below. The function must return width of the largest square sub-matrix of white pixels.



largestMatrix has the following parameter:

    arr[arr[0][0],...arr[n-1][n-1]]:  a 2D array of integers



Constraints

1 ≤ n ≤ 500
arr[i][j] ∈ {0, 1} (0 denotes black pixel and 1 denotes white pixel)
     */

    /**
     * This tech screen was from a company called Exabeam. The problem statement is as follows
     *
     * Casey has a square image made up of black and white pixels represented as 0 and 1 respectively.
     * As part of an image analysis process, Casey needs to determine the size of the largest square
     * area of white pixels.  Given a 2-dimensional square matrix that represents the image,
     * write a function to determine the length of a side of the largest square area made up
     * of white pixels.
     *
     * For example, the n x n = 5 x 5 matrix of pixels is represented as
     * arr = [[1,1,1,1,1], [1,1,1,0,0], [1,1,1,0,0], [1,1,1,0,0], [1,1,1,1,1].
     * A diagram of the matrix is:
     *
     * 1 1 1 1 1
     * 1 1 1 0 0
     * 1 1 1 0 0
     * 1 1 1 0 0
     * 1 1 1 1 1
     *
     * The largest square sub-matrix is 3 x 3 in size starting at position (0, 0), (1, 0) or (2, 0).
     * The expected return value is 3.
     *
     * So I had a correct idea on how to solve this problem. With the problem statement asking for
     * the largest sub matrix, I realized one approach was to find all sub matrices of square dimensions
     * and then check each one to see if they all contained 1. For the sub matrices that contained
     * all ones, I would simply record the dimension length, and update it if a subsequent dimension
     * was found to be longer.
     *
     * I could tell that this would not be optimal, but it should work. Considering it was an exhaustive
     * search, the problem called for recursion. This is where I fumbled, and I neglected to first
     * get a clear understanding of the recursive structure of the problem.
     *
     * I have to remember one thing for problems that are recursive in nature, and that is
     * TO DRAW THE FUCKING RECURSION TREE FIRST!!!!!!
     *
     * When you do, that, writing the code afterwards becomes much clearer.
     *
     * Essentially this solution works, by starting at cell 0, 0, it then generates all square sub matrices
     * of length, 1, 2, 3, up to the bounds of the matrix.
     *
     * For each sub matrix a check is performed to determine if the sub matrix contains all ones or not.
     * Drawing the recursion tree, and understanding the base cases upon which to return, which is
     * if the startRow or startCol plus the dimension im checking is outside of the bounds, is all that
     * is necessary.
     *
     * In this problem we start with dimenion 0, knowing it won't count, but thats no problem, as proceed
     * to dimension, as the cell 0,0 will be counted because 0 < 1.
     *
     * I'm pretty sure this problem is a prime candidate for a dynamic programming solution, because
     * there would appear to be a lot of overlapping sub problems.
     *
     * We will try to the problem again later, as DP problem.
     *
     * RUNTIME: the runtime of the problem is quite heavy. For every call, we check
     * the sub matrix given the dimension. That means a check of O(n^2) per recursive call.
     * Every recursive call, spawns n new calls, where n is the dimension of the square.
     * And the height of the recursion tree is n, where n is the dimension of the square matrix.
     * So I think the runtime is n^2 * n * n
     *
     * SPACE COMPLEXITY: O(n^2) to store the 2-D array
     *
     *
     * @param arr 2-D array of zeros and ones
     * @return int the largest dimension of a subm atrix containing all ones
     */
    public int largestMatrix(List<List<Integer>> arr) {

        int rows = arr.size();
        int cols = arr.get(0).size();

        int[][] array = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = arr.get(i).get(j);
            }
        }
        int[] largestDimension = new int[1];
        largestMatrixHelper(array, 0, 0, 0, largestDimension);
        return largestDimension[0];
    }

    private void largestMatrixHelper(int[][] array, int row, int col, int dimension, int[] largestDimension) {
        if (row + dimension > array.length) {
            return;
        }

        if (col + dimension > array[0].length) {
            return;
        }

        if (hasAllOnes(array, row, col, dimension)) {
            if (dimension > largestDimension[0]) {
                largestDimension[0] = dimension;
            }
        }

        for (int i = row; i < array.length; i++) {
            for (int j = col; j < array[0].length; j++) {
                largestMatrixHelper(array, i, j,  dimension + 1, largestDimension);
            }
        }
    }

    private boolean hasAllOnes(int[][] array, int startRow, int startCol, int dimension) {
        for (int i = startRow; i < startRow + dimension; i++) {
            for (int j = startCol; j < startCol + dimension; j++) {
                if (array[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This tech screen was from DyDx.
     *
     * Given an object, serialize it into json format. This is best done with a dynamic
     * language like Python or JavaScript.
     *
     * This problem isn't that complicated, but to do this in Java, requires knowing the
     * Reflection API's in java, which I wasn't familiar with at the time.
     *
     * At any rate, I knew the correct algorithm to use. Essentially one had to use reflection
     * to get the fields of a java object, and then perform a DFS on every field.
     *
     * The problem only becomes complicated when you try to serialize it into JSON format
     * for different field Types, like String, int, array, or nested objects.
     * @param object object we wish to serialize
     * @return json string
     */
    public String getJsonString(Object object) {
        StringBuffer sb = new StringBuffer();
        sb.append(OPEN_BRACKET);
        sb.append(getJsonStringHelper(object, null, false));
        sb.append(CLOSE_BRACKET);
        return sb.toString();
    }

    public String getJsonStringHelper(Object object, String name, boolean isArray) {
        StringBuffer sb = new StringBuffer();
        if (isArray) {
            sb.append(getNameFromField(name));
            sb.append(OPEN_ARRAY_BRACKET);
            int length = Array.getLength(object);
            String prefix = "";
            for (int i = 0; i < length; i ++) {
                sb.append(prefix);
                prefix = ",";
                Object arrayElement = Array.get(object, i);
                sb.append(getJsonStringHelper(arrayElement,
                    arrayElement.getClass().getSimpleName(),
                    arrayElement.getClass().isArray())
                );
            }
            sb.append(CLOSE_ARRAY_BRACKET);
            return sb.toString();
        } else {
            if (name != null && !protectedTypes.contains(name)) {
                sb.append(getNameFromField(name));
                sb.append(OPEN_BRACKET);
            }
        }

        Field[] fields = object.getClass().getDeclaredFields();
        String prefix = "";
        for (Field field : fields) {
            Object nested = getFieldValue(field, object);
            if (nested != null) {
                sb.append(prefix);
                prefix = COMMA;
                if (field.getType().isArray()) {
                    sb.append(getJsonStringHelper(nested, field.getName(), field.getType().isArray()));
                } else if (field.getType().equals(Person.class)) {
                    sb.append(getJsonStringHelper(nested, field.getName(), field.getType().isArray()));
                } else if (field.getType().equals(String.class)) {
                    sb.append(getStringFromField(field, object));
                } else if (field.getType().equals(Integer.class)) {
                    sb.append(getStringFromField(field, object));
                }
            } else {
                if (field.getType().getName().equals("[C")) {
                    sb.append(QUOTE);
                    sb.append(object);
                    sb.append(QUOTE);
                }
            }
        }

        if (name != null && !protectedTypes.contains(name)) {
            sb.append(CLOSE_BRACKET);
        }

        return sb.toString();
    }

    public String getStringFromField(Field field, Object object) {
        StringBuffer sb = new StringBuffer();
        sb.append(QUOTE);
        sb.append(field.getName());
        sb.append(QUOTE);
        sb.append(COLON);
        sb.append(QUOTE);
        sb.append(getFieldValue(field, object));
        sb.append(QUOTE);
        return sb.toString();
    }

    public String getNameFromField(String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(QUOTE);
        sb.append(name);
        sb.append(QUOTE);
        sb.append(COLON);
        return sb.toString();
    }

    public Object getFieldValue(Field field, Object object) {
        try {
            return field.get(object);
        } catch (Exception ex) {
            // error
        }
        return null;
    }


    /**
     * This tech screen was provided by Karat by way of the Zume company
     *
     * Suppose we have some input data describing a graph of relationships between parents and children over multiple generations.
     * The data is formatted as a list of (parent, child) pairs, where each individual is assigned a unique integer identifier.
     * For example, in this diagram, 3 is a child of 1 and 2, and 5 is a child of 4:
     *
     *   1   2   4
     *    \ /   / \
     *     3   5   8
     *      \ / \   \
     *       6   7   10
     *
     * Write a function that takes this data as input and returns two collections: one containing all individuals with zero known parents,
     * and one containing all individuals with exactly one known parent.
     *
     * Sample output (pseudodata):
     * findNodesWithZeroAndOneParents(parentChildPairs) => [
     * [1, 2, 4],    // Individuals with zero parents
     * [5, 7, 8, 10]] // Individuals with exactly one parent
     *
     * This problem looks much more complicated that it actually is. It just involves writing two functions, one for finding nodes
     * with zero parents and the other for finding nodes with only one parent.
     *
     * The graph is in the format of
     *
     * int[][] parentChildPairs = new int[][] {
     *   {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
     *   {4, 5}, {4, 8}, {8, 10}
     * };
     *
     *
     * @param parentChildPairs object we wish to serialize
     * @return List<List<Integer>>
     */
    public List<List<Integer>> findNodesWithZeroAndOneParents(int[][] parentChildPairs) {
        List<List<Integer>> results = new ArrayList<>();
        Set<Integer> nodesWithZeroParents = findNodesWithZeroParents(parentChildPairs);

        results.add(Arrays.asList(nodesWithZeroParents.toArray(new Integer[0])));
        Set<Integer> nodesWithExactlyOneParent = findNodesWithOneParent(parentChildPairs);

        results.add(Arrays.asList(nodesWithExactlyOneParent.toArray(new Integer[0])));
        return results;
    }

    /**
     * Finding nodes with Zero parents, just means, taking each parent, from the 0th
     * column, and then checking to see if they are also present in the child column.
     *
     * This could have been made a little more efficient by keeping track nodes
     * that are present in the child column. If a node is present in the child column,
     * then we we get to that node in the parent column, then we can skip that parent
     * in our loop.
     *
     * @param parentChildPairs int[][]
     * @return set of nodes with zero parents
     */
    public Set<Integer> findNodesWithZeroParents(int[][] parentChildPairs) {
        Set<Integer> results = new HashSet<>();
        for (int i = 0; i < parentChildPairs.length; i++) {
            int parent = parentChildPairs[i][0];
            for (int j = 0; j < parentChildPairs.length; j++) {
                if (parent == parentChildPairs[j][1]) {
                    break;
                } else if (j == parentChildPairs.length - 1) {
                    results.add(parent);
                }
            }
        }
        return results;
    }


    /**
     * findNodesWithOneParent - to find nodes with one parent, we just have to
     * moved down the child column, checking each row for parents that occur
     * more than once.
     *
     * We do this by using to sets. One set for parents that are only present
     * once, and another set for repeat parents. If we find a parent, that is
     * already in our oneChildParents set, then we remove it and add it to the
     * repeatSet.
     *
     * If we find a parent currently not in the oneChild set, we check to see
     * if its been added to the repeat set, if not, then we can add it to our
     * one Child set.
     *
     * We simply return the contents of our oneChild set.
     * @param parentChildPairs int[][]
     * @return set of one child parents
     */
    public Set<Integer> findNodesWithOneParent(int[][] parentChildPairs) {
        Set<Integer> oneChildParents = new HashSet<>();
        Set<Integer> repeatParents = new HashSet<>();

        for (int i = 0; i < parentChildPairs.length; i++) {
            if (oneChildParents.contains(parentChildPairs[i][1])) {
                oneChildParents.remove(parentChildPairs[i][1]);
                repeatParents.add(parentChildPairs[i][1]);
            } else {
                if (!repeatParents.contains(parentChildPairs[i][1])) {
                    oneChildParents.add(parentChildPairs[i][1]);
                }
            }
        }
        return oneChildParents;
    }
}


