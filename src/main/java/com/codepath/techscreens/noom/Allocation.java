package main.java.com.codepath.techscreens.noom;

import java.util.HashMap;
import java.util.Map;

public class Allocation implements Comparable<Allocation> {
    public AllocationType getType() {
        return type;
    }

    private final AllocationType type;
    private final Map<ResponsibilityType, Integer> responsibilityMap = new HashMap<ResponsibilityType, Integer>();
    private final int start;
    private final int end;

    // For Baseline allocation there is not start and end date.
    public Allocation(Responsibility personalCoaching, Responsibility managing, Responsibility groupCoaching) {
        this.type = AllocationType.BASELINE;
        loadResponsibilityMap(personalCoaching, managing, groupCoaching);
        this.start = 0;
        this.end = 0;
    }

    // For Temporary allocation there is a start and end time.
    public Allocation(Responsibility personalCoaching, Responsibility managing, Responsibility groupCoaching, int start, int end) throws Exception {
        this.type = AllocationType.TEMPORARY;
        loadResponsibilityMap(personalCoaching, managing, groupCoaching);
        validatedStartAndEnd(start, end);
        this.start = start;
        this.end = end;
    }

    private void loadResponsibilityMap(Responsibility personalCoaching, Responsibility managing, Responsibility groupCoaching) {
        responsibilityMap.put(personalCoaching.getType(), personalCoaching.getPercentage());
        responsibilityMap.put(managing.getType(), managing.getPercentage());
        responsibilityMap.put(groupCoaching.getType(), groupCoaching.getPercentage());
    }

    private void validatedStartAndEnd(int start, int end) throws Exception {
        if (start < 0 || end < 0) {
            throw new Exception("Start and end time must be greater non-negative integers.");
        }
        if (start > end) {
            throw new Exception("Start time can't be greater than end time.");
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getResonsibilityPercentage(ResponsibilityType type) {
        return responsibilityMap.get(type).intValue();
    }

    public boolean isInInterval(int day) {
        if (type != AllocationType.TEMPORARY) {
            return false;
        }
        return day >= start && day <= end;
    }

    @Override
    public int compareTo(Allocation allocation) {
        return this.start - allocation.start;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.type);
        builder.append(":");
        builder.append(this.responsibilityMap.toString());
        return builder.toString();
    }
}
