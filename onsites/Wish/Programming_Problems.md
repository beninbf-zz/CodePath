# Programming problems

1. Find the minimum path through a grid of non negative integers, starting at the 0,0 cell,
and ending at the bottom right most cell.

2. Given an array, find every subset of size k, that sums to a total. The important thing to
remember about this is, while using recursion to generate the subsets would work, it is not
recommended as the running time is O(2 ^ (r * c)). If the problem gives us a specific
size of the subset, its better to not use the classic recursive solution of generating
all subsets. We can still use recursion but it would be better to NOT generate all of the
subsets.

In this problem its not simply asking if there is a subset of a given size k, that equals
our total, it also wants the numbers in that subset.

Given we know the size k, we can recurse k times, each time adding an element from our 
array to a subset. When the size of our subset is equal to k, then add it to another set and return.

As the recursion returns we must un choose our latest selection to the subset, and move to the next
element in our original array. We can generate the subsets of size k, then check if there sum equals 
the total.

```java
class Solution {
    public List<List<Integer>> findSubsetsOfSizek(int[] array, int k, int total) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> candidate = new ArrayList();
            subsetsOfSizeKhelper(array, 0, k, candidate, result);
            
            for (List<Integer> list: result) {
                if (sum(list) == total) {
                    print(list);
                }
            }
            return result;
        }
    
    private void subsetsOfSizeKhelper(int[] array, int index, int k, List<Integer> candidate, List<List<Integer>> result) {
        if (candidate.size() == k) {
            result.add(new ArrayList<>(candidate));
            return;
        }
        
        for (int i = index; i < array.length; i++) {
            candidate.add(array[i]);
            subsetsOfSizeKhelper(array, i + 1, k, candidate, result);
            candidate.remove(candidate.size() - 1);
        }
    }
    
    private int sum(List<Integer> list) {
        int sum = 0;
        for (Integer n: list) {
            sum += n.intValue();
        }
        return sum;
    }
    
    private void print(List<Integer> list) {
        for (Integer n: list) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

```