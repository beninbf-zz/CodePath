package main.java.com.codepath.instacart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Password Creator.
 */
public class PasswordCreator {
    public static final char OPEN_BRACKET = '[';

    public static final char CLOSE_BRACKET = ']';

    public static final String COMMA = ",";

    private int position = -1;

    private String location = null;

    private Map<Integer, Character> passwordMap = new HashMap<Integer, Character>();

    private List<String> charRows;

    /**
     * Password creator.
     */
    public PasswordCreator() {
        this.charRows = new ArrayList<String>();
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(String position) {
        this.position = Integer.valueOf(position);
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Add char rows.
     *
     * @param row the row
     */
    public void addCharRows(String row) {
        this.charRows.add(row);
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Gets character.
     *
     * @return the character
     */
    public boolean processChunk() {
        Chunk chunk = new Chunk(this.position, getArray(this.location), this.charRows);
        Character character = new Character(chunk.getCharacter());
        if (!passwordMap.containsKey(chunk.getPositionInPassword())) {
            passwordMap.put(chunk.getPositionInPassword(), character);
        } else {
            // start creating new chunk, clear out current map
            return false;
        }
        return true;
    }

    /**
     * Is integer boolean.
     *
     * @param content the content
     * @return the boolean
     */
    public boolean isInteger(String content) {
        try {
            Integer.valueOf(content);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }

    private int[] getArray(String input) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == OPEN_BRACKET || input.charAt(i) == CLOSE_BRACKET) {
                continue;
            } else {
                sb.append(input.charAt(i));
            }
        }

        int[] locationArray = new int[2];
        String[] numbers = sb.toString().split(COMMA);
        for (int i = 0; i < numbers.length; i++) {
            locationArray[i] = Integer.valueOf(numbers[i].trim());
        }
        return locationArray;
    }

    public String getPassword() {
        StringBuffer sb = new StringBuffer();
        if (!this.passwordMap.isEmpty()) {
            int i = 0;
            while (this.passwordMap.containsKey(i)) {
                sb.append(this.passwordMap.get(i));
                i++;
            }
        }
        return sb.toString();
    }
}
