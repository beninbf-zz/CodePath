## URL SHORTENER

Given a long url, return a shortened url.

### Questions to Ask

1. What size of the shortened url will we return?
2. What is the expected write QPS? 
3. What is the expected Read QPS?


The size of the shortened URL should be no more than 7 characters

We essentially want to create a key value store. Where the "key" is something
unique and the value is our original url. Our "key" should allow us to find
the original url quickly.

What if we just used the request number (i.e. the first request is request number 1)
as a key. This wouldn't scale as the numbers would eventually grow to be massive.
As such, we can think of using a base 62 conversion.

Why base 62? 

We will essentially use a base 10 number and convert it to base 62. What will we use
to create a base 62 number. 

We will use characters a - z (26 characters), A - Z (26 characters), and 0 - 9 
(10 characters), thats a total of 62 possible characters for every spot in our
shortened url. With 7 characters needed for our shortened url, that means we have
a total of 

62^7 different possibilities for urls. If we just increase the length of the shorted
url by 2 characters, we greatly increase the total number of different possibilities
so the shortened urls we generate should always be unique.

To compute the conversion we divide the number by our base (in this case 2). After 
each division we store the remainder of the quotient % our base. We keep
dividing until we get to 0. Our stored remainders, represent indices to our 62 possible
characters.

An example conversion with the base 10 number 125 is below:

* 125 / 62 (using integer division, which gives us the floor) = 2
* 125 % 62 gives us a remainder of 1 (store 1)
* 2 / 62 = 0, 2 % 62 = 2, store remainder of (2)

We should store our remainder in the reverse order, so [2, 1] as opposed to
[1, 2]. The characters [2, 1] correspond to "c" and "b".

* 0 ----> a
* 1 ----> b
* 2 ----> c

etc...

so our shortened url would only be "cb".

```java
public class UrlShortener {
    
    public String getShortenedUrl(int number) {
        Stack<Integer> urlIndices = new Stack<>();
        int quotient = number;
        int remainder = 0;
        while (quotient > 0) {
            quotient = number / 62;
            remainder = quotient % 62;
            urlChars.push(remainder);
        }
        
        StringBuilder sb =  new StringBuilder();
        while(!urlIndices.isEmpty()) {
            int index = urlIndices.pop();
            sb.append(urlChars.get(index));
        }
        return sb.toString();
    }
}
```
Given the shortened url, the string "cb", how could we retrieve our "key",
the base 10 number? 

Remember that all numbers are essentially of the form

digit * (base) ^ (n - 1) + digit * (base) ^ (n - 2) + digit * (base) ^ (n - 3)+...+ digit * (base) ^ 0 

In this case that would mean

"c" * (62) ^ 1 + "b" * (62) ^ 0, is original base 10 number, here we just have to replace "c" and "b"
with the positions our those characters from our urlChar map, "c" = 2, "b" = 1.

base10 Number = 2 * (62) ^ 1 + 1 * (62) ^ 0 = 124 + 1 = 125.

```java
public class UrlShortener {
    
    public int getKeyFromShortUrl(String shortUrl) {
        
        int key = 0;
        for (int i = 0; i < shortUrl.length(); i++) {
            char c = shortUrl.charAt(i);
            int base62Position = urlBase62Map.get(c);
            int base10Position = urlBase10Map.get(c);
            key += base10Position * Math.pow(62, position);
        }
        return key;
    }
}
```

Now that we have our key, we should be able to look up the value of our original
long url.

How are we going to get a base 10 number to convert? If we are storing data in a RDBMs
then we could use the sequence number of the DB as a way of getting a unique number.

We can see that this will have problems though.

1. When we get our shortened url, we will have to save it in the DB to store our
long url. Only when the record is committed, will our DB generate the sequence number.

2. After getting the sequence number, the code will then have to convert it
to a shorteded url, and then save the shorted Url back to the DB for the same
row that was just committed.

This will be slow and not scalable, because we have 2 DB transactions for every url conversions
Another downside is that, if we every have to perform a DB migration, depending on the sequence
number will be problematic if we have to merge records into the same table, we could
have collisions.

We should come up with another way to get the unique base 10 number to convert.
For this we can use some random number generator, or even Redis. We can seed redis
with a request number, and then call redis "incrementID" each time we need a number 
number. This number can then represent our base 10 number to convert.

As requests are made to shorten URL's, we can grab a base 10 number from redis to covert
to a base 62 string. After converting we can save a record in our DB like such

URL:
id, shortenedUrl, base10ConversionNumber, longUrl, created_d

We could then index the base10ConversionNumber column for optimized reads from the database.

When a GET for a shortened URl comes in, we could c convert the string to its base 10 number,
and then query the DB for the record. 

We could then retrieve the longUrl and redirect the requester to that destination.

Next we must consider the scale of this service.

We can expect 500 Million url shortening requests per month.

* 500 M / (30 days * 24 hours * 3600 secs) = 192 reads/ s (write qps = 192 Urls/sec)

if instead it was 1000 M, or 1 billion reqs per second, then that would double to 
384 urls/sec (write qps)

what about for reads? Which in this case is request to redirect, from the short url
to the long url. Lets assume a read to write ratio of 100:1, so 100 * 500 M redirections 
a month or 50 billion redirects per month.

That is

* 50 Billion / (30 days * 24 hours * 3600 sec) = 19,000 read/sec (read qps = 19K/s)

If that doubles it would be 38 K/s. 

We have to store our short urls for 2 years. 

1. Given the 2 year requirement how much storage do we need?

Lets first count the number of db objects. We will have anywhere from 500 M to 1 Billion
requests per month. 

So thats a maximum of 

* 1000 M * 12 months * 2 years = 24 billion db objects

what is the size of one db object, or one row?

Our schema consists of
id, base10Number, shortUrl, longUrl, created_id, expiration_date

id (16 B), base10Number (8B), shortUrl (512 KB), longUrl (500 B), created(8B), expiration (8B)

for simplicity sake, lets just call it 500 bytes.

So thats 24 billion * 500 bytes, 12 billion bytes. So comes out to 12 TB of storage.

A production machine can hold 2 TB of storage locally. So that's 12 / 2 is 6 machines
for storage for out DB cluster

Given 6 machines for storage, we must implement sharding, we can use the base10Number
hash as a shard key.

For writes, we said we should expect a max of 386 writes urls a second. So thats 386 *
500 bytes per second to be stored. That's about 200 KB / s. 

So we will have to store 200 KB/s. 

Regarding our expiration of data, we should have an offline process purge data
on a schedule, and do it in batches, so as not to overload the system.

