package main.java.com.codepath.objects;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
    private List<Vertex<T>> neigbhors;
    private T label;
    private boolean visited;

    public Vertex(T label) {
        this.label = label;
    }

    public Vertex(T label, ArrayList<Vertex<T>> neighbors) {
        this.neigbhors = neighbors;
        this.label = label;
    }

    /**
     * Gets neigbhors.
     *
     * @return the neigbhors
     */
    public List<Vertex<T>> getNeigbhors() {
        return neigbhors;
    }

    /**
     * Sets neigbhors.
     *
     * @param neigbhors the neigbhors
     */
    public void setNeigbhors(List<Vertex<T>> neigbhors) {
        this.neigbhors = neigbhors;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public T getLabel() {
        return label;
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
        return "{ " + label + " }";
    }


}
