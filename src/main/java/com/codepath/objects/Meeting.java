package main.java.com.codepath.objects;

public class Meeting {

    private int startTime;
    private int endTime;

    public Meeting(int startTime, int endTime) {
        // number of 30 min blocks past 9:00 am
        this.startTime = startTime;
        this.endTime   = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Meeting)) {
            return false;
        }
        final Meeting meeting = (Meeting) o;
        return startTime == meeting.startTime && endTime == meeting.endTime;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + startTime;
        result = result * 31 + endTime;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", startTime, endTime);
    }
}
