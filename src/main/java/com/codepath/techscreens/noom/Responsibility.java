package main.java.com.codepath.techscreens.noom;

enum ResponsibilityType {
    PERSONAL_COACHING, GROUP, MANAGING
}

public class Responsibility {

    public int getPercentage() {
        return percentage;
    }

    public ResponsibilityType getType() {
        return type;
    }

    private final int percentage;
    private final ResponsibilityType type;

    public Responsibility(ResponsibilityType type, int percentage) {
        this.type = type;
        this.percentage = percentage;
    }
}
