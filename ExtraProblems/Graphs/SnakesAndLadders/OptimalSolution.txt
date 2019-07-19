import java.util.TreeSet;

public class OptimalSolution {
    // ==================start==================
    
    /*
     * Space Complexity: O(n)
     * Time Complexity: O(n)
     */
    static int minNumberOfRolls(int n, List<Integer> moves) {
        int[] move = new int[n];
        // Converting to 0 index based array
        for (int i = 0; i < n; i++) {
            move[i] = moves.get(i);
            if (move[i] != -1)
                move[i]--;
        }
        return minNumberOfRollsHelper(move, n);
    }
    
    // An entry in queue used in BFS
    static class Node {
        int index;// Vertex number
        int dist;// Distance of 0
    }
    
    static int minNumberOfRollsHelper(int move[], int n) {
        boolean visited[] = new boolean[n];
        
        // Add the 0th cell to the queue as we start from there
        Queue<Node> queue = new LinkedList<>();
        Node node = new Node();
        node.index = 0;
        node.dist = 0;
        queue.add(node);
        
        // Run BFS from Node 0
        while (!queue.isEmpty()) {
            Node removed = queue.poll();
            // Check if already visited
            if (!visited[removed.index]) {
                // Mark the removed cell visited and update the distance
                visited[removed.index] = true;
                if(removed.index==n-1){
                    return removed.dist;
                }
                // Add all the neighbors to the queue.
                for (int dice = 1; dice <= 6; dice++) {
                    if (removed.index + dice < n) {
                        Node newNode = new Node();
                        if (move[removed.index + dice] == -1) {
                            // If there is no snake or ladder
                            newNode.index = removed.index + dice;
                        } else {
                            // If there is a snake or a ladder
                            newNode.index = move[removed.index + dice];
                        }
                        // This requires one dice roll
                        newNode.dist = removed.dist + 1;
                        queue.add(newNode);
                    }
                }
            }
        }
        
        // No way to reach
        return -1;
    }
    
    // ==================end==================
}
