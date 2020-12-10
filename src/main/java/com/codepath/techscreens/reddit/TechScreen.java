package main.java.com.codepath.techscreens.reddit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TechScreen {

    /**
     * Given a series of mappings like the following:
     *
     * a -> 5
     * b -> 6
     * c => {
     *     f -> 7
     *     g => {
     *         m -> 17
     *         n -> 9
     *     }
     * }
     *
     * Print the map flattened out.
     * Expected output: {a=5, b=6, c.f=7, c.g.m=17, c.g.n=9}
     * @param map - Map[String, Object]
     * @return
     */
    public Map<String, Integer> getFlattenedMap(Map<String, Object> map) {
        return getFlattenedMapHelper(map,"");
    }

    private Map<String, Integer > getFlattenedMapHelper(Map<String, Object> map, String parent) {
        Map<String, Integer> result = new HashMap<>();
        Map<String, Integer> mapToMerge = null;

        Set<String> keys = map.keySet();
        for (String key: keys) {
            Object value = map.get(key);
            if (value instanceof Map) {
                Map<String, Object> recurseMap = (Map<String, Object>) value;
                mapToMerge = getFlattenedMapHelper(recurseMap, key);
            } else if (value instanceof Integer) {
                Integer v = (Integer) value;
                result.put(key, v);
            }
        }
        for (String key : keys) {
            Object v = result.get(key);
            if (v instanceof Integer) {
                Integer value = (Integer) v;
                String newKey = getKey(parent, key);
                result.remove(key);
                result.put(newKey, value);
            }
        }
        if (mapToMerge != null && !mapToMerge.isEmpty()) {
            return mergeMaps(result, mapToMerge, parent);
        }
        return result;
    }

    public Map<String, Integer> mergeMaps(Map<String, Integer> result,  Map<String, Integer> mapToMerge, String parent) {
        Map<String, Integer> keyChangedMap = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry: mapToMerge.entrySet()) {
            keyChangedMap.put(getKey(parent, entry.getKey()), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry: result.entrySet()) {
            if (result.get(entry.getKey()) != null) {
                keyChangedMap.put(entry.getKey(), entry.getValue());
            }
        }
        return keyChangedMap;
    }

    private String getKey(String parent, String child) {
        if (parent.isEmpty()) {
            return child;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(parent);
        sb.append(".");
        sb.append(child);
        return sb.toString();
    }
}
