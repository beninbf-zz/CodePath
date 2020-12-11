package test.java.com.codepath.noom;

import main.java.com.codepath.techscreens.noom.Allocation;
import main.java.com.codepath.techscreens.noom.Coach;
import main.java.com.codepath.techscreens.noom.Responsibility;
import main.java.com.codepath.techscreens.noom.ResponsibilityType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class NoomTechScreenTest {

    @Test
    public void testNoomPersonalAllocation() throws Exception {

        Responsibility personal = new Responsibility(ResponsibilityType.PERSONAL_COACHING, 50);
        Responsibility managing = new Responsibility(ResponsibilityType.MANAGING, 50);
        Responsibility group = new Responsibility(ResponsibilityType.GROUP, 0);

        Responsibility temporaryPersonal = new Responsibility(ResponsibilityType.PERSONAL_COACHING, 0);
        Responsibility temporaryManaging = new Responsibility(ResponsibilityType.MANAGING, 100);
        Responsibility temporaryGroup = new Responsibility(ResponsibilityType.GROUP, 0);

        Allocation baseline = new Allocation(personal, managing, group);
        Allocation temporary = new Allocation(temporaryPersonal, temporaryManaging, temporaryGroup, 1, 1);

        Coach testObj = new Coach(baseline, Collections.singletonList(temporary));

        assertEquals("Personal Coaching should be 25", 25,
                testObj.generateResponsibilityAvg(ResponsibilityType.PERSONAL_COACHING, 0, 1));

        assertEquals("Managing Coaching should be 50", 75,
                testObj.generateResponsibilityAvg(ResponsibilityType.MANAGING, 0, 1));

        assertEquals("Group Coaching should be 0", 0,
                testObj.generateResponsibilityAvg(ResponsibilityType.GROUP, 0, 1));

       testObj.generateReport(0, 1);
    }

    @Test
    public void testNoomPersonalAllocation2() throws Exception {

        Responsibility personal = new Responsibility(ResponsibilityType.PERSONAL_COACHING, 75);
        Responsibility managing = new Responsibility(ResponsibilityType.MANAGING, 25);
        Responsibility group = new Responsibility(ResponsibilityType.GROUP, 0);

        Responsibility temporaryPersonal = new Responsibility(ResponsibilityType.PERSONAL_COACHING, 40);
        Responsibility temporaryManaging = new Responsibility(ResponsibilityType.MANAGING, 30);
        Responsibility temporaryGroup = new Responsibility(ResponsibilityType.GROUP, 30);
        Allocation temporary = new Allocation(temporaryPersonal, temporaryManaging, temporaryGroup, 2, 3);

        Responsibility temporaryPersonal1 = new Responsibility(ResponsibilityType.PERSONAL_COACHING, 10);
        Responsibility temporaryManaging1 = new Responsibility(ResponsibilityType.MANAGING, 50);
        Responsibility temporaryGroup1 = new Responsibility(ResponsibilityType.GROUP, 40);
        Allocation temporary1 = new Allocation(temporaryPersonal1, temporaryManaging1, temporaryGroup1, 5, 6);

        Allocation baseline = new Allocation(personal, managing, group);
        Coach testObj = new Coach(baseline, Arrays.asList(temporary, temporary1));

        assertEquals("Personal Coaching should be 46", 46,
                testObj.generateResponsibilityAvg(ResponsibilityType.PERSONAL_COACHING, 0, 6));

        assertEquals("Managing Coaching should be 50", 33,
                testObj.generateResponsibilityAvg(ResponsibilityType.MANAGING, 0, 6));

        assertEquals("Group Coaching should be 20", 20,
                testObj.generateResponsibilityAvg(ResponsibilityType.GROUP, 0, 6));

        testObj.generateReport(0, 6);
    }
}
