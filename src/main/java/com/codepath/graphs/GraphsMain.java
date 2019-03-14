package main.java.com.codepath.graphs;

import main.java.com.codepath.objects.Cell;

import java.util.List;
import java.util.PriorityQueue;

public class GraphsMain {
    public static void main(String[] args) {

//        GraphProblems graphProblems = new GraphProblems();
//
//        String[] dictionary = {"hot","dot","dog","lot","log"};
//        List<String> path = graphProblems.wordLadder("hit", "cog", dictionary);

//        String[] dictionary = {"cat", "hat", "bad", "had"};
//        List<String> path = graphProblems.wordLadder("bat", "had", dictionary);

//
//        String[] dictionary = {"aaa"};
//        List<String> path = graphProblems.wordLadder("baa", "aab", dictionary);
//
//        String[] dictionary = {"cccw","accw","accc"};
//        List<String> path = graphProblems.wordLadder("cccc", "cccc", dictionary);


//        Collections.reverse(path);
//
//        String[] answer = new String[path.size()];
//        answer = path.toArray(answer);

//        System.out.println(Util.charDifferenceOther1("baa", "aab"));
//        System.out.println(Util.charDifferenceOther1("hit", "hot"));
//        System.out.println(Util.charDifferenceOther1("hat", "had"));


//        System.out.println(path);

        PriorityQueue<Integer> list = new PriorityQueue<>();

        list.add(21);
        list.add(3);
        list.add(233);
        list.add(1);



        while(!list.isEmpty()) {
            System.out.println(list.poll());
        }
    }
}
