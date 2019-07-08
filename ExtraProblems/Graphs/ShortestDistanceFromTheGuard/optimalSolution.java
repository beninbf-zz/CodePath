import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    // -------------------- START ----------------------
    // Complete the find_shortest_distance_from_a_guard function below.
    static char GUARD = 'G';
    static char OPEN = 'O';
    static char WALL = 'W';
    
    static List<List<Integer>> find_shortest_distance_from_a_guard(List<List<Character>> grid) {
        int n = grid.size();
        int m = grid.get(0).size();
        List<List<Integer>> distance = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            distance.add(new ArrayList<Integer>());
            for (int j = 0; j < m; j++) {
                distance.get(i).add(-1);
            }
        }
        
        Queue<Node> queue = new LinkedList<>();
        // Initialize marker to keep track of visited cells
        boolean visited[][] = new boolean[n][m];
        // Adding all guards to the Queue
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid.get(i).get(j) == GUARD) {
                    // Cells with guards are at 0 distance from the Guard
                    queue.add(new Node(i, j, 0));
                }
            }
        }
        // Starting a multi-source BFS
        while (!queue.isEmpty()) {
            Node removedCell = queue.poll();
            // Continue if already visited
            if (visited[removedCell.x][removedCell.y]) {
                continue;
            }
            
            // Mark the new cell visited
            visited[removedCell.x][removedCell.y] = true;
            // Update the distance
            distance.get(removedCell.x).set(removedCell.y, removedCell.dist);
            // Iterate over other valid neighbours
            for (Node neighbour : validNeighboursOf(removedCell.x, removedCell.y, n, m, grid)) {
                neighbour.dist = removedCell.dist + 1;
                queue.add(neighbour);
            }
        }
        
        return distance;
    }
    
    // An entry in queue used in BFS
    static class Node {
        Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
        
        int x, y;// Row and Column number
        int dist;// Distance of this vertex from source
    }
    /*
     * To calculate valid neighbours of node at index (x, y) from grid.
     */
    static ArrayList<Node> validNeighboursOf(int x, int y, int n, int m, 
        List<List<Character>> grid) {
        ArrayList<Node> listOfValidNeighbours = new ArrayList<>();
        
        // To the left
        if (isOnBoard(x - 1, y, n, m, grid)) {
            Node node = new Node(x - 1, y, 0);
            listOfValidNeighbours.add(node);
        }
        
        // To the right
        if (isOnBoard(x + 1, y, n, m, grid)) {
            Node node = new Node(x + 1, y, 0);
            listOfValidNeighbours.add(node);
        }
        
        // To the top
        if (isOnBoard(x, y - 1, n, m, grid)) {
            Node node = new Node(x, y - 1, 0);
            listOfValidNeighbours.add(node);
        }
        
        // To the bottom
        if (isOnBoard(x, y + 1, n, m, grid)) {
            Node node = new Node(x, y + 1, 0);
            listOfValidNeighbours.add(node);
        }
        
        return listOfValidNeighbours;
    }
    
    static boolean isOnBoard(int x, int y, int n, int m, List<List<Character>> grid) {
        // Cases for the cell not being on the board
        if (x < 0 || y < 0 || x >= n || y >= m || grid.get(x).get(y) != OPEN) {
            return false;
        } else {
            return true;
        }
    }

    // -------------------- END ----------------------
}

class Solution{
    public static void main(String args[]) {
        /*
        This function is used to increase the size of recursion stack. It makes the size of stack
        2^26 ~= 10^8
        */
        new Thread(null, new Runnable() {
            public void run() {
                try{
                    solve();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 26).start();
    }
    public static void solve() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int gridRows = Integer.parseInt(bufferedReader.readLine().trim());
        int gridColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Character>> grid = new ArrayList<>();

        IntStream.range(0, gridRows).forEach(i -> {
            try {
                grid.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(""))
                        .map(e -> e.charAt(0))
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<List<Integer>> result = Result.find_shortest_distance_from_a_guard(grid);

        result.stream()
            .map(
                r -> r.stream()
                    .map(Object::toString)
                    .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
                try {
                    bufferedWriter.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        bufferedWriter.close();

        bufferedReader.close();
    }

}
