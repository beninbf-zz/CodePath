package main.java.com.codepath.objects;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeKeyValueMap {

    class Entry {
        public long timestamp;
        public String value;

        Entry(long timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private Map<String, List<Entry>> innerMap;


    public TimeKeyValueMap() {
        this.innerMap = new HashMap<>();
    }

    public void put(String key, String value) {
        Date date = new Date();
        Entry entry = new Entry(date.getTime(), value);

        if (this.innerMap.containsKey(key)) {
            List<Entry> list = this.innerMap.get(key);
            list.add(entry);
        } else {
            List<Entry> list = new ArrayList<>();
            list.add(entry);
            this.innerMap.put(key, list);
        }
    }

    public String get(String key, Long timestamp) {
        if (timestamp == null) {
            if (this.innerMap.containsKey(key)) {
                List<Entry> entryList = this.innerMap.get(key);
                Entry entry = entryList.get(entryList.size() - 1);
                return entry.value;
            } else {
                return null;
            }
        } else {
            if (this.innerMap.containsKey(key)) {
                List<Entry> entryList = this.innerMap.get(key);
                if (timestamp.longValue() > entryList.get(entryList.size() - 1).timestamp) {
                    Entry entry = entryList.get(entryList.size() - 1);
                    return entry.value;
                } else if (timestamp.longValue() < entryList.get(0).timestamp)  {
                    Entry entry = entryList.get(0);
                    return entry.value;
                } else {
                    Entry entry = linearGet(entryList, timestamp);
                    return entry.value;
                }

            }
        }
        return null;
    }

    private Entry linearGet(List<Entry> entries, Long timestamp) {
        int length = entries.size();
        int index = 0;
        for (int i = 0; i < length; i++) {
            Entry entry = entries.get(i);
            if (entry.timestamp < timestamp.longValue()) {
                continue;
            } else if (entry.timestamp > timestamp.longValue()) {
                index = i - 1;
                break;
            }  else {
                index = i;
                break;
            }
        }
        return entries.get(index);
    }
}
