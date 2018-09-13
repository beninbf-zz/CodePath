package main.java.com.codepath.recursion.towerofhanoi;
import java.util.*;

/**
 * When in Eclipse the game be played by hitting the run button. The user
 * will be prompted to enter in a number of Discs, then hit enter.
 * If run in Unix just compile, and then type: java Hanoi at the command line, then
 * the user will be prompted. Be aware of the package up above when running.
 */
public class Hanoi
{
    // My game board.
    public static Stack<Disc>[] board =  new Stack[3];

    // Vector I use to iterate through my Discs, making a decision on each one.
    public static Vector<Disc> Discs =  new Vector<Disc>();

    // Use as a frame of reference point, for moving along the board.
    public static int i = 0;

    // Count the number of moves.
    public static int moves = 0;

    // Test the weight of the disc against another so that in can decide if it can move.
    public static boolean testWeight(Disc d) {
        if((board[i+d.dest].peek()).weight < (board[i + d.curr].peek()).weight ) {
            return false;
        }
        return true;
    }

    // Moves the disc.
    public static void move(Disc d) {
        board[i + d.dest].push(d);
        board[i + d.curr].pop();
    }

    // Tells me if the disc is on top of the stack at the particular peg.
    public static boolean isOnTop(Disc d)
    {
        if( board[d.curr].search(d) == 1) {
            return true;
        }
        return false;
    }

    // If the destination of our disc has a disc that is of a lower weight
    // we must check the next available peg, and see if it is empty before we move.
    public static boolean nextStop(Disc d, Disc next) {
        int position = 0;
        position = d.dest + next.curr;

        // So we don't move off of the board.
        if(position > 3) {
            return true;
        }

        if(!board[i + position].empty()) {
            return true;
        } else {
            d.dest = position;
            return false;
        }
    }

    // Deciding if the disc we are looking at can move in regards to the weights
    // and position of discs on the board.
    public static boolean canMove(Disc d)
    {
        int x = d.dest;
        if(!board[i+x].empty()) {
            Disc next = (board[i+x].peek());
            if( testWeight(d)) {
                move(d);
                moves++;
                System.out.println("MOVE: Disc " + d.weight + " to peg" + d.dest);
                return true;
            } else if(nextStop(d,next)) {
                if(testWeight(d)) {
                    move(d);
                    moves++;
                    System.out.println("MOVE: Disc " + d.weight + " to peg" + d.dest);
                    return true;
                } else if(nextStop(d,next)) {
                    return false;
                } else {
                    return false;
                }
            } else {
                move(d);
                moves++;
                System.out.println("MOVE: Disc " + d.weight + " to peg" + d.dest);
                return true;
            }
        } else {
            move(d);
            moves++;
            System.out.println("MOVE: Disc " + d.weight + " to peg" + d.dest);
            return true;
        }
    }

    // This is the framework of the game
    public static void towerOfHanoi(int numbDiscs)
    {
        // If all of the discs are on the last peg, then return.
        if(board[2].size() == numbDiscs) {
            System.out.println("Game won");
            return;
        }

        // Move through the vector of the discs one at a time and make a decision.
        for(Disc d: Discs) {
            Thread myThread = new Thread();
            try {
                myThread.sleep(250);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int whereAt = d.curr;

            if(isOnTop(d)) {
                if(canMove(d)){
                    d.upDate(whereAt);
                }
            }
        }

        towerOfHanoi(numbDiscs);
    }

    public static void main(String[] args)
    {
        System.out.print("Please enter in the number of Discs: ");
        //int numbD = Integer.parseInt(args[0]);

        Scanner sc = new Scanner(System.in);
        int numbD = sc.nextInt();

        System.out.println("Number of Discs you entered: " + numbD);

        // populate our Disc vector
        for(int i = 1; i <= numbD; i++) {
            Discs.add(new Disc(i,0,0,1));
        }

        // If the number of discs is even, set the destination of the lightest disc
        // to peg 1, if the number is odd, set the number to 3.
        if(Discs.size()%2 == 0) {
            (Discs.elementAt(0)).setDest(1);
        } else {
            (Discs.elementAt(0)).setDest(2);
        }

        // My pegs for the game are stacks, that will go on a board
        Stack<Disc> peg0 = new Stack<Disc>();
        Stack<Disc> peg1 = new Stack<Disc>();
        Stack<Disc> peg2 = new Stack<Disc>();

        for(int i = 0; i < Discs.size(); i++) {
            peg0.push(Discs.elementAt(Discs.size() - i - 1));
        }

        // Putting all pegs on to the board. Pegs are indexed starting at zero.
        board[0] = peg0;
        board[1] = peg1;
        board[2] = peg2;

        towerOfHanoi(Discs.size());
        for(int i = 0; i < board.length; i++) {
            System.out.println("discs at peg"+i+" : " + board[i].size());
        }

        System.out.println("Disc on top of stack at the third peg is: " + (board[2].peek()).weight);
        System.out.println("the total number of moves is: " + moves);

    }
}

