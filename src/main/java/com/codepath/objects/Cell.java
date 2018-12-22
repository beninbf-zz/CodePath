package main.java.com.codepath.objects;

public class Cell {
    public int row;
    public int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String toString() {
        return String.format("{%s, %s}", row, col);
    }
}