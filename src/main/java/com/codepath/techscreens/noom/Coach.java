package main.java.com.codepath.techscreens.noom;

public class Coach {

    private final Allocation[] allocations;

    public Allocation[] getAllocations() {
        return allocations;
    }

    Coach(Allocation[] allocations) {
        this.allocations = allocations;
    }

    public void generateReport(long start, long end) {

    }
}
