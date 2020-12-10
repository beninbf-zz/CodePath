package main.java.com.codepath.techscreens.darkstore;

import java.time.LocalDate;
import java.util.*;

public class TechScreen {
    /**
     * Problem: Given the following input
     *         4/01     4/02    4/03    4/04
     * MSFT |  200    |       |      | 400
     * GOOG |  500    |       |      | 700
     * APPL |  400    | 200   |      | 600
     *
     * Out put the total value of your portfolio.
     * [(2019-04-01, 600), (2019-04-02, 900), (2019-04-03, 1100), (2019-04-04, 1700)]
     *
     * Dark store tech screen
     *
     * RUNTIME COMPLEXITY:
     * For the initial bucket map, the run time O(m * n) where m is the number of assets
     * and n is the dimension of the longest position array in our portfolio.
     *
     * We then sort the keys which is O(d log d), where d is the number of unique dates
     * in our portfolio.
     *
     * As we then move through each Integer[] asset array. We use each key, which is
     * a unique date to get the array, the move through the array suming the values.
     * the run time of this is O(d * m), where m is the number of assets, and d
     * is the number of unique dates.
     *
     * The total runtime of this algorithm will be dominated by which ever is larger
     * the O(m * n) or O(d * m). If the market is very volatile, then there could
     * be many position changes, and hence n, could be quite numerous per given
     * asset.
     *
     * If we are looking for totals position totals over a great deal of time,
     * then its possible that O(d * m) could be larger, if d is the number of
     * unique dates.
     *
     * Considering market values of given assets change frequently, its more likely
     * that the O(m * n) will dominate.getTotalsFromPortfolio
     *
     * SPACE COMPLEXITY: For our bucket array we are created an Integer[] per unique date
     * where the dimension of the array is equal to the number of assets so that additional
     * memory is of O(d * m).
     *
     * We store our results in a new position array which is made up of integer totals
     * and dimension d. So that additional memory is O(d).
     *
     * The memory complexity of our bucket map, dominates, so total memory complexity is
     * O(d * m)
     *
     *
     **/
    public Position[] getTotalsFromPortfolio(Position[][] portfolio) {

        /**
         * Originally I thought of this as being nothing more than a 2-D array,
         * we where would essentially take the summation of each column. Every index
         * where there was a null value, we would simply look at that row, in the prior
         * column to get the lastvalue.
         *
         * That approach, of course, doesn't work because all of the rows are not of the same
         * length.
         *
         * Instead I took the approach of creating buckets. Where the buckets are stored in a map.
         * The entry in the map, maps a date to an Integer[] array. The Integer[] array will store
         * the value of the assets.
         *
         * The index of the Integer[] array corresponds to the particular assest. For example
         * Given our first use case of AMZN, GOOG, and MSFT, in our input, AMZN is 0, GOOG is 1,
         * MSFT is 2.
         *
         * The value of the given asset for that day will be stored in this Integer array,
         * using the index a key to quickly look up the value of a given asset. We can always be sure of
         * the number of assests by looking at the row dimension of the portfolio input.
         * One row for each asset.
         *
         * By the end of the execution of these initial 2 for loops for the first use case
         * we end up with a map like so
         *
         * 4/1 => 200      |  null    |  400
         *        AMZN=0      GOOG=1    MSFT=2
         *
         * 4/2 => null      |  500    |  200
         *       AMZN=0      GOOG=1    MSFT=2
         *
         * 4/3 => null      |  700    |  200
         *        AMZN=0      GOOG=1    MSFT=2
         *
         * 4/4 => 400      |  null    |  600
         *        AMZN=0      GOOG=1    MSFT=2
         *
         */
        Map<LocalDate, Integer[]> results = new HashMap<>();
        int numberOfAssets = portfolio.length;
        for (int i = 0; i < portfolio.length; i++) {
            Position[] positions = portfolio[i];
            for (int j = 0; j < positions.length; j++) {
                Position p = positions[j];
                if (!results.containsKey(p.getDate())) {
                    Integer[] dayTotals = new Integer[numberOfAssets];
                    dayTotals[i] = p.getValue();
                    results.put(p.getDate(), dayTotals);
                } else {
                    Integer[] dayTotals = results.get(p.getDate());
                    dayTotals[i] = p.getValue();
                }
            }

        }

        /**
         * Now that we have our buckets, with dates mapped to arrays with
         * the values of each asset on that day, we can now begin to
         * total the assets for each day.
         *
         * Given that for a day, the value of an asset may have not changed
         * we need to know the value of that asset on the last day.
         * Considering the dates are in increasing order, we will sort the keys
         * of our map.
         **/

        List<LocalDate> keys = new ArrayList<>(results.keySet());
        Position[] totalPosition = new Position[keys.size()];
        Collections.sort(keys);

        // Grap the very first Integer[] containing our asset values for the first day.
        // if the subsequent day is missing a value, we will look to the prior day Integer[]
        // for the latest asset value, using the index of the array (aka the asset symbol)
        // as a key
        int lengthOfKeySet = keys.size();
        LocalDate previousDateKey = keys.get(0);

        // So that we can get a position total for our first day, the sum first day integer array
        Integer[] array = results.get(previousDateKey);
        int sum = 0;
        for (Integer n: array) {
            if (n != null) {
                sum += n.intValue();
            }
        }
        totalPosition[0] = new Position(previousDateKey, sum);

        /**
         * Using the sorted keySet, we will start with the second day. Remember
         * we can look up the asset values from the prior day with our
         * previousDateKey...which initially has the asset values from the first day
         *
         * We will now move through the key values, mapped to by our dates, our values
         * being our Integer arrays which are storing the values of each asset
         * where the index is serving as a key.
         *
         * When we come across a index in our asset array that is null, we will look
         * to the prior asset array for that value. If its non null, then we will
         * use it to update the current asset array. Every time a null is found in an
         * asset array, we will take the prior asset arrays value and update that index
         * with its value.
         *
         * Doing this allows checking subsequent asset arrays to only have to look at the
         * prior asset array to get the last value of an asset.
         *
         * As we do this check we will take a sum, and at the end of checking each asset array
         * we will sum the total, and create a new position which will represet the total
         * value of all assets on that given day.
         *
         **/
        for (int i = 1; i < lengthOfKeySet; i++) {
            Integer[] dayTotals = results.get(keys.get(i));
            int total = 0;
            for (int j = 0; j < dayTotals.length; j++) {
                /* j here is our key, which correlates with an asset
                 * if dayTotals[j] == null, for a given asset j, then we
                 * need to check the prior days asset information
                 */
                if (dayTotals[j] == null) {
                    Integer[] proirDayTotals = results.get(previousDateKey);
                    Integer value = proirDayTotals[j];
                    // if non null then take the sum
                    if (value != null) {
                        /* copy the value to this array, as the next asset array might need this
                         * latest value for a given asset.
                         */
                        dayTotals[j] = value;
                        total = Integer.sum(value, total);
                    }
                } else { // the position changed so just take this value and sum it with the current total
                    total = Integer.sum(total, dayTotals[j].intValue());
                }
            }
            /*
             * And the end of each interation we have summed up the total assets for a given day
             * so create the position entry for the day and store it in our return array
             */
            totalPosition[i]= new Position(keys.get(i), total);
            /* update the previousDateKey, so that the next interation of the loop can simply
             * look back to the prior day to get the integer value.
             */
            previousDateKey = keys.get(i);
        }

        return totalPosition;
    }
}
