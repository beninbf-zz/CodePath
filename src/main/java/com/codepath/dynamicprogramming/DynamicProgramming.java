package main.java.com.codepath.dynamicprogramming;

import main.java.com.codepath.objects.Cell;
import main.java.com.codepath.recursion.Palindrome.Palindrome;
import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DynamicProgramming {

    public int findMinPath(int[][] grid) {

        if (grid == null) {
            return -1;
        }
        if (grid[0].length == 1 && grid.length == 1) {
            return grid[0][0];
        }
        return findMinPath(grid, 0, 0);
    }

    private int findMinPath(int[][] grid, int down, int right) {
        if (down >= grid.length || right >= grid[0].length) {
            return -1;
        }

        int downPath = findMinPath(grid, down + 1, right);
        int rightPath = findMinPath(grid, down, right + 1);

        if (downPath > 0 && rightPath > 0) {
            return Math.min(downPath + grid[down][right], rightPath + grid[down][right]);
        } else if (downPath > 0) {
            return downPath + grid[down][right];
        } else if (rightPath > 0) {
            return rightPath + grid[down][right];
        } else {
            return grid[down][right];
        }
    }

    public int findMinPathDP(int[][] grid) {
        if (grid == null) {
            return -1;
        }
        if (grid[0].length == 1 && grid.length == 1) {
            return grid[0][0];
        }

        int[][] aux = new int[grid.length][grid[0].length];

        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i - 1 < 0 && j - 1 < 0) {
                    aux[i][j] += grid[i][j]; // sum current value
                } else if (i - 1 < 0) {
                    aux[i][j] += grid[i][j] + aux[i][j - 1]; // sum value from above
                } else if (j - 1 < 0) {
                    aux[i][j] += grid[i][j] + aux[i - 1][j]; // sum value from left
                } else {
                    // take min between value from above and value from left
                    aux[i][j] += Math.min(aux[i][j - 1] + grid[i][j], aux[i - 1][j] + grid[i][j]);
                }
            }
        }
        print2DArray(aux);
        return aux[rows - 1][cols - 1];
    }

    public void print2DArray(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
    }

    public int findMaxScoreRec(int[][] A) {
        int[] numberOfCalls = new int[1];
        int result = findMaxScoreRecUtil(A, 0, numberOfCalls);
        System.out.println("Number of calls of naive recursion=" + numberOfCalls[0]);
        return result;
    }

    private int findMaxScoreRecUtil(int[][] A, int start, int[] numberOfCalls) {
        if (start >= A[0].length) {
            return 0;
        }
        numberOfCalls[0]++;
        int cols = A[0].length;
        int maxScore = 0;
        int colMax = 0;
        for (int i = start; i < cols; i++) {
            maxScore = findMaxScoreRecUtil(A, start + 2, numberOfCalls);
            colMax = Math.max(A[0][start], A[1][start]);
            maxScore = Math.max(colMax + maxScore, maxScore);
        }

        return maxScore;
    }

    public int findMaxScoreDP(int[][] A) {
        int[] numberOfCalls = new int[1];
        int[] memo = new int[A[0].length];
        Arrays.fill(memo, -1);
        findMaxScoreRecUtilDP(A, 0, memo, numberOfCalls);
        System.out.println("Number of calls of recursion + memoization=" + numberOfCalls[0]);
        return Math.max(memo[0], memo[1]);
    }

    private int findMaxScoreRecUtilDP(int[][] A, int start, int[] memo, int[] numberOfCalls) {
        if (start >= A[0].length) {
            return 0;
        }

        if (memo[start] > 0) {
            return memo[start];
        }

        numberOfCalls[0]++;
        int cols = A[0].length;
        int maxScore = 0;
        int colMax = 0;
        for (int i = start; i < cols; i++) {
            // maxScore is literally the lastSum
            maxScore = findMaxScoreRecUtilDP(A, start + 2, memo, numberOfCalls);
            colMax = Math.max(A[0][start], A[1][start]);
            maxScore = Math.max(colMax + maxScore, maxScore);
            memo[start] = maxScore;
        }
        return memo[start];
    }

    public int findMaxScoreBottomUp(int[][] A) {
        int[] numberOfCalls = new int[1];
        int[] memo = new int[A[0].length];
        Arrays.fill(memo, -1);
        return Math.max(memo[0], memo[1]);
    }

    public boolean wordBreak(String str, Set<String> dictionary) {
        wordBreakRec(str, dictionary);
        return dictionary.isEmpty();
    }

    public void wordBreakRec(String str, Set<String> dictionary) {
        if (str.isEmpty() || dictionary.isEmpty()) {
            return;
        }
        int length = str.length();
        for (int i = 1; i <= length; i++) {
            if (dictionary.contains(str.substring(0, i))) {
                dictionary.remove(str.substring(0, i));
            }
        }
        String remaining = str.substring(1, length);
        wordBreakRec(remaining, dictionary); // results
    }


    public void printSubString(String workTodo, String workDone) {
        if (workTodo.isEmpty()) {
            System.out.println(workDone);
        }

//        int
//        printSubString(workTodo.substring(0, workDone.length() - 1), workTodo.substring(0, ))
//        String remaining = s.substring(1, length);
//        return wordBreakRec(remaining, results);
    }

    /**
     * This is a recursive algorithm for generating the number of different ways to generate
     * change for a given amount given an array of denominations.
     *
     * We essentially keep subtracting denominations until we get an amount of zero. When
     * we get to zero, we know we must have some list of denominations that gives us
     * exact change, so we return 1. If we get less than zero, then we return zero.
     *
     * We pass in the index of the coin we are on, so that we don't generate all
     * permutations of denominations.
     *
     * RUNTIME COMPLEXITY: our branching factor is length of d and the height
     * is O(amount) so the total running time is O(d.length ^ amount)
     *
     * SPACE COMPLEXITY: The height of the recursion tree so O(amount)
     *
     * @param amount the integer amount to make change for
     * @param d the list of denominations
     * @param index the spot we are in, in the denomination array
     * @return the number of unique ways to generate change for the amount
     */
    public int numberOfDifferentWaystoGetChange(int amount, int[] d, int index) {

        if (amount == 0) {
            return 1;
        }

        if (amount < 0) {
            return 0;
        }

        int result = 0;
        for (int i = index; i < d.length; i++) {
            result += numberOfDifferentWaystoGetChange(amount - d[i], d, i);
        }
        return result;
    }

    /**
     * The dynamic programming solution follows from a very straight forward way, however
     * with one wrinke. The wrinkle is from the recursion relation, of passing in the
     * index of the coin so that we don't repeatedly use the same coin over and over again.
     *
     * To do this we fix the coin by making it the out for loop, and iterate over the
     * amount, using the inner for loop.
     *
     * @param amount integer amount to make change for
     * @param d the denomination array
     * @return number of unique ways to make change
     */
    public int numberOfDiffChangeDp(int amount, int[] d) {
        int[] table = new int[amount + 1];
        table[0] = 1;

        for (int j = 0; j < d.length; j++) {
            for (int i = 1; i < table.length; i++) {
                if (d[j] <= i) {
                    table[i] = table[i] + table[i - d[j]];
                }
            }
        }
        return table[amount];
    }


    /**
     * This is a recursive implementation that gets all of the different combinations
     * of denominations that will equal our
     * amount A.
     * <p>
     * We are generating these combinations and returning all of the lists.
     * The key part to remember, is the recursion tree, and that every time
     * we subtract a denomination from our amount, that we have effectively
     * chosen that denomination and hence, when the recursion finishes, we need to
     * "un choose" that denomination so that we can choose the next one.
     * <p>
     * RUNTIME: The recursion tree is such that the running time is O(b^h) where b is our
     * branching factor, and h is our height of the tree. The branching factor is the size
     * of the denomations array, and the height of the tree is O(A), so this is
     * O(d ^ A)
     * <p>
     * SPACE: The space for the recursion tree is O(A), and for the lists its
     * order of O(A * A) as the size of the list is O(A) and the number of lists
     * is also order of A.
     *
     * Previously when we did this problem, we actually generated all permutations
     * of coins that gave us the change for our amount. So we got repeats.
     *
     * In order to prevent repeats, we need to not use the same coin more than once.
     * This just means, passing in the index of the coin that we are on, and starting
     * from there in recursive calls.
     *
     * @param A             the amount we wish to make change for
     * @param denominations an integer array of denominations
     * @return A list of lists containing all of the combinations of denominations
     */
    public List<List<Integer>> getCoins(int A, int[] denominations) {
        List<List<Integer>> coins = new ArrayList<>();
        coinsHelper(A, denominations, 0, new ArrayList<>(), coins);
        return coins;
    }

    private void coinsHelper(int A, int[] denominations, int index, List<Integer> coins, List<List<Integer>> coinsAll) {
        if (A == 0) {
            coinsAll.add(new ArrayList<>(coins));
            return;
        }

        if (A < 0) {
            return;
        }

        for (int i = index; i < denominations.length; i++) {
            coins.add(denominations[i]);
            coinsHelper(A - denominations[i], denominations, i, coins, coinsAll);
            coins.remove(coins.size() - 1);
        }
    }

    /**
     * This is a recursive implementation that returns the minimum number of coins
     * to generate the amount of change.
     * <p>
     * It looks similar to the recursive algorithm above which is generating ALL combinations
     * of denominations that will make correct change. This implementation is different
     * because its choosing the minimum.
     * <p>
     * Each coin selection represents an edge in the recursion tree, so its a lot like
     * choosing the minimum number of steps to a leaf node.
     * <p>
     * Our recursive formulation is that f(a) is the minimum number of coins to make
     * the value a. This will be important to remember for your db table.
     * <p>
     * RUNTIME: The recursion tree is such that the running time is O(b^h) where b is our
     * branching factor, and h is our height of the tree. The branching factor is the size
     * of the denominations array, and the height of the tree is O(A), so this is
     * O(d ^ A)
     *
     * @param A             amount to make change for
     * @param denominations integer denominations, coins
     * @return minimum number of coins
     */
    public int minNumberCoinsToMakeChange(int A, int[] denominations) {
        if (A == 0) {
            return 0;
        }

        if (A < 0) {
            return 0;
        }

        int min = 0;
        for (int i = 0; i < denominations.length; i++) {
            int coinsNumber = minNumberCoinsToMakeChange(A - denominations[i], denominations);
            min = Math.min(coinsNumber, min) + 1;
        }
        return min;
    }

    /**
     *
     * BEFORE STARTING A DP ALGORITHM!!!
     * We should be sure to prove to ourselves that Greedy algorithm would not work. Lets take the
     * following example. make change for 76 cents, and our denomination array is [1, 5, 10, 25], we
     * know the min number of coins is 4, three 25 cent pieces and one penny.
     *
     * Ok, now what about this problem, make change for 15, and the denomination array is
     * [1, 4, 10, 12]. Greedy algorithm gives us 4, 12 + 1 + 1 + 1 = 15. DP solution would
     * yield, 10 + 4 + 1 = 15. So this problem should be solved with DP given that the
     * denomination array input can be different.
     *
     * Understand the value of the memory table. Our recursive formation said that the f(a) was
     * the minimum number of coins to make the value of a. so our table will store the minimum
     * number of coins. What will the size of the table be?
     *
     * It must be O(A), if we are going to see the minimum number of coins to make 1, 2, 3, 4, 5, etc...
     * The exact size of the table is A + 1. Why A + 1? Well we want to know what the minimum number
     * of coins is to make CHANGE for 8, so we need an 8th index, which means our size should be 9.
     *
     * table:        0, _, _, _, _, _, _, _, _
     * amount:       0          1  2  3  4  5  6  7  8
     *
     * We clearly need 0 coins to make 0 change. Well how many coins do we need to make
     * change for 1. Well if our smallest coin is 2, then we should just skip this
     * because we don't need to make change for 1, when our coin is 2.
     *
     * The following line confused me the first time, table[i - denominations[j]];
     *
     * I assumed we should be doing, table[i] - denominations[j], but doing this doesn't make any
     * considering what the DP table holds. The DP table holds, the min number of coins, while
     * the index represents the actual amount. The key to understanding this is looking
     * at the recursive formulation. We did A - denomination[i].
     *
     * In our DP table, its the index that is the amount, not the value stored at the index,
     * which is the minimum number of coins to make that amount, so it should be table[i - denomination[j]]
     *
     * Also, considering, for our base case we are filling everything, accept table[0] with infinity, we
     * must be careful not to add 1 to infinity, so we first check the value of our table, for the min
     * number of coins.
     *
     * If it not infinity, then we simply take the minimum of table[i] and table[i - denomination[j]] + 1.
     * The final thing we return is table[A];
     *
     * @param A             the amount we wish to make change for
     * @param denominations the denominations
     * @return minimum number of coins to make change for amount A
     */
    public int coinChangeDP(int A, int[] denominations) {
        int[] table = new int[A + 1];
        table[0] = 0;
        for (int i = 1; i < table.length; i++) {
            table[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i < table.length; i++) {
            for (int j = 0; j < denominations.length; j++) {
                // only look at coins smaller than i
                // it doesn't make sense to compute the number of coins it take to make
                // change for 1, when your coin is 2.
                if (denominations[j] <= i) {
                    int sub = table[i - denominations[j]];
                    if (sub != Integer.MAX_VALUE) {
                        table[i] = Math.min(table[i], sub + 1);
                    }
                }
            }
        }

        Util.print(table);
        System.out.println(getDenominations(table, A, denominations));
        return table[A];
    }

    /**
     * This little shitty fucking algorithm to come up with the denominations that make up
     * the minimum took me forever. Its only because I wasn't actually understanding what
     * the DP table was actually storing.
     * <p>
     * When I was computing the DP table, I was starting at 0, and relying on solutions
     * to compute the min number of coins to come up with change for 1, 2, 3,
     * and so forth.
     * <p>
     * When trying to come up with the exact coins that made up the, it only made sense,
     * that I would start with the result, 2 coins, and work my way back to 0. Moving
     * back along the indices, every time I saw a minimum count, that was 1 less than
     * the current count I was at, I was suppose to save that denomination, and then
     * move the index back, that many spaces.
     * <p>
     * Its incredible how difficult that is, if one doesn't actually completely understand
     * the DP table.
     *
     * @param table         the filled in DP table
     * @param A             the amount to make change for
     * @param denominations the denominations
     * @return the coins that made up the change
     */
    public List<Integer> getDenominations(int[] table, int A, int[] denominations) {
        List<Integer> coins = new ArrayList<>();
        int i = A;
        while (i != 0 && table[i] != Integer.MAX_VALUE) {
            for (int k = 0; k < denominations.length; k++) {
                if (table[i - denominations[k]] == table[i] - 1) {
                    i = i - denominations[k];
                    coins.add(denominations[k]);
                    break;
                }
            }
        }
        return coins;
    }

    /**
     * Given a 2-D matrix with positive integers, compute the maximum path from
     * the 0,0 cell to the row - 1, col - 1 cell.
     *
     * This recursive algorithm has overlapping sub problems because we can see
     * that we are doing the same computation multiple times by looking at the
     * recursion tree.
     *
     *            0,0
     *         /        \
     *        1,0        0,1
     *      /   \        /  \
     *    2,0   1,1    1,1  0,2
     *
     *
     * We recognize that if we are looking for the maximum path from the top
     * left cell, to the bottom right cell. How do we compute that? From 0,0
     * we can only go in two directions. Either to the right, or down.
     *
     * We do this, by recursing all the way down to the bottom right cell.
     * Then as we move back up the tree, we find out the maximum from
     * down, or the maximum from the right.
     *
     * As we move back up the recursion tree, we compare which value was higher
     * and return it.
     *
     * RUNTIME COMPLEXITY: our branching factor is 2, and the height of our tree is row + cols
     * so the total run time is O(2^(rows + cols)).
     *
     * SPACE COMPLEXITY: The space is the height of our tree for the recursion call stack
     * so that is O(R + C)
     *
     * @param grid 2-D array of integers
     * @return the value of the maximum path
     */
    public int maxPathRec(int[][] grid) {
        return maxPathRecHelper(grid, 0, 0);
    }

    private int maxPathRecHelper(int[][] grid, int row, int col) {
        if (row >= grid.length || col >= grid[0].length) {
            return 0;
        }

        if (row == grid.length - 1 && col == grid[0].length - 1) {
            return grid[row][col];
        }

        int down = grid[row][col] + maxPathRecHelper(grid, row + 1, col);
        int right = grid[row][col] + maxPathRecHelper(grid, row, col + 1);
        return Math.max(down, right);
    }

    /**
     * This dynamic programming solution for the max path problem follows from the
     * recursive solution.
     *
     * We need a table that is 2 dimensions because in our recursive solution, we have
     * two parameters that are changing, r and c.
     *
     * The size of our DP table is such that, we need a solution for the path from 0,0,
     * to n - 1, m - 1 in our original grid. Our solutions for each sub problem depend
     * on maximum of next right, or next down values, so we should pad our DP table
     * with grid + 1 rows, and grid[0] + 1 cols.
     *
     * We then pre-populate the tables with our base cases. And begin to fill in the table.
     * We fill in the table, by essentially reversing the DAG created by the recursion tree.
     *
     * In the recursion tree we started from 0,0, and moved to n-1, m-1. We will take the bottom
     * up approach and move from n-1, m-1 to 0,0. Each time, the solution of r, c in our table
     * will be the maximum of whatever the current value of the grid is, plus the value to the right
     * or the value below.
     *
     * As such we start from the cell noted in our base case, grid[r - 1][c - 1], which in our
     * table corresponds to table[r - 2][c - 2].
     *
     * RUNTIME: is now O(R * C)
     *
     * SPACE COMPLEXITY: O(R * C)
     *
     * @param grid 2-d array of integers
     * @return max path from top left cell, to bottom right cell.
     */
    public int maxPathDP(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        int[][] table = new int[r + 1][c + 1];

        // fill in last row with zeros for the base case
        for (int i = 0; i < table[0].length; i++) {
            table[table.length - 1][i] = 0;
        }

        // fill in last column with zeros for the base case
        for (int i = 0; i < table.length; i++) {
            table[i][table[0].length - 1] = 0;
        }

        // the last base case, the value at grid[r - 1][c - 1] should be in the table
        table[r][c] = grid[r - 1][c - 1];

        for (int i = table.length - 2; i >= 0; i--) {
            for (int j = table[0].length - 2; j >= 0; j--) {
                int down = grid[i][j] + table[i + 1][j];
                int right = grid[i][j] + table[i][j + 1];
                table[i][j] = Math.max(down, right);
            }
        }

        System.out.println(recoverMaxPath(table));
        return table[0][0];
    }

    /**
     * This algorithm recovers the exact path we took. We need to use the filled in
     * DP table, and then use the logic from the code to fill the table, to recover the
     * path.
     *
     * Remember we wanted the max path from 0,0 to r - 1, c - 1. In our DP table thats
     * 0,0 to r - 2, c - 2. So in that case, we will start at r - 2, c - 2, and then look
     * to see which value is greater, the one from above, or the one from the left.
     * We will take the greater value, record the path, and then stop when both r
     * and c are equal to zero.
     *
     * Considering r or c could be zero at different times, we should use a while loop
     * and check the values before moving them in the grid.
     *
     * @param table the filled in DP table
     * @return the max valued path
     */
    public List<Cell> recoverMaxPath(int[][] table) {
        List<Cell> path = new ArrayList<>();
        path.add(new Cell(table.length - 2, table[0].length - 2));
        int r = table.length - 2;
        int c = table[0].length - 2;

        while (r != 0 || c != 0) {

            int up = -1;
            if (r > 0) {
                up = table[r - 1][c];
            }
            int left = -1;
            if (c > 0) {
                left = table[r][c - 1];
            }

            if (up > left) {
                path.add(new Cell(r - 1, c));
                r--;
            } else {
                path.add(new Cell(r, c - 1));
                c--;
            }
        }
        return path;
    }

    public int countPaths(int[][] grid) {
        int[] count = new int[1];
        countPathsHelper(grid, 0, 0, count);
        return count[0];
    }

    private void countPathsHelper(int[][] grid, int row, int col, int[] count) {
        if (row == grid.length || col == grid[0].length) {
            return;
        }

        if (row == grid.length - 1 && col == grid[0].length - 1) {
            count[0]++;
        }

        countPathsHelper(grid, row + 1, col, count);
        countPathsHelper(grid, row, col + 1, count);
    }

    /**
     * This is a recursive algorithm for counting the number of paths from
     * the top left cell to the bottom right.
     *
     * Typically for problems like this I pass an object, containing a counter
     * that I increment, whenever i get to my goal cell, but that strategy
     * can't be translated into a Dynamic programming solution.
     *
     * For a dynamic programming solution for a given sub problem must be based
     * on the solution for a subsequent sub problem and so forth. The following
     * code effectively does the same thing as my non optimal solution.
     *
     * When we get to our destination cell and return 1, that return will travel
     * back up the stack until it gets to the next possible branch.
     *
     * We wind up with a recursion tree like so
     *
     *                   0, 0
     *           /                   \
     *         1, 0                    0,1
     *       /        \              /        \
     *     2,0        1,1           1,1       0,2
     *        \      /   \          /  \      /
     *        2,1   2,1  2,2      2,1   1,2   1,2
     *           \   \              \     /   /
     *           2,2  2,2           2,2  2,2  2,2
     *
     * We really have to get the recurrence relation right. The number of
     * possible ways to get to grid[i][j] is equal, to the number of possible
     * ways to get to grid[i - 1][j] + grid[i][j - 1];
     *
     * In other words, f(1,1) = f(0, 1) + f(1,0);
     * So look at f(0,1) = f(-1, 1) + f(0,0) => 0 + 1;
     * f(1,0) = f(1, -1) + f(0,0) = 0 + 1; So that means, f(1,1) = 1 + 1 = 2;
     *
     * Intially, I was moving from the top cell, 0,0 to the bottom right. And
     * although the answer was correct, the recurrence formulation I had set up
     * was backwards.
     *
     * I was essentially saying, that f(0,0) = f(1,0) + f(0,1), but in actuality
     * that makes no sense. The number of paths to get to cell 0,0, from 0,0 does not
     * depend on the number of paths to get to cell(1,0) and cell(0,1).
     *
     * The number of ways to get to cell 0,0 from 0,0 is just 1. In turn
     * its f(1,1), that depends on paths that came from above, and to the left.
     *
     * This relation will now yield to the correct Dynamic Programming solution.
     *
     * RUNTIME: O(2^(row+col))
     *
     * SPACE: O(ROW + COL)
     *
     * @param grid 2-D integer array
     * @return count paths
     */
    public int countPathsOptimal(int[][] grid) {
        return countPathsOptimalHelper(grid, grid.length - 1, grid[0].length - 1);
    }

    private int countPathsOptimalHelper(int[][] grid, int row, int col) {
        if (row == 0 || col == 0) {
            return 1;
        }

        int down = countPathsOptimalHelper(grid, row - 1, col);
        int right = countPathsOptimalHelper(grid, row, col - 1);
        return down + right;
    }

    /**
     * This problem is slightly different from standard recursion to DP
     * solutions. We must again rely on the recursion, but considering
     * the way we are building this solution, with the reverse DAG
     * there is no need to add extra dimensions for the DP table.
     *
     * Again use our base case, and populate the first row and column
     * with ones. Use our recurrence from the recursive solution.
     * The solution for f(1,1) depends on f(0,1) and f(1,0). So what
     * we want to return is table[row - 1][col - 1].
     *
     * RUNTIME: O(ROW * COL)
     *
     * SPACE: O(ROW * COL)
     * @param grid integer array
     * @return number of paths to get from top left to bottom right.
     */
    public int countPathsDP(int[][] grid) {
        int[][] table = new int[grid.length][grid[0].length];

        // start with the top row
        for (int i = 0; i < table[0].length; i++) {
            table[0][i] = 1;
        }

        // start with the first column
        for (int i = 0; i < table.length; i++) {
            table[i][0] = 1;
        }

        // fill dp table
        for (int i = 1 ; i < table.length; i++) {
            for (int j = 1; j < table[0].length; j++) {
                int down = table[i - 1][j];
                int right = table[i][j - 1];
                table[i][j] = down + right;
            }
        }
        return table[table.length - 1][table[0].length - 1];
    }

    /**
     *
     * This algorithm is for computing the maximum amount of profit that can be generated
     * for cutting a rope, where the lengths of rope can be sold for amounts dictated
     * by a prices array, where the index is the length and the value at the index is
     * the price.
     *
     * For example [0, 5, 1, 1, 1, 1]. This array means that selling rope of length one
     * will yield $5, while selling rope of length 2 - 5, yields $1.
     *
     * If we want to find the maximum profit from all different combinations of cuts,
     * we must compute all combinations in an exhaustive search and take the maximum.
     *
     * The recurrence relation for rope of length 5 is that, f(5) = the maximum amount
     * of profit generated from selling rope of length 4, 3, 2, 1, and 0.
     *
     * In turn, f(4) is the maximum profit generated from selling rope of length 3, 2, 1 and
     * so on.
     *
     * We can see that we have 5 choices to make, one for each index of our prices array.
     * We recurse down until we are asking about the max profit for a rope of length 0,
     * which is clearly 0. Again if the length is less than zero, then we return 0 also.
     *
     *                           f(5)
     *                    /    /   |     \   \
     *                  f(4) f(3) f(2) f(1) f(0)
     *               /   |  \
     *             f(3) f(2) f(1)
     *
     * RUNTIME: branch factor is the length of the prices array. The height of the tree is the
     * length of the prices array, so O(b^H) is O(length^length).
     *
     * SPACE: is the height of the tree O(Height)
     *
     * @param length length of rope, from 1 to length, excluding 0
     * @param prices the prices array that stores price for rope at the given index
     * @return the maximum price that can be obtained
     */
    public int ropeCuttingMax(int length, int[] prices) {
        if (length == 0) {
            return 0;
        }

        if (length < 0) {
            return 0;
        }

        int maxPrice = 0;
        // no such thing as a price for length 0 so start at 1.
        for (int i = 1; i <= length; i++) {
            maxPrice = Math.max(prices[i] + ropeCuttingMax(length - i, prices), maxPrice);
        }
        return maxPrice;
    }

    /**
     * This follows directly from the recursive solution above but we have to remember
     * how to appropriately apply the wrinkle from the recursive solution to the DP
     * solution.
     *
     * The key to understanding the wrinkle, is to understanding the exact definition
     * of what the DP tables holds.
     *
     * In this case, the table stores the maximum price for rope at length i, where
     * i is the index. So, what is the maximum price one can obtain for selling a
     * rope of length 0...well is 0. Ok, then, so what about the maximum price
     * for selling a rope of length 1.
     *
     * To compute that, we only need to go over prices that go up to length 1.
     * We wouldn't bother computing prices for find the max price for rope of length 2, 3, 4
     * if we were only at length 1 would we? That is why the appropriate change here, is to
     * make the inner for loop, that's moving over the prices array, to make its loop invariant
     * only go up to length l.
     *
     * This is clear, if we look at the recursion tree and get the recursion algorithm right.
     *
     * RUNTIME: O(length * prices)
     *
     * SPACE: O(length * prices)
     *
     * @param length length of rope, from 1 to length, excluding 0
     * @param prices the prices array that stores price for rope at the given index
     * @return maximum price
     */
    public int ropeCuttingMaxDP(int length, int[] prices) {
        int[] table = new int[length + 1];

        table[0] = 0;
        for (int l = 1; l < table.length; l++) {
            int maxPrice = 0;
            for (int i = 1; i <= l; i++) {
                maxPrice = Math.max(prices[i] + table[l - i], maxPrice);
            }
            table[l] = maxPrice;
        }
        return table[length];
    }

    /**
     * Test for a tie.
     *
     * Given an array of positive integers, check if the array can be broken into pieces
     * such that one set of numbers is equal to the other.
     *
     * Example: [5, 2, 7]; {5, 2} == {7}
     * Example: [5, 2, 7, 3, 3]: {5, 2, 3} == {7, 3}
     *
     * This is a play on the subset sum problem. Given that the sum of one subset has
     * to be equal to the other subset, we should first sum the entire array. If
     * that sum is not even, we know we can immediately return false, because in order
     * this to be true the total sum must be even.
     *
     * So that means, we can take the total sum, and divide it by two. At this point,
     * we are just looking for a subset that sums to the total/2.
     * @param array
     * @return
     */
    public boolean testForTie(int[] array) {
        Integer[] subset = new Integer[array.length];
        int total = Util.sum(array);
        if (total % 2 != 0) {
            return false;
        }

        return testForTieHelper(array, subset, 0, total / 2);
    }

    private boolean testForTieHelper(int[] array, Integer[] subset, int index, int halfSum) {
        if (index == array.length) {
            if ((Util.sum(subset) == halfSum)) {
                return true;
            } else {
                return false;
            }
        }
        subset[index] = null;
        boolean isInc = testForTieHelper(array, subset, index + 1, halfSum);
        subset[index] = array[index];
        boolean isExc = testForTieHelper(array, subset, index + 1, halfSum);
        return isInc || isExc;
    }

    /**
     * Test for a tie.
     *
     * Given an array of positive integers, check if the array can be broken into pieces
     * such that one set of numbers is equal to the other.
     *
     * Example: [5, 2, 7]; {5, 2} == {7}
     * Example: [5, 2, 7, 3, 3]: {5, 2, 3} == {7, 3}
     *
     * This is a play on the subset sum problem. Given that the sum of one subset has
     * to be equal to the other subset, we should first sum the entire array. If
     * that sum is not even, we know we can immediately return false, because in order
     * this to be true the total sum must be even.
     *
     * So that means, we can take the total sum, and divide it by two. At this point,
     * we are just looking for a subset that sums to the total/2.
     *
     * RUNTIME: O(2 ^ n)
     *
     * SPACE: O(n)
     *
     * @param array list of integers
     * @return boolean
     */
    public boolean testForTieEff(int[] array) {
        int total = Util.sum(array);
        if (total % 2 != 0) {
            return false;
        }
        return testForTieEffHelper(array, 0, total / 2);
    }

    private boolean testForTieEffHelper(int[] array, int index, int halfSum) {
        if (halfSum == 0) {
            return true;
        }

        if (index == array.length) {
            return false;
        }

        boolean isSumInc = false;
        // making sure there are no negatives makes transforming into the DP solution easier
        if (halfSum - array[index] >= 0) {
            // includes the number in the array
             isSumInc = testForTieEffHelper(array, index + 1, halfSum - array[index]);
        }

        // excludes the number in the array
        return isSumInc || testForTieEffHelper(array,index + 1, halfSum);
    }

    /**
     * Follow the recursive solution carefully.
     *
     * RUNTIME: O(n * sum), now this might look for efficient, but if the integers
     * in the array are huge, say numbers into the billions, then our DP table
     * would be huge. The DP table would be 10 * billions, if the list was of size
     * 10. so 2^10 could be better than 10 * billions.
     * @param array
     * @return
     */
    public boolean testForTieDP(int[] array) {
        int total = Util.sum(array);
        if (total % 2 != 0) {
            return false;
        }
        int sum = total / 2;
        boolean[][] table = new boolean[array.length + 1][sum + 1];

        for (int i = 0; i < table.length; i++) {
            table[i][0] = true;
        }

        for (int i = 1; i <= sum; i++) {
            table[table.length - 1][i] = false;
        }
        // for our traversal direction, look at the recursion base cases!!!
        // we start at row table.length - 1, and sum == 0
        for (int i = table.length - 2; i >= 0; i--) {
            for (int j = 1; j <= sum; j++) {
                boolean isSumInc = false;
                if (j - array[i] >= 0) {
                    isSumInc = table[i + 1][j - array[i]];
                }
                table[i][j] = isSumInc || table[i + 1][j];
            }
        }
        return table[0][sum];
    }

    /**
     * Find all subsets of a gi
     * @param array
     * @param n
     * @param sum
     */
    public void subSetOfSizeNSumtoM(int[] array, int n, int sum) {
        Integer[] subset = new Integer[n];
        subSetOfSizeNSumtoMHelper(array, subset, n, sum, 0, 0);
    }

    public void subSetOfSizeNSumtoMHelper(int[] array, Integer[] subset, int size, int sum, int index, int subsetIndex) {
        if (subsetIndex == size) {
            Util.print(subset);
            return;
        }

        if (index == array.length) {
            return;
        }

        // exclude
        subSetOfSizeNSumtoMHelper(array, subset, size, sum, index + 1, subsetIndex);

        subset[subsetIndex] = array[index];

        // include
        subSetOfSizeNSumtoMHelper(array, subset, size, sum, index + 1, subsetIndex + 1);

    }

    /**
     * The following algorithm is a very slick way of finding the minimum edit distance to transform one
     * word into another.
     *
     * Edit distance is defined by the cumulative number of inserts, deletes, and replaces one would have to
     * do to transform one word into another.
     *
     * Now the basic recursive algorithm would find ALL ways to transform one word into another, but all we
     * want to do is find the minimum number of ways. The first thing to do, is to come up with a scheme for
     * determining whether to insert, delete, or replace. We accomplish that with pointers to the location
     * of the characters in the words, starting at the beginning.
     *
     * Lets use i for string one, and j for string two. If one.charAt(i) == two.charAt(j), then we should
     * move them both to the right. If they are not equal, then we must make a choice about whether to insert,
     * replace, or delete. Because we are not sure which will be the minimum, we will do all of them.
     *
     *
     * Example: bat, cat.
     *
     *                      bat, cat, i = 0, j = 0
     *                     /           |             \
     *                   0, 1         1, 0          1,1
     *              /     |   \     /    |  \        |
     *            0,2    1,1 1,2   1,1  2,0 2,1      2,2
     *
     * We can see in this recursion tree, that the call to f(1,2) would hit our base case. What effectively
     * happens is that we should check the difference between our i and j pointers, and the length of the String.
     *
     * Because one of our counters will always get to the end, we don't want to take the minimum of the i and j
     * distances. otherwise we would always return 0. We want to take the max of the distances in our base case.
     * That distance represents how many edits it would take to transform one string into another. Its only in
     * our base case were we take the max difference.
     *
     * We then return the minimum distance from all of our three choices, insert, delete, and replace.
     *
     * For insert, we hold i where it is and only move j to the right. For delete, we move i to the right
     * and hold j in place. For replace we move both pointers to the right.
     *
     * @param one String
     * @param two String
     * @return minimum number of operations to transform one string into another
     */
    public int minimumEditDistance(String one, String two) {
        int lengthOne = one.length();
        int lengthTwo = two.length();

        if (one.equals(two)) {
            return 0;
        }
        return minimumEditDistanceHelper(one, 0, lengthOne, two, 0, lengthTwo);
    }

    private int minimumEditDistanceHelper(String one, int ptrOne, int lengthOne, String two, int ptrTwo, int lengthTwo) {
        if (ptrOne == lengthOne) {
            return lengthTwo - ptrTwo;
        }

        if (ptrTwo == lengthTwo) {
            return lengthOne - ptrOne;
        }

        if (one.charAt(ptrOne) == two.charAt(ptrTwo)) {
            return minimumEditDistanceHelper(one, ptrOne + 1, lengthOne, two, ptrTwo + 1, lengthTwo);
        }

        // for insert we do NOT increment ptrOne. Incrementing would mean that we are reducing the
        // problem space and not considering the character at location ptrOne. We instead increment
        // ptrTwo which means that we are no longer considering the character at location ptrTwo.
        int insert =  minimumEditDistanceHelper(one, ptrOne, lengthOne, two, ptrTwo + 1, lengthTwo);

        // For the delete we move ptrOne to the right, and leave ptrTwo where it is. This means
        // that we are no longer considering the character at location ptrOne, so we move past it
        // we deleted the character at ptrOne, so we can increment. We are still considering the char
        // at ptrTwo so leave it where it is.
        int delete = minimumEditDistanceHelper(one, ptrOne + 1, lengthOne, two, ptrTwo, lengthTwo);

        // For the replace we move both ptrOne and ptrTwo to the right,because we are replacing both characters
        // at the location ptrOne and ptrTwo effectively, removing them from the problem space.

        int replace = minimumEditDistanceHelper(one, ptrOne + 1, lengthOne, two, ptrTwo + 1, lengthTwo);

        return 1 + Math.min(Math.min(insert, delete), replace);
    }

    public int minEditDistanceDp(String one, String two) {
        int lengthOne = one.length();
        int lengthTwo = two.length();

        if (one.equals(two)) {
            return 0;
        }

        int[][] table = new int[lengthOne + 1][lengthTwo + 1];

        for (int i = 0; i < lengthTwo; i++ ) {
            table[lengthOne][i] = lengthTwo - i;
        }

        for (int i = 0; i < lengthOne; i++ ) {
            table[i][lengthTwo] = lengthOne - i;
        }

        for (int i = lengthOne - 1; i >= 0; i--) {
            for (int j = lengthTwo - 1; j >= 0; j--) {
                if (one.charAt(i) == two.charAt(j)) {
                    table[i][j] = table[i + 1][j + 1];
                } else {
                    int insert = table[i][j + 1];
                    int delete = table[i + 1][j];
                    int replace = table[i + 1][j + 1];
                    table[i][j] = 1 + Math.min(Math.min(insert, delete), replace);
                }
            }
        }

        return table[0][0];
    }

    /**
     *
     * Consider a row of n coins of values v1, . . ., vn. We play a game against an opponent by
     * alternating turns. In each turn, a player selects either the first or last coin from the
     * row, removes it from the row permanently, and receives the value of the coin.
     * Determine the maximum possible amount of money we can definitely win if we move first.
     *
     * I found this problem to be quite difficult. I couldn't get the recurrence relation right
     * because I was trying to figure out how to alternate between the users turns.
     *
     * The starting point of the problem HAS TO BE to define the recurrence relationship. I have
     * to define what exactly my function is computing.
     *
     * In this case, F(array, i, j) is computing the maximum value the user can collect
     * from the ith coin to the jth coin. What does that mean exactly, in the context of
     * this problem.
     *
     * Look at the following example
     *
     * {8, 15, 3, 7}.
     *
     * When trying to understand the maximum amount that can be collected for player 1, we have 2
     * choices to make. We can either choose the first coin, 8, or the last coin, 7.
     *
     * If we choose the first coin 8, then that means our opponent has the choice of either picking
     * 7, or 15. If our opponent chooses 7, that means we have the choice of picking the max between
     * 15 and 3. if our opponent chooses 15, then we have the choice of choosing the max between 3, and 7.
     *
     * That looks like the following
     *
     *     8   .... max(15, 3) or max(3,7)
     * our choice
     *
     * Now we don't know what our opponent will pick, so we have to test against all possibilities. We do
     * know that our opponent will make a choice such that, our amount will result in the minimum
     * of max(15,3) and max(3,7).
     *
     * The same rationale applies to if we choose the jth coin, or 7. If we choose 7, that means our
     * opponent could either choose, 8 or 3. If our opponent choose 8, that means we would pick
     * the max(15, 3), if our opponent choose 3, that means we would take the max(8, 15).
     *
     * that looks like
     *
     * max(8, 15) or max(15, 3)........7 (our choice). Again our opponent will try to minimum what we can
     * collection so we will effectively be getting min(max(8,15), max(15,3)).
     *
     * We can see here that we need to indexes. If the indices are such that j is one spot to the right of
     * i, then just return max(array[i], array[j]). if i == j, just return array[i], if i > j,
     * then return positive infinity.
     *
     * With that said our formal recurrence is the following
     * F(i, j) ==> represents the maximum value the user can collect from i'th coin to j'th coin.
     *
     *
     * getMax(i, j) = Max(Vi + Max(getMax(i+1, j-1), getMax(i+2, j)), Vj + min(getMax(i, j-2), getMax(i+1, j-1)))
     * @param array integer coins
     * @return max amount from game.
     */
    public int coinGame(int[] array) {
        return coinGameHelper(array, 0, array.length - 1);
    }

    private int coinGameHelper(int[] array, int i, int j) {
        if (i == j) {
            return array[i];
        } else if (i == j - 1) {
            return Math.max(array[i], array[j]);
        } else if (j == i + 1) {
            return Integer.MAX_VALUE;
        }

        int firstOption = array[i] + Math.min(coinGameHelper(array, i + 2, j), coinGameHelper(array, i + 1, j - 1));
        int secondOption = array[j] + Math.min(coinGameHelper(array, i + 1, j - 1), coinGameHelper(array, i, j - 2));
        return Math.max(firstOption, secondOption);
    }

    /**
     * Translating the recursion into a DP solution for this problem was tricky. Because we have i moving from 0 to
     * the end, j moving from the end to 0, we have to start the table in the bottom right corner
     * and move up to the upper right corner to return the solution at table[0][table.length - 1].
     *
     * We really have to interpret the recursion LITERALLY.
     *
     * We must pay attention that when populating a 2-D array like
     *
     * > > > > > >
     * [8, 15, 0, 0]   ^
     * [0, 15, 15, 0]  ^
     * [_, _, 3, 7]    ^
     * [_, _, _, 7]    ^
     *
     * To fill this array up starting at the bottom right means having i go from table.length - 1 to 0
     * and j going from i to table.length; That means the starting point for j will start by a measure
     * of i array from table.length - 1
     *
     * @param array int array of coins
     * @return max winning amount
     */
    public int coinGameDp(int[] array) {
        int[][] table = new int[array.length][array.length];

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (i == j) {
                    table[i][j] = array[i];
                } else if (i == j - 1) {
                    table[i][j] = Math.max(array[i], array[j]);
                }
            }
        }

        print2DArray(table);
        for (int i = table.length - 1; i >= 0; i--) {
            for (int j = i; j < table.length; j++) {

                int o1 = (i < table.length - 2) ? table[i + 2][j] : Integer.MAX_VALUE;
                int o2 = (i < table.length - 1 && j > 0) ? table[i + 1][j - 1] : Integer.MAX_VALUE;
                int o3 = (j > 1) ? table[i][j - 2] : 0;

                int firstOption = array[i] + Math.min(o1, o2);
                int secondOption = array[j] + Math.min(o2, o3);
                table[i][j] = Math.max(firstOption, secondOption);
            }
        }
       return table[0][table.length - 1];
    }

    public List<String> wordBreak(Set<String> dictionary, String text) {
        List<String> results = new ArrayList<>();
        List<String> candidate = new ArrayList<>();
        wordBreakHelper(dictionary, text, candidate, results);
        return results;
    }

    private void wordBreakHelper(Set<String> dictionary, String text, List<String> candidate, List<String> results) {
        if (text.isEmpty()) {
            results.add(String.join(" ", candidate));
            return;
        }

        int length = text.length();
        for (int i = 1; i <= length; i++) {
            String test = text.substring(0, i);
            if (dictionary.contains(test)) {
                candidate.add(test);
                wordBreakHelper(dictionary, text.substring(i, length), candidate, results);
                candidate.remove(candidate.size() - 1);
            }
        }
    }

    public List<List<Integer>> coinCombinationsToMakeChange(int amount, int[] coins) {
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> change = new ArrayList<>();
        coinRec(amount, coins, change, 0, results);
        return results;
    }

    public void coinRec(int amount, int[] coins, List<Integer> change, int index, List<List<Integer>> results) {

        if (amount == 0) {
            List<Integer> list = new ArrayList<>(change);
            results.add(list);
            return;
        }
        for (int i = index; i < coins.length; i++) {
            if (coins[i] <= amount) {
                change.add(coins[i]);
                coinRec(amount - coins[i], coins, change, i, results);
                change.remove(change.size() - 1);
            }
        }
    }

    public int minCoins(int amount, int[] coins) {
        if (amount == 0) {
            return Integer.MAX_VALUE;
        }
        int result = 0;
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= amount) {
                result = 1 + Math.min(minCoins(amount - coins[i], coins), result);
            }
        }
        return result;
    }

    /**
     * There are n houses built in a line, each of which contains some value in it.
     * A thief is going to steal the maximal value in these houses, but he cannot steal
     * in two adjacent houses because the owner of a stolen house will tell his two
     * neighbors on the left and right side. What is the maximal stolen value?
     *
     * For example, if there are four houses with values [6, 1, 2, 7], the maximal stolen
     * value is 13, when the first and fourth houses are stolen.
     *
     * A function f(i) is defined to denote the maximal stolen value from the first house to the ith house,
     * and the value contained in the ith house is denoted as values[i].
     * When the thief reaches the ith house, he has two choices: to steal or not.
     *
     * This is straight forward recursive solution, where we are either choosing to steal
     * from the ith house, or the i+1 house. As we recurse, we again make these decisions
     * until we get to our base case. if we wind up on the length-2 index, then there are only
     * two possibilities, return the max of both.
     *
     * If we wind up on the last index, just take that one, as we recurse and return, we will
     * take the maximum of our decisions, either to steal the ith house, or the i+1 house.
     * As we recurse, we pass the index to either choose the next i + 2, house, or the
     * i + 1 + 2, as we can't steal from adjacent houses.
     *
     * @param array int values of houses array
     * @return maximum value stolen when avoid adjacent houses
     */
    public int maxRobberyValue(int[] array) {
        return maxRobberyValueHelper(array, 0);
    }

    private int maxRobberyValueHelper(int[] array, int index) {
        if (index >= array.length) {
            return 0;
        }
        if (index == array.length - 1) {
            return array[array.length - 1];
        }

        if (index == array.length - 2) {
            return Math.max(array[array.length - 1], array[array.length - 2]);
        }

        int first = array[index] + maxRobberyValueHelper(array, index + 2);
        int second = array[index + 1] + maxRobberyValueHelper(array, index + 1 + 2);
        return Math.max(first, second);
    }

    /**
     * The only issue with the DP translation is checking the index bounds for
     * i + 1 + 2. We are effectively starting the DP count from the 3rd to last
     * index in the DP table. So if the i + 1 + 2 index is within bounds, then
     * take that value, if not, just take the i + 1.
     *
     * Long story short, if the recursive algo works, then we just need to tweak
     * the DP solution so that it also works.
     *
     * @param values int values of houses array
     * @return max value of adjacent houses
     */
    public int maxRobberyDP(int[] values) {
        int[] table = new int[values.length];

        table[table.length - 1] = values[values.length - 1];
        table[values.length - 2] = Math.max(values[values.length - 1], values[values.length - 2]);

        for (int i = table.length - 3; i >= 0; i--) {
            int first = values[i] + table[i + 2];
            int second = (i + 1 + 2 < table.length) ? values[i + 1] + table[i + 1 + 2] : values[i + 1];
            table[i] = Math.max(first, second);
        }
        return table[0];
    }
}
