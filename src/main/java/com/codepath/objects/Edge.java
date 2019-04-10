package main.java.com.codepath.objects;

public class Edge {
    public Vertex vertex1;
    public Vertex vertex2;
    public int edgeWeight = 0;

    public Edge(Vertex vertex1, Vertex vertex2, int edgeWeight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.edgeWeight = edgeWeight;
    }

    public String toString() {
        return String.format("%s-%s:%s", vertex1.getLabel(), vertex2.getLabel(), String.valueOf(edgeWeight));
    }
}
