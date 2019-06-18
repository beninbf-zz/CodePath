
# Wish Onsite

4 Technical interviews.

1. Write a function to return something that can represent a silhouette.


We have an object like such (1, 1, 3), where first number represents the height, and the last two a left
bound and right, bound. These three numbers can be used to demarcate a building on the x, y plane.

The four coordinates of a square/rectangle on the x, y plane, given (1, 1, 3), would look like the following

(1, 0), (1, 1), (1, 3), (3, 0). Look at the image below.

![](images/one_building_object.png)

What we want is to write a method, that could take in a list of these building objects (i.e. (1, 1, 3)),
and return the representation of a silhouette. Lets assume, we have some additional building objects, 
such as (2, 2, 5) and (1, 3, 6). On a 2-d plane, they would look like following,

![](images/multiple_building_objects.png)


What we want to do, is to find away to represent and return the silhouette created by these building objects. The
silhouette, would look like the following.

![](images/silhouette.png)

Steps to solving this problem:

This problem isn't something that is necessarily straight forward, and it requires some thought. The first thing
to do, is to come up with objects that can represent all of the entities here. The first object is essentially given
to us, is the building, with the three values, the height and the left and right bound. We notice that the appearance
of the silhouette will be largely dictated by the building with the left most bound. As such its a good idea
to sort these building objects based on that left bound. So our building class should implement comparable, and create
a natural ordering based on the "left" value.

```java

class Building implements Comparable {
    int height;
    int left;
    int right;
    
    Building (int height, int left, int height) {
        this.height = height;
        this.left = left;
        this.right = right;
    }
    
    public int compareTo(Building building) {
        if (this.left < building.left) {
            return -1;
        } else if (this.left > building.left) {
            return 1;
        } else {
            return 0;
        }
    }
}
```
Now that we have this, we must ask ourselves what is the best possible way to represent a silhouette. Well, if we
look at the diagram of the silhouette above, we see that a silhouette is just a list of coordinates on the x,y plane.
So lets make a coordinate class.

```java
class Coordinate {
    int x;
    int y;
    
    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

Now the hard part, and interesting point about this problem is how to come up with a solution, to generate all of the
coordinates of a silhouette, given a list of building objects. For ANY problem like this, where the solution is not 
obvious, it is ALWAYS best to start with the smallest possible test case, and build up from there. The reason why
we do this is because this can make the problem simpler. And as we adding more complexity, we can keep a close
eye on what is changing as we scale the problem up, and this is the key to discovering the wrinkle in any problem.

So, lets first start with just one building object. Look at our graph below. How could we generate the silhouette for
this.

![](images/one_building_object.png)

Well, its simple, its only 1 building object, so it would simply be the 4 coordinates of that building. Now what if we
add in a second building.

![](images/second_building.png)

We see now that we will have to do some comparision based on the left bound, and height of the incoming building object.
Specifically, if the left bound of the incoming building object is less, than the right bound of the already present
building object, then the next point will entirely dictated by the height of incoming building. We can see in the 
diagram above, that the existing building, has a right bound of 3, while the incoming building has a left bound
of 2. We also notice that the height of the incoming building is taller than the existing height of the first building
object. This is enough to deduce that the next point will be the left bound of the incoming object, and the height
of the incoming object. What if the incoming building had the same left bound, but was shorter? 

![](images/shorter.png)


Because the height of the incoming building is not equal to the height of the already present building, that means
the y coordinate of the next point will still be the height of the incoming building. What about the x coordinate? Well
again, the left bound of the incoming building is less than the right bound of the already existing building, BUT,
because the building is shorter we will be using the x coordinate of the already existing building. 

Any addition of any more building will essentially follow the same pattern. 

```
    if next.height > current.height
        // in this case where the next height is larger, we will always have 3 points to add
        if current.right > next.left // add three points
            first coordinate with x = next.left, y = current.height;
            second coordinate with x = next.left, y = next.height
            third coordinate with x = next.right, y = next.height
            // in the case that the current buildings right bound is also greater than
            // the next buildings right boundary, we have another point to add!!
            if current.right > next.right
                fourth coordinate = x = next.right, y = current.height
            }
        else if current.right < next.left
            first coordinate with x = next.left, y = 0
            second coordinate with x = next.left, y = next.height
            third coordinate with x = next.right, y = next.height
        else
           coordinate x = current.right, y = current.height
           coordinate x = next.left, y = next.height
           coordinate x = next.right, y = next.height
    else if next.height < current.height
        // the next building is shorter, we will add either 2 or 3 points depending on the right
        // and right of the current building and left bound of the next building 
        if current.right > next.left // only two points to add
            create coordinate with x = current.right, y = next.height
            create coordinate with x = next.right, y = next.height
        else if (current.right < next.left) // three points to add as buildings are not touching
            first coordinate with x = next.left, y = 0
            second coordinate with x = next.left, y = next.height
            third coordinate with x = next.right, y = next.height
        else // next.left and current.right are equal
           coordinate x = next.left, y = next.height
           coordinate x = next.right, y = next.height
    else // heights are equal
        // if the current.right is >= the next.left, then there is only one point to add
      if current.right >= next.left  
        coordinate x = next.right, next.height
      else 
        // three points to add, as buildings are touching
        first coordinate with x = next.left, y = 0
        second coordinate with x = next.left, y = next.height
        third coordinate with x = next.right, y = next.height
        
