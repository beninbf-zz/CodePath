1) optimal_solution.java



Description:



A BuildingIndex class is defined in the solution consisting of three members:

index: it is the value of x-coordinate of the potential skyline point.
startEnd: it is a character that denotes if this particular index is a start point or an end point of the building.
height: it is the height of the building.


A priority queue named priorityQ is created. All the start and end points of all the buildings as objects of BuildingIndex class are added in this queue with the following insertion constraints:

A lower value of index gets priority.
If the index values are the same, then 'start' point gets priority.
If two buildings are starting at the same index, then BuildingIndex with higher height gets priority.
If two buildings are ending at the same index, then BuildingIndex with smaller height gets priority.


A binary search tree named heightCountQ is created which stores the count of heights ordered by the heights of buildings.



Also, a variable named maxHeight is initialized with 0 which stores the current max height of the skyline.



Now pop elements from the priorityQ, one by one, till it is empty.

If the popped element is the starting index, then check
if it is greater than maxHeight, if so add the start index and height pair to the ans list.

If the popped element is the end index, then check if it was the maxHeight till now, if yes, then update the ans list with the current index and height from next tallest building from the heightCountQ. And decrease the count in heightCountQ, if the count is 1, then the node is removed from the tree.


Time Complexity (assuming that input arguments are already given and excluding time used in the declaration of output):



Sorting all the buildings as per the given constraints in the description would take O(2n*log(2n)), where n is the number of buildings in the given array and 2*n is the BuildingIndex. Also maintaining the count of heights take O(n*log(n)), since each insertion and deletion is O(log(n)) in a BST.



So, the total time complexity is O(n*log(n)).



Time Complexity:



Time to read the input is O(3*n) since for each line we have 3 integer values. For output, we can have 2*n skyline points in worst case scenario. Each output line will have 2 integer values, so the output time complexity is 2*O(2*n).



Overall time complexity is O(n*log(n)) + O(n) + O(n) ~= O(n*log(n))



Auxiliary Space Used:



The priorityQ and heightCount both require O(n) auxiliary space to store n buildings. So, total auxiliary space complexity is O(n).



Space Complexity:



Input and output arrays both require 3*n and 2*n amount of space respectively. Total space complexity including auxiliary space comes out to be O(n).