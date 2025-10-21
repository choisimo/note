package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monster {

    private static final List<String> MONSTER_REGISTRY;

    static {
        MONSTER_REGISTRY = new ArrayList<>();
        MONSTER_REGISTRY.add("슬라임");
        MONSTER_REGISTRY.add("고블린");
    }

    private final String species;
    private int level;
    private int hp;

    public Monster(String species, int level, int hp) {
        this.species = species;
        this.level = level;
        this.hp = hp;
        if (!MONSTER_REGISTRY.contains(species)) {
            MONSTER_REGISTRY.add(species);
        }
    }

    public static List<String> getSpeciesRegistry() {
        return Collections.unmodifiableList(MONSTER_REGISTRY);
    }

    public String getSpecies() {
        return species;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public void receiveDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }
        hp += amount;
    }

    public void levelUp() {
        level += 1;
        hp += 5;
    }

    public boolean isDefeated() {
        return hp <= 0;
    }

    public String describe() {
        return "[몬스터] " + species + " (Lv." + level + ", HP:" + hp + ")";
    }
}
