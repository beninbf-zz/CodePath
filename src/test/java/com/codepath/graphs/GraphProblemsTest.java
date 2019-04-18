package test.java.com.codepath.graphs;

import main.java.com.codepath.graphs.GraphProblems;
import main.java.com.codepath.objects.Cell;
import main.java.com.codepath.objects.Edge;
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
        String start = "bat";
        String stop = "had";
        String[] words = new String[]{"cat", "hat", "bad"};

        String[] answer = testObj.wordLadder(start, stop, words);
        String[] expectedAnswer = new String[] {"bat", "hat", "had"};
        assertEquals("Length of answer should be 3", 3, answer.length);
        for (int i = 0; i < expectedAnswer.length; i++) {
            assertEquals("Strings should be equal", expectedAnswer[i], answer[i]);
        }
    }

    @Test
    public void wordLadderEff() {
        String start = "bat";
        String stop = "had";
        String[] words = new String[]{"cat", "hat", "bad", "had"};

        String[] answer = testObj.wordLadderEff(start, stop, words);
        String[] expectedAnswer = new String[] {"bat", "hat", "had"};
        assertEquals("Length of answer should be 3", 3, answer.length);

        for (int i = 0; i < expectedAnswer.length; i++) {
            assertEquals("Strings should be equal", expectedAnswer[i], answer[i]);
        }
    }

    @Test
    public void wordLadder2() {
        String start = "y";
        String stop = "x";
        String[] words = new String[]{"a", "b", "c"};

        String[] answer = testObj.wordLadderEff(start, stop, words);
        String[] expectedAnswer = new String[] {"y", "x"};
        assertEquals("Length of answer should be 2", 2, answer.length);

        for (int i = 0; i < expectedAnswer.length; i++) {
            assertEquals("Strings should be equal", expectedAnswer[i], answer[i]);
        }
    }



    @Test
    public void wordLadder1() {
        String start = "cccc";
        String stop = "cccc";
        String[] words = new String[]{"cccw", "accc", "accw"};

        String[] answer = testObj.wordLadder(start, stop, words);
        String[] expectedAnswer = new String[] {"cccc", "cccw", "cccc"};
        assertEquals("Length of answer should be 3", 3, answer.length);

        for (int i = 0; i < expectedAnswer.length; i++) {
            assertEquals("Strings should be equal", expectedAnswer[i], answer[i]);
        }
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
    public void testPrimsAlgorithm() {
        Vertex<String> aVertex = new Vertex<>("A");
        Vertex<String> dVertex = new Vertex<>("D");
        Vertex<String> cVertex = new Vertex<>("C");
        Vertex<String> bVertex = new Vertex<>("B");
        Vertex<String> eVertex = new Vertex<>("E");
        Vertex<String> fVertex = new Vertex<>("F");
        Vertex<String> gVertex = new Vertex<>("G");

        aVertex.addEdges(dVertex, 3, true);
        aVertex.addEdges(cVertex, 3, true);
        aVertex.addEdges(bVertex, 2, true);
        bVertex.addEdges(cVertex, 4, true);
        bVertex.addEdges(eVertex, 3, true);
        cVertex.addEdges(dVertex, 5, true);
        cVertex.addEdges(eVertex, 1, true);
        cVertex.addEdges(fVertex, 6, true);
        dVertex.addEdges(fVertex, 7, true);
        eVertex.addEdges(fVertex, 8, true);
        fVertex.addEdges(gVertex, 9, true);

        List<Vertex> graph = new ArrayList<>(Arrays.asList(aVertex, bVertex, cVertex, dVertex, eVertex, fVertex, gVertex));

        List<Edge> minimumSpanningTree  = testObj.primsAlgorithmMST(graph);
        System.out.println(minimumSpanningTree);
    }

    @Test
    public void testPrimsAlgorithm1() {
        Vertex<String> aVertex = new Vertex<>("A");
        Vertex<String> dVertex = new Vertex<>("D");
        Vertex<String> cVertex = new Vertex<>("C");
        Vertex<String> bVertex = new Vertex<>("B");
        Vertex<String> eVertex = new Vertex<>("E");
        Vertex<String> fVertex = new Vertex<>("F");

        aVertex.addEdges(dVertex, 1, true);
        aVertex.addEdges(bVertex, 3, true);
        bVertex.addEdges(dVertex, 3, true);
        bVertex.addEdges(cVertex, 1, true);
        dVertex.addEdges(eVertex, 6, true);
        cVertex.addEdges(dVertex, 1, true);
        cVertex.addEdges(fVertex, 4, true);
        cVertex.addEdges(eVertex, 5, true);
        eVertex.addEdges(fVertex, 2, true);

        List<Vertex> graph = new ArrayList<>(Arrays.asList(aVertex, bVertex, cVertex, dVertex, eVertex, fVertex));

        List<Edge> minimumSpanningTree  = testObj.primsAlgorithmMST(graph);
        System.out.println(minimumSpanningTree);
    }

    @Test
    public void testKnightsTour() {
        int ans= testObj.knightsTour(1, 1, 0, 0, 0, 0);
        assertEquals("Should be 0", 0, ans);

        int ans1 = testObj.knightsTour(5, 5, 0, 0, 4, 1);
        assertEquals("Should be 3", 3, ans1);

        int ans2 = testObj.knightsTour(33333, 3, 333, 0, 33332, 2);
        assertEquals("Should be 16501", 16501, ans2);

    }


    @Test
    public void getKnightNeighborCells() {
        Cell cell = new Cell(0, 0);
        List<Cell> neighbors = testObj.getKnightNeighborCells(5, 5, cell);

        System.out.println(neighbors);
        assertEquals("Should only have 2 neighbors", 2, neighbors.size());

        List<Cell> neighbors1 = testObj.getKnightNeighborCells(5, 5, new Cell(3, 3));
        System.out.println(neighbors1);
        assertEquals("Should only have 4 neighbors", 4, neighbors1.size());
    }

    @Test
    public void alienDictionary() {
        String[] input = new String[]{"baa", "abcd", "abca", "cab", "cad"};
        String output = testObj.alienDictionary(input);
        assertEquals("Ordering should be bdac", "bdac", output);
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

        aSource.addEdges(bVertex, 3, true);
        aSource.addEdges(cVertex, 1, true);
        aSource.addEdges(dVertex, 2, true);
        dVertex.addEdges(eVertex, 1, true);
        eVertex.addEdges(cVertex, 4, true);
        cVertex.addEdges(fVertex, 2, true);

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