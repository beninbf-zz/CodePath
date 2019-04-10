package main.java.com.codepath.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MinHeap - A heap is a data structure that is organized around the principle
 * of a heap property. A heap is best visualized with a tree. A min heap
 * is organized by the ordering that the parent is <= to its children. Look below
 *
 *          4
 *        /   \
 *       5    10
 *
 * As we can see with the tree above, the parent is less than or equal to both of its
 * children.
 *
 * Now how are nodes inserted into a heap? We always insert nodes into a heap on a
 * row by row basis, filling out the existing row, before moving down to the next.
 * See blow for an example:
 *
 * We have heap:
 *
 *          4
 *        /   \
 *       5    10
 *
 * And we want to add, 12, 20, and 23 then we add it in the following manner
 *
 *           4
 *        /    \
 *       5      10
 *      /  \   /
 *     12  20  23
 *
 * If we were to add 24, then it should be added as the right child of 10.
 *
 *             4
 *          /    \
 *         5      10
 *        /  \   /   \
 *       12  20  23  24
 *
 * Then if we add, 25, it should be added as the left child of 12
 *
 *             4
 *          /    \
 *         5      10
 *        /  \   /   \
 *       12  20  23  24
 *      /
 *     25
 *
 * The next question to ask and answer is how to we maintain this min heap
 * property of parent <= children.
 *
 * Well every time we insert a child node, we simply add to which ever row
 * in the tree to fill it out. When we do that though, we need to check to
 * see if it satifies the heap property.
 *
 * We compare the value we just added with its parent, if it violates the
 * property then we swap it with its parent. Look Below
 *
 * Lets say we want to add 1 to this heap
 *
 *          4
 *        /   \
 *       5    10
 *      /
 *     1
 *
 *  This violates the heap property, as 5 is not less than or equal to 1, so we will swap
 *  5 and 1.
 *
 *          4
 *        /   \
 *       1    10
 *      /
 *     5
 *
 * We now see that the heap property is maintained for the nodes, 1 and 5, but not for the root
 * of the tree which is 4. So we now swap 1 and 4
 *
 *          1
 *        /   \
 *       4    10
 *      /
 *     5
 *
 * Now this is a proper heap, so we can stop swapping. The next question to ask is, how do we remove
 * from a heap? Well the point of a heap, is to either get the minimum or maximum in constant time.
 * So we do that by simply returning the root. When we do that, we need to ensure that the remaining
 * heap maintains the heap property.
 *
 * We do that by taking the last element in the heap (or in terms of a tree, the last level order node)
 * and placing it at the root.
 *
 * We now have to check the children of this new root, and ensure the heap property is maintained. If any
 * of the children of the new root violate the heap property, then we swap the root with the child
 * that is smallest. We do this because we want the smallest value at the top of our min heap.
 * (For a max heap we would swap for the largest child.) We then check the heap property
 * again for the node we just swapped down, and repeat until the heap property is maintained.
 * Look below:
 *
 *          1       Remove 1
 *        /   \
 *       4    10
 *      /
 *     5
 *
 *          5     swap with the smallest
 *        /   \
 *       4    10
 *
 *          4
 *        /   \
 *       5    10
 *
 *
 * In order to implement this data structure, its easiest to use an array. Because we
 * are using an array, then any  node at index i, has a left child at 2 (i) + 1,
 * and has a right child at 2(i) + 2.
 *
 * To find the parent of any index, we simply take the floor of (i - 1) / 2. Standard integer
 * division will take the floor for us.
 *
 * For adding nodes to this heap, we simply stick the node at the end of the backing array.
 * Then perform our "heapify" that we described earlier. For removing nodes,
 * we simply remove the root, which is at index = 0. Then perform our heapify again, by putting
 * the last element at the root, and them maintaining the heapify principle down the tree.
 *
 * This data structure has runtime complexity for the following purposes.
 *
 * Extract min: O(log n) because we have to maintain the heap, which means looking continuously
 * at half of the nodes
 *
 * Insert : O(log n), if our heap property is violated we must "heapify" our data, which  means
 * looking continuously at half of the nodes
 *
 * Because we will be using this MinHeap, primarily for Prim's Algorithm we have added a bit
 * of functionality by including a backing Map. Why the backing Map? Our map, will map
 * our vertices (used as keys) to their position in the minHeap array. That will allow
 * us to find out two things about a vertex in constant time. The first, is if our minheap
 * contains a particular vertex, and the second what position in the backing array our vertex
 * is at.
 *
 * Contains: O(1)
 *
 * Also, because we are using for this Prim's algorithm, we might have to update the weight
 * value of a given node, if we find a lesser weight edge. So there is a "decrease" operation
 * here. That will involve looking up the index of the vertex in the backing array, which will
 * happen in O(1). We can then update the weight of the given node in our backing array.
 * Because the value was updated, we must then "heapify" our heap to ensure the property
 * is maintained, which means looking, continuously, at most half of the nodes.
 *
 * Decrease: O(log n)
 *
 *
 *
 *
 * @param <T>
 */
