package main.java.com.codepath.recursion.towerofhanoi;

public class TowerOfHanoi {
    public void game(int n, String src, String aux, String dest) {
        if (n == 0) {
            return;
        }
        if (n == 1) {
            System.out.println(String.format("%s to %s", src, dest));
            return;
        } else {
            game(n - 1, src, dest, aux);
            game(1 , src, aux, dest);
            game(n - 1, aux, src, dest);
        }
        return;
    }
    public static void main(String[] args) {
        TowerOfHanoi towerOfHanoi = new TowerOfHanoi();

        int n = 1;
        System.out.println(String.format("Game with %s disc's", n));
        towerOfHanoi.game(n, "A", "B", "C");

        n = 2;
        System.out.println(String.format("Game with %s disc's", n));
        towerOfHanoi.game(n, "A", "B", "C");

        n = 3;
        System.out.println(String.format("Game with %s disc's", n));
        towerOfHanoi.game(n, "A", "B", "C");

        n = 4;
        System.out.println(String.format("Game with %s disc's", n));
        towerOfHanoi.game(n, "A", "B", "C");
    }
}
