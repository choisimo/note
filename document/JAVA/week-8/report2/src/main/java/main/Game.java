package main;

public class Game {
    Town firstTown = new Town();

    public void printTown() {
        System.out.print("FirstTown's name: " + firstTown.name);
        System.out.print(", the size: " + firstTown.size);
        System.out.println(", the number of people: " + firstTown.numOfPeople);
    }
    
    public void printProtagonistInfo() {
        System.out.print("Protagonist's name: " + Protagonist.name);
        System.out.print(", the age: " + Protagonist.age);
        System.out.print(", HP: " + Protagonist.HP + ", MP: " + Protagonist.MP);
        System.out.println(", Job: " + Protagonist.job);
    }
}