public class MinHeap<T> {

    /**
     * An inner node representing the key and a weight. We will add
     * these nodes to our backing Array. The represent the data
     * we are storing in the heap.
     */
    class Node {
        int weight;
        T key;

        Node(T key, int weight) {
            this.key = key;
            this.weight = weight;
        }

        public String toString() {
            return String.format("key: %s, value: %s", key, weight);
        }
    }

    /* Our backing array list. We use an array list so that we can continuously expand it */
    private List<Node> backingArray = new ArrayList<>();

    /* Our backing map, which stores the index of every node in the backing array for quick look up */
    private Map<T, Integer> positionMap = new HashMap<>();

    /**
     * add - adds a node to our heap in O(log n) time.
     * Must maintain the heap property
     * @param key key
     * @param weight integer
     */
    public void add(T key, int weight) {
        Node current = new Node(key, weight);
        if (backingArray.size() == 0) {
            backingArray.add(current);
            positionMap.put(current.key, 0);
            return;
        }

        backingArray.add(current);
        int currentIndex = backingArray.size() - 1;
        positionMap.put(key, currentIndex);
        int parentIndex = (currentIndex - 1) / 2;

        while (parentIndex >= 0) {
            Node parent = backingArray.get(parentIndex);
            Node newlyAddedNode = backingArray.get(currentIndex);
            if (newlyAddedNode.weight < parent.weight) {
                swap(parent, newlyAddedNode);
                updatePositionMap(newlyAddedNode.key, currentIndex, parent.key, parentIndex);
                currentIndex = parentIndex;
                parentIndex = (parentIndex - 1)/2;
            } else {
                break;
            }
        }
    }

    /**
     * Considering we have to update the position of a node in the array, we must
     * also update the position of the node in our node position map.
     *
     * @param newNodeKey key of the node we just added
     * @param parentIndex the index of the node where are newly added node is going to.
     * @param parentKey the key of the parent that is about to move
     * @param currentIndex the index where the original parent is going
     */
    private void updatePositionMap(T newNodeKey, int parentIndex, T parentKey, int currentIndex) {
        positionMap.put(newNodeKey, parentIndex);
        positionMap.put(parentKey, currentIndex);
    }

    /**
     * Swaps the two nodes in the backing array, by first getting temp values of one of the nodes
     * and then copying them to the other node.
     * Doesn't actually swap the nodes in the array, just swaps the values of the nodes in the
     * array.
     * @param parent node to swap
     * @param recentlyAdded node to swap
     */
    private void swap(Node parent, Node recentlyAdded) {
        T tempKey = parent.key;
        int tempWeight = parent.weight;
        parent.key = recentlyAdded.key;
        parent.weight = recentlyAdded.weight;
        recentlyAdded.key = tempKey;
        recentlyAdded.weight = tempWeight;
    }

