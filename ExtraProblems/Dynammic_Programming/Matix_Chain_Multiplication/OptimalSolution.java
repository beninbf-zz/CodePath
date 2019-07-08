import java.util.TreeSet;

public class OptimalSolution {
    // ==================start==================
    
    /*
     * Code author: Akshay Miterani
     * Space Complexity: O(len(mtxSizes)^2)
     * Time Complexity: O(len(mtxSizes)^3)
     */
    static int minMultiplicationCost(List<Integer> mtxSizes) {
        int n = mtxSizes.size();
        // Make the dp table
        int table[][] = new int[n][n];
        // Initialize all values with max value
        IntStream.range(0, n).forEach(i -> {
            Arrays.fill(table[i], Integer.MAX_VALUE);
        });
        return minCostFor(0, n - 1, mtxSizes, table);
    }
    
    private static int minCostFor(int start, int end, List<Integer> mtxSizes, int table[][]) {
        // If the value is stored in table, return the value
        if (table[start][end] != Integer.MAX_VALUE) {
            return table[start][end];
        }
        
        // If start and end have met, we have hit the base condition
        if (start + 1 == end) {
            return 0;
        }
        
        int min = Integer.MAX_VALUE;
        // Place parenthesis at every index and find out the minimum recursively
        for (int index = start + 1; index <= end - 1; index++) {
            // Cost of placing parenthesis at index
            int cost = mtxSizes.get(start) * mtxSizes.get(end) * mtxSizes.get(index)
            + minCostFor(start, index, mtxSizes, table) + minCostFor(index, end, mtxSizes, table);
            
            // Take the minimum of cost at every index
            min = Math.min(min, cost);
        }
        
        // Store and return the value
        table[start][end] = min;
        return table[start][end];
    }
    
    // ==================end==================}
