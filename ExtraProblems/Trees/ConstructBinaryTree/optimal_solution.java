import java.io.*;
import java.util.*;

class Solution {

    public static void main(String[] args) throws IOException{
        // TODO Auto-generated method stub
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int arr_len = Integer.parseInt(sc.readLine());

        int inorder [] = new int[arr_len];
        int preorder [] = new int[arr_len];

        for(int i = 0 ; i < arr_len ; i++) {
            inorder[i] = Integer.parseInt(sc.readLine());
        }

        for(int i = 0 ; i < arr_len ; i++) {
            preorder[i] = Integer.parseInt(sc.readLine());
        }

        TreeNode root = constructBinaryTree(inorder, preorder);
    }


    // ---------------------------- Start -------------------------------
    
    static int current_index;

    
    public static TreeNode constructBinaryTree(int inorder[], int preorder[]) {

        int arr_len = inorder.length;
        
        /* HashMap to store values of 'inorder[]' with index. So, here key and value pair 
            of hashmap will be value and index pair of 'inorder[]'. */
        
        HashMap<Integer, Integer> value_hash_inorder = new HashMap();
        
        // Storing value and index pair of 'inorder[]' in hashmap.

        for(int i = 0 ; i<arr_len ; i++) {
            value_hash_inorder.put(inorder[i], i);
        }

        current_index = 0;

        return utilConstructBinaryTree(inorder, preorder, 0, arr_len - 1, value_hash_inorder);

    }
    
    /* Recursive function to construct a binary tree using Inorder traversal 'inorder[]'
       and Preorder traversal 'preorder[]'. Initial value of 'start' and 'end' should be '0' 
       and 'arr_len-1'*/

    // Initially 'current_index' should be '0'.

    /* Here, 'start' and 'end' values indicate search range in 'inorder[]' for value at index 
       'current_index' of 'preorder[]'.*/

    // Create 'root' node of value at index 'current_index' of 'preorder[]'.

    /* If value found at index 'x' in 'inorder[]', it means values of range [start, x] of 'inorder[]' will 
       lie in left subtree of root node and values of range [x, end] of 'inorder[]' will lie 
       in right subtree of root node.*/

    public static TreeNode utilConstructBinaryTree(
            int inorder[], int preorder[], int start, 
            int end, HashMap<Integer,Integer> value_hash_inorder) {

        if(start>end)
            return null;

        /* Pick current node from Preorder traversal using current_index 
        and increment current_index */
        
        TreeNode root = new TreeNode(preorder[current_index++]);
        
        /* If above node has no children then return */

        if(start == end) {
            return root;
        }

        /* Else find the index of this node in Inorder traversal */
        int index_of_inorder = value_hash_inorder.get(root.value);
        
        root.left = utilConstructBinaryTree(
                inorder, preorder, start, index_of_inorder - 1, value_hash_inorder);
        root.right = utilConstructBinaryTree(
                inorder, preorder, index_of_inorder + 1, end, value_hash_inorder);

        return root;
    }
    
    // ---------------------------- End -------------------------------

}

class TreeNode {
    
    int value;
    TreeNode left,right;
    
    TreeNode(int value) {
        this.value = value;
        left = null;
        right = null;
    }

}
