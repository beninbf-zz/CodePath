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

    /*
     * Complete the 'hasCycle' function below.
     *
     * The function accepts INTEGER N, INTEGER M and 2D_INTEGER_ARRAY edges as parameter.
     */


    // ============================ Start ============================
    

    public static boolean hasCycle(int N, int M, List<List<Integer>> edges) {
        ArrayList<Integer> adjacency[] = new ArrayList[N];
        // To store adjacency matrix of graph

        for (int var = 0; var < N; var++) {
            adjacency[var] = new ArrayList<Integer>();
        }

        for (int var = 0; var < M; var++) {
            int u = edges.get(var).get(0), v = edges.get(var).get(1);
            adjacency[u].add(v);
        }
        boolean visited[] = new boolean[N];
        boolean is_in_stack[] = new boolean[N];
        for (int var = 0; var < N; var++) {
            if(hasCycleUtil(var, adjacency, visited, is_in_stack)) {
                return true;
            }
        }
        return false;
    }


    public static boolean hasCycleUtil(int cur_vertex, ArrayList<Integer> adj[],
       	boolean visited[], boolean is_in_stack[]) {
        
        // If back edge present then return true
        if (is_in_stack[cur_vertex])
            return true;

        // If cur_vertex is already visited then return false, 
        // no need to apply logic as it has been already applied
        if (visited[cur_vertex])
            return false;

        // Mark the current node as visited and
        // part of recursion stack
        visited[cur_vertex]=true;
        is_in_stack[cur_vertex]=true;

        // Recur for all the vertices adjacent to this vertex
        for (int v: adj[cur_vertex])
            if (hasCycleUtil(v, adj, visited, is_in_stack))
                return true;

        // remove the vertex from recursion stack
        is_in_stack[cur_vertex]=false;

        return false;

	}
}


// ============================ End =============================


class Solution {
    public static void main(String args[]) {
        /*
        This function is used to increase the size of recurison stack. 
        It makes the size of stack 2^26 ~= 10^8
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(bufferedReader.readLine().trim());// N = Number of vertices
        int M = Integer.parseInt(bufferedReader.readLine().trim());// M = Number of directed edges

        List<List<Integer>> edges = new ArrayList<>();// To store directed edges

        IntStream.range(0, M).forEach(i -> {
            try {
                edges.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        bufferedReader.close();

        boolean result = Result.hasCycle(N,M, edges);

        bufferedWriter.write(result);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }
}
