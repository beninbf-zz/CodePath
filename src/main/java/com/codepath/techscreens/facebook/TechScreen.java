package main.java.com.codepath.techscreens.facebook;

public class TechScreen {
    /**
     * Facebook Tech Screen
     *
     * We have a list of various types of tasks to perform. Task types are identified by number:
     * task type 1, task type 2, task type 3, etc.
     *
     * Each task takes 1 time slot to execute, and requires a cool down to recover before we can execute
     * another task of the same type.  However, we can execute tasks of other types in the meantime.
     * The recovery interval is the same for all task types.
     *
     * Given a list of input tasks to run, and the cool down interval, output the number of
     * time slots required to run them in the given order of the tasks.
     *
     * tasks = [1, 1, 2, 1]
     * cooldown = 2
     * Output: 7  (order is 1 _ _ 1 2 _ 1)
     *
     * I found this problem to be very difficult. My initial intiution on how to solve the problem
     * was incorrect. Initially I thought that, given the task that I was on, I had to check
     * the previous task type to see if it was of the same type, and if it was add the cool down
     * period to the total. This was incorrect of course. Checking the previous task type won't
     * help if the cool down period is large. The last task of the same type might be any number of
     * time slots away.
     *
     * This problem is a good exercise in learning strategies for how to break down a tricky
     * problem and solve it, when there is no obvious way to solve it, and when it clearly
     * doesn't map to any class of algorithms.
     *
     * The information given in the problem, that is a list of tasks, and that, there is a
     * cool down period, and that every task takes up at least one time slot, directly hint
     * that this problem is an array type of problem.
     *
     * I think that the strategy for learning how to do the problem, will probably come from
     * breaking the problem down into its simplest case, and building up from there. So
     * lets try that.
     *
     * Lets assume the cool down is 2
     * [1] => 1, the time is clearly one, as there is only one time slot
     * [1 2] => 2, the time is 2, because neither task is of the same type
     * [1,2,3] => 3, the same rationale as before
     *
     * [1,1] => 4, the time is +1 for doing the first take, then +2 for cool down period,
     * because the next task is of the same type, +1 again for the time to execute the
     * second task 1
     *
     * [1, 2, 1] => 4 (1, 2, _, 1). Again this is also 4, because the first task executes,
     * then the next task does, but we must wait one time slot because we can't execute
     * the next taskType 1 because its within the cool down period of the first one.
     *
     * This should give away how to do the problem. Anytime we execute a given task type,
     * we should check to see if there is another task type of the same type,
     * withing the given cool down period. All that means is if taskType 1 is
     * at position i, then check if there is another task type 1, within
     * [i + 1,...,i + 1 + cooldown). If there is, then calculate the extra time
     * that would be required. If a task type of the same type is found within
     * the cool down period, then we just check the magnitude of how far away
     * its from the beginning of the boundary (i + 1).
     *
     * If there is no task type found withing this cool down period, then
     * there is no extra time to add, because the other tasks can run
     * during the cool down period. here the time slot is the index
     * and the cooldown is merely the current position + the cool down.
     *
     * Given the format information is given to us for the problem,
     * task types in a list, and the fact they must be executed in order,
     * we must ask ourselves what the problem is telling us, and how the
     * format of the information can help us. if we are having trouble,
     * we should then break the problem down into its very simplest case
     * and slowly build up from their until the rationale for solving it
     * becomes clear.
     *
     * @param array int array
     * @param coolDown int
     * @return total number of time slots
     */
    public int getTimeToCompleteTasks(int[] array, int coolDown) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            count += 1;
            count += calculateRemainingTime(array, array[i], i + 1, coolDown);
        }
        return count;
    }

    private int calculateRemainingTime(int[] array, int taskType, int nextIndex, int coolDown) {
        for (int i = nextIndex; i < nextIndex + coolDown; i++) {
            if (i < array.length) {
                if (array[i] == taskType) {
                    return (nextIndex + coolDown) - i;
                }
            }
        }
        return 0;
    }
}
