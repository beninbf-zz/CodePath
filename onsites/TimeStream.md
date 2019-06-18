# Time Stream

There is a file, the file is too large to be read into memory, so we will have to stream it, reading it
line by line. 

The file is of the following format

FILE:

ts1 : uid A
ts2 : uid B
ts3 : uid C
ts4 : uid D
.
.
.

Given this file, we want to write a function that can return the users that have a given number of its, in
a time T. For example

```java
class Solution {
    public List<Users> getUsers(File file, int hits, int T);
}
```
The function will be called as such getUsers(file, 5, 30), which is to say, return all of the users 
who had 5 "hits" or events in 30 seconds.

Again...the same fucking problem. I got stuck at the outset, because I couldn't realize how to retrieve
users who had x number of hits, or entries in this file within 30 seconds. My first approach,
was to store entries in a map, maping the USER to a map if timestamps and counts. I had then planned
to count from 1 to 30, for each user and see if there were so many hits in that time span.

This solution isn't optimal, and structure of the map is overly complicated. A better map structure, would be
to Still have users as keys, but map the user to a tuple. The tuple would store a count, and a timestamp
like so

A => {ts, count}

We would stream each line of the file in, one line at a time, and then update our map. When we encounter a 
line, we would take the user, and add it to the map, mapping it to a tuple.

When we encounter, another line, with the same user id, we then have a choice to make. We can check the current
time stamp in the tuple. If the incoming timestamp is with a time "T" from the current timestamp in the tuple,
then we will increment the count in the tuple. Check the count, if it equals "hits", then add the User to a list
that we will return.

If the next line in the file has a time stamp that is greater than time "T", then we can just overwrite
the existing count in the tuple with 1.

We can do this same scheme for every line in the file, accumulating counts for each user, and overwriting 
them if the next timestamp for a given user is within time "T". If the next timestamp for that user was
beyond the time T, then we would just reset the count again starting at 1.
