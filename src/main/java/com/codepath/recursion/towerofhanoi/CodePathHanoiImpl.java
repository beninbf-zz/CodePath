package main.java.com.codepath.recursion.towerofhanoi;
import main.java.com.codepath.recursion.CountingObject;

/**
 * CodePathHanoiImpl - This class is an implementation of the CodePathHanoi Interface that was provided
 * by Code Path. Its an implementation for the Tower of Hanoi Game.
 *
 * The key thing to remember is what our base case is. Which is that when n is 1, we are always moving
 * from our src to our destination. To do that everytime, the twist to this problem is that, we simply
 * reset (using recursion) which peg is our actual src and destination.
 *
 * Again, remember with recursion that solving for our base case allows us to solve for other sub
 * problems. And soliving for sub problems allows us to solve for other sub problems.
 *
 * In other words solving for f(1) (our base case) can lead us to our solution for f(2). Solving for
 * for f(2), then allows us to solve for f(3), so on and so on. Our solution for f(3) is composed of our
 * solutions of f(2) and f(1).
 *
 */
public class CodePathHanoiImpl implements CodePathHanoi {

    public void game(int n, String src, String aux, String dest, Move[] moves, CountingObject counter) {
        // System.out.println(String.format("f(%s)", n));
        if (n == 1) {
            moves[counter.getCount()] = Move.valueOf(src+dest);
            counter.incrementCount();
            return;
        } else {
            game(n - 1, src, dest, aux, moves, counter);
            game(1 , src, aux, dest, moves, counter);
            game(n - 1, aux, src, dest, moves, counter);
        }
        return;
    }

    public Move[] solve(int n) {
        Double numberOfMoves = Math.pow(2, n) - 1.0d;
        Move[] moves = new Move[numberOfMoves.intValue()];
        game(n, "A", "B", "C", moves, new CountingObject());
        return moves;
    }

    public static void print(Move[] array, int n) {
        System.out.println(String.format("Moves for a game with %s discs", n));
        for(Move m: array) {
            System.out.print(String.format("%s ", m.toString()));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 1;
        CodePathHanoi codePathHanoi = new CodePathHanoiImpl();
        print(codePathHanoi.solve(n), n);

        n = 2;
        print(codePathHanoi.solve(n), n);

        n = 3;
        print(codePathHanoi.solve(n), n);

        n = 4;
        print(codePathHanoi.solve(n), n);

    }
}
