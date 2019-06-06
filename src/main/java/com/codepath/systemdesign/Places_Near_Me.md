# "Places Near Me":

Given a latitude, longtitude, and radius return a list of "places" within that radius

First thing to recognize is that we must be able to associate a "place" with a location.

We must ask ourselves how do we conceptualize a location?

Lets first create a system that will allow us to represent geo spatial data.
We will create a location grid system. This system, through an offline process, will
consume geo-spatial data, and populate the following schema.

## GRID table

| ID        | lattitudeLower | lattitudeUpper  | longitudeLower | longitudeUpper |
| ------------- |:-------------:|:-----:|:------:| ------:|
| abc2     | -3 | 0             | -3    | 0      |
| abc      | 0  | 3             | 0     | 3      |
| bcd      | 3  | 6             | 3     | 6      |


In order to speed up queries to this table we will need to apply an index to the following columns
(lattitudeLower, lattitudeUpper, longitudeLower, longitudeUpper). This will speed up queries
for retrieval.

The following SQL query could be used to retrieve grids, for a given lattitude and longitude.

```oracle-sql
select ID from GRID 
where lattitudeLower >= my_lat_lower 
and lattiudeUpper <= my_lat_upper
and longitutdeLower >= my_long_lower
and longitudeUpper <= my_long_upper;
```
In order to actually perform this query the grid service will have to have logic to take in a lattitude
and longitude and given a raduis perform some transformations.

Given a radius, essentially convert it into some number of degrees. For simplicity sake lets just assume 
we add the radius to the longitude. The same will happen regarding the latitude. So for a lat and
longitude of 1,1 and a radius = 3, then we are essentially looking for all grid with longitudes
in between [-2, 4] and all lattitudes in between [-2, 4]. To perform this query above,
we will essentially take the ceiling of the upper bound with respect to the units that our grid
is broken up into. For our schema, each grid is 3 X 3. So for the upper bound, 4 / 3 == 1, take
the ceiling, so 1 + 1 = 2. 2 times our grid size is 6. SO that is our upper bound. For the lower bound
-2/3 = 0, take the floor, so 0 - 1 = -1. -1 X 3 = -3, so that is our lower bound. if our range of
lat and long happen to divide evenly into our unit(3), then there is not need to take the ceiling
or the floor.

we can then return grids by calling

GET /locationGrid/latitude/<latitude>/longitude/<longitude>/radius/<r>

Our next microservice should be a registration service. When a new place is being added to our system, we should
associate with a location grid. To do that, we should develop an API in our location grid MCS, that will take an 
address, convert it into latitude and longitude, and then return the grid that the address is within.

Our registration microservice will call this Address-> grid API, and save the registered place with this grid.
## place table in registration mcs

| ID        | GRID_ID | NAME
| ------------- |:-------------:| -------------:|
| aaa       | abc | burger joint 

After a record is written to the micro-service, the registration micro-service should then publish an event to
an event logging system. Possibly kinesis. That event will then be consumed by our "Places" MCS.

The places MCS will then, update a cache, taking the GRID_ID, and mapping it to a list of place objects.

When a query is made against our places MCS trying to find "places near me". The places MCS will make a request
to the location grid service, and based on the latitude, longitude, and radius it will return grid_ids.

Those grid ids will then be used to check against the cache. All place objects in the lists being mapped to
by the grid_ids will be returned to the USER.

Microservices:

- Register MCS
- Grid Location MCS
- Places MCS

Register MCS:

As new places are created, they will be assigned to a grid. That assignment will be used
to populate a cache such that, the key will be the grid and the value a list of place
objects.

As registering does not ahve to be fast, as a place is registered, it will make a call
to the grid service to get a grid. Then asynchronously update a Cache in the places
MCS, with the grid mapping to a list.

The larger the radius supplied to the gridlocation service, the more grids returned.

Places MCS:

A cache holding our Key Value pairs: Grid -> List of places.
The Places MCS, will take a lat, long, and radius and call the gridLocaiton service
to retrieve the grids. It will then use each grid to pull a list of places, merge the lists
and return them to the caller.
