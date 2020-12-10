package main.java.com.codepath.techscreens;

import main.java.com.codepath.techscreens.objects.Cell;
import main.java.com.codepath.techscreens.objects.Employee;
import main.java.com.codepath.techscreens.objects.Range;
import main.java.com.codepath.techscreens.objects.Tile;

import java.util.*;

public class TechScreens {

    /**
     * Given two strings, return true if one of the strings is one edit away from being equal to the first one.
     * An edit could be a deletion, insertion, or a replace.
     *
     * This may look similar to the edit distance dynamic programming problem, but its different, because it only
     * wants to know if one string is one edit from another. Its not looking for the maximum or minimum number
     * of edits to make one string equal to another.
     *
     * @param one - string
     * @param two - string
     * @return boolean
     */
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

    /**
     * Given a string input like so: "1:Max:4,2:Ann:0,3:Albert:2,4:Edmond:2" print an employee chart, i.e. an employee
     * tree where the boss is at the root, so on and do forth.
     *
     * The comma separated list is comprised of the following: EmployeeId:Name:ManagerId.
     *
     * Output should look like:
     * Ann
     *  - Albert
     *  - Edmond
     *  -- Max
     *
     * @param input - String, a comma separated i.e. "1:Max:4,2:Ann:0,3:Albert:2,4:Edmond:2"
     */
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
                if (emp.id.equals(neighbor.id)) {
                    continue;
                }
                if (emp.id.equals(neighbor.managerId)) {
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

    /**
     * Given an 2-d array of tiles, a start tile and an end tile, find the shortest path from one tile to another.
     *
     * @param floor - Tile[][] : 2-D array of Tiles
     * @param startRow - int: start row of floor
     * @param startCol - int: start col of floor
     * @param endRow - int: end row of floor
     * @param endCol- int: end row of floor
     * @return List<Cell>: the path from the start cell to the end cell.
     */
    public List<Cell> getPath(Tile[][] floor, int startRow, int startCol, int endRow, int endCol) {
        // up, down, right, left
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

    private boolean isValidMove(int row, int rowDimension, int col, int colDimension) {
        return (row >= 0 && row < rowDimension) && (col >= 0 && col < colDimension);
    }

    private Cell[] findStartAndEnd(Tile[][] floor, int startRow, int startCol, int endRow, int endCol) {
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

    /**
     * You're given a list of ranges of seconds like so:
     * Range range = new Range(0, 5);
     * Range range1 = new Range(2, 6);
     * Range range2 = new Range(11, 17);
     * Range range3 = new Range(14, 17);
     * Range range4 = new Range(10, 18);
     *
     * Some of these ranges overlap, others do not. Find the total number of seconds among the entire list of ranges.
     *
     * For example, consider just the two following ranges:
     * Range(0, 5) and Range(2, 6)
     *
     * From 0 to 5 we have [0, 1, 2, 3, 4, 5],
     * From 2 to 6 we have [2, 3, 4, 5, 6],
     * In our first range we already have [2, 3, 4, 5], so we don't want to double count those, so the total number
     * of seconds between these two ranges is 7 : [0, 1, 2, 3, 4, 5, 6]
     *
     * For the given list of 5 ranges above the total number of seconds is 16;
     *
     * @param ranges - List of Range objects
     * @return - total number of seconds in the list of ranges
     */
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
     *
     * @param csvInput - String defining a CSV
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
     * Given two names and a csv defining parent and child relationships, return the familial relationship between
     * the two names. In other words return a string describing how name1 relates to name2.
     *
     * Valid values:
     * - "child"
     * - "parent"
     * - "ancestor"
     * - "unknown"
     *
     * name1 is what relationship to name2 ?
     *
     * describeRelationship("Daphne", "Chris") // => "child"
     * describeRelationship("Chris", "Daphne") // => "parent"
     * describeRelationship("Chris", "Henry") // => "ancestor"
     * describeRelationship("Annie", "Jack") // => "ancestor"
     * describeRelationship("Fred", "Henry") // => "unknown"
     * describeRelationship("Foo", "Bar") // => "unknown"
     *
     *
     * Assume:
     *  - No CSV metacharacters in the names in our CSV. (no "," or "\n")
     *  - 1:1 mapping between a person and a name.
     *  1st task: parse this CSV. For each line of input, print "{name} is the child of {parent1} and {parent2}"
     *
     * @param name1 - String: name of relative
     * @param name2 - String: name of relative
     * @return - String: their relationship to one another
     */
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
        /* THE BREAKING CONDITION IS ON THE PARENTS, if its null, then we can stop */
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

    /**
     * Given an array of integers and a target int, find the value in the array that is closest
     * to the target. We can assume the array is sorted in ascending order.
     *
     * @param array - int[]
     * @param target - int
     * @return - int
     */
    public int getClosest(int[] array, int target) {
        if (target > array[array.length - 1]) {
            return array[array.length - 1];
        } else if (target < array[0]) {
            return array[0];
        }
        return getClosestHelper(array, 0, array.length - 1, target);
    }

    /**
     * An alteration of binary search to find the closest element to a target element in an array of
     * integers.
     *
     * @param array - int[]
     * @param start - int, the lower bound
     * @param end - int, the upper bound
     * @param target - int the value by which we will find the closest in the array
     * @return - int
     */
    private int getClosestHelper(int[] array, int start, int end, int target) {
        int mid = start + (end - start)/2;
        if (target < array[mid]) {
            if (mid - 1 >= 0 && inBetween(array[mid - 1], target, array[mid])) {
                if (array[mid] - target <= Math.abs(target - array[mid - 1])) {
                    return array[mid];
                } else {
                    return array[mid - 1];
                }
            }
            return getClosestHelper(array, start, mid, target);
        } else if (target > array[mid]) {
            if (mid + 1 < array.length && inBetween(array[mid], target, array[mid + 1])) {
                if (target - array[mid] <= Math.abs(target - array[mid + 1])) {
                    return array[mid];
                } else {
                    return array[mid + 1];
                }
            }
            return getClosestHelper(array, mid + 1, end, target);
        } else {
            return mid;
        }
    }

    private boolean inBetween(int lower, int target, int upper) {
        return target >= lower && target <= upper;
    }
}



