
# Design a HIT counter system

Design a system that will count the number of hits in the past 5 mins.
The argument supplied is a timestamp. So cound the number of hits
in the last 5 minutes starting from the argument.
 
hit(1)
hit(2)
hit(3)
getHits(4) => 3
hit(300)
getHits(300) => 4
getHits(301)  => 3

There are two main wrinkles to this problem. My first solution was just using a list 
or a queue to store the timestamps, and them removing the head of the list if the time 
gap is larger than 5 mins from the last recorded hit. This works
if the timestamps arrive to our system in increasing order.

However, what if timestamps come in out of order due to latency? So
hit(300) could come, and then hit(5) after. How do we return ONLY
the count of things that occured in the last 5 mins?

We must realize that we ONLY need the last 300 entries. This should be a give away 
to use an array, which is fixed. In order to accommodate time stamps above 300, 
for example the time stamp 405, we take the modulus, 405 % 300, which will map to 
index 105. However, we have no way of associating a hit with a timestamp, because 
we are only using an array to store counts within the last 300 seconds.

What if we get a hit for timestamp 405, then 405, again, then 4 hits for timestamp 105, come in
late. How do we actually update the count, if we ONLY want the count for the last 5 minutes.
With just one array storing counts, we have no way to associate a count with a timestamp, 
so we will use an extra array to do this.

We do this by using an extra 300 space array. So we now have 2 arrays, both of length 300, 
one of them, our "hits" array, is used to store the counts at a given index, where the index is timestamp % length.

The other array is time, where the index is the timestamp % length, but the
value stored at the index is timestamp associated with the latest hit.

time[105] = 105, or time[105] = 405.

We use time array, to know when the counts are wrapping. When they wrap, we reset the value at
index to 1, and update the time array. If another hit comes in at the same timestamp, then
we simply increment.

When get hit is called, we do a sum over the array for values within the 300 range.
Scaling this for multiple processes would require using some kind of lock, so that
we can prevent one thread from reading a value with another thread is changing the value.

Scaling this for distributed machines would require aggregating the counts, when get
hit is called.


405 % 300 = 105...405 - 105 > 5 Mins, current count at index 105 = 1

// 105 -> (405, 405, 405, 405)

```java
class HitCounter{
    private final int MIN = 60;

    int[] hits = new int[300];
    int[] times = new int[300];
    void hit(int timestamp) {
         int index = timestamp % hits.length;
         if (times[index] != timestamp)    // times[105] = 105{
             times[index] = timestamp;
             hits[index] = 1;
         } else {
             hits[index]++;
         }
    }

    int getHits(int timestamp) {
           int result = 0;
           for (int i = 0; i < hits.length; i++) {
               if (timestamp - times[i] < 300) {
                  result+=hits[i];
                }
            }
            return result;
        }
      };
}
```
