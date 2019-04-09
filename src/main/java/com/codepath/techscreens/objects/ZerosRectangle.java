package main.java.com.codepath.techscreens.objects;

import main.java.com.codepath.objects.Cell;

public class ZerosRectangle {
    public Cell beginning;
    public int width = 0;
    public int height = 0;

    public ZerosRectangle() {}

    public void createBeginningCell(int row, int col) {
        beginning = new Cell(row, col);
        this.width += 1;
        this.height += 1;
    }

    public String toString() {
        return String.format("x: %s, y: %s, width: %s, height: %s", beginning.col, beginning.row, width, height);
    }
}
