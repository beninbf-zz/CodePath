package main.java.com.codepath.techscreens.objects;

import java.util.List;

public interface CountingService {
    public void recordEvent(String eventName, long timeInMillis);
    public List<Long> getEventCounts(Granularity granularity, String eventName, long startTime, long endTime);
}
