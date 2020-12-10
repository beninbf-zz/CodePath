package main.java.com.codepath.techscreens.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * requirement: maintain aggregate counts of a high-velocity streams of events
 * run these queries for different granularities; specifically,
 * we should be able to get rollups at the minute, hour, or day level
 *
 * example: i want num logins per day for the last 3 days (20190529-20190531)
 * 20190529: ...
 * 20190530: ...
 * 20190531: ...
 */

public class TwitterEventCounter implements CountingService {

    private Map<String, Map<Integer, Long>> minuteMap = new HashMap<>();

    private Map<String, Map<Integer, Long>> hourMap = new HashMap<>();

    private Map<String, Map<Integer, Long>> dayMap = new HashMap<>();


    public void recordEvent(String eventName, long timeInMillis) {
        recordTime(eventName, timeInMillis, Granularity.MINUTE.getTime(), minuteMap);
        recordTime(eventName, timeInMillis, Granularity.HOUR.getTime(), hourMap);
        recordTime(eventName, timeInMillis, Granularity.DAY.getTime(), dayMap);
    }

    public List<Long> getEventCounts(Granularity granularity, String eventName, long startTime, long endTime) {
        switch (granularity) {
            case MINUTE:
                return getCounts(eventName, startTime, endTime, minuteMap, Granularity.MINUTE.getTime());
            case HOUR:
                return getCounts(eventName, startTime, endTime, hourMap, Granularity.HOUR.getTime());
            case DAY:
                return getCounts(eventName, startTime, endTime, dayMap, Granularity.DAY.getTime());
            default :
                return null;
        }
    }

    private List<Long> getCounts(String eventName, long startTime, long endTime, Map<String,
        Map<Integer, Long>> map, long unitTime) {
        List<Long> list = new ArrayList<>();
        if (map.containsKey(eventName)) {
            Map<Integer, Long> countMap = map.get(eventName);
            int startUnit = new Long(startTime/ unitTime).intValue();
            int endUnit = new Long(endTime/ unitTime).intValue();
            for (int i = startUnit; startUnit < endUnit; i++) {
                if (countMap.containsKey(startUnit)) {
                    Long ct = countMap.get(startUnit);
                    list.add(ct);
                } else {
                    list.add(0l);
                }
            }
        }
        return list;
    }

    private void recordTime(String eventName, long timeInMillis, long timeUnit, Map<String, Map<Integer, Long>> map) {
        Long unitTimeLong = new Long(timeInMillis / timeUnit);
        int unitTime = unitTimeLong.intValue();
        if (map.containsKey(eventName)) {
            Map<Integer, Long> countMap = map.get(eventName);
            if (countMap.containsKey(unitTime)) {
                Long count = countMap.get(unitTime);
                long newCount = count.longValue() + 1;
                countMap.put(unitTime, newCount);
            } else {
                countMap.put(unitTime, 1l);
            }
        } else {
            Map<Integer, Long> countMap = new HashMap<>();
            countMap.put(unitTime, 1l);
            map.put(eventName, countMap);
        }
    }
}
