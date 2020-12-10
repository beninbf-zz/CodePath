package test.java.com.codepath.darkstore;

import main.java.com.codepath.techscreens.darkstore.TechScreen;
import main.java.com.codepath.techscreens.darkstore.Position;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

public class DarkStoreTechScreen {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testDarkStore_GetPositions() {
        Position[] AMZN = new Position[]{new Position(LocalDate.parse("2019-04-01"), 200),
                new Position(LocalDate.parse("2019-04-04"), 400)};
        Position[] GOOG = new Position[]{new Position(LocalDate.parse("2019-04-02"), 500),
                new Position(LocalDate.parse("2019-04-03"), 700)};
        Position[] MSFT = new Position[]{new Position(LocalDate.parse("2019-04-01"), 400),
                new Position(LocalDate.parse("2019-04-02"), 200),
                new Position(LocalDate.parse("2019-04-04"), 600)};

        Position[][] portfolio = new Position[][]{AMZN, GOOG, MSFT};
        Position[] totals = testObj.getTotalsFromPortfolio(portfolio);

        System.out.println(Arrays.toString(totals));

        Position[] YAHOO = new Position[]{new Position(LocalDate.parse("2019-04-02"), 200),
                new Position(LocalDate.parse("2019-04-04"), 300)};
        Position[] APPL = new Position[]{new Position(LocalDate.parse("2019-04-02"), 200),
                new Position(LocalDate.parse("2019-04-03"), 100)};
        Position[] GE = new Position[]{new Position(LocalDate.parse("2019-04-01"), 500),
                new Position(LocalDate.parse("2019-04-03"), 700)};


        Position[][] portfolio1 = new Position[][]{YAHOO, APPL, GE};
        Position[] moreTotals = testObj.getTotalsFromPortfolio(portfolio1);

        System.out.println(Arrays.toString(moreTotals));
    }
}
