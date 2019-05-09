package main.java.com.codepath.techscreens.objects;

/*
 * The following was a tech screen from Bolt.
 *
 * If you need more classes, simply define them inline.
 *
 * Design a data structure that can do the following all in constant time
 * get(key) -> value, put(key, value), delete(key), getRandom() -> key
 *
 * This was a simple enough problem although I did have my difficulty. The
 * point to remember is that if we want to do all of the operations above
 * in constant time, then we will need to use extra memory.
 *
 * The difficulty was designing the delete function to operate in O(1) time
 * We do this by essentially remember the index of a key. To do that, we should
 * have a separate map to map the key to its index.
 *
 * We then then look up the index of the key in constant time in our locatio map.
 * We the index we can then find it O(1) in our backing list. But the problem then
 * was to delete it from our keys array list in constant time. For that there is a
 * trick as well as something to understand about the backing array in an array list.
 *
 * The thing to understand about the array list data structure, is that, removing
 * an element from the array, will cause of the elements to shift over one spot.
 * We want to avoid that because it will have a cost O(n). So how could we have
 * the delete operation for an array list occur in constant time? Another question
 * is what element can we remove, where we don't incur any cost of shifting
 * any elements.
 *
 * Once we have the index of the object we want to delete, we can just swap it
 * with the last element of the array (remembering to update our backing
 * locationMap, for the new location of the elements). Now deleting the last element
 * of the array will not incur any cost of shifting all of the elements over. Lastly
 * we remove that deleted element from the location map as well.
 *
 * Now all of the operations are in constant time, albeit, by using extra memory.
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MyMap {
    private final Map<String, String> innerMap;
    private final Map<String, Integer> locationMap;
    List<String> keyList;

    public MyMap() {
        this.innerMap = new HashMap<>();
        this.keyList = new ArrayList<>();
        this.locationMap = new HashMap<>();
    }

    public MyMap(int capacity) {
        this.innerMap = new HashMap<>(capacity);
        this.keyList = new ArrayList<>(capacity);
        this.locationMap = new HashMap<>(capacity);
    }

    public void set(String key, String value) {
        if (!this.innerMap.containsKey(key)) {
            this.keyList.add(key);
            this.locationMap.put(key, keyList.size() - 1);
        }
        this.innerMap.put(key, value);
    }

    public String get(String key) {
        return this.innerMap.get(key);
    }

    public void delete(String key) {
        this.innerMap.remove(key);
        Integer keyIndex = locationMap.get(key);
        swap(keyIndex, keyList.size() - 1);
        this.keyList.remove(keyList.size() - 1);
        this.locationMap.remove(key);
    }

    private void swap(int index1, int index2) {
        String temp = keyList.get(index1);
        String end = keyList.get(index2);
        this.keyList.set(index1, end);
        this.locationMap.put(end, index1);
        this.keyList.set(index2, temp);
        this.locationMap.put(temp, index2);
    }

    public String getRandom() {
        Random random = new Random();
        int rand = random.nextInt(keyList.size());
        String randomKey = keyList.get(rand);
        return this.innerMap.get(randomKey);
    }

    public String toString() {
        return this.innerMap.entrySet().toString();
    }

    public String keyListStr() {
        return this.keyList.toString();
    }

    public String locationMapStr() {
        return this.locationMap.entrySet().toString();
    }
}

