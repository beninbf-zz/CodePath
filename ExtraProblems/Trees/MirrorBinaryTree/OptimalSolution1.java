import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;


class Result {

    /*
    * Complete the 'mirror_image' function below.
    *
    * The function accepts root node of binary tree as parameter.
    */
    // ============================ Start ============================
    public static void mirror_image(TreeNode root) {
        root = mirror_image_util(root);
    }

    public static TreeNode mirror_image_util(TreeNode root){
        if (root == null)
            return root;

        /* do the subtrees */
        TreeNode left = mirror_image_util(root.left);
        TreeNode right = mirror_image_util(root.right);

        /* swap the left and right pointers */
        root.left = right;
        root.right = left;
        return root;
    }
    // ============================= End ==============================
}

class Solution {
    public static void main(String args[]) {
        /*
        This function is used to increase the size of recursion stack. It makes the size of stack
        2^26 ~= 10^8
        */
        new Thread(null, new Runnable() {
            public void run() {
                try{
                    solve();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 26).start();
    }
    public static void solve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());// n = Number of vertices
        int root_index = Integer.parseInt(bufferedReader.readLine().trim());// root_index = index of root vertex
        List<List<Integer>> edges = new ArrayList<>();// To store edges

        IntStream.range(0, n).forEach(i -> {
            try {
                edges.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        bufferedReader.close();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        TreeNode root = build_tree(n, root_index, edges);
        TreeNode mirror_root = build_tree(n, root_index, edges);
        Result.mirror_image(mirror_root);
        if(!is_mirror_image(root, mirror_root)) {
            bufferedWriter.write("Not a mirror image");
            bufferedWriter.newLine();
        }
        print_level_order(n, mirror_root, bufferedWriter);
        bufferedWriter.close();
    }

    public static TreeNode build_tree(int n, int root_index, List<List<Integer>> edges){
        if(n==0) {
            return null;
        }
        TreeNode nodes[] = new TreeNode[n];
        for(int i=0;i<n;i++) {
            nodes[i] = null;
        }
        nodes[root_index] = new TreeNode(root_index);
        for(int i=0;i<n;i++) {
            List<Integer> edge = edges.get(i);
            int vertex = edge.get(0);
            int left = edge.get(1);
            int right = edge.get(2);
            TreeNode cur_node = nodes[vertex];

            if(cur_node==null) {
                cur_node = new TreeNode(vertex);
            }
            if(left!=-1) {
                if(nodes[left]==null){
                    nodes[left] = new TreeNode(left);
                }
                cur_node.left = nodes[left];
            }
            if(right!=-1) {
                if(nodes[right]==null){
                    nodes[right] = new TreeNode(right);
                }
                cur_node.right = nodes[right];
            }
            nodes[vertex] = cur_node;
        }
        return nodes[root_index];
    }

    public static boolean is_mirror_image(TreeNode root, TreeNode mirror_root){
        if(root==null && mirror_root==null) {
            return true;
        }
        if(root==null || mirror_root==null) {
            return false;
        }
        if(root.data!=mirror_root.data) {
            return false;
        }
        boolean left_result = is_mirror_image(root.left, mirror_root.right);
        boolean right_result = is_mirror_image(root.right, mirror_root.left);

        return left_result && right_result;
    }

    public static void print_level_order(int n, TreeNode root, BufferedWriter bufferedWriter) throws IOException{
        if(root==null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        int left_data = -1;
        int right_data = -1;

        while(!queue.isEmpty()) {
            TreeNode cur_node = queue.poll();
            left_data = -1;
            right_data = -1;

            if(cur_node.left!=null) {
                queue.add(cur_node.left);
                left_data = cur_node.left.data;
            }
            if(cur_node.right!=null) {
                queue.add(cur_node.right);
                right_data = cur_node.right.data;
            }
            bufferedWriter.write(cur_node.data+" "+left_data+" "+right_data+"\n");
        }
    }
}
