package main.java.com.codepath.techscreens.objects;

import java.util.HashMap;
import java.util.Map;
/*

 /api/price/AAPL
 - 200.00

 - clientId
 - ticker symbol

 - Rate limiter
 - Call the API n numbers of times within a time period
 - ex: 10 calls per second

 - If number of calls exceed the threshold, block the call with an error message
 - If number of calls are within in the threshold, allow the call to proceed

 - HTTP method call


 Per client - ex: clientId = 1
 // 0th. - 3
 // 1st sec - 0, 1 - 10, 11 onwards block
 // 2nd sec - 0, 1 - 10, 11 onwards block

 clientId = 2
 0th,
 1st
 2nd..
 10th..

 // Fixed time period


 */
public class MyCache {

    private Map<Integer, Map<Long, Integer>> clientCountMap = new HashMap<>();

    private static final long SECOND = 1000;

    public MyCache() {
        this.clientCountMap = new HashMap<>();
    }

    // public boolean allowCall(Integer clientId) {
    //   if (!clientCountMap.containsKey(clientId)) {
    //     return true;
    //   } else {
    //     Integer count = clientCountMap.get(clientId);
    //     return count.intValue() <= 10;
    //   }
    //   // return true - if within threshold
    //   // return false - if exceeding threshold
    // }

    public boolean allowCall(Integer clientId) {
        Long timestamp = System.currentTimeMillis();
        Long second = timestamp / SECOND;
        return allowCall(clientId, second);
    }

    // @VisibleForTesting
    boolean allowCall(Integer clientId, Long second) {
        // if (!allowCall(clientId)) {
        //   return; // return HTTP code to client
        // }

        // Integer count = clientCountMap.get(clientId);
        // int newCount = count.intValue() + 1;
        // this.clientCountMap.put(newCount);

//
//        if (!clientCountMap.containsKey(clientId)) {
//            Map<Long, Integer> clientIdTimeMap = new HashMap<>();
//            clientIdTimeMap.put(second, 1);
//            clientCountMap.put(clientId, clientIdTimeMap);
//        } else {
//            Map<Long, Integer> clientIdTimeMap = clientCountMap.get(clientId);
//            if (clientIdTimeMap.containsKey(second)) {
//                Integer count = clientIdTimeMap.get(second);
//                if (count.intValue() > 10) {
//                    return false;
//                }
//                int updateCt = count.intValue() + 1;
//                clientIdTimeMap.put(clientId, updateCt);
//            } else {
//                clientIdTimeMap.clear();
//                //Map<Long, Integer> clientIdTimeMap = new HashMa
//                clientIdTimeMap.put(second, 1);
//            }
//        }
        return true;


        // Unix epoch millis
        // timestamp / 1000 - 14201
        // 14201 - no entry - allow the call, initialize 1
        // 14201 - 1 + 1 = 2
        // 14202 - no entry
        // timestamp + 10 s, clientId
    }
}