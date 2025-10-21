package main;

// 형 지정 없을 시 기본적으로는 abstract 형태임
abstract class Shape {
    protected int x, y;
}

class Rectangle extends Shape {
    public int width, height;
}

class Triangle extends Shape {
    public int base, height;
}

class Circle extends Shape {
    public int radius;
}