package main.java.com.codepath.graphs;

import main.java.com.codepath.objects.Vertex;
import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        ShortestPathWithKeysAndDoors obj = new ShortestPathWithKeysAndDoors();

        String[][] grid = {{".", ".", ".", "B"},
                           {".", "b", "#", "."},
                           {"@", "#", "+", "."}};

//        ArrayList<int[]> path = obj.findPath(grid);
//
//        for (int[] coordinates : path) {
//            System.out.println("[" + coordinates[0] + ", " + coordinates[1] + "]");
//        }
//
//        System.out.println(path.size());

        GraphProblems graphProblems = new GraphProblems();

        String[] dictionary = {"hot","dot","dog","lot","log"};
        List<String> path = graphProblems.wordLadder("hit", "cog", dictionary);

//        String[] dictionary = {"cat", "hat", "bad", "had"};
//        List<String> path = graphProblems.wordLadder("bat", "had", dictionary);

//
//        String[] dictionary = {"aaa"};
//        List<String> path = graphProblems.wordLadder("baa", "aab", dictionary);
//
//        String[] dictionary = {"cccw","accw","accc"};
//        List<String> path = graphProblems.wordLadder("cccc", "cccc", dictionary);
//

        Collections.reverse(path);

        String[] answer = new String[path.size()];
        answer = path.toArray(answer);

//        System.out.println(Util.charDifferenceOther1("baa", "aab"));
//        System.out.println(Util.charDifferenceOther1("hit", "hot"));
//        System.out.println(Util.charDifferenceOther1("hat", "had"));


        System.out.println(path);
    }
}
