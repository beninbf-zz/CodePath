package main.java.com.codepath.techscreens.sonder;

/**
 * Booking class. Use to create an object containing a checkIn and checkOut date.
 * Dates are in the form of an integer. Object should have been used
 * within the Sonder Tech Screen
 */
public class Booking {
    public int checkIn;

    public int checkOut;

    public Booking(String checkIn, String checkOut) {
        this.checkIn = Integer.parseInt(checkIn);
        this.checkOut = Integer.parseInt(checkOut);
    }

    @Override
    public String toString() {
        return "checkIn: "+checkIn+" checkOut:"+checkOut;
    }
}
