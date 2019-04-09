package main.java.com.codepath.techscreens.objects;

public class Person {

    public String firstName;
    public String lastName;
    public Integer age;
    public Person parent;

    public String[] lastFiveOccupations = new String[]{"Engineer", "Doctor", "Lawyer"};

    public Person(String firstName, String lastName, int age, Person parent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.parent = parent;
    }
}
