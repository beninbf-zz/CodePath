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

class MyMap {

    private Map<String, Integer> innerMap = null;
    private List<String> keys = null;
    private Map<String, Integer> locationMap = null;

    public MyMap() {
        this.innerMap = new HashMap<>();
        this.locationMap = new HashMap<>();
        this.keys = new ArrayList<>();
    }

    public MyMap(int capacity) {
        this.innerMap = new HashMap<>(capacity);
        this.locationMap = new HashMap<>(capacity);
        this.keys = new ArrayList<>(capacity);
    }

    public Integer get(String key) {
        return innerMap.get(key);
    }

    public void put(String key, Integer value) {
        this.innerMap.put(key, value);
        this.keys.add(key);
        this.locationMap.put(key, keys.size() - 1);
    }

    public void delete(String key) {
        this.innerMap.remove(key);
        int index = this.locationMap.get(key);
        swap(index, keys.size() - 1);
        this.keys.remove(keys.size() - 1);
        this.locationMap.remove(key);
    }

    public String getRandom() {
        Random rand = new Random();
        int index = rand.nextInt(keys.size());
        return keys.get(index);
    }

    private void swap(int index1, int index2) {
        String item1 = this.keys.get(index1);
        String item2 = this.keys.get(index2);
        this.keys.set(index1, item2);
        this.locationMap.put(item1, index2);
        this.keys.set(index2, item1);
        this.locationMap.put(item2, index1);
    }
}
