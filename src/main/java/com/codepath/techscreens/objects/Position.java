package main.java.com.codepath.techscreens.objects;

import java.time.LocalDate;

public class Position {
    private LocalDate date;

    private Integer value;

    public Position(LocalDate date, Integer value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", this.date, this.value);
    }
}