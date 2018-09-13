package main.java.com.codepath.trees;

import main.java.com.codepath.objects.TreeNode;

public class Main {
    public static void main(String[] args) {
//        TreeNode<Integer> root = new TreeNode<Integer>(5);
//        TreeNode<Integer> three = new TreeNode<Integer>(3);
//        TreeNode<Integer> six = new TreeNode<Integer>(6);
//        TreeNode<Integer> two = new TreeNode<Integer>(2);
//        TreeNode<Integer> four = new TreeNode<Integer>(4);
//
//        root.setLeft(three);
//        root.setRight(six);
//
//        three.setLeft(two);
//        three.setRight(four);
//
//        TreeProblems treeProblems = new TreeProblems();
//        System.out.println(treeProblems.countLeafNodes(root));
//
//        TreeNode<Integer> root1 = new TreeNode<Integer>(5);
//        TreeNode<Integer> three1 = new TreeNode<Integer>(3);
//        TreeNode<Integer> six1 = new TreeNode<Integer>(6);
//        TreeNode<Integer> two1 = new TreeNode<Integer>(2);
//        TreeNode<Integer> four1 = new TreeNode<Integer>(4);
//
//        root1.setLeft(three1);
//        root1.setRight(six1);
//
//        three1.setLeft(two1);
//        three1.setRight(four1);
//
//        TreeNode<Integer> root2 = new TreeNode<Integer>(5);
//        TreeNode<Integer> three2 = new TreeNode<Integer>(3);
//        TreeNode<Integer> seven2 = new TreeNode<Integer>(7);
//        TreeNode<Integer> two2 = new TreeNode<Integer>(2);
//        TreeNode<Integer> four2 = new TreeNode<Integer>(4);
//
//        TreeNode<Integer> six2 = new TreeNode<Integer>(6);
//
//        root2.setLeft(three2);
//        root2.setRight(seven2);
//
//        three2.setLeft(two2);
//        three2.setRight(four2);
//
//        seven2.setLeft(six2);
//
//        System.out.println(treeProblems.compareTrees(root, root1));
//        System.out.println(treeProblems.compareTrees(root, root2));
//
//        /* Path Sum */
//        TreeNode<Integer> psRoot5 = new TreeNode<Integer>(5);
//        TreeNode<Integer> psRoot4 = new TreeNode<Integer>(4);
//        TreeNode<Integer> psRoot8 = new TreeNode<Integer>(8);
//        TreeNode<Integer> psRoot11 = new TreeNode<Integer>(11);
//        TreeNode<Integer> psRoot13= new TreeNode<Integer>(13);
//        TreeNode<Integer> psRootOther4= new TreeNode<Integer>(4);
//        TreeNode<Integer> psRoot7= new TreeNode<Integer>(7);
//        TreeNode<Integer> psRoot2= new TreeNode<Integer>(2);
//        TreeNode<Integer> psRootOther5 = new TreeNode<Integer>(5);
//        TreeNode<Integer> psRoot1= new TreeNode<Integer>(1);
//
//        psRoot5.setLeft(psRoot4);
//        psRoot5.setRight(psRoot8);
//
//        psRoot4.setLeft(psRoot11);
//
//        psRoot8.setLeft(psRoot13);
//        psRoot8.setRight(psRootOther4);
//
//        psRoot11.setLeft(psRoot7);
//        psRoot11.setRight(psRoot2);
//
//        psRootOther4.setLeft(psRootOther5);
//        psRootOther4.setRight(psRoot1);
//
//        //System.out.println("PathSum="+treeProblems.pathSumWithBFS(psRoot5, 22));
//        //System.out.println("PathSum="+treeProblems.pathSumWithDfs(psRoot5, 22));
//
//        TreeProblems treeProblems = new TreeProblems();
//
//        System.out.println(treeProblems.pathWithDfsWrapper(psRoot5, 22));

                TreeProblems treeProblems = new TreeProblems();


        /* Path Sum */
        TreeNode<Integer> lvRoot5 = new TreeNode<Integer>(5);
        TreeNode<Integer> lvRoot4 = new TreeNode<Integer>(4);
        TreeNode<Integer> lvRoot4_1 = new TreeNode<Integer>(4);
        TreeNode<Integer> lvRoot4_2 = new TreeNode<Integer>(4);
        TreeNode<Integer> lvRoot5_1 = new TreeNode<Integer>(5);
        TreeNode<Integer> lvRoot5_2 = new TreeNode<Integer>(5);
        TreeNode<Integer> lvRoot5_3 = new TreeNode<Integer>(5);
        TreeNode<Integer> lvRoot5_4 = new TreeNode<Integer>(5);

        lvRoot5.setLeft(lvRoot4);
        lvRoot5.setRight(lvRoot5_1);

        lvRoot4.setLeft(lvRoot4_1);
        lvRoot4.setRight(lvRoot4_2);


        lvRoot5_1.setRight(lvRoot5_2);

        lvRoot5_2.setLeft(lvRoot5_3);
        lvRoot5_2.setRight(lvRoot5_4);



        System.out.println(treeProblems.longestUniValue(lvRoot5));

        System.out.println(treeProblems.findLongestPath(lvRoot5));

        System.out.println(treeProblems.leetCodeSolution(lvRoot5));




    }
}