// we will need to update the leading point with next.right and next.height
```

The addition of every subsequent building is going to force us to ask the same questions as our psuedo code above,
what is the height of the incoming building, and what is its left boundary, and how does that compare with
the height of the current building and right boundary of the current building.

Essentially the height and right boundary current building represent a leading point. And the new coordinates, we will
create for every added building, we be dicated by the height, and left most boundary any incoming building. After adding
a new building, we will have a new leading point, with a different height, and add different right boundary. We 
might have some edge cases regarding building with no overlapping right and left boundaries but that is simple enough
to solve. We can see that having a silhouette class, with a leading coordinate, whose x and y values represent
the leading height, and the leading right boundary, will help us in generating new coordinates every time we 
add in a new building. After the addition of every new building, then the leading coordinate should be updated
to reflect the leading point.

One thing to recognize is that the final point in our coordinate list, will be just to add one more coordinate, with
the right boundary of the leading coordinate, and a height of zero. Essentially just dropping a line down
to the 0 line on a graph.

```java
class Silhouette {
    private List<Coordinate> points = null;
    
    private Coordinate leadingPointOne = null;
    private Coordinate leadingPointTwo = null;

    
    // Use an initial building object to give us our first points
    Silhouette(Building b) {
        points = new ArrayList<>();
        points.add(new Coordinate(b.left, 0));
        points.add(new Coordinate(b.left, b.height));
        points.add(new Coordinate(b.right, b.height));
        leadingPoint = new Coordinate(b.right, b.height);
        // we will leave out the last point, until we know we wont have to add any more buildings
        // this is easier than having to remove the last point in our points list;
    }
    
    public void add(Building next) {
        if (next.height > leadingPoint.height) { 
            if (leadingPoint.right > next.left) { 
                points.add(new Coordinate(next.left, leadingPoint.height));
                points.add(new Coordinate(next.left, next.height));
                points.add(new Coordinate(next.right, next.height));
                if (leadingPoint.right > next.right) {
                    // if we run into this case, we could have multiple leading point
                    // if that happens then we will have to pick which leading point
                    // to use in our creating of points
                    points.add(new Coordinate(next.right, current.height));
                }
            } else if (leadingPoint.right < next.left)  {
                points.add(new Coordinate(next.left, 0));
                points.add(new Coordinate(next.left, next.height));
                points.add(new Coordinate(next.right, next.height));
            } else  { 
                points.add(new Coordinate(leadingPoint.right, leadingPoint.height));
                points.add(new Coordinate(next.left, next.height));
                points.add(new Coordinate(next.right, next.height));
            }
        } else if (next.height < leadingPoint.height) {
            if (leadingPoint.right > next.left) {
                points.add(new Coordinate(leadingPoint.right, next.height));
                points.add(new Coordinate(next.right, next.height));
            } else if (leadingPoint.right < next.left) {
                points.add(new Coordinate(next.left, 0));
                points.add(new Coordinate(next.left, next.height));
                points.add(new Coordinate(next.right, next.height));
            } else { 
                points.add(new Coordinate(next.left, next.height));
                points.add(new Coordinate(next.right, next.height));
            }
        } else { // heights are equal
            if (leadingPoint.right >= next.left) { 
                points.add(new Coordinate(next.right, next.height));
            } else {
                points.add(new Coordinate(next.left, 0));
                points.add(new Coordinate(next.left, next.height));
                points.add(new Coordinate(next.right, next.height));
            }
        }
        
        leadingPointOne.height = next.height;
        leadingPointOne.right = next.right;
    }
    
    public void completeSilhouette() {
        points.add(new Coordinate(leadingPoint.right, 0));
    }
}

```

We could then instantiate our Silhouette class, using the first building object for the constructor. We could
then move through the rest of the points in our building object list. 

Whenever we are finished, we could add our last point, which is essentially dropping a line from the leading point
to the height of zero.

There is one edge case to consider and this is if the next building is taller, but its right bound is less than
the right bound of the current leading edge, in that case our logic in the next.height > current.height, will have
to add extra points, and we might have to have more than one leading edge. Look at the image below.

![](images/within.png)
