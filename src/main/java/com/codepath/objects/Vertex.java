package main.java.com.codepath.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vertex<T extends Comparable<T>> implements Comparable<Vertex<T>> {
    public List<Vertex<T>> neighbors = new ArrayList<>();
    public Map<Vertex<T>, Integer> edges = new HashMap<>();
    public T label;
    public boolean visited;
    public Integer weight = Integer.MAX_VALUE;
    public Color color;

    public Vertex(T label) {
        this.label = label;
        this.color = Color.WHITE;
    }

    public Vertex(T label, int weight) {
        this.label = label;
        this.color = Color.WHITE;
        this.weight = weight;
    }

    public void addNeighbors(List<Vertex<T>> neighbors) {
        this.neighbors.addAll(neighbors);
    }

    public void addNeighbor(Vertex<T> neighbor) {
        this.neighbors.add(neighbor);
    }

    public void addEdges(Vertex<T> neighbor, Integer weight) {
        edges.put(neighbor, weight);
        addNeighbor(neighbor);

        neighbor.edges.put(this, weight);
        neighbor.addNeighbor(this);
    }

    /**
     * Gets neighbors.
     *
     * @return the neighbors
     */
    public List<Vertex<T>> getNeigbhors() {
        return neighbors;
    }

    /**
     * Sets neigbhors.
     *
     * @param neigbhors the neigbhors
     */
    public void setNeigbhors(List<Vertex<T>> neigbhors) {
        this.neighbors = neigbhors;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public T getLabel() {
        return label;
    }

    public Color getColor() {
        return this.color;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(T label) {
        this.label = label;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String toString() {
        return String.format("{%s : %s}", label, weight);
    }

    @Override
    public int compareTo(Vertex<T> other) {
        if (this.weight != null && other.weight != null) {
            return this.weight - other.weight;
        } else {
            return this.label.compareTo(other.getLabel());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Vertex v = (Vertex) obj;
        return this.label.equals(v.label);
    }

    @Override
    public int hashCode() {
        return String.valueOf(label).hashCode();
    }

}
