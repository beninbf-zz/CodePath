package main.java.com.codepath.objects;

public class ShortestPathCell {
    public int row;
    public int col;
    public int keyRing;

    public ShortestPathCell(int row, int col, int keyRing) {
        this.row = row;
        this.col = col;
        this.keyRing = keyRing;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        ShortestPathCell cell = (ShortestPathCell) obj;
        return this.row == cell.row && this.col == cell.col && this.keyRing == cell.keyRing;
    }

    @Override
    public int hashCode() {
        StringBuffer sb = new StringBuffer();
        sb.append(row);
        sb.append(col);
        sb.append(keyRing);
        return sb.toString().hashCode();
    }

    public String toString() {
        return String.format("{%s, %s, %s}", row, col, keyRing);
    }
}

