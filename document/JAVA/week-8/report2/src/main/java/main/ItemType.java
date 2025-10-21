package main;

public enum ItemType {
    COIN("머니") {
        @Override
        public void use(String itemName) {
            System.out.println("[코인]" + itemName + "을(를) 사용해 골드를 소비했습니다.");
        }
    },
    WEAPON("무기") {
        @Override
        public void use(String itemName) {
            System.out.println("[무기]" + itemName + "으로 공격력을 높였습니다.");
        }
    },
    ARMOR("방어구") {
        @Override
        public void use(String itemName) {
            System.out.println("[방어구]" + itemName + "을(를) 착용하여 방어력을 강화했습니다.");
        }
    };

    private final String displayName;

    ItemType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public abstract void use(String itemName);
}
