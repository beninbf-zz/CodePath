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

    /*============================== START =========================================*/
    public static List<List<Integer>> nearest_neighbours(int p_x, int p_y, int k, List<List<Integer>> n_points) {
        int len = n_points.size();
        /*Point is a class with two attributes for each point 
        present n_points- index and distance from point P.*/
        Point[] pnt = new Point[len]; 
        /*We fill the values in pnt array accordingly*/
        for(int i = 0; i<len; i++) {
            int x = n_points.get(i).get(0), y = n_points.get(i).get(1);
            pnt[i] = new Point(i, Math.sqrt((p_x-x)*1l*(p_x-x) + (p_y-y)*1l*(p_y-y)));
        }
        /*We sort the point array according to distance, that means the point
        having least distance from point P would be at the lowest index (index 0) and the
        point with the maximum distance would be at the last index.*/
        Arrays.sort(pnt);
        List<List<Integer>> result = new ArrayList<>();
        /*We simply take the top k points and return them as the answer*/
        for(int i = 0; i < k; i++){
            int index = pnt[i].index;
            result.add(n_points.get(index));
        }
        return result;
    }

    static class Point implements Comparable<Point>{
        int index;
        double dist;

        public Point(int i, double dist){
            this.index = i;
            this.dist = dist;
        }

        public int compareTo(Point p){
            return Double.compare(this.dist,p.dist);
        }
    }
/*============================== END =========================================*/
}


class Solution {
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

        int p_x = Integer.parseInt(bufferedReader.readLine().trim());

        int p_y = Integer.parseInt(bufferedReader.readLine().trim());

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        int n_pointsRows = Integer.parseInt(bufferedReader.readLine().trim());
        int n_pointsColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> n_points = new ArrayList<>();

        IntStream.range(0, n_pointsRows).forEach(i -> {
            try {
                n_points.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<List<Integer>> result = Result.nearest_neighbours(p_x, p_y, k, n_points);

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