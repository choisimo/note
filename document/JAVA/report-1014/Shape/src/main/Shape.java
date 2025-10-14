package main;

public class Shape {
    private int x;
    private int y;

    void print() {
        System.out.println("x: " + x + " y: " + y);
    }

    public void setShape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getShape() {
        this.print();
    }
}