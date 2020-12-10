package main.java.com.codepath.techscreens.instacart;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tech Screen from InstaCart
 *
 * This tech screen involved me making my own map. Test cases were added that were more challenging each
 * time in such a fashion such that my code would have to be amended to support the added use cases.
 *
 * In this case, the map had to support a timestamp functionality where keys, and values entered would
 * return the timestamp of entry. When looking the key up, I had to provide the ability to
 * look up values by the key and timestamp, where in supplying the timestamp would return the value
 * of the key, at that given time OR the value of the key, closest to that timestamp.
 *
 * This essentially meant changing the inner map, from String => String to String => List<Entry>
 * where an entry as a custom object I made that had three vields, a key, a value and a timestamp.
 *
 * When looking for the value of the key at a given timestamp, one had to look up the list of entrys
 * then search for either the matching timestamp OR the closest. I did this with simple for loop,
 * but the optimal way, given the list of entries is by default sorted by timestamp was just to
 * perform a binary search, which is O(log n) vs O(n) of the for loop.
 *
 *
 */
public class InstaCartMap {

    class Entry {
        String key;
        String value;
        long timestamp;

        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
            Date date = new Date();
            this.timestamp = date.getTime();
        }
    }
    private Map<String, List<Entry>> innerMap;

    public InstaCartMap() {
        this.innerMap = new HashMap<>();
    }

    public InstaCartMap(int capacity) {
        this.innerMap = new HashMap<>(capacity);
    }

    public String get(String key) {
        List<Entry> entries = this.innerMap.get(key);
        Entry entry = entries.get(entries.size() - 1);
        return entry.value;
    }

    public String get(String key, long timestamp) {
        if (this.innerMap.containsKey(key)) {
            List<Entry> entries = this.innerMap.get(key);
            for (Entry entry: entries) {
                if (timestamp == entry.timestamp) {
                    return entry.value;
                }
            }

            if (timestamp > entries.get(entries.size() - 1).timestamp) {
                return entries.get(entries.size()).value;
            }

            for (int i = 0; i < entries.size() - 1; i++) {
                Entry current = entries.get(i);
                Entry next = entries.get(i + 1);
                if ((timestamp > current.timestamp) && (timestamp < next.timestamp)) {
                    long diff = Math.abs(timestamp - current.timestamp);
                    long diffNext =  Math.abs(timestamp - next.timestamp);

                    if (diffNext < diff) {
                        return next.value;
                    } else {
                        return current.value;
                    }
                }
            }
        }
        return null;
    }

    public long set(String key, String value) {
        if (!this.innerMap.containsKey(key)) {
            List<Entry> entries = new ArrayList<>();
            Entry entry = new Entry(key, value);
            entries.add(entry);
            this.innerMap.put(key, entries);
            return entry.timestamp;
        } else {
            List<Entry> entries = this.innerMap.get(key);
            Entry entry = new Entry(key, value);
            entries.add(entry);
            return entry.timestamp;
        }
    }
}


