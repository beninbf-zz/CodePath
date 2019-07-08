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
        for(int i = 0; i<len; i++){
            int x = n_points.get(i).get(0), y = n_points.get(i).get(1);
            pnt[i] = new Point(i, Math.sqrt((p_x-x)*1l*(p_x-x) + (p_y-y)*1l*(p_y-y)));
        }
        /*Shuffle the array points*/
        pnt = shuffle(pnt,new Random());
        List<List<Integer>> result = new ArrayList<>();
        topK(pnt,k);
        for(int i = 0; i<k; i++){
            result.add(n_points.get(pnt[i].index));
        }
        return result;
    }

    /* Get k points having least distance from point P.*/
    public static void topK(Point[] points, int k){
        int left = 0, right = points.length - 1;
        /*We just need the k smallest points. We dont care whether they are 
        sorted or not. Similarly once we get the smallest possible k elements, 
        we can skip sorting of unnecessary sub arrays.*/
        while(left<right){
            int part = split(points, left, right);
            if (part==k) {
                return;
            } 
            else if(part<k) {
                left=part+1;
            } 
            else{
                right=part-1;
            }
        }
    }

    /*Shuffles the array randomly*/
    public static Point[] shuffle(Point[] a, Random gen){
        for (int i = 0, n = a.length; i < n; i++){
            int ind = gen.nextInt(n - i) + i;
            Point d = a[i];
            a[i] = a[ind];
            a[ind] = d;
        }
        return a;
    }

    /*Similar to the partition function of quicksort. It partitions the array along the pivot 
    such that elements left to the pivot are smaller than it and to the right are larger than it.*/
    public static int split(Point[] points, int left, int right) {
        Point piv = points[left];
        int i = left, j = right + 1;
        while (true) {
            while (i < right && points[++i].compareTo(piv) < 0);
            while (j > left && points[--j].compareTo(piv) > 0);
            if (i >= j) {
                break;
            }
            Point temp = points[i];
            points[i] = points[j];
            points[j] = temp;
        }
        Point temp = points[j];
        points[j] = points[left];
        points[left] = temp;
        return j;
    }
    static class Point implements Comparable<Point>{
        int index;
        double dist;

        public Point(int i, double dist){
            this.index = i;
            this.dist = dist;
        }

        public int compareTo(Point part){
            return Double.compare(this.dist,part.dist);
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
    
    public static int MIN_POINT = -1000000000, MAX_POINT = 1000000000;

    public static void solve() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int p_x = Integer.parseInt(bufferedReader.readLine().trim());

        int p_y = Integer.parseInt(bufferedReader.readLine().trim());

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        int n_pointsRows = Integer.parseInt(bufferedReader.readLine().trim());
        int n_pointsColumns = Integer.parseInt(bufferedReader.readLine().trim());
        
        assert (MIN_POINT <= p_x && p_x <= MAX_POINT) : "invalid value of p_x";
        assert (MIN_POINT <= p_y && p_y <= MAX_POINT) : "invalid value of p_y";
        assert (1 <= n_pointsRows && n_pointsRows <= 100000) : "invalid value of n_pointsRows";
        assert (1 <= k && k <= n_pointsRows) : "invalid value of k";
        assert (n_pointsColumns == 2) : "invalid value of n_pointsColumns";

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

        for (int i=0; i<n_pointsRows; i++) {
            assert (MIN_POINT <= n_points.get(i).get(0) && n_points.get(i).get(0) <= MAX_POINT) : "invalid value of x coordinate";
            assert (MIN_POINT <= n_points.get(i).get(1) && n_points.get(i).get(1) <= MAX_POINT) : "invalid value of y coordinate";
        }

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