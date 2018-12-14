package main.java.com.codepath.graphs;


import main.java.com.codepath.objects.Vertex;
import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

public class GraphProblems {
    public void shortestPathWithKeysAndDoors(String[][] grid) {

    }

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

        ArrayList<String> totalWords = new ArrayList<>();
        totalWords.add(start);
        for (String word : dictionary) {
            totalWords.add(word);
        }
        totalWords.add(stop);

        Map<String, Vertex<String>> lookUp = new HashMap<String, Vertex<String>>();
        List<Vertex<String>> graph = buildGraph(totalWords, lookUp);
        Map<String, Vertex<String>> previous = new HashMap<>();
        Vertex<String> startVertex = graph.get(0);
        Vertex<String> stopVertex = graph.get(graph.size() - 1);

        LinkedList<Vertex<String>> q = new LinkedList<Vertex<String>>();
        q.addFirst(startVertex);

        Set<String> seen = new HashSet<>();
        seen.add(startVertex.getLabel());
        while (!q.isEmpty()) {
            Vertex<String> current = q.pollFirst();
            for (Vertex neighbor: current.getNeigbhors()) {
                if (!seen.contains(neighbor.getLabel())) {
                    seen.add((String) neighbor.getLabel());
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
        if (Util.charDifferenceOther1(vertex, potentialNeighbor) == 1) {
            return true;
        }
        return false;
    }

}
