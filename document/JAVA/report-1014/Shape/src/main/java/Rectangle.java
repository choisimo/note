package main.java;

public class Rectangle extends Shape {
    private int width;
    private int height;

    double calcArea() {
        return this.width * this.height;
    }


    void draw() {
            System.out.println("At (" + super.getShape() + ") " + "width: "
        + width + " height: " + height);
    }
    
}