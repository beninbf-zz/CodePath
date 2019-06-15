package main.java.com.codepath.techscreens.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object for Samsara Tech Screen
 *
 * We are going to build a system to help track the contents of a trailer (think 18 wheeler) and
 * calculate the weight of the trailer over time.
 * - Trailers can hold pallets.
 * - A pallet has a weight, and a unique pallet ID.
 * - Pallets can be loaded and unloaded from trailers.
 *
 * Create a Pallet class (or object) that represents a pallet with a weight and a unique id.
 *
 * Create a Trailer class that can hold multiple pallets.
 * Write load(pallet) and unload(id) functions that support loading and unloading pallets from the trailer.
 * Unload should remove the pallet from the trailer and return the pallet object back to the caller.
 *
 * Add a method weight() that returns the total weight of the pallets on the trailer.
 *
 *
 * Create a method weightAt(t) that returns the weight of the pallets
 * in the trailer at an arbitrary time. Modify the current class and
 * methods as necessary to be able to answer this question.
 */
public class Trailer {
    class Tuple {
        public int timestamp;
        public int weight;

        public Tuple(int timestamp, int weight) {
            this.timestamp = timestamp;
            this.weight = weight;
        }

        public String toString() {
            return String.format("tuple{timestamp=%s weight=%s}", this.timestamp, this.weight);
        }
    }

    private Map<Integer, Pallet> palletMap;

    private List<Tuple> timeWeightList;

    private int trailerWeight;

    public Trailer() {
        this.palletMap = new HashMap<>();
        this.timeWeightList = new ArrayList<>();
    }

    public void load(Pallet pallet, int timestamp) {
        if (!palletMap.containsKey(pallet.id)) {
            this.palletMap.put(pallet.id, pallet);
            this.trailerWeight += pallet.weight;
            Tuple tuple = new Tuple(timestamp, trailerWeight);
            timeWeightList.add(tuple);
        }
    }

    public Pallet unload(int id, int timestamp) {
        if (this.palletMap.containsKey(id)) {
            Pallet pallet = palletMap.get(id);
            palletMap.remove(id);
            this.trailerWeight -= pallet.weight;
            Tuple tuple = new Tuple(timestamp, this.trailerWeight);
            this.timeWeightList.add(tuple);
            return pallet;
        }
        return null;
    }

    public int getWeight() {
        int total = 0;
        if (this.palletMap.isEmpty()) {
            return total;
        }
        for (Map.Entry<Integer, Pallet> entry: this.palletMap.entrySet()) {
            Pallet pallet = entry.getValue();
            total += pallet.weight;
        }
        return total;
    }

    public int weightAt(int checkTimeStamp) {
        int length = this.timeWeightList.size();
        int index = 0;
        for (int i = 0; i < length; i++) {
            Tuple tuple = timeWeightList.get(i);
            if (tuple.timestamp == checkTimeStamp) {
                return tuple.weight;
            }

            if (tuple.timestamp > checkTimeStamp) {
                index = i;
                break;
            }
        }
        if (index > 0) {
            Tuple tuple = timeWeightList.get(index - 1);
            return tuple.weight;
        }
        return 0;
    }
}
