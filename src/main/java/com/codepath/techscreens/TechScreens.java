package main.java.com.codepath.techscreens;

import main.java.com.codepath.techscreens.objects.Booking;
import main.java.com.codepath.techscreens.objects.Cell;
import main.java.com.codepath.techscreens.objects.Employee;
import main.java.com.codepath.techscreens.objects.Person;
import main.java.com.codepath.techscreens.objects.Position;
import main.java.com.codepath.techscreens.objects.Range;
import main.java.com.codepath.techscreens.objects.StackRoxNode;
import main.java.com.codepath.techscreens.objects.StudentCoursePair;
import main.java.com.codepath.techscreens.objects.Tile;
import main.java.com.codepath.techscreens.objects.ZerosRectangle;
import main.java.com.codepath.techscreens.objects.Range;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
    private static final String CHILD = "child";
    private static final String PARENT = "parent";
    private static final String UNKNOWN = "unknown";
    private static final String ANCESTOR = "ancestor";
    private static final Map<String, Set<String>> relationshipMap = new HashMap<>();

    private static final String CSV = "Name,Parent1,Parent2\n"
        + "Chris,Bret,Annie\n"
        + "Daphne,Chris,Emily\n"
        + "Fred,Chris,Emily\n"
        + "Henry,George,Daphne\n"
        + "Ivy,George,Daphne\n"
        + "Jack,George,Daphne";


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

    public boolean isConsonant(char ch) {
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


    /**
     * Tech screen from WeWork
     *
     * Suppose a array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e., [0,1,2,4,5,6,7,8] might become [4,5,6,7,8,0,1,2]).
     *
     * You are given a target value to search. If found in the array return its index, otherwise return -1.
     *
     * You may assume no duplicate exists in the array.
     *
     * Your algorithm's runtime complexity must be in the order of O(log n).
     *
     * This problem problem gave me a great deal of difficulty because I hadn't reviewed how to do
     * properly do a binary search. I was able to come up with algorithm, but my approach was flawed
     * the idea is a play on binary search. The goal is to keep dividing the array in half
     * looking for the sorted portion of the array. When you find the sorted portion
     * and the target is in between the left and right values, then just run binarySearch.
     *
     * If not, then continue to recurse on the left and right half, until you find the
     * sorted portion where your target is in between the left and right.
     *
     * The key to this problem as to start with the conditions for running binarySearch first.
     * Then if you didn't find the necessary conditions to do such, then go ahead and apply the
     * wrinkle to this problem, to keep searching for the sorted half of the array that contains
     * your target.
     *
     * @param nums array of integers
     * @param target integer
     * @return the index of the integer or -1
     */
    public int findTargetInSplitArray(int[] nums, int target) {
        return findTargetInSplitArrayHelper(nums, 0, nums.length - 1, target);
    }

    public int findTargetInSplitArrayHelper(int[] nums, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        int mid = (right + left) / 2;

        if (nums[mid] == target) {
            return mid;
        }

        if (nums[left] <= nums[mid] && isInBetween(nums[left], target, nums[mid])) {
            // This portion of the array is sorted and our target is in between the bounds
            return binarySearch(nums, left, mid, target);
        } else if (nums[mid] <= nums[right] && isInBetween(nums[mid], target, nums[right])) {
            // This portion of the array is sorted and our target is in between the bounds
            return binarySearch(nums, mid + 1, right, target);
        } else if (nums[mid] >= nums[right]) {
            // This portion must have the rotation point, so recurse and look for the sorted portion
            return findTargetInSplitArrayHelper(nums, mid + 1, right, target);
        } else if (nums[left] >= nums[mid]){
            // This portion must have the rotation point, so recurse and look for the sorted portion
            return findTargetInSplitArrayHelper(nums, left, mid, target);
        }
        return -1;
    }

    private boolean isInBetween(int leftBound, int number, int rightBound) {
        return leftBound <= number && number <= rightBound;
    }

    /**
     * Binary search always fucks me up because I always mess up the base case.
     * considering we are constantly moving the left and right indices towards each other,
     * when they cross, i.e. left > right, then we should stop. THATS ALL.
     *
     * @param nums array of integers
     * @param left the left bound of the array
     * @param right the right bound of the array
     * @param target the target value we are searching for
     * @return the index of our target value
     */
    public int binarySearch(int[] nums, int left, int right, int target) {
        if (left > right) {
            return -1;
        }

        int mid = (right + left) / 2;

        if (nums[mid] == target) {
            return mid;
        }

        if (target < nums[mid]) {
            return binarySearch(nums, left, mid, target);
        } else {
            return binarySearch(nums, mid + 1, right, target);
        }
    }

    public boolean oneEditAway(String one, String two) {
        int lengthOfOne = one.length();
        int lengthOfTwo = two.length();

        if (lengthOfOne == 0 && lengthOfTwo == 0) {
            return true;
        }

        if ((lengthOfOne == 1 && lengthOfTwo == 0) || lengthOfOne == 0 && lengthOfTwo == 1) {
            return true;
        }

        if (Math.abs(lengthOfOne - lengthOfTwo) > 1) {
            return false;
        }

        int ptrOne = 0;
        int ptrTwo = 0;
        int differences = 0;
        while (ptrOne <= lengthOfOne || ptrTwo <= lengthOfTwo) {
            if (differences > 1) {
                return false;
            }

            if (ptrOne == lengthOfOne && ptrTwo == lengthOfTwo) {
                return differences <= 1;
            }

            if (ptrOne == lengthOfOne || ptrTwo == lengthOfTwo) {
                differences += Math.abs(lengthOfOne - lengthOfTwo);
                return differences <= 1;
            }

            if (one.charAt(ptrOne) == two.charAt(ptrTwo)) {
                ptrOne++;
                ptrTwo++;
            } else if (lengthOfOne == lengthOfTwo) {
                differences++;
                ptrOne++;
                ptrTwo++;
            } else if (lengthOfOne > lengthOfTwo) {
                ptrOne++;
                differences++;
            } else {
                ptrTwo++;
                differences++;
            }
        }
        return true;
    }

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
     * The following was a question from an online amazon assessment
     *
     * Write an algorithm to compute the minimum amount of time to put N items together
     * worker can only combine 2 items at a time. Time require to put two parts together is
     * equal to the sum of the part sizes. The size of the newly constructed part is equal to the sum
     * of the part sizes.
     *
     * I tried doing this problem using Dynamic Programming, and I think that was a mistake. DP
     * problems require overlapping sub problem and it took me too long to see, that this problem
     * DOESN"T have overlapping sub problems, because the problem space changes. We keep
     * collasping the iput array until we have two numbers, and then we return that up
     * the stack, taking the minimum sum.
     *
     * The recusion isn't even that hard, I simply spent too much time going down the incorrect approach.
     *
     * @param numOfParts int
     * @param parts list of integers
     * @return minimum time
     */
    public int minimumTime(int numOfParts, List<Integer> parts) {
        if (numOfParts == 2) {
            return parts.get(0).intValue() + parts.get(1).intValue();
        }

        int firstSmallest = findKthSmallest(parts);
        int firstSmallestValue = parts.get(firstSmallest).intValue();
        parts.set(firstSmallest, Integer.MAX_VALUE);

        int secondSmallest = findKthSmallest(parts);
        int secondSmallestValue = parts.get(secondSmallest).intValue();
        parts.set(secondSmallest, Integer.MAX_VALUE);
        int sum = firstSmallestValue + secondSmallestValue;

        List<Integer> newParts = new ArrayList<>();
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i) < Integer.MAX_VALUE) {
                newParts.add(parts.get(i));
            }
        }

        newParts.add(sum);
        return sum + minimumTime(newParts.size(), newParts);
    }

    /**
     * Finds the smallest Element.
     * @param list of integers
     * @return
     */
    public int findKthSmallest(List<Integer> list)  {
        int length = list.size();
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < length; i++) {
            Integer n = list.get(i);
            if (min > n) {
                min = n;
                index = i;
            }
        }
        return index;
    }

    /**
     * The second question from an online assessment from Amazon. A straight forward graphing
     * problem, an implementation of BFS.
     *
     * Just remember that, every neighbor cell we visit, the distance to that cell is
     * the parent distance + 1;
     *
     * When we find our destination cell, while visiting each neighbor, thats when we return
     * our shortest distance.
     * @param numRows
     * @param numCols
     * @param lot
     * @return
     */
    public int minDistanceToRemoveObstacle(int numRows, int numCols, List<List<Integer>> lot) {
        int[] rowMoves = {0, 1, 0, 1};
        int[] colMoves = {-1, 0, 1, 0};

        int endingCell = 9;
        int flatLand = 1;

        boolean[][] visited = new boolean[lot.size()][lot.get(0).size()];
        int[][] distance = new int[lot.size()][lot.get(0).size()];

        Queue<Cell> queue = new LinkedList<>();
        Cell startingCell = new Cell(0, 0);
        queue.add(startingCell);
        while (!queue.isEmpty()) {
            Cell parentCell = queue.poll();
            if (visited[parentCell.row][parentCell.col] == false) {
                visited[parentCell.row][parentCell.col] = true;
                for (int i = 0; i < 4; i++) {
                    if (isValidMove(parentCell.row + rowMoves[i], parentCell.col + colMoves[i], lot)) {
                        int nextRow = parentCell.row + rowMoves[i];
                        int nextCol = parentCell.col + colMoves[i];
                        if (lot.get(nextRow).get(nextCol) == flatLand) {
                            distance[nextRow][nextCol] = distance[parentCell.row][parentCell.col] + 1;
                            Cell neighborCell = new Cell(nextRow, nextCol);
                            queue.add(neighborCell);
                        } else if (lot.get(nextRow).get(nextCol) == endingCell) {
                            distance[nextRow][nextCol] = distance[parentCell.row][parentCell.col] + 1;
                            return distance[nextRow][nextCol];
                        }
                    }
                }
            }
        }
        return -1;
    }

    private boolean isValidMove(int row, int col, List<List<Integer>> lot) {
        return row >= 0 && row < lot.size() && col >= 0 && col < lot.get(0).size();
    }

    /**
     * Facebook Tech Screen
     *
     * We have a list of various types of tasks to perform. Task types are identified by number:
     * task type 1, task type 2, task type 3, etc.
     *
     * Each task takes 1 time slot to execute, and requires a cool down to recover before we can execute
     * another task of the same type.  However, we can execute tasks of other types in the meantime.
     * The recovery interval is the same for all task types.
     *
     * Given a list of input tasks to run, and the cool down interval, output the number of
     * time slots required to run them in the given order of the tasks.
     *
     * tasks = [1, 1, 2, 1]
     * cooldown = 2
     * Output: 7  (order is 1 _ _ 1 2 _ 1)
     *
     * I found this problem to be very difficult. My initial intiution on how to solve the problem
     * was incorrect. Initially I thought that, given the task that I was on, I had to check
     * the previous task type to see if it was of the same type, and if it was add the cool down
     * period to the total. This was incorrect of course. Checking the previous task type won't
     * help if the cool down period is large. The last task of the same type might be any number of
     * time slots away.
     *
     * This problem is a good exercise in learning strategies for how to break down a tricky
     * problem and solve it, when there is no obvious way to solve it, and when it clearly
     * doesn't map to any class of algorithms.
     *
     * The information given in the problem, that is a list of tasks, and that, there is a
     * cool down period, and that every task takes up at least one time slot, directly hint
     * that this problem is an array type of problem.
     *
     * I think that the strategy for learning how to do the problem, will probably come from
     * breaking the problem down into its simplest case, and building up from there. So
     * lets try that.
     *
     * Lets assume the cool down is 2
     * [1] => 1, the time is clearly one, as there is only one time slot
     * [1 2] => 2, the time is 2, because neither task is of the same type
     * [1,2,3] => 3, the same rationale as before
     *
     * [1,1] => 4, the time is +1 for doing the first take, then +2 for cool down period,
     * because the next task is of the same type, +1 again for the time to execute the
     * second task 1
     *
     * [1, 2, 1] => 4 (1, 2, _, 1). Again this is also 4, because the first task executes,
     * then the next task does, but we must wait one time slot because we can't execute
     * the next taskType 1 because its within the cool down period of the first one.
     *
     * This should give away how to do the problem. Anytime we execute a given task type,
     * we should check to see if there is another task type of the same type,
     * withing the given cool down period. All that means is if taskType 1 is
     * at position i, then check if there is another task type 1, within
     * [i + 1,...,i + 1 + cooldown). If there is, then calculate the extra time
     * that would be required. If a task type of the same type is found within
     * the cool down period, then we just check the magnitude of how far away
     * its from the beginning of the boundary (i + 1).
     *
     * If there is no task type found withing this cool down period, then
     * there is no extra time to add, because the other tasks can run
     * during the cool down period. here the time slot is the index
     * and the cooldown is merely the current position + the cool down.
     *
     * Given the format information is given to us for the problem,
     * task types in a list, and the fact they must be executed in order,
     * we must ask ourselves what the problem is telling us, and how the
     * format of the information can help us. if we are having trouble,
     * we should then break the problem down into its very simplest case
     * and slowly build up from their until the rationale for solving it
     * becomes clear.
     *
     * @param array int array
     * @param coolDown int
     * @return total number of time slots
     */
    public int getTimeToCompleteTasks(int[] array, int coolDown) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            count += 1;
            count += calculateRemainingTime(array, array[i], i + 1, coolDown);
        }
        return count;
    }

    private int calculateRemainingTime(int[] array, int taskType, int nextIndex, int coolDown) {
        for (int i = nextIndex; i < nextIndex + coolDown; i++) {
            if (i < array.length) {
                if (array[i] == taskType) {
                    return (nextIndex + coolDown) - i;
                }
            }
        }
        return 0;
    }

    /**
     * Thumbtack Tech screen, number 2.
     *
     * This tech screen was straight forward.
     *
     * You have a dictionary (set) of words. You need to implement a spellchecker that
     * returns the correct word for an input word.
     *
     * The spellchecker only handles the following two class of spelling mistakes -
     *
     * Capitalization
     * Example 1: <set: {yellow, radish}, input: yelloW, output: yellow>
     * Example 2: <set: {Yellow, radish}, input: yelloW, output: Yellow> // yolloW => Yellow
     *
     * Vowels (letters in the set {a,e,i,o,u}) mixed up - consonants are in the correct order,
     * but one or more vowels in the input word is/are not the same as the vowels in the
     * corresponding dictionary word.
     * Example 1: <set: {yellow, radish}, input: yollow, output: yellow //>
     * yellow is a valid suggestion for yollow
     * Example 2: <set: {yellow, radish}, input: redosh, output: radish> // redosx => radish -- NO_MATCH
     * In addition, the following cases should be handled -
     *
     * When there is no valid suggestion, your function should return the string "NO_MATCH".
     * When there is more than one valid suggestion, your function can return any one of them.
     * When there is no spelling mistake in the input (exact match found), your function should
     * return the same word back.
     *
     * What I messed up with this problem, was the follow up question, where the interview asked
     * what pre-processing could we do to make this faster. I struggled with this. The current run
     * time was O(n * c), where n is the number of words in the dictionary, and c is the number
     * of characters in the word we wish to spell check.
     *
     * I think the thing I need to remember, is when asked these questions, how can I make them of
     * order of magnitude faster. So this is polynomial, so how could I make it linear? Making
     * it linear would be simply iterating over the dictionary, and making the check with our input
     * constant. How could we do that?
     *
     * Given that the spell checking is largely based on the positions of consonants, what I could
     * do is create a wild card for the input. So that the mispelled word "yollow", has wildcards
     * like "y*ll*w". The dictionary, could also be in this format, such that the key "y*ll*w",
     * would map to a set of valid words where all of the consonants are in the same format.
     *
     * @param dictionary set of words
     * @param input the word we wish to return a suggestion for
     * @return String
     */
    public String spellChecker(Set<String> dictionary, String input) {
        if (input == null || input.isEmpty()) {
            return "NO_MATCH";
        }
        for (String s: dictionary) {
            if(isMatch(s, input)) {
                return s;
            }
        }
        return "NO_MATCH";
    }

    private boolean isMatch(String word1, String word2) {
        int lengthOne = word1.length();
        int lengthTwo = word2.length();
        if (lengthOne != lengthTwo) {
            return false;
        }

        for (int i = 0; i < lengthOne; i++) {
            char ch1 = Character.toLowerCase(word1.charAt(i));
            char ch2 = Character.toLowerCase(word2.charAt(i));
            if (isConsonant(ch1) && isConsonant(ch2)) {
                if (ch1 != ch2) {
                    return false;
                }
            }
        }
        return true;
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

    /**
     * DROP BOX tech screen. Code doesn't compile so its commented out.
     /*
     * To execute Java, please define "static void main" on a class
     * named Solution.
     *
     * If you need more classes, simply define them inline.
     */

//    class Solution {
//        /**
//         * function(filePath, byte[] array) => true, if array is in the filePath or else
//         *
//         */
//        public boolean contains(String filePath, byte[] array) {
//
//            if (filePath == null) {
//                return false;
//            }
//
//            if (filePath.length == 0) {
//                return false;
//            }
//
//            if (array == null || array.length == 0) {
//                return false;
//            }
//
//            String contents = BufferReader(filePath);
//            return contents.contains(new String(array));
//        }
//
//        public boolean containsMoreEff(String filePath, byte[] array) {
//
//            if (filePath == null) {
//                return false;
//            }
//
//            if (filePath.length == 0) {
//                return false;
//            }
//
//            if (array == null || array.length == 0) {
//                return false;
//            }
//
//            // stream(filepath, starting index, number of bytes)
//            // "Hello World my name is", "d my n"
//            // 0   array.length
//            // 1   array.length + 1
//            // 2   array.length + 2
//            int lowerBound = 0
//            int upperBound = array.length;
//            boolean eofFound = false;
//            while (!eofFound) {
//                byte[] chunk = stream(filepath, lowerBound, upperBound);
//                if (isEquals(chunk, array)) {
//                    return true;
//                }
//                lowerBound++;
//                upperBound++;
//                eofFound = containsEof(chunk)
//            }
//            return false;
//        }
//
//        public boolean isEquals(byte[] array1, byte[] array2) {
//
//            if (array1 == null && array2 == null) {
//                return true;
//            }
//
//            if (array1 == null && array2 != null) {
//                return false;
//            }
//
//            if (array1 != null && array2 == null) {
//                return false;
//            }
//
//            if (array1.length != array2.length) {
//                return false;
//            }
//
//            for( int i = 0; i < array1.length; i++) {
//                if (array1[i] != array2[i]) {
//                    return false;
//                }
//                return true;
//            }
//
//
//
//            public static void main(String[] args) {
//                ArrayList<String> strings = new ArrayList<String>();
//                strings.add("Hello, World!");
//                strings.add("Welcome to CoderPad.");
//                strings.add("This pad is running Java " + Runtime.version().feature());
//
//                for (String string : strings) {
//                    System.out.println(string);
//                }
//            }
//        }


    /**
     * DROP BOX Tech Screen
     * function(filePath, byte[] array) => true, if array is in the filePath or else
     *
     */
//    public boolean contains(String filePath, byte[] array) {
//
//        if (filePath == null) {
//            return false;
//        }
//
//        if (filePath.length == 0) {
//            return false;
//        }
//
//        if (array == null || array.length == 0) {
//            return false;
//        }
//
//        String contents = BufferReader(filePath);
//        return contents.contains(new String(array));
//    }

//    public boolean containsMoreEff(String filePath, byte[] array) {
//
//        if (filePath == null) {
//            return false;
//        }
//
//        if (filePath.length == 0) {
//            return false;
//        }
//
//        if (array == null || array.length == 0) {
//            return false;
//        }
//
//        // stream(filepath, starting index, number of bytes)
//        // "Hello World my name is", "d my n"
//        // 0   array.length
//        // 1   array.length + 1
//        // 2   array.length + 2
//        int lowerBound = 0
//        int upperBound = array.length;
//        boolean eofFound = false;
//        // O(# of bytes of filePath contents)
//        // (# of bytes in filePath contents * # of bytes in filePath contents)
//
//        while (!eofFound) {
//            byte[] chunk = stream(filepath, lowerBound, upperBound); O(# of size search array)
//            if (isEquals(chunk, array)) { // O(n) # of bytes in search array
//                return true;
//            }
//            lowerBound++;
//            upperBound++;
//            eofFound = containsEof(chunk) // O(n) # of bytes in search array
//        }
//        return false;
//    }

  /*
  RollingHash():
      add_bytes(x) # adds x bytes to right side of a buffer in O(len(x)) time
      remove_bytes(x) # removes x bytes from left side of a buffer in O(len(x)) time
      hash()  # returns hash of buffer in O(1) time

      boolean eofFound = false;
      int x = array.length;
      Buffer buff = new Buffer();
      buff.add_bytes(x);
      while (!buff.isEmpty()) {
         byte[] chunk = buff.get(x)
         if (chunk.hash() == array.hash()) {
            return true;
         }

         buff.remvove(x);
         buff.add_bytes(x);
      }

  */

//    public boolean isEquals(byte[] array1, byte[] array2) {
//
//        if (array1 == null && array2 == null) {
//            return true;
//        }
//
//        if (array1 == null && array2 != null) {
//            return false;
//        }
//
//        if (array1 != null && array2 == null) {
//            return false;
//        }
//
//        if (array1.length != array2.length) {
//            return false;
//        }
//
//        for( int i = 0; i < array1.length; i++) {
//            if (array1[i] != array2[i]) {
//                return false;
//            }
//            return true;
//        }

    public void printEmployeeChart(String input) {
        Map<Integer, Employee> employeeMap = new HashMap<>();
        String[] records = input.split(",");
        List<Employee> employeeList = new ArrayList<>();
        for (String record: records) {
            Employee emp = new Employee(record);
            employeeList.add(emp);
            if (!employeeMap.containsKey(emp.id)) {
                employeeMap.put(emp.id, emp);
            }
        }

        Employee root = findRoot(employeeMap);
        Map<Integer, List<Employee>> adjList = getAdjList(employeeList);
        printChart(root, adjList);
    }

    private void printChart(Employee root,  Map<Integer, List<Employee>> adjList) {
        printChartHelper(root, adjList, "");
    }

    private void printChartHelper(Employee root,  Map<Integer, List<Employee>> adjList, String dash) {
        System.out.println(dash + root);
        List<Employee> list = adjList.get(root.id);
        if (list != null) {
            for (Employee emp: list) {
                printChartHelper(emp, adjList, "-" + dash);
            }
        }
    }

    private Employee findRoot(Map<Integer, Employee> employeeMap) {
        Set<Integer> keys = employeeMap.keySet();
        for (Integer key: keys) {
            Employee emp = employeeMap.get(key);
            if (!employeeMap.containsKey(emp.managerId)) {
                return emp;
            }
        }
        return null;
    }

    private Map<Integer, List<Employee>> getAdjList(List<Employee> employeeList) {
        Map<Integer, List<Employee>> adjList = new HashMap<>();
        for (Employee emp: employeeList) {
            for (Employee neighbor: employeeList) {
                if (emp.id == neighbor.id) {
                    continue;
                }
                if (emp.id == neighbor.managerId) {
                    if (!adjList.containsKey(emp.id)) {
                        List<Employee> neighborList = new ArrayList<>();
                        neighborList.add(neighbor);
                        adjList.put(emp.id, neighborList);
                    } else {
                        List<Employee> list = adjList.get(emp.id);
                        list.add(neighbor);
                    }
                }
            }
        }
        return adjList;
    }

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

    public List<Cell> getPath(Tile[][] floor, int startRow, int startCol, int endRow, int endCol) {
        //up, down, right, left
        int[] rowMoves = {-1, 1, 0, 0};
        int[] colMoves = { 0, 0, 1, -1};

        // cell[0] is start, cell[1] is end
        Cell[] cells = findStartAndEnd(floor, startRow, startCol, endRow, endCol);
        boolean[][] visited = new boolean[floor.length][floor[0].length];

        Map<Cell, Cell> pathMap = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        queue.add(cells[0]);
        while (!queue.isEmpty()) {
            Cell parentCell = queue.poll();
            Tile parentTile = floor[parentCell.row][parentCell.col];
            visited[parentCell.row][parentCell.col] = true;
            for (int i = 0; i < 4; i++) {
                int tileRow = parentCell.row + rowMoves[i];
                int tileCol = parentCell.col + colMoves[i];
                if (isValidMove(tileRow, floor.length, tileCol, floor[0].length)) {
                    if (visited[tileRow][tileCol] == false) {
                        Tile tile = floor[tileRow][tileCol];
                        if (tileRow == endRow && tileCol == endCol) {
                            Cell neighborCell = new Cell(tileRow, tileCol);
                            pathMap.put(neighborCell, parentCell);
                            break;
                        }
                        if (tile.color == parentTile.color && !tile.isBlocked) {
                            Cell neighborCell = new Cell(tileRow, tileCol);
                            queue.add(neighborCell);
                            pathMap.put(neighborCell, parentCell);
                        }
                    }
                }
            }
        }

        List<Cell> path = new LinkedList<>();
        Cell nextCell = pathMap.get(cells[1]);
        if (nextCell != null) {
            path.add(cells[1]);
            while (nextCell != null) {
                path.add(0, nextCell);
                Cell current = pathMap.get(nextCell);
                nextCell = current;
            }
        }

        return path;
    }

    public boolean isValidMove(int row, int rowDimension, int col, int colDimension) {
        return (row >= 0 && row < rowDimension) && (col >= 0 && col < colDimension);
    }

    public Cell[] findStartAndEnd(Tile[][] floor, int startRow, int startCol, int endRow, int endCol) {
        Cell[] cells = new Cell[2];
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor[0].length; j++) {
                if (i == startRow && j == startCol) {
                    Cell startCell = new Cell(i, j);
                    cells[0] = startCell;
                    continue;
                }
                if (i == endRow && j == endCol) {
                    Cell endCell = new Cell(i, j);
                    cells[1] = endCell;
                    continue;
                }
            }
        }
        return cells;
    }

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

    /**
     *
     * Uber tech screen.
     *
     * Get the next number. Write a function that rearrange input elements into to generate the immediate next value,
     * if is not possible (is be biggest one) return the smaller one.
     *
     * Examples:
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     *
     *
     * 1, 2, 7, 6, 5, 4, 3, 1 => 1, 3, 1, 2, 4, 5, 6, 7
     *
     * This problem, I found to be tricky. In the end i was able to get a working solution, however
     * i needed to be given some hints.
     *
     * The problem goes as follows, if the numbers are such that, every digit, moving from right
     * to left, is in ascending order, then there is no next largest number to generate
     * and we infact have to wrap around, but reversion the number, look at example
     *
     * 5 1 1 => 1, 1, 5...there is no next larget number to make for the array 5, 1, 1, so the
     * smallest number to be made with this digits is infact the reverse of the array.
     *
     * For other cases, we essentially do, is move from right to left, looking for an inflection
     * point.
     *
     * When we find that point, we record that index. We then move from that inflection point
     * to the right, looking for the smallest number that is larger that our inflection point.
     *
     * Those are the numbers we should swap to make the next largest number. At this point,
     * everything to the right of the inflection point should be sorted, so then we can
     * simply reverse that portion of the array to generate the next largest number.
     *
     * @param input in[] array
     */
    public void findNextLargest(int[] input) {
        int inflectionPoint = -1;
        for (int i = input.length - 1; i >= 0; i--) {
            if (i - 1 >= 0) {
                if (input[i - 1] < input[i]) {
                    inflectionPoint = i - 1;
                    break;
                }
            }
        }

        if (inflectionPoint != -1) {
            int toSwap = getNextLargest(input[inflectionPoint], inflectionPoint + 1, input);
            swap(inflectionPoint, toSwap, input);
            reverse(input, inflectionPoint + 1);
        } else {
            reverse(input, inflectionPoint + 1);
        }
    }

    public void reverse(int[] input, int start) {
        int steps = (input.length - start) / 2;
        int j = 0;
        for (int i = start; i < start + steps; i++) {
            swap(i, input.length - j - 1, input);
            j++;
        }
    }
    public void swap(int i, int j, int[] input) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

    public int getNextLargest(int value, int start, int[] input) {
        int nextLargest = Integer.MAX_VALUE;
        int nextLargestIndex = -1;
        for (int i = start; i < input.length; i++) {
            if (input[i] > value && input[i] < nextLargest) {
                nextLargest = input[i];
                nextLargestIndex = i;
            }
        }
        return nextLargestIndex;
    }

    private static final List<String[]> folders = Arrays.asList(new String[][]{
        {"A", null},
        {"B", "A"},
        {"C", "B"},
        {"D", "B"},
        {"E", "A"},
        {"F", "E"},
    });

    /**
     * Dropbox 2nd techs screen
     *
     * Filesystem and trees, checking for access
     *
     *  /A
     *  |___ /B
     *  |     |___ /C <-- access
     *  |     |___ /D
     *  |___ /E <-- access
     *        |___ /F
     *
     * @param folder
     * @param access
     * @return
     */
    public boolean hasAccess(String folder, Set<String> access) {

        // run time : O(n)
        // space: O(1)
        Map<String, String> adjMap = createAdjMap();

        // run O(1)
        if (access.contains(folder)) {
            return true;
        }

        //run O(1)
        String current = adjMap.get(folder);

        // run O(N)
        while (current != null) {
            if (access.contains(current)) {
                return true;
            }
            current = adjMap.get(current);
        }

        return false;
    }

    public boolean efficientHasAccess(String folder, Set<String> access) {
        Map<String, Set<String>> ancestorMap = getGrandParents(folder);
        if (ancestorMap.containsKey(folder)) {
            Set<String> ancestors = ancestorMap.get(folder);
            for (String parent: access) {
                if (ancestors.contains(parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map<String, Set<String>> getGrandParents(String folder) {
        Map<String, Set<String>> ancestorMap = new HashMap<>();
        Map<String, String> adjMap = createAdjMap();

        Set<String> ancestors = new HashSet<>();
        String current = adjMap.get(folder);
        while (current != null) {
            ancestors.add(current);
            current = adjMap.get(current);
        }

        ancestorMap.put(folder, ancestors);
        return ancestorMap;
    }

    public Map<String, String> createAdjMap() {
        Map<String, String> adjMap = new HashMap<>();
        for (String[] strArray: folders) {
            if (strArray[1] != null) {
                adjMap.put(strArray[0], strArray[1]);
            }
        }
        return adjMap;
    }

    public Map<String, Integer> getFlattendedMap(Map<String, Object> map) {
        return getFlattendedMapHelper(map,"");
    }

    private Map<String, Integer > getFlattendedMapHelper(Map<String, Object> map, String parent) {
        Map<String, Integer> result = new HashMap<>();
        Map<String, Integer> mapToMerge = null;

        Set<String> keys = map.keySet();
        for (String key: keys) {
            Object value = map.get(key);
            if (value instanceof Map) {
                Map<String, Object> recurseMap = (Map<String, Object>) value;
                mapToMerge = getFlattendedMapHelper(recurseMap, key);
            } else if (value instanceof Integer) {
                Integer v = (Integer) value;
                result.put(key, v);
            }
        }
        for (String key : keys) {
            Object v = result.get(key);
            if (v instanceof Integer) {
                Integer value = (Integer) v;
                String newKey = getKey(parent, key);
                result.remove(key);
                result.put(newKey, value);
            }
        }
        if (mapToMerge != null && !mapToMerge.isEmpty()) {
            return mergeMaps(result, mapToMerge, parent);
        }
        return result;
    }

    public Map<String, Integer> mergeMaps(Map<String, Integer> result,  Map<String, Integer> mapToMerge, String parent) {
        Map<String, Integer> keyChangedMap = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry: mapToMerge.entrySet()) {
            keyChangedMap.put(getKey(parent, entry.getKey()), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry: result.entrySet()) {
            if (result.get(entry.getKey()) != null) {
                keyChangedMap.put(entry.getKey(), entry.getValue());
            }
        }
        return keyChangedMap;
    }

    private String getKey(String parent, String child) {
        if (parent.isEmpty()) {
            return child;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(parent);
        sb.append(".");
        sb.append(child);
        return sb.toString();
    }

    public int getUniqueSeconds(List<Range> ranges) {
        Set<Integer> uniqueSeconds = new HashSet<>();
        for (Range range: ranges) {
            for (int i = range.start; i <= range.end; i++) {
                if (!uniqueSeconds.contains(i)) {
                    uniqueSeconds.add(i);
                }
            }
        }
        return uniqueSeconds.size();
    }

    public int getUniqueSecondsEff(List<Range> ranges) {
        Collections.sort(ranges);
        int ct = ranges.get(0).end - ranges.get(0).start + 1;
        int length = ranges.size();
        for (int i = 1; i < length - 1; i++) {
            Range priorRange = ranges.get(i - 1);
            Range currentRange = ranges.get(i);
            if (priorRange.end < currentRange.end && priorRange.end > currentRange.start) {
                ct += currentRange.end - priorRange.end;
            } else if (currentRange.start > priorRange.start && currentRange.end > priorRange.end) {
                ct +=  currentRange.end - currentRange.start + 1;
            }
        }
        return ct;
    }

    /*

 /api/price/AAPL
 - 200.00

 - clientId
 - ticker symbol

 - Rate limiter
 - Call the API n numbers of times within a time period
 - ex: 10 calls per second

 - If number of calls exceed the threshold, block the call with an error message
 - If number of calls are within in the threshold, allow the call to proceed

 - HTTP method call


 Per client - ex: clientId = 1
 // 0th. - 3
 // 1st sec - 0, 1 - 10, 11 onwards block
 // 2nd sec - 0, 1 - 10, 11 onwards block

 clientId = 2
 0th,
 1st
 2nd..
 10th..

 // Fixed time period


 */

    /**
     Assume:

     - No CSV metacharacters in the names in our CSV. (no "," or "\n")
     - 1:1 mapping between a person and a name.

     1st task: parse this CSV. For each line of input, print "{name} is the child of {parent1} and {parent2}"
     */
    private void populateMap(String csvInput) {
        String[] csvInputArray = csvInput.split("\\n");
        for (int i = 1; i < csvInputArray.length; i++) {
            String[] line = csvInputArray[i].split(",");
            if (line.length == 3) {
                if (!relationshipMap.containsKey(line[0])) {
                    Set<String> parents = new HashSet<>();
                    parents.add(line[1]);
                    parents.add(line[2]);
                    relationshipMap.put(line[0], parents);
                }
            }
        }
    }

    /**
     Returns a string describing how name1 relates to name2. Valid values:

     - "child"
     - "parent"
     - "ancestor"
     - "unknown"

     describeRelationship("Daphne", "Chris") // => "child"
     describeRelationship("Chris", "Daphne") // => "parent"
     describeRelationship("Chris", "Henry") // => "ancestor"
     describeRelationship("Annie", "Jack") // => "ancestor"
     describeRelationship("Fred", "Henry") // => "unknown"
     describeRelationship("Foo", "Bar") // => "unknown"
     */
    // name1 is what relationship to name2
    public String describeRelationship(String name1, String name2) {
        populateMap(CSV);
        if (relationshipMap.containsKey(name1)) {
            Set<String> parents = relationshipMap.get(name1);
            if (parents.contains(name2)) {
                return CHILD;
            } else { // check for parent relationship
                if (relationshipMap.containsKey(name2)) {
                    Set<String> potentialParents = relationshipMap.get(name2);
                    if (potentialParents.contains(name1)) {
                        return PARENT;
                    }
                }
            }
        } else {
            if(relationshipMap.containsKey(name2)) {
                Set<String> parents = relationshipMap.get(name2);
                if (parents.contains(name1)) {
                    return PARENT;
                }
            }
        }

        if (isAncestor(name1, name2)) {
            return ANCESTOR;
        }
        return UNKNOWN;
    }

    private boolean isAncestor(String name1, String name2) {
        Set<String> parents = relationshipMap.get(name2);
        /*THE BREAKING CONDITION IS ON THE PARENTS, if its null, then i can stop*/
        while(parents != null) {
            Set<String> potentionalAncestors = null;
            for (String parent : parents) {
                if (relationshipMap.containsKey(parent)) {
                    potentionalAncestors = relationshipMap.get(parent);
                    if (potentionalAncestors.contains(name1)) {
                        return true;
                    }
                }
            }
            parents = potentionalAncestors;
        }
        return false;
    }
}



