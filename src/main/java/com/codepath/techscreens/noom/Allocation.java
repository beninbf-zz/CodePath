package main.java.com.codepath.techscreens.noom;

enum AllocationType {
    BASELINE, TEMPORARY
}
public class Allocation {
    public AllocationType getType() {
        return type;
    }

    public Responsibility[] getResponsibilities() {
        return responsibilities;
    }

    private final AllocationType type;
    private final Responsibility[] responsibilities;

    public Allocation(Responsibility[] responsibilities) {
        this.type = AllocationType.BASELINE;
        this.responsibilities = responsibilities;
    }

    public Allocation(Responsibility[] responsibilities, AllocationType type) {
        this.type = AllocationType.TEMPORARY;
        this.responsibilities = responsibilities;
    }
}
