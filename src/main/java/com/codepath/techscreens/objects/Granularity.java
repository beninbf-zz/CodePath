package main.java.com.codepath.techscreens.objects;

public enum Granularity {
    MINUTE(60000l),
    HOUR(360000l),
    DAY(8640000);

    private long time;

    Granularity(long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }
}
