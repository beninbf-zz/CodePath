package main.java.com.codepath.techscreens.objects;

public class Cell {
    public int row;

    public int col;

    public int index;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Cell(int row, int col, int index) {
        this.row = row;
        this.col = col;
        this.index = index;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Cell other = (Cell) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return String.format("{row=%s, col=%s, index=%s}", row, col, index);
    }
}
