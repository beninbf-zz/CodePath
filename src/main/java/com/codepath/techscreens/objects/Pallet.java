package main.java.com.codepath.techscreens.objects;

/**
 * Object for Samsara Tech Screen
 */
public class Pallet {

    public final int weight;
    public final int id;

    public Pallet(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public String toString() {
        return String.format("pallet{id=%s weight=%s}", this.id, this.weight);
    }
}