    /**
     * This method will return the minimum of the our minHeap, which is our element
     * at the root index, which is another way of saying its the element at the 0 index
     * of our backing array.
     *
     * It then has to ensure that the heap maintains its heap property. It works in a
     * similar way as adding a node to the heap. In this case, we remove the root, then
     * take the last node in the heap, and place it at the root.
     *
     * We then compare our "newRoot" with its children, pick the less of the two children
     * and swap them, and keep doing this until either the heap property is satisfied
     * or until there are no more child nodes to compare to.
     *
     * RUNTIME: O(log n)
     *
     * @return T element
     */
    public T extractMin() {
        if (backingArray.isEmpty()) {
            return null;
        }

        if (backingArray.size() == 1) {
            Node toReturn = backingArray.remove(0);
            positionMap.remove(toReturn.key);
            return toReturn.key;
        }

        /* Get the minimum node to return, the element at our root */
        Node minimum = backingArray.get(0);

        /* As we are extracting the minimum node remove it from the position array */
        positionMap.remove(minimum.key);

        /* Get the last element in the backing array */
        Node current = backingArray.remove(backingArray.size() - 1);

        /* Make it the new root and upate its position in the position map */
        backingArray.set(0, current);
        positionMap.put(current.key, 0);

        /* Set up our left and right child indexes */
        int parentIndex = 0;
        int leftChildIndex = (2 * parentIndex) + 1;
        int rightChildIndex = (2 * parentIndex) + 2;

        /**
         *  Check that left and right child indexes are within array to avoid out of
         *  bounds exception.
         */
        Node leftChild = leftChildIndex < backingArray.size() ? backingArray.get(leftChildIndex) : null;
        Node rightChild = rightChildIndex < backingArray.size() ? backingArray.get(rightChildIndex) : null;

        /**
         *  Because we populate the heap from left to right we only have to check for the
         *  left child, we will never have a null left child with a right child present.
         *  We keep performing this heapify operation until the heap property is maintained
         */
        while (leftChild != null) {
            current = backingArray.get(parentIndex);
            Node nextParent = null;
            int nextParentIndex = 0;
            if (leftChild != null && rightChild != null) {
                if (leftChild.weight < rightChild.weight) {
                    nextParent = leftChild;
                    nextParentIndex = leftChildIndex;
                } else {
                    nextParent = rightChild;
                    nextParentIndex = rightChildIndex;
                }

                if (nextParent.weight < current.weight) {
                    swap(current, nextParent);
                    updatePositionMap(current.key, parentIndex, nextParent.key, nextParentIndex);
                    parentIndex = nextParentIndex;
                    leftChildIndex = (2 * parentIndex) + 1;
                    rightChildIndex = (2 * parentIndex) + 2;
                    leftChild = leftChildIndex < backingArray.size() ? backingArray.get(leftChildIndex) : null;
                    rightChild = rightChildIndex < backingArray.size() ? backingArray.get(rightChildIndex) : null;
                } else {
                    break;
                }
            } else if (leftChild != null) {
                if (leftChild.weight < current.weight) {
                    swap(current, leftChild);
                    updatePositionMap(current.key, leftChildIndex, leftChild.key, parentIndex);
                    parentIndex = leftChildIndex;
                    leftChildIndex = (2 * parentIndex) + 1;
                    leftChild = leftChildIndex < backingArray.size() ? backingArray.get(leftChildIndex) : null;
                } else {
                    break;
                }
            }
        }
        return minimum.key;
    }

    public void decrease(T key, int weight) {
        Integer currentIndex = positionMap.get(key);
        Node updatedNode = backingArray.get(currentIndex.intValue());
        updatedNode.weight = weight;

        int parentIndex = (currentIndex - 1) / 2;
        while (parentIndex >= 0) {
            Node parent = backingArray.get(parentIndex);
            updatedNode = backingArray.get(currentIndex);
            if (updatedNode.weight < parent.weight) {
                swap(parent, updatedNode);
                updatePositionMap(updatedNode.key, currentIndex, parent.key, parentIndex);
                currentIndex = parentIndex;
                parentIndex = (parentIndex - 1)/2;
            } else {
                break;
            }
        }
    }

    public boolean contains(T key) {
        return positionMap.containsKey(key);
    }

    /**
     * Get the weight of given key
     */
    public Integer getWeight(T key) {
        Integer position = positionMap.get(key);
        if( position == null ) {
            return null;
        } else {
            return backingArray.get(position).weight;
        }
    }

    /**
     * See the minimum key without removing it, similar to peak.
     * @return key
     */
    public T seeMin() {
        return backingArray.get(0).key;
    }
    /**
     * isEmpty - return true if the backing array has no more elements,
     * false otherwise
     * @return boolean
     */
    public boolean isEmpty() {
        return backingArray.isEmpty();
    }

}
