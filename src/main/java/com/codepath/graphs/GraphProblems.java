package main.java.com.codepath.graphs;


import main.java.com.codepath.objects.Vertex;
import main.java.com.codepath.util.Util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

public class GraphProblems {
    public void BFS(List<Vertex<Integer>> adjList) {
        if (adjList == null || adjList.size() ==  0) {
            return;
        }

        Set<Integer> seen = new HashSet<>();
        for (Vertex<Integer> vertex : adjList) {
            if (!seen.contains(vertex)) {
                List<Integer> component = new ArrayList<>();
                bfsExplore(vertex, seen, component);
                Util.print(component);
            }
        }
    }

    private void bfsExplore(Vertex<Integer> vertex, Set<Integer> seen, List<Integer> component) {
        Queue<Vertex<Integer>>  q = new LinkedBlockingDeque<>();
        q.add(vertex);

        while (!q.isEmpty()) {
            Vertex<Integer> current = q.poll();
            for (Vertex<Integer> neighbor : current.getNeigbhors()) {
                if (!seen.contains(neighbor.getLabel())) {
                    seen.add(neighbor.getLabel());
                    component.add(neighbor.getLabel());
                    q.add(neighbor);
                }
            }
        }
    }

    public List<String> wordLadder(String start, String stop, String[] dictionary) {
        if (dictionary.length == 0) {
            if (isNeighbor(start, stop)) {
                return Arrays.asList(start, stop);
            }
            return Arrays.asList("-1");
        }

        if (start.equals(stop)) {
            for (String word : dictionary) {
                if (isNeighbor(word, start)) {
                    return Arrays.asList(start, word, stop);
                } else {
                    return Arrays.asList("-1");
                }
            }
        }

        ArrayList<String> totalWords = new ArrayList<>();
        totalWords.add(start);
        for (String word : dictionary) {
            totalWords.add(word);
        }
        totalWords.add(stop);

        Map<String, Vertex<String>> lookUp = new HashMap<String, Vertex<String>>();
        List<Vertex<String>> graph = buildGraphOther(totalWords, lookUp);
        Map<String, Vertex<String>> previous = new HashMap<>();
        Vertex<String> startVertex = graph.get(0);
        Vertex<String> stopVertex = graph.get(graph.size() - 1);

        LinkedList<Vertex<String>> q = new LinkedList<Vertex<String>>();
        q.addFirst(startVertex);

        startVertex.setVisited(true);
        while (!q.isEmpty()) {
            Vertex<String> current = q.pollFirst();
            for (Vertex neighbor: current.getNeigbhors()) {
                if (!neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    q.add(lookUp.get(neighbor.getLabel()));
                    previous.put((String) neighbor.getLabel(), current);
                }
            }
        }

        List<String> path = buildPath(previous, startVertex, stopVertex);
        if (path.size() == 1) {
            return Arrays.asList("-1");
        }
        return path;
    }

    private List<Vertex<String>> buildGraph(List<String> allWords, Map<String, Vertex<String>> lookUp) {
        List<Vertex<String>> adjlist = new ArrayList<>();
        for (String word : allWords) {
            Vertex<String> vertex = new Vertex<>(word);
            List<Vertex<String>> neighbors = new ArrayList<Vertex<String>>();
            vertex.setNeigbhors(neighbors);
            for (String otherWord: allWords) {
                if (word.equals(otherWord)) {
                    continue;
                } else {
                    if (isNeighbor(word, otherWord))  {
                        Vertex<String> neighbor = new Vertex<>(otherWord);
                        vertex.getNeigbhors().add(neighbor);
                    }
                }
            }
            adjlist.add(vertex);
            lookUp.put(vertex.getLabel(), vertex);
        }
        return adjlist;
    }

    private List<Vertex<String>> buildGraphOther(List<String> allWords, Map<String, Vertex<String>> lookUp) {
        List<Vertex<String>> adjlist = new ArrayList<>();
        for (String word : allWords) {
            Vertex<String> vertex = new Vertex<>(word);
            adjlist.add(vertex);
        }

        for (Vertex<String> vertex : adjlist) {
            List<Vertex<String>> neighbors = new ArrayList<>();
            vertex.setNeigbhors(neighbors);
            for (Vertex<String> otherVertex : adjlist) {
                if (vertex.getLabel().equals(otherVertex.getLabel())) {
                    continue;
                } else {
                    if (isNeighbor(vertex.getLabel(), otherVertex.getLabel())) {
                        vertex.getNeigbhors().add(otherVertex);
                    }
                }
            }
            lookUp.put(vertex.getLabel(), vertex);
        }
        return adjlist;
    }

    private List<String> buildPath(Map<String, Vertex<String>> previous, Vertex<String> startVertex, Vertex<String> stopVertex) {
        List<String> path = new ArrayList<>();
        path.add(stopVertex.getLabel());
        Vertex<String> current = previous.get(stopVertex.getLabel());
        while (current != null) {
            path.add(current.getLabel());
            current = previous.get(current.getLabel());
        }

        return path;
    }

    public boolean isNeighbor(String vertex, String potentialNeighbor) {
        if (Util.charDifference(vertex, potentialNeighbor) == 1) {
            return true;
        }
        return false;
    }

    /**
     * dijkstrasAlgorithm - Below is an implementation of dijkstra's algorithm, which
     * calculates the shortest path to each node from a given source. The most
     * difficult part is actually building the graph. Remember, we need to pull the element
     * with the smallest weight each time. I make use of an ArrayDeque, and then use the
     * Collections.min method to select the small element from the queue.
     *
     * RUNTIME: Because we have to choose minimum from, and we are using collections.min
     * that call takes linear time, so O(v) where v is the number of vertices.
     * We are also moving over the degree of v, for every vertex. That summation
     * comes to 2 |E| which is O(E). So the total run time is O(V + E)
     *
     * SPACE: is O(1) because are not using any addition memory, we are just adding
     * the vertex references to the solution set.
     *
     * @param source source vertex of the graph
     * @param graph graph, a list of vertices
     */
    public void dijkstrasAlgorithm(Vertex<String> source, List<Vertex<String>> graph) {
        Set<Vertex<String>> solutionSet = new HashSet<>();
        source.weight = 0;

        Queue<Vertex<String>> pQueue = new ArrayDeque<>(graph);
        while (!pQueue.isEmpty()) {
            Vertex<String> vertex = Collections.min(pQueue);
            solutionSet.add(vertex);
            pQueue.remove(vertex);
            for (Vertex<String> v : vertex.getNeigbhors()) {
                if (!solutionSet.contains(v)) {
                    int value = vertex.weight + vertex.edges.get(v);
                    v.weight = Math.min(v.weight, value);
                }
            }
        }
    }
}
