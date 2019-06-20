package main.java.com.codepath.objects;

public class UserClick {
    public String source;
    public String destination;

    public UserClick(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }
    public String toString(){
        return String.format("%s -> %s", this.source, this.destination);
    }
}
