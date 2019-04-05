package test.java.com.codepath.graphs;

import main.java.com.codepath.graphs.GraphProblems;
import main.java.com.codepath.objects.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import static org.junit.Assert.*;

public class GraphProblemsTest {

    private GraphProblems testObj;

    @Before
    public void setUp() {
        testObj = new GraphProblems();
    }

    @Test
    public void BFS() {
    }

    @Test
    public void wordLadder() {
    }

    @Test
    public void isNeighbor() {
    }

    @Test
    public void dijkstrasAlgorithm() {
      List<Vertex<String>> graph = getGraph();
      testObj.dijkstrasAlgorithm(graph.get(0), graph);

      for (Vertex<String> v : graph) {
          if (v.label.equals("A")) {
              assertTrue("should be 0", v.weight == 0);
          } else if (v.label.equals("B")) {
              assertTrue("should be 3", v.weight == 3);
          } else if (v.label.equals("C")) {
              assertTrue("should be 1", v.weight == 1);
          } else if (v.label.equals("D")) {
              assertTrue("should be 2", v.weight == 2);
          } else if (v.label.equals("E")) {
              assertTrue("should be 3", v.weight == 3);
          } else if (v.label.equals("F")) {
              assertTrue("should be 3", v.weight == 3);
          }
      }
    }

    @Test
    public void priorityQueue() {
        Queue<String> queue = new PriorityQueue<>();
        queue.add("Z");
        queue.add("T");
        queue.add("B");

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }

        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                if (a.compareTo(b) < 0) {
                    return 1;
                } else if (a.compareTo(b) > 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        Queue<String> queueGreater = new PriorityQueue<>(cmp);
        queueGreater.add("Z");
        queueGreater.add("T");
        queueGreater.add("B");

        while (!queueGreater.isEmpty()) {
            System.out.println(queueGreater.poll());
        }

    }

    private List<Vertex<String>> getGraph() {

        Vertex<String> aSource = new Vertex<>("A");
        Vertex<String> bVertex = new Vertex<>("B");
        Vertex<String> cVertex = new Vertex<>("C");
        Vertex<String> dVertex = new Vertex<>("D");
        Vertex<String> eVertex = new Vertex<>("E");
        Vertex<String> fVertex = new Vertex<>("F");


        Set<Vertex<String>> set = new HashSet<>();

        set.add(aSource);
        set.add(bVertex);

//        if (set.contains(bVertex)) {
//            System.out.println("working");
//        } else {
//            System.out.println("not working");
//        }
//
//        if (!set.contains(cVertex)) {
//            System.out.println("working");
//        }



        addEdge(aSource, bVertex, 3);
        addEdge(aSource, cVertex, 1);
        addEdge(aSource, dVertex, 2);
        addEdge(dVertex, eVertex, 1);
        addEdge(eVertex, cVertex, 4);
        addEdge(cVertex, fVertex, 2);

        //aSource.weight = 0;
        //bVertex.weight = 10;

        List<Vertex<String>> graph = new ArrayList<>(Arrays.asList(aSource, bVertex, cVertex, dVertex, eVertex, fVertex));

        //Queue<Vertex<String>> queue = new LinkedList<>(graph);

        //Queue<Vertex<String>> arrayDeque = new ArrayDeque<>(graph);

//        while (!arrayDeque.isEmpty()) {
//            //Vertex<String> item = queueGreater.poll();
//
//
//            Vertex<String> item = Collections.min(arrayDeque);
//            arrayDeque.remove(item);
//
//            //queue.remo.
//            if (item.label.equals("C")) {
//                item.weight = 1;
//            }
//            System.out.println(item);
//
//            //arrayDeque.remove(item);
//
//        }

        return graph;
    }

    private void addEdge(Vertex vertex1, Vertex vertex2, int weight) {
        vertex1.addEdges(vertex2, weight);
        vertex1.addNeighbor(vertex2);

        vertex2.addEdges(vertex1, weight);
        vertex2.addNeighbor(vertex1);
    }


}