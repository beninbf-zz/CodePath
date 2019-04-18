package main.java.com.codepath.techscreens.objects;

import java.util.ArrayList;
import java.util.List;

public class StudentCoursePair {
    public List<String> students;
    public List<String> classes;

    public StudentCoursePair() {
        this.students = new ArrayList<>();
        this.classes = new ArrayList<>();
    }

    public String toString() {
        return String.format("%s : %s", students, classes);
    }
}
