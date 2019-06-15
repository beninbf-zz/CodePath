package main.java.com.codepath.techscreens.objects;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tech screen for uber.
 *
 * Create a fare calculator
 * Example below:
 *
 * A driver starts a home
 *
 *        1 mile                        2 miles                        1 mile
 * start------------> pick up p1 -------------------> pick up p2 ---------------- > drop p1
 *
 * When calculating the fare calculate it including the driver, so the driver counts a rider.
 *
 * When dropping of pick up 1, and assuming a cost per mile of $10 USD the cost should be
 *
 * (10 / 2) * 2 (2 mile driven with 2 people)  + 10 / 3 * 1 (3 miles driven with one person)
 *
 * The fare for a given pick up is determined by the number of riders per mile and the cost
 * is distributed.
 *
 * To the solve this problem, we must see that we are actually summing up segments. How we store
 * the rider information is what will help us calculate the fare when we drop off a rider.
 *
 * Note the that actual rider fare is only dependent upon the segments, not necessarily the distance
 * of when the driver was pick up.
 *
 * When I started this problem, I broke it down to a simpler case to solve for. Solving for just one
 * other occupant after 1 mile. The ended up using a map that stored a pick up object, where the object
 * stored the id of the rider and distance they were pick up at (offset from where the driver started,
 * which is 0).
 *
 * The approach I took was not extendable to more complicated use cases, so I it had to be re-thought,
 * and that's when storing segments would have been the best approach. In truth I needed both my original
 * approach and the segment approach.
 *
 * When calculating a riders fare, we need to calculate the riders contribution to the cost for reach
 * segment. BUT ONLY the segments for which there are in the cab. As such keeping a map, that stores
 * the start distance of when a rider was picked up, would help us make sure we don't over charge
 * a rider.
 *
 */
public class FareCalculator {
    class Segment {
        int mile;
        int numberOfOccupants;

        Segment(int mile, int numberOfOccupants) {
            this.mile = mile;
            this.numberOfOccupants = numberOfOccupants;
        }

        @Override
        public String toString() {
            return String.format("Segment{mile=%s , numberOccupants=%s}", mile, numberOfOccupants);
        }
    }

    class Rider {
        int riderId;
        int start;

        Rider(int riderId, int start) {
            this.riderId = riderId;
            this.start = start;
        }

        @Override
        public String toString() {
            return String.format("Rider{riderId=%s , start=%s}", this.riderId, this.start);
        }
    }

    private List<Segment> segments;

    private Map<Integer, Rider> riderMap;

    private Double costPerMile;

    private int numberOfOccupants;

    public FareCalculator(Double costPerMile) {
        this.costPerMile = costPerMile;
        this.segments = new ArrayList<>();
        this.riderMap = new HashMap<>();
        Rider rider = new Rider(0, 0);
        riderMap.put(rider.riderId, rider);
    }

    public void addRider(int riderId, int mile) {
        addSegment(mile);
        Rider rider = new Rider(riderId, mile);
        riderMap.put(rider.riderId, rider);
    }

    private void addSegment(int mile) {
        Segment segment = new Segment(mile, this.riderMap.size());
        segments.add(segment);
    }

    public Double dropFare(int riderId, int mile) {
        if (!riderMap.containsKey(riderId)) {
            return null;
        }
        Rider rider = riderMap.get(riderId);
        double result = 0.0d;
        int segmentLength = segments.size();
        int endMile = mile;
        int numberOfRiders = riderMap.size();
        for(int i = segmentLength - 1; i >= 0; i--) {
            Segment segment = segments.get(i);
            if (rider.start <= segment.mile) {
                result += (costPerMile / numberOfRiders) * (Math.abs(endMile - segment.mile));
                endMile = segment.mile;
                numberOfRiders = segment.numberOfOccupants;
            }
        }
        addSegment(mile);
        riderMap.remove(riderId);
        return result;
    }
}
