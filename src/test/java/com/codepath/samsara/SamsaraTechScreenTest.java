package test.java.com.codepath.samsara;

import main.java.com.codepath.techscreens.samsara.Pallet;
import main.java.com.codepath.techscreens.samsara.Trailer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SamsaraTechScreenTest {
    @Test
    public void testTrailerWeight() {
        Pallet pallet1 = new Pallet(1, 5);
        Pallet pallet2 = new Pallet(2, 6);
        Pallet pallet3 = new Pallet(3, 7);

        Trailer trailer = new Trailer();

        trailer.load(pallet1, 2);
        trailer.load(pallet2, 5);
        Pallet pallet = trailer.unload(1, 6);

        assertEquals("pallet id should be 1", 1, pallet.id);

        trailer.load(pallet3, 8);

        assertEquals("weight should be 13", 13, trailer.getWeight());
        assertEquals("weight at time 0: ", 0,trailer.weightAt(0));
        assertEquals("weight at time 1: ", 0, trailer.weightAt(1));
        assertEquals("weight at time 2: ", 5, trailer.weightAt(2));
        assertEquals("weight at time 3: ", 5,trailer.weightAt(3));
        assertEquals("weight at time 4: ", 5, trailer.weightAt(4));
        assertEquals("weight at time 5: ", 11, trailer.weightAt(5));
        assertEquals("weight at time 6: ", 6, trailer.weightAt(6));
        assertEquals("weight at time 7: ", 6, trailer.weightAt(7));
        assertEquals("weight at time 8: ", 13, trailer.weightAt(8));
    }

}
