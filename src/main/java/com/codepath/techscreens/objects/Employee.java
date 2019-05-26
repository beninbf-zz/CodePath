package main.java.com.codepath.techscreens.objects;

public class Employee {
    public Integer id = null;
    public String name = null;
    public Integer managerId = null;

    public Employee(String input) {
        String[] data = input.split(":");
        this.id = Integer.valueOf(data[0]);
        this.name = data[1];
        this.managerId = Integer.valueOf(data[2]);
    }

    public String toString() {
        return String.format("%s", this.name);
    }
}
