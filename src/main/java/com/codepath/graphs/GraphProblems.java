package main.java.com.codepath.graphs;


import main.java.com.codepath.objects.Cell;
import main.java.com.codepath.objects.Edge;
import main.java.com.codepath.objects.MinHeap;
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

    /**
     * primsAlgorithmMST
     * The following is an implementation of prim's algorithm. The first thing to understand
     * is what exactly we are going to return with regarding the execution of Prim's Algorithm.
     *
     * Considering we are building a minimum spanning tree from an existing Graph, which is to
     * say a series of edges in a graph such that all nodes are connected and the sum of those
     * edges is the least weight possible, we need to return a list of edges.
     *
     * We see that will be at most V - 1 edges.
     *
     * Prim's algorithm works like so. First we start with any arbitrary node. Then given that nodes
     * children, we select the least weight edge. This is the start of our MST. Given the two nodes
     * connected by 1 edge in our MST, we then look at all of the edges of those two nodes
     * and then select the least edge. Keep doing this, keeping in mind NOT to create a cycle.
     *
     * We do this until we have build a minimum spanning tree. The difficult part to this problem
     * was knowing how to continuously choose the least edge of the graph over and over again.
     * We do that operation by making a data structure. We construct a Minimum Heap, such that
     * the smallest element is always at the root. When we remove the root, the min heap maintains
     * its heap property by shuffling its values around such that the minimum is always at the root,
     * there by allowing us to remove the minimum element in constant time.
     *
     * We often have to update the edge weights, as we explore new lower weight edges. This values must
     * also be updated in our minHeap, and then the values in the minheap have to be reshuffled to maintain
     * the heap property. The plain english code the algorithm goes as follows
     *
     * 1) Create an empty list, which we will use to store our edges of the MST
     * 2) Create an edge map, which will store the vertices and the edges they map to
     * 3) Create a min heap
     * 4) Load all vertices into the min heap, which their weights initially set to infinity,
     * with the exception of our start node, which will be 0.
     *
     * 5) As long as the heap is not empty, we do the following
     *    a. Extract the minimum value from the min heap
     *    b. if that node minimum node, has an edge in our edge map, that add it to our result list
     *    c. For all of the neighbors of this minimum node
     *         i. Check if the minheap contains the neighbor
     *         ii. If it does, then get its edge weight with our current node
     *         iii. If the edge weight, is less than the weight we have for that node in our minheap
     *              then update the edge weight in the heap.
     *              Then add that edge to our edge map
     *     d. After exploring all neighbors, we then go back up the top of the for loop
     *     e. extract the next minimum node. And see if there is an edge for it, in our edge map
     *         i. if there is then that means that is one edge formed by our MST, so add it to our
     *         results. Repeat step c-e, until the min heap is empty
     *
     * Our results represent the edges of our minimum spanning tree.
     *
     * SPACE COMPLEXITY: We are storing all of the vertices in the heap, so its al least O(V).
     * We are also storing the edges in an Edge map, which is an additional O(E), so thats O(E + V)
     *
     * RUN TIME: Considering we are using a binary heap, we can extract the min in O(1) time,
     * perform a contains in O(1) time, and decrease a value in O(log v) time.
     *
     * We essentially have to sum up the degree for nodes, which is the Sum of Degree i, for all
     * v in V (all vertices in the graph), that sum is 2 |E| or O(E). We also have to decrease the
     * neigbhor vertex each time we find a lower weight, so that makes a running time of O(E log V).
     *
     *
     * @param graph graph input
     * @return list of edges
     */
    public List<Edge> primsAlgorithmMST(List<Vertex> graph) {
        List<Edge> edgesOfMst = new ArrayList<>();
        Map<Vertex, Edge> edgeMap = new HashMap<>();
        MinHeap<Vertex> minHeap = new MinHeap<>();

        Vertex start = graph.get(0);
        start.weight = 0;

        for (Vertex v: graph) {
            minHeap.add(v, v.weight);
        }

        while (!minHeap.isEmpty()) {
            Vertex current = minHeap.extractMin(); // O(1)
            if (edgeMap.containsKey(current)) {
                edgesOfMst.add(edgeMap.get(current));
            }
            List<Vertex> neighbors = current.getNeigbhors();
            for (Vertex neighbor: neighbors) { // sum of degree i, equals O(E)
                if (minHeap.contains(neighbor)) { // O(1)
                    int edgeWeight = current.getEdgeWeight(neighbor);
                    if (edgeWeight < minHeap.getWeight(neighbor).intValue()) {
                        minHeap.decrease(neighbor, edgeWeight); // O(log V)
                        Edge edge = new Edge(current, neighbor, edgeWeight);
                        edgeMap.put(neighbor, edge);
                    }
                }
            }
        }
        return edgesOfMst;
    }

    /**
     * Knights tour
     *
     * This problem is an application of BFS, my implementation is correct, but also slow for large test cases.
     *
     * Essentially the "neighbors" of any given cell represent the indices in the chessboard that correspond with
     * the moves that the "knight" can make in the game of chess. Those moves are outlined in the method
     * getKnightNeighborCells. I was essentially creating a list of neighbors and then, for every neighbor,
     * if I hadn't already seen it, then I was adding its path to a map.
     *
     * There is a quicker way of getting the neighbors though. Since we know that the moves have to correspond
     * to the rules of the knight character in chess, we could just keep two arrays of the move combinations,
     * then iterate through each combination, checking to see if its a valid move.
     *
     * This is much faster, than creating a list of up to 8 cell objects each time.
     *
     * The problem asked for returning the shortest path, so what I did was stored the path in a map,
     * essentially mapping a neighbor (or child) node to its parent. Then when the queue was empty
     * I would move back through the map, the same way one would with a linked list, and then count
     * + 1 each time to get the path length.
     *
     * While this works, that should only be done, if the actual path is required. It is not in this case,
     * all we need to do is get the path length. We can do that in the while loop, by simply saying,
     * take the length that I've already gone to get to my parent, and 1 to it, for each neighbor.
     * When we finally get to our target cell, we know to just return that number.
     *
     * We store the lengths to get to each cell in a 2-D array. We can also make use of this array
     * to check whether or not we have visited that cell already, thereby eliminating our need
     * for a HashSet. This over all is much faster.
     *
     * SPACE COMPLEXITY: O(rows * cols) for the 2-D array
     * RUNTIME COMPLEXITY: O(rows * cols) because we could potentially have rows * cols Cells in our queue
     *
     * @param rowDimension the row dimension of the chess board
     * @param colDimension the col dimension of the chess board
     * @param startRow the start row
     * @param startCol the start col
     * @param endRow the target row
     * @param endCol the target column
     * @return the length of the shortest path to the target for a knight
     */
    public int knightsTour(int rowDimension, int colDimension, int startRow, int startCol, int endRow, int endCol) {
        if (rowDimension == 0 && colDimension == 0) {
            return -1;
        }

        if (startRow == endRow && startCol == endCol) {
            return 0;
        }

        return knightTourHelper(rowDimension, colDimension, startRow, startCol, endRow, endCol);
    }

    public int knightTourHelper(int rowDimension, int colDimension, int startRow, int startCol, int endRow, int endCol) {
        Queue<Cell> cellQueue = new LinkedList<>();
        int[] knightMoveRow = new int[]{-2, -2, -1, 1, 2, 2, 1, -1};
        int[] knightMoveCol = new int[]{-1, 1, 2, 2, 1, -1, -2, -2};

        int[][] distance = new int[rowDimension][colDimension];

        Cell start = new Cell(startRow, startCol);
        cellQueue.add(start);
        int count = 0;

        Cell target = null;
        while (!cellQueue.isEmpty()) {
            Cell parentCell = cellQueue.poll();
            if (target != null) {
                break;
            }

            for (int i = 0; i < 8; i++) {
                int neighborRow = parentCell.row + knightMoveRow[i];
                int neighborCol = parentCell.col + knightMoveCol[i];
                if (isValidMove(neighborRow, rowDimension, neighborCol, colDimension)) {
                    if (distance[neighborRow][neighborCol] == 0) {
                        cellQueue.add(new Cell(neighborRow, neighborCol));
                        distance[neighborRow][neighborCol] =
                            distance[parentCell.row][parentCell.col] + 1;

                        if (neighborRow == endRow && neighborCol == endCol) {
                            count = distance[neighborRow][neighborCol];
                        }
                    }
                }
            }
        }

        if (count == 0) {
            return -1;
        }
        return count;
    }

    public boolean isValidMove(int row, int rowDimension, int col, int colDimension) {
        return (row >= 0 && row < rowDimension) && (col >= 0 && col < colDimension);
    }

    public List<Cell> getKnightNeighborCells(int rowDimension, int colDimension, Cell cell) {
        List<Cell> neighbors = new ArrayList<>(8);

        // 2 down and 1 right
        if (cell.row + 2 < rowDimension && cell.col + 1 < colDimension) {
            neighbors.add(new Cell(cell.row + 2, cell.col + 1));
        }
        // 2 down 1 left
        if (cell.row + 2 < rowDimension&& cell.col - 1 >= 0) {
            neighbors.add(new Cell(cell.row + 2, cell.col - 1));
        }

        // 2 right 1 down
        if (cell.col + 2 < colDimension && cell.row + 1 < rowDimension) {
            neighbors.add(new Cell(cell.row + 1, cell.col + 2));
        }
        // 2 right 1 up
        if (cell.col + 2 < colDimension && cell.row - 1 >= 0) {
            neighbors.add(new Cell(cell.row - 1, cell.col + 2));
        }

        // 2 left 1 down
        if (cell.col - 2 >= 0 && cell.row + 1 < rowDimension) {
            neighbors.add(new Cell( cell.row + 1, cell.col - 2));
        }
        // 2 left 1 up
        if (cell.col - 2 >= 0 && cell.row - 1 >= 0) {
            neighbors.add(new Cell(cell.row - 1, cell.col - 2));
        }

        // 2 up 1 right
        if (cell.row - 2 >= 0 && cell.col + 1 < colDimension) {
            neighbors.add(new Cell( cell.row - 2, cell.col + 1));
        }
        // 2 up 1 left
        if (cell.row - 2 >= 0 && cell.col - 1 >= 0) {
            neighbors.add(new Cell(cell.row - 2, cell.col - 1));
        }

        return neighbors;
    }
}
