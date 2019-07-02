# Time Metrics Counter System

## System design

This was the problem presented to me by Stripe.

Create an API that can record the count of an event, and then later retrieve those events so that they can be neatly
displayed in charts. Think of tools like Graphite.

I could tell right off the bat this problem would call for a NoSQL solution, as those systems are the best suited
to create these kinds of metric and analytic based systems. I have, however, never used NoSQL before, so I tried to 
squeeze a kind of NoSQL solution into the same paradigm many of my other System Design problems have followed.

Initially I felt good about it as I was able to answer questions regarding the draw backs of this system, but after 
further reviewing my scalable system notes (which I had planned to do before the interview, but didn't because I thought
I needed a break) I realized my solution was unnecessarily complicated. I had a sharded
DB, NoSQL, being written too, and then another service consuming those events via a pub sub model, and then this
read service was used to retrieve events. TOTALLY FUCKING UNNECESSARY.

I keep asking myself if I had reviewed my notes earlier, would it have made a difference. I think it might have, but
I'm not sure as I didn't previously have the context for the particular scalable system design that would have fit
this problem.

FUCK...FUCK...FUCK. I might be cursed....seriously.


Anyways, the system design for this problem should have been like such.

An API call to record an event would look like the following

```bash
POST /events/{event_name}/

{
    event_name: "name",
    timestamp: some_date 
}
```

We should then ask what the Write QPS is. How many times with the POST for this API be called? The answer
to this will let us know if we need to implement sharding or not. However this question could wait until the end.
The first things to get down, are the logical schema, and the API call (which is above). The logical schema, given
we are using NoSQL, because of its columnar storage, would be the following.

```bash
seconds_table: key->event_name : value-> List of timestamps
minutes_table: key->event_name : value-> List of timestamps
hours_table: key->event_name : value-> List of timestamps
days_table: key->event_name : value-> List of timestamps
```

Boom, thats all there is there. Now we are told that the system is write heavy, but NOT read heavy as a GET api, will
be called sparingly to get data for the purpose of generating charts. The API would be

```bash
GET /events/{event_name}/unit_of_time/{unit_of_time}/start/{start_time}/end/{end_time}
```

This is how most time metric systems structure their queries, a user will essentially query for the number of hits, of
a given event with a given unit of time, within a start and end time. So something like, "give me the number of signups
per hour starting from 8:00 am to 12:00pm"

As we write to our storage, our timestamps will be entering the list in ascending order, simply by adding the timestamp
to the end of the list. Now to ensure that our list doesn't grow unmanageably large, we can simply bound the number
of timestamps in one list, and simply add another list to a new column. So lets say we had to store a million 
timestamps. We wouldn't want one list with size 1 million. So we could create 100 columns, each with a bounded size
of 10,000. This would optimize read performance. Remember that NoSQL is already optimized for read performance, already
given its column family memory store.

It is however NOT optimized for writes. As such our system should handle that. For handling write heavy systems, on
approach that can be used is "write later" caching. This essentially means that we will first write to a Cache. And then
asynchronously write to storage. We should do this, because NoSQL is not optimized for writes. NoSQL first writes
rows to a bin.log file, then every few hundred milliseconds those rows are crunched and then used to update the 
appropriate column families, so the writes are fairly slow.

So we will use this write later approach. We could update our NoSQL data store in batches, which will give each
batch enough time to write. Considering the write volume will be fairly high, it might be a good idea to have
replication. We could have read requests be routed specifically to different replica's then the ones being written to,
to avoid reading from a db server already being slammed with lots of requests.

Now, the question about weather to shard or not, really comes down to the write QPS. Lets assume a write QPS of
1000/second. 

How much storage would we need? Well production data is usually head for 2 years. So lets look at the size of what we 
are storing. We are storing lists of timestamps. A timestamp is 8 bytes. So for 1000 writes per second, how much is that
in a day.

```bash
1000 /second * 60 (minute) * 60 (hour) * 24 (days) = 86,400,000
```
So we would have to store 86,400,000 timestamps in a single day. How much storage is that? Well a timestamp is around
8 bytes. So thats 86,400,000 * 8 = 691,200,000 bytes to be stored in a single day. That comes out to about 691 MB,
in a day. For one year, thats 691 MB * 365 is around 252 GB a year. For two years thats about 500 GB. 

A production quality machine has 128 GB of ram, and 2TB of storage. So one machine would appear to be enough. 

This is when we need to figure out if the writes per second will be larger, as this will dictate if we need to
implement sharding. If we do implement sharding, a possible shard key could be in the event name, but that probably
wouldn't ensure even distributions between shards. We would probably have to make the shard key a combination of the
event name, and a range of time. 