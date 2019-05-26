package main.java.com.codepath.instacart;

import java.util.ArrayList;
import java.util.List;

/**
 * Chunk.
 */
public class Chunk {
    private int positionInPassword;

    private int[] charLocation;

    private char[][] charMatrix;

    /**
     * Chunk constructor.
     * @param positionInPassword int
     * @param charLocation int[]
     * @param charRows List
     */
    public Chunk(int positionInPassword, int[] charLocation, List<String> charRows) {
        this.positionInPassword = positionInPassword;
        this.charLocation = charLocation;
        buildCharMatrix(new ArrayList<>(charRows));
    }

    /**
     * Position in password.
     * @return int
     */
    public int getPositionInPassword() {
        return positionInPassword;
    }

    private void buildCharMatrix(List<String> charRows) {
        int length = charRows.size();
        this.charMatrix = new char[length][charRows.get(0).length()];
        for (int i = 0; i < length; i++) {
            this.charMatrix[i] = charRows.get(i).toCharArray();
        }
    }

    /**
     * Get char location.
     * @return int[]
     */
    public int[] getCharLocation() {
        return charLocation;
    }

    /**
     * Get character.
     * @return char
     */
    public char getCharacter() {
        return charMatrix[charMatrix.length - 1 - charLocation[1]][charLocation[0]];
    }

    @Override
    public String toString() {
        return String.format("location: %s coord: $s, %s", positionInPassword, charLocation[0], charLocation[1]);
    }
}
