package main.java.com.codepath.techscreens.objects;

public class Range implements Comparable<Range> {
    public int start;
    public int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int compareTo(Range range) {
        if (this.start < range.start) {
            return -1;
        } else if (this.start > range.start) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        return String.format("{start: %s , end: %s}", this.start, this.end);
    }
}
