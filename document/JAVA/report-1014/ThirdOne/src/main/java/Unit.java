package ThirdOne.src.main.java;

import java.util.Random;

public class Unit {
    private static final int DEFAULT_HP = 40;
    private static final int DEFAULT_ATTACK = 4;

    protected String name;
    protected int hp;
    protected int attackPower;
    protected static final Random RNG = new Random();

    public Unit(String name) {
        this(name, DEFAULT_HP, DEFAULT_ATTACK);
    }

    public Unit(String name, int hp) {
        this(name, hp, DEFAULT_ATTACK);
    }

    public Unit(String name, int hp, int attack) {
        this.name = name;
        this.hp = Math.max(1, hp);
        this.attackPower = Math.max(0, attack);
    }

    public void attack() {
        System.out.println(name + " attacks with basic damage " + attackPower + ".");
    }

    public void move(String location) {
        System.out.println(name + " moves toward " + location + ".");
    }

    public void showStatus() {
        System.out.println("[STATUS] " + name + " | HP: " + hp + " | ATK: " + attackPower);
    }

    public void attack(Unit target) {
        attack();
        target.takeDamage(attackPower);
    }

    public void takeDamage(int incomingDamage) {
        hp = Math.max(0, hp - incomingDamage);
        System.out.println(name + " takes " + incomingDamage + " damage! (HP " + hp + ")");
    }

    public boolean isAlive() {
        return hp > 0;
    }

    protected void heal(int amount) {
        if (amount > 0) {
            hp = Math.min(hp + amount, DEFAULT_HP + 40);
            System.out.println(name + " recovers " + amount + " HP (" + hp + ")");
        }
    }

    public void endTurn() {
        // 기본 유닛은 턴 종료 시 특별한 효과 없음
    }
}
