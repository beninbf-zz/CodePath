package main.java.com.codepath.techscreens.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Tech screen for Lyft.
 *
 * A class was provided containing the stub of a method, that given two iterators,
 * when .next() called, would return the next element shared by two iterators.
 *
 * I found this problem to be quite tricky. The amount of code with all of the edge
 * cases and such, I struggled to understand the best approach to take in trying
 * to solve something this challenging in 40 minutes time.
 *
 * It would appear that the best way to do so, would be to start with small test
 * cases. Make sure the code passes those small test cases, and then continue
 * building from there.
 *
 * Doing it that way might provide the most efficient way building the solution.
 */
public class IntersectionIterator {

    private Iterator<Integer> it1;
    private Integer one;
    private Iterator<Integer> it2;
    private Integer two;
    private boolean lastElementsChecked;

    public IntersectionIterator(Iterator<Integer> it1, Iterator<Integer> it2) {
        this.it1 = it1;
        this.it2 = it2;
    }

    public Integer next() {

        if (iteratorsFinished()) {
            if (!lastElementsChecked) {
                lastElementsChecked = true;
                if (pointersNotNull() && one.compareTo(two) == 0) {
                    return one;
                }
            } else {
                return null;
            }
        }

        while (it1.hasNext() || it2.hasNext()) {
            // if its the first time next has been called, one is null
            if (one == null && it1.hasNext()) {
                one = it1.next();
            }
            // if its the first time next has been called, two is null
            if (two == null && it2.hasNext()) {
                two = it2.next();
            }

            if (one.compareTo(two) < 0) {
                if (it1.hasNext()) {
                    one = it1.next();
                } else {// because the iterators are sorted, if one
                    // is less than two and we are at the end, then
                    // there is nothing else left to check
                    if (one.compareTo(two) < 0) {
                        return null;
                    }
                }
            } else if (one.compareTo(two) > 0) {
                if (it2.hasNext()) {
                    two = it2.next();
                } else { // because the iterators are sorted if, two
                    // is less than one and we are at the end, then
                    // there is nothing else left to check
                    if (one.compareTo(two) > 0) {
                        return null;
                    }
                }
            } else {
                int result = one.intValue();
                // for the case of [1], [1]
                if (iteratorsFinished()) {
                    lastElementsChecked = true;
                    break;
                }

                if (it1.hasNext()) {
                    one = it1.next();
                }

                if (it2.hasNext()) {
                    two = it2.next();
                }
                return result;
            }
        }

        // for the case of two empty iterators
        if (pointersNotNull() && one.compareTo(two) == 0) {
            lastElementsChecked = true;
            return one;
        }

        return null;
    }

    private boolean pointersNotNull() {
        return one != null && two != null;
    }

    private boolean iteratorsFinished() {
        return !it1.hasNext() && !it2.hasNext();
    }

    private static List<Integer> commonElements(Iterator<Integer> it1, Iterator<Integer> it2) {
        ArrayList<Integer> output = new ArrayList<>();
        IntersectionIterator interItr = new IntersectionIterator(it1, it2);
        Integer common = interItr.next();
        while (common != null) {
            output.add(common);
            common = interItr.next();
        }
        System.out.println(output);
        return output;
    }

}