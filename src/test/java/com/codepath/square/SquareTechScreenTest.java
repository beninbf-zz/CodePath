package test.java.com.codepath.square;

import main.java.com.codepath.techscreens.square.ConnectFour;
import org.junit.Test;

public class SquareTechScreenTest {
    @Test
    public void testConnectFour() {
        ConnectFour board = new ConnectFour();
        Integer PLAYER_ONE = 1;
        Integer PLAYER_TWO = 2;

        // vertical
        System.out.println(board.play(PLAYER_ONE, 3));
        System.out.println(board.play(PLAYER_ONE, 3));
        System.out.println(board.play(PLAYER_ONE, 3));
        System.out.println(board.play(PLAYER_ONE, 3));

        // horizontal
        System.out.println(board.play(PLAYER_ONE, 1));
        System.out.println(board.play(PLAYER_ONE, 2));
        System.out.println(board.play(PLAYER_ONE, 3));
        System.out.println(board.play(PLAYER_ONE, 4));

        //Need complete diagonal tests

        board.print();
    }
}
