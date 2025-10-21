class Protagonist{
    static String name;
    static int age; static int HP;  static int MP;
    static String job; static String[] skills;
    
    static {
        name = "KDK"; 
        age = 60; HP = 30; MP = 50;
        job = "magician";
    }
}

public enum ItemType {
    COIN("머니") {
        
    },
    WEAPON("무기") {

    },
    ARMOR("방어구") {
        
    };
    private final String name;
    public abstract void use() {
        
    }
    ItemType(String name) {
        this.name = name;
    }
}

class Item 
{

}

class Monster
{

}