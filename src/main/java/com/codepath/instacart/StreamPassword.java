package main.java.com.codepath.instacart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Stream passoword.
 */
public final class StreamPassword {

    /**
     * Stream password constructor
     */
    private StreamPassword() {}

    /**
     * HttpCall.
     * @param httpVerb String
     * @param uri String
     */
    public static void httpCall(String httpVerb, String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(httpVerb);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            PasswordCreator passwordCreator = new PasswordCreator();
            while ((inputLine = in.readLine()) != null) {

                /**
                 * The passwordCreator object is reading in the stream data
                 * initially its internal position field will be -1, so the first
                 * position stream information will set that field,
                 * with the next stream information, the location array, will be set,
                 * Subsequent Streams containing the password character array will
                 * be added to an internal list.
                 *
                 * When the passwordCreator then reads a new integer, denoting a new
                 * chunk, it will, grab the character from its internal "Chunk" instance
                 * and load it into a map.
                 *
                 * The logic for grabbing the correct character lives in the Chunk class
                 *
                 * This will keep happening until a collision is created with the internal
                 * instance of the password map in the location instance, at the point,
                 * we will break out of the loop and read the password.
                 *
                 * That will involve moving over the entries in the map and printing the
                 * characters based on their order.
                 */
                if (passwordCreator.isInteger(inputLine)) {
                    if (passwordCreator.getPosition() == -1) {
                        passwordCreator.setPosition(inputLine);
                    } else {
                        boolean isNotCollision = passwordCreator.processChunk();
                        if (isNotCollision) {
                            passwordCreator.setPosition(inputLine);
                        } else {
                            break;
                        }
                    }
                } else if (inputLine.contains(String.valueOf(PasswordCreator.OPEN_BRACKET))
                    && inputLine.contains(String.valueOf(PasswordCreator.COMMA))) {
                    passwordCreator.setLocation(inputLine);
                } else {
                    if (!inputLine.isEmpty()) {
                        passwordCreator.addCharRows(inputLine);
                    }
                }
            }
            in.close();
            System.out.println(passwordCreator.getPassword());
        } catch (Exception ex) {

        }
    }

    /**
     * Main method.
     * @param args String[]
     */
    public static void main(String[] args) {
        String TEST_URL = "https://enigmatic-plains-7414.herokuapp.com";
        httpCall("GET", TEST_URL);
    }
}
