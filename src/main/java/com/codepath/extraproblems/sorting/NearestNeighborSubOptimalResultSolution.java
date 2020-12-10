package main.java.com.codepath.extraproblems.sorting;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class NearestNeighborSubOptimalResult {
    /*============================== START =========================================*/
    public static List<List<Integer>> nearest_neighbours(int p_x, int p_y, int k, List<List<Integer>> n_points) {
        int len = n_points.size();
        /*This priority queue will act as a maxheap, where the points having the
        most distance will be at the front of the queus*/
        PriorityQueue<Point> maxHeap = new PriorityQueue<>();
        for(int i = 0; i<len; i++){
            int x = n_points.get(i).get(0), y = n_points.get(i).get(1);
            /*We keep on adding all the points and ensure that the size of heap never
            exceeds k. If it does, we poll from it, that is remove the point having
            the most distance from point P from all the existing points in the maxheap.*/
            maxHeap.add(new Point(i, Math.sqrt((p_x-x)*1l*(p_x-x) + (p_y-y)*1l*(p_y-y))));
            if(maxHeap.size()>k){
                maxHeap.poll();
            }
        }
        /*All the k points in the maxheap are the answers*/
        List<List<Integer>> result = new ArrayList<>();
        for(Point p : maxHeap){
            int index = p.index;
            result.add(n_points.get(index));
        }
        return result;
    }

    /*Point is a class with two attributes for each point
        present n_points- index and distance from point P.*/
    static class Point implements Comparable<Point>{
        int index;
        double dist;

        public Point(int i, double dist){
            this.index = i;
            this.dist = dist;
        }

        public int compareTo(Point p){
            return Double.compare(p.dist,this.dist);
        }
    }
    /*============================== END =========================================*/
}


class NearestNeighborSubOptimalResultSolution {
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

        List<List<Integer>> result = NearestNeighborSubOptimalResult.nearest_neighbours(p_x, p_y, k, n_points);

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
