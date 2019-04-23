package main.java.com.codepath.graphs;


import main.java.com.codepath.objects.Cell;
import main.java.com.codepath.objects.Color;
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
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

    /**
     * You are given a dictionary of words named words, and two strings named start and stop.
     * All given strings have equal length. Dictionary words are not in any particular order,
     * there may be duplicates, too.
     *
     * You need to find the shortest possible sequence of strings (two or more) such that:
     *
     * First string is start.
     * Last string is stop.
     * Every string (except the first one) differs from the previous one by exactly one character.
     * Every string (except, possibly, first and last ones) are in the dictionary of words.
     *
     * If two or more such sequences exist, any one of them is a correct answer.
     *
     * If no such sequence is there to be found, [“-1”] (a sequence of one string, “-1”)
     * is the correct answer.
     *
     * There are three arguments:
     *
     * Array of strings words,
     * String start,
     * String stop.
     *
     * This word transformation algorithm is another graphing problem. The key take away is that
     * we want to find the shortest number of words in a transformation to get from a start word
     * to a stop word. The key word being "shortest" should give away that this should be done
     * with a BFS implementation.
     *
     * Also the problem description says that every word in the dictionary differs from the "previous"
     * one by exactly one character. That explains that two words differing by one character should in
     * effect create an edge between two words. In other words, a words neighbors in a graph will only
     * differ by one character.
     *
     * With that said there are two ways to build the graph for this algorithm.
     *
     * 1) For each and every word in the dictionary, check to see which are exactly one character different.
     * if they're is character different, then that will constitute a neighbor, so that word will be added to
     * the neighbors list of the other word. When all of the words in the dictionary including the stop and start
     * have been assembled into a list of vertices, all with their appropriate neighbors, we then simply run
     * BFS, saving the current node and the children nodes in map. At the end of BFS, we will use the map to
     * recreate the path from the start to the stop.
     *
     * RUNTIME COMPLEXITY: Doing it this way, building the graph is quite expensive. Its
     * O(numberOfWordsInDictionary * numberOfWordsInDictionary * numberOfCharactersInLongestWord)
     *
     * The number Of Characters In tne Longest Word is due to the isNeighbor method which has to compare
     * all of the characters in two strings.
     *
     * After that we run we BFS, the interior loop is the sum of the degree of a vertex for all vertices
     * in the graph which is O(E), where E is the number of words one character away from a given word,
     * the edges.
     *
     * The running time here is dominated by O(numberOfWordsInDictionary * numberOfWordsInDictionary
     * * numberOfCharactersInLongestWord)
     *
     * SPACE COMPLEXITY: O(N) where N is the number of words in the dictionary.
     *
     * 2) (in wordLadderEff) If the number of words in the dictionary is large, then we need another way to determine the edges.
     * The other option we can use, given that we know that words that are neighbors only differ by one character
     * is, for every word in the dictionary (including the start and stop) generate all possible words, that differ
     * by one character. For this newly generated, then check to see if its in the dictionary, if it is, then we
     * know we have a neighbor, so then we can simply save that word and the original (untransformed word) in a
     * map, which we can use to generate a path later.
     *
     * Every word that we find, that is a neighbor, we will add into the queue, and continue to run BFS.
     *
     * RUNTIME COMPLEXITY: for every word in the dictionary, we generate each possible string with at most
     * one character changed. That one character changed at each position in the String. So there are
     * O(wordLength * 26) different strings when we change a character at one position. But we have to
     * do that for every position in the word, so that becomes O(wordLength * wordLength * 26). We might
     * end up doing this for every word in the dictionary so that becomes
     * so thats O(wordLength * wordLength * 26 * number of words in the dictionary). In this case
     * if the average word length in the dictionary is high, this solution will be slow.
     *
     * SPACE COMPLEXITY: O(N) where N is the number of words in the dictionary, considering we
     * create an additional hash map and hash set.
     *
     * @param start initial word
     * @param stop target word
     * @param words dictionary of words for transformation
     * @return list of words for transformation
     */
    public String[] wordLadder(String start, String stop, String[] words) {
        if (words.length == 0) {
            if (isNeighbor(start, stop)) {
                return new String[]{start, stop};
            }
            return new String[] {"-1"};
        }

        if (start.equals(stop)) {
            for (String word : words) {
                if (isNeighbor(word, start)) {
                    return new String[]{start, word, stop};
                }
            }
            return new String[] {"-1"};
        }

        ArrayList<String> totalWords = new ArrayList<>(words.length + 2);
        totalWords.add(start);
        totalWords.addAll(Arrays.asList(words));
        totalWords.add(stop);

        List<Vertex<String>> graph = buildWordLadderGraph(totalWords);

        Map<Vertex<String>, Vertex<String>> pathMap = new HashMap<>();
        Vertex<String> stopVertex = graph.get(graph.size() - 1);

        Queue<Vertex<String>> q = new LinkedList<>();
        q.add(graph.get(0));
        while (!q.isEmpty()) {
            Vertex<String> current = q.poll();
            current.setVisited(true);
            for (Vertex neighbor: current.getNeigbhors()) {
                if (!neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    q.add(neighbor);
                    pathMap.put(neighbor, current);
                }
            }
        }

        List<String> path = buildPath(pathMap, stopVertex);
        if (path.size() == 1) {
            return new String[]{"-1"};
        }
        return path.toArray(new String[0]);
    }

    private List<Vertex<String>> buildWordLadderGraph(List<String> allWords) {
        List<Vertex<String>> graph = new ArrayList<>();
        for (String word : allWords) {
            Vertex<String> vertex = new Vertex<>(word);
            graph.add(vertex);
        }

        for (Vertex<String> vertex : graph) {
            List<Vertex<String>> neighbors = new ArrayList<>();
            vertex.setNeigbhors(neighbors);
            for (Vertex<String> otherVertex : graph) {
                if (vertex.getLabel().equals(otherVertex.getLabel())) {
                    continue;
                } else {
                    if (isNeighbor(vertex.getLabel(), otherVertex.getLabel())) {
                        vertex.getNeigbhors().add(otherVertex);
                    }
                }
            }
        }
        return graph;
    }

    private List<String> buildPath(Map<Vertex<String>, Vertex<String>> pathMap, Vertex<String> stopVertex) {
        List<String> path = new LinkedList<>();
        path.add(stopVertex.getLabel());
        Vertex<String> current = pathMap.get(stopVertex);
        while (current != null) {
            path.add(0, current.getLabel());
            current = pathMap.get(current);
        }

        return path;
    }

    /**
     * 2) (in wordLadderEff) If the number of words in the dictionary is large, then we need another way to determine the edges.
     * The other option we can use, given that we know that words that are neighbors only differ by one character
     * is, for every word in the dictionary (including the start and stop) generate all possible words, that differ
     * by one character. For this newly generated, then check to see if its in the dictionary, if it is, then we
     * know we have a neighbor, so then we can simply save that word and the original (untransformed word) in a
     * map, which we can use to generate a path later.
     *
     * Every word that we find, that is a neighbor, we will add into the queue, and continue to run BFS.
     *
     * RUNTIME COMPLEXITY: for every word in the dictionary, we generate each possible string with at most
     * one character changed. That one character changed at each position in the String. So there are
     * O(wordLength * 26) different strings when we change a character at one position. But we have to
     * do that for every position in the word, so that becomes O(wordLength * wordLength * 26). We might
     * end up doing this for every word in the dictionary so that becomes
     * so thats O(wordLength * wordLength * 26 * number of words in the dictionary). In this case
     * if the average word length in the dictionary is high, this solution will be slow.
     *
     * SPACE COMPLEXITY: O(N) where N is the number of words in the dictionary, considering we
     * create an additional hash map and hash set.
     *
     * @param start start word
     * @param stop stop word
     * @param words dictionary of words
     * @return
     */
    public String[] wordLadderEff(String start, String stop, String[] words) {
        if (words.length == 0) {
            if (isNeighbor(start, stop)) {
                return new String[]{start, stop};
            }
            return new String[] {"-1"};
        }

        if (start.equals(stop)) {
            for (String word : words) {
                if (isNeighbor(word, start)) {
                    return new String[]{start, word, stop};
                }
            }
            return new String[] {"-1"};
        }

        Set<String> totalWords = new HashSet<>(words.length);
        totalWords.add(start);
        totalWords.addAll(Arrays.asList(words));
        totalWords.add(stop);

        Queue<String> queue = new LinkedList<>();
        Set<String> seen = new HashSet<>(words.length);
        Map<String, String> pathMap = new HashMap<>(words.length);

        queue.add(start);
        while (!queue.isEmpty()) {
            String word = queue.poll();
            buildWordLadderGraphEff(seen, word, totalWords, queue, pathMap);
        }

        List<String> path = buildPathEff(pathMap, start, stop);
        if (path.size() == 1) {
            return new String[]{"-1"};
        }
        return path.toArray(new String[0]);
    }

    private void buildWordLadderGraphEff(Set<String> seen, String word, Set<String> allWords, Queue<String> queue, Map<String, String> pathMap) {
        StringBuffer sb = new StringBuffer(word);
        for (int i = 0; i < word.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch == word.charAt(i)) {
                    continue;
                }

                char original = word.charAt(i);
                sb.setCharAt(i, ch);
                String transform = sb.toString();

                if (allWords.contains(transform)) {
                    if (!seen.contains(transform)) {
                        queue.add(transform);
                        seen.add(transform);
                        pathMap.put(transform, word);
                    }
                }
                sb.setCharAt(i, original);
            }
        }
    }

    private List<String> buildPathEff(Map<String, String> pathMap, String start, String stop) {
        List<String> path = new LinkedList<>();
        path.add(stop);
        String current = pathMap.get(stop);
        while (current != null) {
            path.add(0, current);
            if (current.equals(start)) {
                break;
            }
            current = pathMap.get(current);
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
                        distance[neighborRow][neighborCol] = distance[parentCell.row][parentCell.col] + 1;
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

    /**
     * Alien Dictionary
     *
     * This problem is very interesting. Understanding the problem is the most difficult part.
     *
     * Problem statement:
     *
     * Given a sorted dictionary in an alien language, you have to find the order of characters in
     * that language. The input is an array of strings in sorted order which denotes the dictionary
     * of the alien language.
     *
     * We have to return a string, that is ordered, denoting the order of characters in the alien
     * language. The length of the output string will be the number of different characters in the
     * input dictionary.
     *
     * This problem is very interesting because in order to solve it, in order to apply any graphing
     * algorithms whatsoever, we must first understand the nature of a dictionary. Understanding the
     * nature of a dictionary initially give me problems.
     *
     * There are 2 things we must first understand about the nature of a dictionary which relate
     * to how we determine relationships between characters.
     *
     * In a dictionary between two adjacent words, one of the following must be true.
     *
     * 1) there is at least one letter different between the two words, "abcd", "abde"
     * 2) One word is shorter than the other
     *
     * We know from the property of a dictionary that the index, of the very first mismatching characters
     * denotes an ordering of characters. Example with "abcd" and "abde", we know that c must come before d.
     *
     * If we have two words where all of the characters are the same, but one word is longer, like "abc" and
     * "abcd", then there is nothing we can really discern. However for words like "c" and "aaaa", we would
     * know that c comes before a...but that is all we would know.
     *
     * The last thing to remember, and this is what confused me, is the following, that the characters after
     * the first mismatch don't convey anything anything about the relationship between those characters after
     * the first mismatch. How could that be? That didn't make sense to me, until I considered how one would order
     * Strings of numbers. Example below
     *
     * "119"
     * "120"
     *
     * If we look at the first mismatch, and lets just assume this is the ordering we are given (which happens
     * to be the human english language :P) we would see that "1" come before "2". Ok cool, makes sense right.
     * But now lets look at the next characters "9" and "0". Now because we know english, we know that 9 does not
     * come before 0. Hence, it would not make any sense to put any value into the fact that its a mismatch with "0".
     * Because its not our first mismatch, we can therefore ignore.
     *
     * This is actually intuitive because, because the characters of the alphabet are just like nummerical characters,
     * they just happend to be base 26 as opposed to base 10. So we can see with this example that with "119"
     * and "120", the only thing we could rightfully determine is that 1 comes before 2. For the characters after
     * the first mismatch, we can discern nothing (if its an alien lauguage) about the ordering of the entire alphabet.
     *
     * Its only because we know english, that the 119 and 120, tells us that we certainly can't say 9 comes before 0.
     *
     * As soon as the problem asked for an ordering of characters, it should have screamed to us
     *
     * TOPLOGICAL SORT.
     *
     * What is a topological sort. A topological sort is an ordering of the verticies in a directed graph such that
     * vertex A appears before vertex B for all directed edges A->B. Another way to think about it is that you're
     * given a graph of dependencies and we want to order the vertices such that no dependencies are broken when
     * going from left to right.
     *
     * If we can make a directed acyclic graph (DAG) then we can run a basic topological sort to get the ordering.
     * Remember that topological sort only works on a DAG because to have an ordering means to have directed edges
     * and we can't have a cycle because we wouldn't know where the order started and ended.
     *
     * This makes the implementation of building the graph much simpler than my original understanding.
     *
     * To make the graph, we must first figure out all of the vertices, which in this case corresponds to all of the
     * unique characters in the dictionary.
     *
     * We then build the graph using the understanding of the dictionary that we described above
     *
     * Then we run topological sort on this DAG using DFS. Boom!
     *
     * RUNTIME: In order to find all of the unique characters we we have to check every word,
     * and then add each character to a set to check for collisions. This is
     * O(numberOfWords * numberOfCharactersInLargestWord) or O(total number of characters
     *
     * To build the graph we again do the same thing, where for each word we compare it with the
     * next and move through each character so that's again O(numberOfWords * numberOfCharactersInLargestWord)
     * which is the same as O(total number of characters)
     *
     * Running topological sort is typically O(V + E). Where every vertex is the unique character,
     * and the edges are the number of different words so O(numberOfUnique characters + Number of different words)
     *
     * O(total number of characters) dominates.
     *
     * SPACE COMPLEXITY: We use an array list for the vertices so that O(V) where V is the number
     * of unique characters. We also use a Set for the number of edges for a given vertex,
     * which is O(the number of words), so thats O(numberOfUnique characters + the number of Words).
     *
     * We also have the total number of characters of the input, which dominates, so in total
     * its O(total number of characters)
     *
     * @param words dictionary
     * @return String representing order
     */
    public String alienDictionary(String[] words) {

        Set<Character> uniqueCharacters = new HashSet<>();
        Map<Character, Vertex<Character>> vertexMap = new HashMap<>();
        for (String word: words) {
            int length = word.length();
            for (int i = 0; i < length; i++) {
                if(!uniqueCharacters.contains(word.charAt(i))) {
                    uniqueCharacters.add(word.charAt(i));
                }
            }
        }

        List<Vertex<Character>> vertices = new ArrayList<>(uniqueCharacters.size());
        for (Character c: uniqueCharacters) {
            Vertex v = new Vertex(c.toString());
            vertices.add(v);
            vertexMap.put(c, v);
        }

        buildAlienDictionaryGraph(words, vertexMap);

        Stack<Vertex<Character>> ordering = new Stack<>();
        for (Vertex<Character> vertex: vertices) {
            if (vertex.color == Color.WHITE) {
                topologicalSort(vertex, ordering);
            }
        }

        StringBuffer sb = new StringBuffer();
        while (!ordering.isEmpty()) {
            sb.append(ordering.pop().label);
        }
        return sb.toString();
    }

    public void topologicalSort(Vertex<Character> vertex, Stack<Vertex<Character>> ordering) {
        vertex.color = Color.GREY;
        List<Vertex<Character>> neighbors = new ArrayList<>(vertex.edges.keySet());
        for (Vertex<Character> nextVertex: neighbors) {
            if (nextVertex.color == Color.WHITE) {
                topologicalSort(nextVertex, ordering);
            }
        }
        vertex.color = Color.BLACK;
        ordering.push(vertex);
    }

    public void buildAlienDictionaryGraph(String[] input, Map<Character, Vertex<Character>> vertexMap) {
        for (int i = 0; i < input.length - 1; i++) {
            String word = input[i];
            int wordLength = word.length();
            String nextWord = input[i + 1];
            int nextWordLength = nextWord.length();
            for (int j = 0; j < wordLength; j++) {
                if (j < nextWordLength) {
                    Character one = word.charAt(j);
                    Character two = nextWord.charAt(j);
                    Vertex vertex = vertexMap.get(word.charAt(j));
                    if (word.charAt(j) != nextWord.charAt(j)) {
                        Vertex neighbor = vertexMap.get(nextWord.charAt(j));
                        if (!neighbor.edges.containsKey(vertex)) {
                            vertex.addEdges(neighbor, 0, false);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * zombieCluster
     *
     * This problem is just an iteration on finding the number of connected components. One of the
     * wrinkles to this problem is the fact that we are given our graph in the form of an ADJ matrix
     * as opposed to an adjacency list, so we just have to run DFS on a 2-D matrix, as opposed to
     * a list of vertices with lists of neighbors.
     *
     * I had a little bit of difficulty because I first messed up writing the default version of
     * DFS. I have to remember, that the out for loop, is the loop that runs over the vertices
     * in the graph.
     *
     * The method call that should be inside of that for loop is the recursive dfs implementation
     * where we go to all of the neighbors of every vertex connected with that vertex in the outer
     * for loop.
     *
     * All of the vertices added in our component list, will represent one connected component. We
     * then move on to the remaining unvisited vertices in our graph.
     *
     * In an ADJ matrix, that just means the outer for loop runs over the rows, while the inner
     * dfs for loop runs across the columns. Each time it finds a connecting vertex, it take that
     * column index, and then looks up that row in the dfs implementation.
     *
     * We must also remember, that in the outer for loop, we should first check that the vertex
     * has not been visited. A vertex may have been visited by a prior recursive call. If it hasn't been
     * visited then we call our dfs implementation, and inside that call immediately mark it as visited.
     *
     * As we move over the neighbors in the dfs call, we always check that it hasn't been visited before
     * we actually recurse.
     *
     * RUNTIME COMPLEXITY: Its standard for connected components. We have O(V) where V is the number
     * of rows for the outer for loop. The inner for loop, the dfs routines is again, the sum of the
     * degree of each vertex for all vertexes in the graph, which is O(E) or the number of characters
     * in each string. Because we only visit a vertex once, the total running time is O(V + E)
     * or O(numberOfZombies + NumberOfZombieConnectionsForMostConnectedZombie);
     *
     * SPACE COMPLEXITY: We are using a visited array that is O(NumberOfZombies), and we are also using
     * the stack given the recursive call, so thats at most O(NumberOfZombies).
     *
     * @param zombies String[] of zombie connections
     * @return number of connected components.
     */
    public int zombieCluster(String[] zombies) {
        boolean visited[] = new boolean[zombies.length];
        int connectedComponentCount = 0;
        for (int i = 0; i < zombies.length; i++) {
            List<Integer> component = new ArrayList<>(zombies.length);
            if (visited[i] == false) {
                zombieClusterHelper(zombies, i, visited, component);
                if (!component.isEmpty()) {
                    connectedComponentCount++;
                }
            }
        }
        return connectedComponentCount;
    }

    private void zombieClusterHelper(String[] zombies, int row, boolean[] visited, List<Integer> component) {
        visited[row] = true;
        component.add(row);
        String neighbors = zombies[row];
        int length = neighbors.length();
        for (int j = 0; j < length; j++) {
            if (zombies[row].charAt(j) == '1') {
                if (visited[j] == false) {
                    zombieClusterHelper(zombies, j, visited, component);
                }
            }
        }
    }

    /**
     * Reverse edges of a strongly connected graph.
     *
     * This problem gave me quite a bit of problems, even though the solution is quite simple.
     * Its literally nothing more than running DFS, and when the recursion starts to bottom out,
     * as we build back up the stack, we just want to use the newVertex that was instantiated and inserted
     * into the map, and add it to the list of the neighbor within the for loop. This effectively, adds the parent
     * vertex to the list of neighbors for the given neighbor.
     *
     * buildGraph(Node)
     *      create new node 1, insert into map with key being the label
     *      1 - > {1: []} // for every neighbor of 1, recurse
     *        2 -> {2: []}
     *           3 -> {3: []}
     *              4 -> {4: []}
     *                  when we check to see if the neighbor of 4 is in our map, we will see that it is. so grab, that node
     *                     get(neibhor of 4) which is 1
     *                     now, for the entry 1, put 4 as its neighbor, so effectively we get
     *                     1 -> {1: [4]}
     *                     As we recurse back up the stack, we will effectively be updating the neighbors list of these new nodes
     *                     for every neighbor
     *
     * This solution is simple while at the same time being clever.
     *
     * Runtime Complexity: Standard DFS, albeit a little more efficient because the the graph is strongly connected. O(V + E)
     * Space Complexity: O(# of nodes) for the new nodes in the map.
     * @param vertex
     * @return
     */
    public Vertex<Integer> build_other_graph(Vertex<Integer> vertex) {
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        build_other_graph_helper(vertex, vertexMap);
        return vertexMap.get(vertex.label);
    }

    private void build_other_graph_helper(Vertex<Integer> vertex, Map<Integer, Vertex> vertexMap) {
        Vertex<Integer> newVertex = new Vertex<>(vertex.getLabel());
        vertexMap.put(vertex.label, newVertex);
        List<Vertex<Integer>> neighbors = vertex.getNeigbhors();
        for (Vertex<Integer> neighbor : neighbors) {
            if (!vertexMap.containsKey(neighbor.label)) {
                build_other_graph_helper(neighbor, vertexMap);
            }
            Vertex<Integer> reversedVertex = vertexMap.get(neighbor.label);
            List<Vertex<Integer>> list = reversedVertex.getNeigbhors();
            list.add(newVertex);
        }
    }
}
