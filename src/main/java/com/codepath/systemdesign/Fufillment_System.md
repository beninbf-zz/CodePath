# FULFILLMENT SYSTEM

Design a system of distribution centers spread across different locations that must be able to
to deliver packages at the lowest cost possible.

The distribution centers have two kinds of trucks. Diesel trucks that have a long
range but are at a high cost, and electric trucks that are at a low cost, but have
a shorter range.

Order for customers must be fulfilled within the desired delivery time, 1, 3, or 5 days
AND deliveries must be sent out at the lowest cost.

How to design a system that will accomplish these things.

## Fulfillment Micro-service

Our first micro-service will be a fulfillment micro-service.
This micro-service will service as a controller. A request will be made to the fulfillment service from a web front,
which we will consider to be a black box for now, for an order to fulfilled. It is the Fulfillment services job to 
pick the correct distribution center to assign to the order to.

The fulfillment center knows about ALL of the distribution centers. Based on the delivery location for an order, 
the Fulfillment service will leverage the location distance service in order to find a subset of distribution centers 
of size n. This subset of distribution centers will have requests made to them, inquiring about the next available 
truck that can place an order. 

Considering the fulfillment center knows about the all of the distribution centers, its logical schema should
contain a table with all of the distribution centers.

The fulfillment service could make requests to the location distance service, to find the nearest distribution
centers.

### FULFILLMENT LOGICAL SCHEMA

#### DISTRIUBTION_CENTER TABLE
| ID        | region |
|:------------- |:-------------|
| abc2     | 1 |
| abc      | 2  | 
| bcd      | 3  |



## Distribution Micro-servce

The responses from the distribution center will contain information regarding whether or not that particular 
distrubtion center will be able to honor the order (such as if the inventory is depleted). The distribution
center knows about all information that is local to it, such as its delivery radius, its inventory, and its own
trucks.

If the distribution center has that particular inventory then it will respond with the available options and costs 
for delivery. Based on this response, the fulfillment center will chose the appropriate distribution center.

When a request comes to the distribution center micro-service, the service will respond with a list of "Delivery 
Options", where each object will contain a delivery date, and a cost.

The Distribution micro-service will need to be able to calculate distances to a given order destination
in its region, as such it will have to make calls to a location distance service to get such information.
This should be another micro-service.

When the distribution center receives an inquiry from the fufillment center, any truck that is returned
with delivery options should be have a "hold" put on it, such that it remains in a "pending" state, so that
it can be reserved by another request.

When the fulfillment center picks a "Delivery Option", it must respond to all of the fulfillment centers letting
them know that they can release the "holds" on their trucks

### LOGICAL SCHEMA FOR DISTRIBUTION CENTER

Logical schema: 
inventory table - (id, name, count, price)
truck table - (id, type_cd, status_cd, capacity)
truck_delivery (id, truck_id, expected_delivery_d)
order table - (id, created_d, truck_id, expected_order_delivery_d, actual_order_delivery_d)
order_inventory_table (id, order_id, inventory_id)


## DISTANCE MICRO-SERVICE

This micro-service must expose an API such that, the fulfillment center can make a request to it and find the distance
to the particular orders.

Micro-services in all:

- Fufillment Microservice
- Distribution Center Microservice
- Location Distance Service

