package main.java.com.codepath.techscreens;

import main.java.com.codepath.techscreens.objects.Person;
import main.java.com.codepath.techscreens.objects.Position;
import main.java.com.codepath.techscreens.objects.StackRoxNode;
import main.java.com.codepath.techscreens.objects.Booking;
import main.java.com.codepath.techscreens.objects.StudentCoursePair;
import main.java.com.codepath.techscreens.objects.ZerosRectangle;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
     * Sonder
     *
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
     * Karat by way of the Zume company
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

    /*
        Below is a tech screen I received from Quizlet. It was interesting as there was a DB portion
        and a coding portion. I only got through 2.5 questions of the SQL :(. I was rusty because
        I don't typically do a lot of SQL any more.
        Your previous MySQL content is preserved below:

        # Hello from Quizlet!!!

        # Given the following schema:
        #
        # desc teachers;
        # +-----------+--------------+------+-----+---------+-------+
        # | Field     | Type         | Null | Key | Default | Extra |
        # +-----------+--------------+------+-----+---------+-------+
        # | id        | int(11)      | YES  |     | NULL    |       |
        # | name      | varchar(128) | YES  |     | NULL    |       |
        # | school_id | int(11)      | YES  |     | NULL    |       |
        # | age       | int(11)      | YES  |     | NULL    |       |
        # +-----------+--------------+------+-----+---------+-------+
        #
        # desc schools;
        # +-------+--------------+------+-----+---------+-------+
        # | Field | Type         | Null | Key | Default | Extra |
        # +-------+--------------+------+-----+---------+-------+
        # | id    | int(11)      | NO   | PRI | NULL    |       |
        # | name  | varchar(128) | YES  |     | NULL    |       |
        # +-------+--------------+------+-----+---------+-------+

        # Write some SQL to answer the following:

        #
        # 1. Output a list of all teacher names and the name of the school they teach.
        # Select t.name, s.name from teachers t
        # JOIN schools s
        #   ON t.school_id = s.id;
        #
        # 2. Output the school name and average age of teachers at each school.
         # Select avg(t.age), s.name
         # from teachers t
         # JOIN schools s
         #   ON t.school_id = s.id
         # group by (s.name);
        #
        # 3. Output the name and age of the oldest teacher(s) at each school.
         Select max(t.age), school_id
         from teachers t
         GROUP BY (school_id);


        #
        # 4. Output the difference in age between each teacher and the next oldest teacher at each school.
    */

    /**
     * The Coding question from Quizlet. This was fairly easy, just need to make use
     * of Maps.
     * Write a function that takes in a list of key-value mappings
     * and returns true iff they all have the "same schema":
     *
     *   - Each mapping has the same set of keys
     *   - For a given key, each mapping has the same data type for the corresponding value
     */
    public boolean sameSchema(List<Map<String, Object>> inputs) {

        Map<String, Object> map = inputs.get(0);
        Set<String> setOfKeys = map.keySet();
        Map<String, String> typeMap = new HashMap<>();

        for (String key: setOfKeys) {
            Object value = map.get(key);
            typeMap.put(key, value.getClass().getTypeName());
        }

        for (int i = 1; i < inputs.size(); i++) {

            Map<String, Object> candidateMap = inputs.get(i);
            Set<String> keySet = candidateMap.keySet();

            if (keySet.size() != setOfKeys.size()) {
                return false;
            }

            for (String key: keySet) {
                if (!setOfKeys.contains(key)) {
                    return false;
                }

                Object candidateValue = candidateMap.get(key);
                String type = typeMap.get(key);

                if (!candidateValue.getClass().getTypeName().equals(type)) {
                    return false;
                }
            }
        }
        return true;
    }

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

    /**
     * SignalFx
     *
     * This tech screen was given by signal Fx. It wasn't that hard, but it exposed a limitation
     * of mine regarding find duplicates, without using extra memory as well as re-organizing
     * information in array.
     *
     * Given array [3, 6, 2, 3, 6, 5, 2]
     * return only unique values [3, 6, 2, 5]
     *
     * Iteration of this problem is to return
     *
     * [3, 6, 2, 5, -1, -1, -1, -1]
     *
     * While only looping through the array once. To do this we still need a set for
     * storing duplicates but that's ok, its no extra memory. We also need to remember
     * the location of the lastUnique occurrence. Because we want to rearrange the array,
     * with all of the unique occurrences toward the beginning, then if we find another unique value
     * and the position of the last occurrence of a unique value it to its left, as in
     *
     * [3, 6, 2, -1, -1, -1, 5, -1], the position of 2 is i=2, and i=2, is less than i=7.
     *
     * That means the last occurrence of a unique number occurred at i=2, and if, 2 is too
     * the left of 7, that means thare are -1's in between. So, just look to the very right of
     * i=2 (our last occurrence of a unique number), and there should be a -1. Swap the two values,
     * effectively moving 5, to the sopt of the -1, i=3.
     *
     * @param input List of integers
     * @return List<Integer> of unique values
     */
    public List<Integer> getUniqueValuesEff(List<Integer> input) {
        Set<Integer> results = new HashSet(input.size());

        int length = input.size();
        int lastUniqueOccurence = 0;
        for (int i = 0; i < length; i++) {
            if(!results.contains(input.get(i))) {
                results.add(input.get(i));
                if (lastUniqueOccurence < i) {
                    swap(input, i, lastUniqueOccurence + 1);
                }
                lastUniqueOccurence = i;
            } else {
                Integer repeat = new Integer(-1);
                input.set(i, repeat);
            }
        }
        return input;
    }

    /**
     * Swaps to Integer values in a list
     * @param input the list
     * @param index index to swap
     * @param afterLastNonRepeat index to swap
     */
    public void swap(List<Integer> input, int index, int afterLastNonRepeat) {
        Integer negativeValue = input.get(index);
        Integer after = input.get(afterLastNonRepeat);
        input.set(afterLastNonRepeat, negativeValue);
        input.set(index,  after);
    }

    /**
     *
     * Dark store tech screen
     *
     * RUNTIME COMPLEXITY:
     * For the initial bucket map, the run time O(m * n) where m is the number of assets
     * and n is the dimension of the longest position array in our portfolio.
     *
     * We then sort the keys which is O(d log d), where d is the number of unique dates
     * in our portfolio.
     *
     * As we then move through each Integer[] asset array. We use each key, which is
     * a unique date to get the array, the move through the array suming the values.
     * the run time of this is O(d * m), where m is the number of assets, and d
     * is the number of unique dates.
     *
     * The total runtime of this algorithm will be dominated by which ever is larger
     * the O(m * n) or O(d * m). If the market is very volatile, then there could
     * be many position changes, and hence n, could be quite numerous per given
     * asset.
     *
     * If we are looking for totals position totals over a great deal of time,
     * then its possible that O(d * m) could be larger, if d is the number of
     * unique dates.
     *
     * Considering market values of given assets change frequently, its more likely
     * that the O(m * n) will dominate.
     *
     * SPACE COMPLEXITY: For our bucket array we are created an Integer[] per unique date
     * where the dimension of the array is equal to the number of assets so that additional
     * memory is of O(d * m).
     *
     * We store our results in a new position array which is made up of integer totals
     * and dimension d. So that additional memory is O(d).
     *
     * The memory complexity of our bucket map, dominates, so total memory complexity is
     * O(d * m)
     *
     *
     **/
    public Position[] getTotalsFromPortfolio(Position[][] portfolio) {

        /**
         * Originally I thought of this as being nothing more than a 2-D array,
         * we where would essentially take the summation of each column. Every index
         * where there was a null value, we would simply look at that row, in the prior
         * column to get the lastvalue.
         *
         * That approach, of course, doesn't work because all of the rows are not of the same
         * length.
         *
         * Instead I took the approach of creating buckets. Where the buckets are stored in a map.
         * The entry in the map, maps a date to an Integer[] array. The Integer[] array will store
         * the value of the assets.
         *
         * The index of the Integer[] array corresponds to the particular assest. For example
         * Given our first use case of AMZN, GOOG, and MSFT, in our input, AMZN is 0, GOOG is 1,
         * MSFT is 2.
         *
         * The value of the given asset for that day will be stored in this Integer array,
         * using the index a key to quickly look up the value of a given asset. We can always be sure of
         * the number of assests by looking at the row dimension of the portfolio input.
         * One row for each asset.
         *
         * By the end of the execution of these initial 2 for loops for the first use case
         * we end up with a map like so
         *
         * 4/1 => 200      |  null    |  400
         *        AMZN=0      GOOG=1    MSFT=2
         *
         * 4/2 => null      |  500    |  200
         *       AMZN=0      GOOG=1    MSFT=2
         *
         * 4/3 => null      |  700    |  200
         *        AMZN=0      GOOG=1    MSFT=2
         *
         * 4/4 => 400      |  null    |  600
         *        AMZN=0      GOOG=1    MSFT=2
         *
         */
        Map<LocalDate, Integer[]> results = new HashMap<>();
        int numberOfAssets = portfolio.length;
        for (int i = 0; i < portfolio.length; i++) {
            Position[] positions = portfolio[i];
            for (int j = 0; j < positions.length; j++) {
                Position p = positions[j];
                if (!results.containsKey(p.getDate())) {
                    Integer[] dayTotals = new Integer[numberOfAssets];
                    dayTotals[i] = p.getValue();
                    results.put(p.getDate(), dayTotals);
                } else {
                    Integer[] dayTotals = results.get(p.getDate());
                    dayTotals[i] = p.getValue();
                }
            }

        }

        /**
         * Now that we have our buckets, with dates mapped to arrays with
         * the values of each asset on that day, we can now begin to
         * total the assets for each day.
         *
         * Given that for a day, the value of an asset may have not changed
         * we need to know the value of that asset on the last day.
         * Considering the dates are in increasing order, we will sort the keys
         * of our map.
         **/

        List<LocalDate> keys = new ArrayList<>(results.keySet());
        Position[] totalPosition = new Position[keys.size()];
        Collections.sort(keys);

        // Grap the very first Integer[] containing our asset values for the first day.
        // if the subsequent day is missing a value, we will look to the prior day Integer[]
        // for the latest asset value, using the index of the array (aka the asset symbol)
        // as a key
        int lengthOfKeySet = keys.size();
        LocalDate previousDateKey = keys.get(0);

        // So that we can get a position total for our first day, the sum first day integer array
        Integer[] array = results.get(previousDateKey);
        int sum = 0;
        for (Integer n: array) {
            if (n != null) {
                sum += n.intValue();
            }
        }
        totalPosition[0] = new Position(previousDateKey, sum);

        /**
         * Using the sorted keySet, we will start with the second day. Remember
         * we can look up the asset values from the prior day with our
         * previousDateKey...which initially has the asset values from the first day
         *
         * We will now move through the key values, mapped to by our dates, our values
         * being our Integer arrays which are storing the values of each asset
         * where the index is serving as a key.
         *
         * When we come across a index in our asset array that is null, we will look
         * to the prior asset array for that value. If its non null, then we will
         * use it to update the current asset array. Every time a null is found in an
         * asset array, we will take the prior asset arrays value and update that index
         * with its value.
         *
         * Doing this allows checking subsequent asset arrays to only have to look at the
         * prior asset array to get the last value of an asset.
         *
         * As we do this check we will take a sum, and at the end of checking each asset array
         * we will sum the total, and create a new position which will represet the total
         * value of all assets on that given day.
         *
         **/
        for (int i = 1; i < lengthOfKeySet; i++) {
            Integer[] dayTotals = results.get(keys.get(i));
            int total = 0;
            for (int j = 0; j < dayTotals.length; j++) {
                /* j here is our key, which correlates with an asset
                 * if dayTotals[j] == null, for a given asset j, then we
                 * need to check the prior days asset information
                 */
                if (dayTotals[j] == null) {
                    Integer[] proirDayTotals = results.get(previousDateKey);
                    Integer value = proirDayTotals[j];
                    // if non null then take the sum
                    if (value != null) {
                        /* copy the value to this array, as the next asset array might need this
                        * latest value for a given asset.
                        */
                        dayTotals[j] = value;
                        total = Integer.sum(value, total);
                    }
                } else { // the position changed so just take this value and sum it with the current total
                    total = Integer.sum(total, dayTotals[j].intValue());
                }
            }
            /*
             * And the end of each interation we have summed up the total assets for a given day
             * so create the position entry for the day and store it in our return array
             */
            totalPosition[i]= new Position(keys.get(i), total);
            /* update the previousDateKey, so that the next interation of the loop can simply
             * look back to the prior day to get the integer value.
             */
            previousDateKey = keys.get(i);
        }

        return totalPosition;
    }

    public void printArray(LocalDate date, Integer[] array) {
        System.out.print(date + ":");
        for (Integer item: array) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    /**
     * Tech screen from Zume:
     *
     * This tech screen was the follow up to my Zume tech screen. It went well,
     * I had one bug, and one hint from the interview managed to help me fix it.
     *
     * Imagine we have an image. We'll represent this image as a simple 2D array where every pixel is a 1 or a 0.
     * The image you get is known to have a single rectangle of 0s on a background of 1s.
     *
     * Write a function that takes in the image and returns the coordinates of the rectangle
     * of 0's -- either top-left and bottom-right; or top-left, width, and height.
     *
     * findRectangle(image1) =>
     *    x: 3, y: 2, width: 3, height: 2
     *    2,3 3,5 -- row,column of the top-left and bottom-right corners
     *
     * @param image 2 - D array of zeros and ones
     * @return dimensions and start of zeros rectangle
     */
    public ZerosRectangle findRectangleOfZeros(int[][] image) {
        ZerosRectangle result = new ZerosRectangle();
        int rows = image.length;
        int cols = image[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (image[i][j] == 0 && result.beginning == null) {
                    result.createBeginningCell(i, j);
                } else if (image[i][j] == 0) {
                    result.width += 1;
                    if ( ((j + 1) < cols && image[i][j + 1] == 1) || (j + 1) == cols) {
                        countColumnZeros(image, i + 1, j, result);
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public void countColumnZeros(int[][] image, int row, int col, ZerosRectangle result) {
        int length = image.length;
        for (int i = row; i < length; i++) {
            if (image[i][col] == 0) {
                result.height += 1;
            } else {
                return;
            }
        }
    }

    /**
     * Karat via Roblox:
     *
     * This problem presented me with a lot of issues even though its not really difficult.
     * I had troubles breaking down the problem and seeing how simple the solution was.
     * From the outset of the problem it appeared as if it required a recursive
     * dfs solution, but it was much more simple than that. I'm not sure why it was
     * so hard to see the simplicity for what it was.
     *
     *
     * You are in charge of a display advertising program. Your ads are displayed on websites all
     * over the internet. You have some CSV input data that counts how many times that users
     * have clicked on an ad on each individual domain. Every line consists of a click count
     * and a domain name, like this:
     *
     * counts = [ "900,google.com",
     *      "60,mail.yahoo.com",
     *      "10,mobile.sports.yahoo.com",
     *      "40,sports.yahoo.com",
     *      "300,yahoo.com",
     *      "10,stackoverflow.com",
     *      "2,en.wikipedia.org",
     *      "1,es.wikipedia.org",
     *      "1,mobile.sports" ]
     *
     * Write a function that takes this input as a parameter and returns a data structure containing
     * the number of clicks that were recorded on each domain AND each sub domain under it.
     * For example, a click on "mail.yahoo.com" counts toward the totals for "mail.yahoo.com",
     * "yahoo.com", and "com". (Sub domains are added to the left of their parent domain.
     * So "mail" and "mail.yahoo" are not valid domains. Note that "mobile.sports" appears
     * as a separate domain as the last item of the input.)
     *
     * Sample output (in any order/format):
     *
     * calculateClicksByDomain(counts)
     * 1320    com
     *  900    google.com
     *  410    yahoo.com
     *   60    mail.yahoo.com
     *   10    mobile.sports.yahoo.com
     *   50    sports.yahoo.com
     *   10    stackoverflow.com
     *    3    org
     *    3    wikipedia.org
     *    2    en.wikipedia.org
     *    1    es.wikipedia.org
     *    1    mobile.sports
     *    1    sports
     *
     *
     * @param counts String array
     * @return Map of domains and their counts
     */
    public Map<String,Integer> aggregateCounts(String[] counts) {
        Map<String,Integer> results = new HashMap<>();
        for (String s: counts) {
            String[] items = s.split(",");

            int count = Integer.valueOf(items[0]);
            List<String> domains = getDomains(items[1]);
            for (String domain: domains) {
                if (!results.containsKey(domain)) {
                    results.put(domain, count);
                } else {
                    Integer currentCount = results.get(domain);
                    results.put(domain, Integer.sum(currentCount, count));
                }
            }
        }

        return results;
    }

    public List<String> getDomains(String domain) {
        int length = domain.length();
        List<String> subDomains = new ArrayList<>();
        for (int i = length - 1; i >= 0; i--) {
            if(domain.charAt(i) ==  '.') {
                subDomains.add(domain.substring(i + 1, length));
            }
        }
        subDomains.add(domain);
        return subDomains;
    }

    /**
     * Test from Codility for Wayfair
     * Given an array of integers, return the smallest non negative
     * integer, not in the array. Can't return 0, but must be at least 1
     *
     * input: [1, 3, 4, 2, 1, 6]
     * output: 5
     *
     * This is an interesting problem with a number of different solutions.
     * The simplest is to search through the array, storing numbers that are larger
     * greater than 0. Store the integers in a Set
     *
     * If, the set is empty that means there were no positive integers so we can
     * simply return 1, as the largest non negative integer missing.
     *
     * If the set is not empty, then we simply check, from 1 to the max number in
     * the input. The first number the set doesn't contain, is the smallest missing number.
     *
     * If we make it through the entire set, and nothing is missing, then that means
     * we should return the max number + 1.
     *
     * RUNTIME: O(N)
     * SPACE: O(N)
     * @param A input array of integers
     * @return smallest missing positive integer
     */
    public int findSmallestMissingPositiveInteger(int[] A) {
        Set<Integer> integers = new HashSet<>(A.length);
        int max = Arrays.stream(A).max().getAsInt();
        for (int i = 0; i < A.length; i++) {
            if (A[i] > 0) {
                integers.add(A[i]);
            }
        }

        if (integers.isEmpty()) {
            return 1;
        }

        for (int i = 1; i < max; i++){
            if (!integers.contains(i)) {
                return i;
            }
        }

        return max + 1;
    }

    /**
     *
     * My second tech screen with Karat via Roblox. Again...I fucked it up.
     *
     * I definitely need practice on problems that require a lot of hash maps.
     * I didn't exactly understand how taking the approach I had chosen was going to work
     * but I thought it was mostly there and it really bit me in the ass. The approach
     * I initially took was mapping classes to students, this seemed promising,
     * but I couldn't make it work.
     *
     * BEFORE CODING ANYTHING...make sure I have the right approach first.
     *
     * After struggling after the tech screen I switched up it up. In this case all I had
     * to do was build a hash map with students mapped to their courses. Then to find the
     * over lap, I just had to iterate over the keys (the students)
     * for each student, and compare student to student with a for loop. My problem was, I didn't
     * know how to generate these pairs, but it should of been obvious. Its just a double for loop.
     * Pairing each student with the other.
     *
     * We would simply skip keys that matched, and make sure we didn't generate the same pair
     * twice by storing students we had already paired in a HashMap with students mapping
     * to a set of students that it had been paired with.
     *
     * Once I realized that was the correct approach to take, writing the code wasn't complicated.
     *
     * FUCKING GODDDAMNIT!!
     *
     * You are a developer for a university. Your current project is to develop a system for students
     * to find courses they share with friends. The university has a system for querying courses students
     * are enrolled in, returned as a list of (ID, course) pairs. Write a function that takes in a list
     * of (student ID number, course name) pairs and returns, for every pair of students, a list of all courses
     * they share.
     * Sample Input:
     * student_course_pairs = [["58", "Software Design"],
     * ["58", "Linear Algebra"],
     *   ["94", "Art History"],
     *   ["94", "Operating Systems"],
     *   ["17", "Software Design"],
     *   ["58", "Mechanics"],
     *   ["58", "Economics"],
     *   ["17", "Linear Algebra"],
     *   ["17", "Political Science"],
     *   ["94", "Economics"]
     *   ]
     *   Sample Output (pseudocode, in any order)
     *
     *   find_pairs(student_course_pairs) => {
     * [58, 17]: ["Software Design", "Linear Algebra"]
     * [58, 94]: ["Economics"]
     * [17, 94]: []
     * }
     */
    public List<StudentCoursePair> find_pairs(String[][] input) {
        List<StudentCoursePair> results = new ArrayList<>();
        Map<String, Set<String>> studentsToClasses = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            if (!studentsToClasses.containsKey(input[i][0])) {
                Set<String> classes = new HashSet<>();
                classes.add(input[i][1]);
                studentsToClasses.put(input[i][0], classes);
            } else {
                Set<String> classes = studentsToClasses.get(input[i][0]);
                classes.add(input[i][1]);
            }
        }

        Map<String, Set<String>> alreadyCoupled = new HashMap<>();
        for (String key : studentsToClasses.keySet()) {
            Set<String> classes = studentsToClasses.get(key);
            for (String otherKey: studentsToClasses.keySet()) {
                if (key.equals(otherKey)) {
                    continue;
                }


                if (alreadyCoupled.containsKey(otherKey)) {
                    Set<String> setOfCoupledStudents = alreadyCoupled.get(otherKey);
                    if (setOfCoupledStudents.contains(key)) {
                        continue;
                    }
                }
                Set<String> otherClasses = studentsToClasses.get(otherKey);
                List<String> classesForPair = new ArrayList<>();
                for (String schoolClass: classes) {
                    if (otherClasses.contains(schoolClass)) {
                        classesForPair.add(schoolClass);
                    }
                }

                if (!alreadyCoupled.containsKey(key)) {
                    Set<String> coupledStudents = new HashSet<>();
                    coupledStudents.add(otherKey);
                    alreadyCoupled.put(key, coupledStudents);
                } else {
                    Set<String> coupledStudents = alreadyCoupled.get(key);
                    coupledStudents.add(otherKey);
                }

                StudentCoursePair pair = new StudentCoursePair();
                pair.students.add(key);
                pair.students.add(otherKey);
                pair.classes.addAll(classesForPair);
                results.add(pair);
            }
        }
        return results;
    }
}


