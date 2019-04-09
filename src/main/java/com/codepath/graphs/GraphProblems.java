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

    /**
     * This is an application of BFS algorithm for finding the shortest path between two
     * nodes in a graph. In stead of finding the shortest path from a source to all nodes,
     * we just find the shortest path from a start to a target. We use a hashmap to store
     * the path, and set a visited bool to make sure we don't visit nodes twice.
     *
     * Because there maybe multiple paths from our start to our target, we have to remember that we want
     * the shortest one. So before adding a subsequent vertex to the path, we should first check to
     * see if its already in our hashmap, if we don't we will end up overwriting it, because putting
     * something in a hashmap with the same key results in overrwriting the original value.
     *
     * We then construct the path using a list, and continuously inserting at the beginning of the list
     * so that we don't have to reverse it.
     *
     * Its basically just moving through a linked list using the hashmap to move Vertex to Vertex.
     *
     * @param start vertex
     * @param target target vertex
     * @return shortest path between start and target list of nodes
     */
    public List<Vertex> findShortestPathBetweenTwoVertices(Vertex start, Vertex target) {
        if (start == null || target == null) {
            return null;
        }

        Queue<Vertex> queue = new LinkedList<>();
        Map<String, Vertex> path = new HashMap<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            current.visited = true;
            if (current.label.equals(target.label)) {
                break;
            }
            List<Vertex> children = current.getNeigbhors();
            for (Vertex child : children) {
                if (child.visited == false && !path.containsKey(child.label)) {
                    path.put(String.valueOf(child.label), current);
                    queue.add(child);
                }
            }
        }

        if (path.get(String.valueOf(target.label)) == null) {
            return null;
        }

        Vertex current = target;
        List<Vertex> result = new LinkedList<>();
        while (current != null) {
            result.add(0, current);
            Vertex next = path.get(String.valueOf(current.label));
            current = next;
        }
        return result;
    }

    public List<Vertex> primsAlgorithmMST(List<Vertex> graph) {
        Set<String> visited = new HashSet<>();
        Vertex start = graph.get(0);
        List<Vertex> mst = new ArrayList<>();

        List<Vertex> queue = new ArrayList<>();
        queue.add(start);
        mst.add(start);
        visited.add(String.valueOf(start.getLabel()));
        while (visited.size() != graph.size()) {
            Vertex current = getMinimumEdgeVertex(queue, visited);
            visited.add(String.valueOf(current.getLabel()));
            List<Vertex> neighbors = current.getNeigbhors();
            for (Vertex child : neighbors) {
                if (!visited.contains(String.valueOf(child.getLabel()))) {
                    if (!queue.contains(child)) {
                        queue.add(child);
                    }
                }
            }
            mst.add(current);
        }
        return mst;
    }

    public Vertex getMinimumEdgeVertex(List<Vertex> vertices, Set<String> visited) {
        Vertex minimumVertexEdge = null;
        Map.Entry minimumEdge = null;
        for (Vertex candidate: vertices) {
            Set<Map.Entry> entrySet = candidate.edges.entrySet();
            for (Map.Entry entry : entrySet) {
                Vertex candidatedVertex = (Vertex) entry.getKey();
                if (!visited.contains(candidatedVertex.getLabel())) {
                    if (minimumEdge == null) {
                        minimumEdge = entry;
                    } else {
                        Integer entryValue = (Integer) entry.getValue();
                        Integer minimumEdgeValue = (Integer) minimumEdge.getValue();
                        if (entryValue.compareTo(minimumEdgeValue) < 0) {
                            minimumEdge = entry;
                        }
                    }
                }
            }
            if (minimumEdge != null) {
                minimumVertexEdge = (Vertex) minimumEdge.getKey();
            }
        }
        return minimumVertexEdge;
    }

}
