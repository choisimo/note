package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Item {

    private static final List<Item> REGISTRY;
    private static int nextId;

    static {
        REGISTRY = new ArrayList<>();
        nextId = 1;
    }

    private final int id;
    private final String name;
    private final ItemType type;
    private int potency;

    private Item(String name, ItemType type, int potency) {
        this.id = nextId++;
        this.name = name;
        this.type = type;
        this.potency = potency;
        REGISTRY.add(this);
    }

    public static Item createWeapon(String name, int attackPower) {
        return new Item(name, ItemType.WEAPON, attackPower);
    }

    public static Item createArmor(String name, int defensePower) {
        return new Item(name, ItemType.ARMOR, defensePower);
    }

    public static Item createCoinPouch(int amount) {
        return new Item(amount + " Gold", ItemType.COIN, amount);
    }

    public static List<Item> getRegistry() {
        return Collections.unmodifiableList(REGISTRY);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getPotency() {
        return potency;
    }

    public void upgrade(int amount) {
        if (amount <= 0) {
            return;
        }
        potency += amount;
    }

    public void use() {
        System.out.println("[아이템] " + name + "을(를) 사용했습니다.");
        type.use(name);
    }
}
