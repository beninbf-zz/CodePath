package main.java.com.codepath.recursion;

public class Main {
    public static void main(String[] args) {
        RecursionProblems recursionProblems = new RecursionProblems();
        Permutations permutations = new Permutations();

//        String test = "test";
//
//        System.out.println(test.substring(0, 0));
//        System.out.println(test.substring(0, test.length()));
//
        //System.out.println(recursionProblems.getSubStrings("12345"));
//
        //System.out.println(recursionProblems.getSubStringRecursive("12345"));

        //System.out.println(recursionProblems.nChooseK(23, 3));
        //System.out.println(recursionProblems.nChooseKMemoization(23, 3));

        int n = 123;
        int k = 4;

//        CountingObject countingObject = new CountingObject();
//        CountingObject countingObject2 = new CountingObject();
//
//        System.out.println(String.format("Value of n choose k for n=%s and k=%s is [%s], recursive count is [%s]", n, k,
//            recursionProblems.nChooseKWithCount(n, k, countingObject), countingObject.getCount()));
//
//        System.out.println(String.format("Value of n choose k for n=%s and k=%s is [%s], memoziation recursive count is [%s]", n, k,
//            recursionProblems.nChooseKMemoizationWithCount(n, k, countingObject2), countingObject2.getCount()));
//
//
//        System.out.println(recursionProblems.returnBinaryNumbers(3));
//        recursionProblems.printBinaryNumbers(3);
//
//        System.out.println(recursionProblems.fibonnaci(10));
//        System.out.println(recursionProblems.fibonnaciIter(10));

        //recursionProblems.printDecimalNumbers(2);

        //recursionProblems.diceRoll(2);
        //recursionProblems.diceSum(3, 7);

        //recursionProblems.printBinary(3);
        //recursionProblems.rollingDiceChooseSum(3, 7);

        //String word = "abc";
        //int length = word.length();
        //word.substring(0, word.length() -1);
//        System.out.println(word.length());
//        System.out.println(word.substring(1, length));
//        System.out.println(word.substring(0, 1));

        //recursionProblems.permutationsMyWay("abc");
        //permutations.permutations(word);

        int[] array = {2, 10, 20};

        //System.out.println(recursionProblems.findAllWellFormedBrackets(2));
        System.out.println(recursionProblems.subSetSum(array, 0));
    }
}
