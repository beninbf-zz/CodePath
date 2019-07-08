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
    /*
    * Complete the 'findPascalTriangle' function below.
    *
    * The function accepts INTEGER as parameter.
    * Return 2D INTEGER ARRAY.
    */
    static List<List<Integer>> findPascalTriangle(int n) {
        int mod = 1000000007;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++){ 
            // Every ith row has number of integers  
            // equal to row number
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j <= i; j++){
                // First and last values in every row are 1 
                if (i == j || j == 0){ 
                    row.add(1); 
                }
                // Other values are sum of values just  
                // above and left of above 
                else{
                    row.add((result.get(i-1).get(j-1) + result.get(i-1).get(j))%mod); 
                }
            }
            result.add(row);
        }
        return result;
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());
        List<List<Integer>> result = Result.findPascalTriangle(n);

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
/**
 * Time complexity: O(n^2)
 * Space complexity: O(n^2)
 */