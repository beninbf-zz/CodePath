# Yelp Onsite

There was only one programming question. It was two parts. The first part was easy, the second part
more difficult. Of course I managed to fuck up the second part.

It was essentially a graphing problem, and the applications of the classic graphing problems, just
weren't at the top of my mind at the time.

The first part of the problem, we were presented with a series of "UserClick" objects that only
contain two fields, a source and destination.


```java
    class UserClick {
      String source;
      String destination;
    
      public UserClick(String source, String destination) {
        this.source = source;
        this.destination = destination;
      }
    
      public String toString(){
        return String.format("%s -> %s", this.source, this.destination);
      }
    };
```

Given a list of user clicks that would be in no particular order, our goal was to write a function
to take this list and return the final destination of the user.

In part one, the user never traveled to the same page twice, so the solution for that was quite simple.
In part two, however the user to travel to the same page multiple times. I was using a map to store
the page visits for any given source. So if page B was a source and the destination C, then my map
would have B => C. If another user click showed that page B was a source and page D a destination, 
then my map would change like so, B => "C, D". This essentially represented an adjacency list.

I couldn't figure out how to exactly return a given page, that a user stopped on, if that page
was visited more than once. I hadn't reviewed my graphing problems, and what I should have done, 
was just perform DFS by hand, listing the nodes as my code saw them, and I would have realized
that all we are essentially doing is a basic traversal sort. And the final desintation that we want
to return is the last string in the sort.

FUCK. The code for that isn't even complicated. Its just a play on dfs, where we would
add the nodes that we are about to visit, before actually checking if we visited them already.

```java
class Solution {
    
  public static String getFinalDestination(List<UserClick> list, String start) {
      
      Map<String, String> clickMap = new HashMap<>();
      
      for (UserClick click: list) {
          if (!clickMap.containsKey(click.source)) {
            clickMap.put(click.source, click.destination);
          }
      }
      
      String value = clickMap.get(start);
      String result = null;
      while (value != null) {
          if(clickMap.containsKey(value)) {
              value = clickMap.get(value);
          } else {
              result = value;
              break;
          }
      }
      return result;
  }
  
   public static String getFinalDestinationOther(List<UserClick> list, String start) {
      
      Map<String, List<String>> clickMap = new HashMap<>();
      for (UserClick click: list) {
          if (!clickMap.containsKey(click.source)) {
              List<String> list = new ArrayList<>();
              list.add(click.destination);
             clickMap.put(click.source, list);
          } else {
             List<String> list = clickMap.get(click.source);
             list.add(click.destination);
          }
      }
      
      Set<String> keySet = clickMap.keyset();
      List<String> result = new ArrayList<>();
      Set<String> visited = new HashSet<>();
      result.add(start);
      if (!visited.contains(start)) {
          explore(start, clickMap, result);
      }
      
      return result.get(result.size() - 1);
  }
  
  private static void explore(String key, Map<String, List<String>> clickMap, List<String> result) {
      List<String> neighbors = clickMap.get(key);
      for (String neighbor: neighbors) {
          result.add(neighbor);
          if (!visited.contains(neighbor)) {
              visited.add(neighbor);
              explore(neighbor, clickMap, result);
          }
      }
  }
  
  public static void main(String[] args) {
    ArrayList<UserClick> userData = new ArrayList<>(Arrays.asList(
      new UserClick("Home",           "Home Cleaning"),
      new UserClick("Home Cleaning",  "Restaurants"),
      new UserClick("Restaurants",    "Delivery"),
      new UserClick("Delivery",       "Address Search"),
      new UserClick("Address Search", "Burgers"),
      new UserClick("Burgers",        "Order Delivery"),
      new UserClick("Order Delivery", "Start Order"),
      new UserClick("Start Order",    "Turkey Burger")
    ));
    
    Collections.shuffle(userData);
    
    String result = getFinalDestination(userData, "Home");
    System.out.println("Solution");
    System.out.println(result);
  }
}
```
