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
    public void testGetShortestPathBetweenTwoVertices() {
        Vertex<String> oneVertex= new Vertex<>("1");
        Vertex<String> twoVertex = new Vertex<>("2");
        Vertex<String> threeVertex = new Vertex<>("3");
        Vertex<String> fourVertex = new Vertex<>("4");
        Vertex<String> fiveVertex = new Vertex<>("5");

        oneVertex.addNeighbor(threeVertex);
        oneVertex.addNeighbor(twoVertex);
        twoVertex.addNeighbor(fiveVertex);
        fourVertex.addNeighbor(oneVertex);
        fourVertex.addNeighbor(threeVertex);
        fiveVertex.addNeighbor(fourVertex);

        List<Vertex> result = testObj.findShortestPathBetweenTwoVertices(twoVertex, threeVertex);
        String[] answer = new String[]{"2", "5", "4", "3"};

        for (int i = 0; i < answer.length; i++) {
            assertTrue("should be equal", result.get(i).label.equals(answer[i]));
        }
    }

    @Test
    public void testGetMinimumEdgeVertex() {
        Vertex<String> aVertex = new Vertex<>("A");
        Vertex<String> dVertex = new Vertex<>("D");
        Vertex<String> cVertex = new Vertex<>("C");
        Vertex<String> bVertex = new Vertex<>("B");
        Vertex<String> eVertex = new Vertex<>("E");

        aVertex.addEdges(dVertex, 3);
        aVertex.addEdges(cVertex, 3);
        aVertex.addEdges(bVertex, 2);

        List<Vertex> graph = Arrays.asList(aVertex);

        Set<String> visited = new HashSet<>();

        Vertex<String> minimum = testObj.getMinimumEdgeVertex(graph, visited);
        assertEquals("Should be vertex b", "B", minimum.getLabel());

        bVertex.addEdges(cVertex, 4);
        bVertex.addEdges(eVertex, 3);

        visited.add(aVertex.getLabel());
        visited.add(bVertex.getLabel());
        List<Vertex> newGraph = Arrays.asList(aVertex, bVertex);
        Vertex<String> nextMinimum = testObj.getMinimumEdgeVertex(newGraph, visited);

        assertEquals("next minimum should be 3", "C", nextMinimum.getLabel());
    }

    @Test
    public void testPrimsAlgorithm() {
        Vertex<String> aVertex = new Vertex<>("A");
        Vertex<String> dVertex = new Vertex<>("D");
        Vertex<String> cVertex = new Vertex<>("C");
        Vertex<String> bVertex = new Vertex<>("B");
        Vertex<String> eVertex = new Vertex<>("E");
        Vertex<String> fVertex = new Vertex<>("F");
        Vertex<String> gVertex = new Vertex<>("G");

        aVertex.addEdges(dVertex, 3);
        aVertex.addEdges(cVertex, 3);
        aVertex.addEdges(bVertex, 2);
        bVertex.addEdges(cVertex, 4);
        bVertex.addEdges(eVertex, 3);
        cVertex.addEdges(dVertex, 5);
        cVertex.addEdges(eVertex, 1);
        cVertex.addEdges(fVertex, 6);
        dVertex.addEdges(fVertex, 7);
        eVertex.addEdges(fVertex, 8);
        fVertex.addEdges(gVertex, 9);

        List<Vertex> graph = new ArrayList<>(Arrays.asList(aVertex, bVertex, cVertex, dVertex, eVertex, fVertex));

        List<Vertex> minimumSpanningTree  = testObj.primsAlgorithmMST(graph);
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

        aSource.addEdges(bVertex, 3);
        aSource.addEdges(cVertex, 1);
        aSource.addEdges(dVertex, 2);
        dVertex.addEdges(eVertex, 1);
        eVertex.addEdges(cVertex, 4);
        cVertex.addEdges(fVertex, 2);

        List<Vertex<String>> graph = new ArrayList<>(Arrays.asList(aSource, bVertex, cVertex, dVertex, eVertex, fVertex));
        return graph;
    }

    private void getDirectedGraph() {
        Vertex<String> oneVertex= new Vertex<>("1");
        Vertex<String> twoVertex = new Vertex<>("2");
        Vertex<String> threeVertex = new Vertex<>("3");
        Vertex<String> fourVertex = new Vertex<>("4");
        Vertex<String> fiveVertex = new Vertex<>("5");

        oneVertex.addNeighbor(threeVertex);
        oneVertex.addNeighbor(twoVertex);
        twoVertex.addNeighbor(fiveVertex);
        fourVertex.addNeighbor(oneVertex);
        fourVertex.addNeighbor(threeVertex);
        fiveVertex.addNeighbor(fourVertex);
    }


}