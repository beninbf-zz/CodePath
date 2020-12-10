package main.java.com.codepath.techscreens.lyft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TechScreen {
    /**
     * Lyft tech screen making use of a custom object.
     * The intersection iterator will return the next common element
     * from the two iterators.
     *
     * @param it1 Iterator
     * @param it2 Iterator
     * @return List of common elements
     */
    public List<Integer> commonElements(Iterator<Integer> it1, Iterator<Integer> it2) {
        ArrayList<Integer> output = new ArrayList<>();
        IntersectionIterator interItr = new IntersectionIterator(it1, it2);
        Integer common = interItr.next();
        while (common != null) {
            output.add(common);
            common = interItr.next();
        }
        return output;
    }
}
