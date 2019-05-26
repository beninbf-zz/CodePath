package main.java.com.codepath.techscreens.objects;

/**
 * Square tech screen, code the connect 4 game. Need to complete the diagonal checks!
 */
public class ConnectFour {
    private Integer[][] board;

    public ConnectFour() {
        this.board = new Integer[6][7];
    }

    public void print() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("[");
            for(int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("]\n");
        }
    }

    public boolean play(Integer disc, int column) {

        if (disc == null) {
            return false;
        }
        if (column < 0 || column > board[0].length) {
            return false;
        }

        if (board[0][column] != null) {
            return false;
        }

        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][column] == null) {
                board[i][column] = disc;
                return verticalWin(i, column) || horizontalWin(i, column);
            }
        }
        return false;
    }

    public boolean verticalWin(int row, int col) {
        Integer disc = board[row][col];
        int count = 0;
        for (int i = row; i < board.length; i++) {
            if (board[i][col].intValue() == disc.intValue()) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean horizontalWin(int row, int col) {
        Integer disc = board[row][col];
        int likeDiscsToTheRight = 0;
        // check to the right
        for (int i = col + 1; i < board[0].length; i++) {
            if (col + 1 < board[0].length) {
                if (board[row][i] != null && board[row][i].intValue() == disc.intValue()) {
                    likeDiscsToTheRight++;
                }
            } else {
                break;
            }
        }

        //System.out.println("likeDiscsToTheRight:" + likeDiscsToTheRight);

        // check to the left
        int leftDiscs = 4 - likeDiscsToTheRight;
        for (int i = col; i >= 0; i--) {
            if (board[row][i] != null && board[row][i].intValue() == disc.intValue()) {
                leftDiscs--;
                if (leftDiscs == 0 ) {
                    return true;
                }
            } else {
                return false;
            }
        }

        return false;
    }
}
