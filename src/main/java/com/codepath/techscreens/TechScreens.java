package main.java.com.codepath.techscreens;

import main.java.com.codepath.techscreens.objects.Booking;

public class TechScreens {

    /**
     * This problem was given during a tech screening. The company was call Sonder.
     * An Airbnb type company. The problem statement below is given a series of bookings
     * in the form of "4:8 19:35 80:160", a current_date, i.e. 4, and stay_length, i.e. 1
     * return the first available day of booking.
     *
     * What gave me problems during this problem was the fact that I would have to map the current
     * date to the bookings. To do that, I would, in some cases, need to check the checkIn date
     * of the very next booking.
     *
     * During the tech screen I neglected to create a Booking Object, and instead just tried to
     * parse strings and string arrays which makes it difficult to see the checkIn Date of the
     * "next" booking.
     *
     * I was struggling describing my solution because I was doing so withing the constraints
     * of the String array, where the next booking, needed to first be split, before I could check
     * the checkIn date.
     *
     * The take away from this, is to bend the problem to my algorithm solution by transforming it
     * some how. I should have just made a Booking Object, and then create a list of bookings
     * to iterate through.
     *
     * @param bookingsInput string of bookings where integer is day "4:8 19:35 80:160"
     * @param current_date int
     * @param stay_length int
     * @return first available day
     */
    public int getDate(String bookingsInput, int current_date, int stay_length) {
        String[] array = bookingsInput.split(" ");
        Booking[] bookings = new Booking[array.length];
        for (int i = 0; i < array.length; i++) {
            String[] booking = array[i].split(":");
            bookings[i] = new Booking(booking[0], booking[1]);
        }

        int bookingToStart = getBookingToStartSearch(bookings, current_date);
        if (bookingToStart == bookings.length - 1) {
            return bookings[bookingToStart].checkOut;
        }

        return getFirstAvailableDay(bookings, bookingToStart, current_date, stay_length);
    }

    /**
     * We need to be able to find the booking date from which our start will commence.
     * That is exactly what this logic does.
     *
     * @param bookings string a booking, i.e. "0:2"
     * @param current_date the current date the customer is looking for bookings
     * @return index of the booking
     */
    private int getBookingToStartSearch(Booking[] bookings, int current_date) {
        for (int i = 0; i < bookings.length; i++) {
            Booking booking = bookings[i];
            /* if the current date is neatly within the bounds of a booking, then simply return that booking*/
            if (current_date >= booking.checkIn && current_date <= booking.checkOut) {
                return i;
            } else { /* if its not within a booking then find the booking that immediately preceeds the current date*/
                if (i + 1 < bookings.length && current_date >= booking.checkOut && current_date <= bookings[i + 1].checkIn) {
                    return i;
                }
            }
        }
        /* if we iterate through the entire array, then that means the current date is outside of the bounds of the bookings
        *  so we can just return the last booking.
        * */
        return bookings.length - 1;
    }

    /**
     * Now that we know the booking to start from, we can walk through the booking array, searching for the first
     * available date that will accommodate our stay.
     *
     * @param bookings array of Booking objects
     * @param bookingToStart the booking from which we are beginning or search
     * @param current_date the current date
     * @param stay_length the length of the stay
     * @return int the first available day that the customer can book
     */
    private int getFirstAvailableDay(Booking[] bookings, int bookingToStart, int current_date, int stay_length) {
        for (int i = bookingToStart; i < bookings.length; i++) {
            int entireStay = current_date + stay_length;
            // we need to be able to check the current booking and the next one, so this is a boundary check
            if ((i + 1) < bookings.length) {
                // if the entire stay is sandwiched between two bookings then we can just return the current date
                if (current_date >= bookings[i].checkOut && entireStay <= bookings[i + 1].checkIn) {
                    return current_date;
                } else if (entireStay >= bookings[i + 1].checkIn) { // if the entire stay overlaps into the booking then "walk" up to the checkout of the next booking
                    current_date = bookings[i + 1].checkOut;
                } else {
                    return bookings[i].checkOut;
                }
            }
        }
        return bookings[bookings.length  - 1].checkOut;
    }
}
