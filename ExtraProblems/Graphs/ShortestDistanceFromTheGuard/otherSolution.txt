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
    
    static class Cell {
        int x, y, val;
        Cell(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }
    
    static List<List<Integer>> find_shortest_distance_from_a_guard(List<List<Character>> grid) {
        int n = grid.size();
        int m = grid.get(0).size();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                temp.add(-1);
            }
            result.add(temp);
        }
        // A queue for multi source bfs
        Queue<Cell> q = new LinkedList<>();
        //boolean array to keep a track of visited cells
        boolean b[][] = new boolean[n][m];
        //we start from any guard, so we add them to the queue
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid.get(i).get(j) == 'G') {
                    q.add(new Cell(i, j, 0));
                }
            }
        }
        //multi source bfs starts
        while (!q.isEmpty()) {
            Cell cell = q.poll();
            if (b[cell.x][cell.y]) {
                continue;
            }
            // mark the cell visited
            b[cell.x][cell.y] = true;
            result.get(cell.x).set(cell.y, cell.val);
            //iterate through the other valid neighbours
            for (Cell side : getNeighbours(cell.x, cell.y, n, m, grid)) {
                side.val = cell.val + 1;
                q.add(side);
            }
        }
        return result;
    }
    // get all the valid neighbours for a cell (x,y)
    static ArrayList<Cell> getNeighbours(int x, int y, int n, int m, 
        List<List<Character>> grid) {
        ArrayList<Cell> ans = new ArrayList<>();
        
        if(x>0 && grid.get(x-1).get(y)!='W') {
            Cell cell = new Cell(x - 1, y, 0);
            ans.add(cell);
        }
        if(y>0 && grid.get(x).get(y-1)!='W') {
            Cell cell = new Cell(x, y - 1, 0);
            ans.add(cell);
        }
        if(x<(n-1) && grid.get(x+1).get(y)!='W') {
            Cell cell = new Cell(x + 1, y, 0);
            ans.add(cell);
        }
        if(y<(m-1) && grid.get(x).get(y+1)!='W') {
            Cell cell = new Cell(x, y + 1, 0);
            ans.add(cell);
        }
        return ans;
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

        int n = Integer.parseInt(bufferedReader.readLine().trim());
        int m = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Character>> grid = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
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
