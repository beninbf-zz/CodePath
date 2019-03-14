package main.java.com.codepath.objects;

public class Cell {
    public int row;
    public int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Cell cell = (Cell) obj;
        return this.row == cell.row && this.col == cell.col;
    }

    @Override
    public int hashCode() {
        StringBuffer sb = new StringBuffer();
        sb.append(row);
        sb.append(col);
        return sb.toString().hashCode();
    }

    public String toString() {
        return String.format("{%s, %s}", row, col);
    }
}