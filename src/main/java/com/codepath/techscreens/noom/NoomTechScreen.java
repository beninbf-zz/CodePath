package main.java.com.codepath.techscreens.noom;

// Three types of responsibilities: personal coaching, group coaching, managing
// Two types of allocations: baseline, temporary
// Task: For a given coach and a date interval, calculate the percent of time spent working on each responsibility type

/**
 * Noom Tech Screen
 *
 * Domain Problem:
 *
 * Noom is a company that provides coaching services. They have hired coaches on staff who are used to help coach people
 * to lower weights. The following problem was a domain problem they had to solve regarding scheduling their coaches.
 *
 * A coach has 3 types of responsibilities: personal coaching, group coaching and managing other coaches.
 * A coach has 2 ways to allocate their time: a baseline allocation and a temporary allocation. A baseline allocation
 * represents what a coach will typically do every day they work, where they might allocate 50% of their time to
 * personal coaching and the other 50% to group coaching. Every once and a while a coach will be assigned a
 * temporary allocation for a given interval of time, say 1 day here, or a couple days there. During those days, they
 * will ignore their baseline allocation and only consider their temporary allocation.
 *
 * What we want to solve is a way to generate a report which describes how a coach, a coach with baseline and temporary
 * allocation, has allocated their time given an interval of time. An example is below
 *
 * For coach A, their baseline might be
 * Personal Coaching: 50%
 * Managing: 50%
 *
 * Coach A might be assigned a Temporary allocation from 2019-03-15 - 2019-03-15 (for one day) of
 * Managing: 100%
 *
 * If asked to generate a report for coach A for how they have allocated their time, from 2019-03-14 - 2019-03-05, the
 * report should look like:
 * Personal coaching: 25%
 * Managing: 75%
 *
 * How did we come to these values. Well the interval, from 3-14 - 3-15 spans two days. We can see that the temporary
 * allocation is only for 3-15. So on 3-14 coach A spent 50% of their time on personal coaching and 50% on managing.
 * We calculate that like so:
 *
 *                    Baseline  Temporary
 *                    3/14      3/15    total
 * personal coaching: 50        0       50/2 = 25
 * managing:          50        100     150/2 = 75
 *
 * For this problem we must figure out ways to model all of these objects and then create a method to generate
 * a report for a given coach.
 *
 * In doing this problem I struggled to model it, as I wasn't aware of the composition of the objects. In the future,
 * I should start not with objects composed of others, but instead, the smallest discernible objects and then build up
 * from there.
 *
 * So that means, starting with a Responsibility Class, where the responsibility class will have a type (personal,
 * group, or managing) and a percentage.
 *
 * Then an Allocation class, where an allocation class is made up of an AllocationType (baseline or temporary) and a
 * list of responsibilities.
 *
 * A Coach class, should then have a list of allocations, and a method named generate report, where the weight average
 * of how they allocated their time can be calculated
 */

public class NoomTechScreen {

}
