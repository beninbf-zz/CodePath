package main.java.com.codepath.techscreens.wework;

public class TechScreen {
    /**
     * Tech screen from WeWork
     *
     * Suppose a array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e., [0,1,2,4,5,6,7,8] might become [4,5,6,7,8,0,1,2]).
     *
     * You are given a target value to search. If found in the array return its index, otherwise return -1.
     *
     * You may assume no duplicate exists in the array.
     *
     * Your algorithm's runtime complexity must be in the order of O(log n).
     *
     * This problem problem gave me a great deal of difficulty because I hadn't reviewed how to do
     * properly do a binary search. I was able to come up with algorithm, but my approach was flawed
     * the idea is a play on binary search. The goal is to keep dividing the array in half
     * looking for the sorted portion of the array. When you find the sorted portion
     * and the target is in between the left and right values, then just run binarySearch.
     *
     * If not, then continue to recurse on the left and right half, until you find the
     * sorted portion where your target is in between the left and right.
     *
     * The key to this problem as to start with the conditions for running binarySearch first.
     * Then if you didn't find the necessary conditions to do such, then go ahead and apply the
     * wrinkle to this problem, to keep searching for the sorted half of the array that contains
     * your target.
     *
     * @param nums array of integers
     * @param target integer
     * @return the index of the integer or -1
     */
    public int findTargetInSplitArray(int[] nums, int target) {
        return findTargetInSplitArrayHelper(nums, 0, nums.length - 1, target);
    }

    public int findTargetInSplitArrayHelper(int[] nums, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        int mid = (right + left) / 2;

        if (nums[mid] == target) {
            return mid;
        }

        if (nums[left] <= nums[mid] && isInBetween(nums[left], target, nums[mid])) {
            // This portion of the array is sorted and our target is in between the bounds
            return binarySearch(nums, left, mid, target);
        } else if (nums[mid] <= nums[right] && isInBetween(nums[mid], target, nums[right])) {
            // This portion of the array is sorted and our target is in between the bounds
            return binarySearch(nums, mid + 1, right, target);
        } else if (nums[mid] >= nums[right]) {
            // This portion must have the rotation point, so recurse and look for the sorted portion
            return findTargetInSplitArrayHelper(nums, mid + 1, right, target);
        } else if (nums[left] >= nums[mid]){
            // This portion must have the rotation point, so recurse and look for the sorted portion
            return findTargetInSplitArrayHelper(nums, left, mid, target);
        }
        return -1;
    }

    private boolean isInBetween(int leftBound, int number, int rightBound) {
        return leftBound <= number && number <= rightBound;
    }

    /**
     * Binary search always fucks me up because I always mess up the base case.
     * considering we are constantly moving the left and right indices towards each other,
     * when they cross, i.e. left > right, then we should stop. THATS ALL.
     *
     * @param nums array of integers
     * @param left the left bound of the array
     * @param right the right bound of the array
     * @param target the target value we are searching for
     * @return the index of our target value
     */
    public int binarySearch(int[] nums, int left, int right, int target) {
        if (left > right) {
            return -1;
        }

        int mid = (right + left) / 2;

        if (nums[mid] == target) {
            return mid;
        }

        if (target < nums[mid]) {
            return binarySearch(nums, left, mid, target);
        } else {
            return binarySearch(nums, mid + 1, right, target);
        }
    }
}
