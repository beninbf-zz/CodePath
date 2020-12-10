package main.java.com.codepath.techscreens.twitter;

import main.java.com.codepath.techscreens.twitter.Granularity;

import java.util.List;

public interface CountingService {
    public void recordEvent(String eventName, long timeInMillis);
    public List<Long> getEventCounts(Granularity granularity, String eventName, long startTime, long endTime);
}
