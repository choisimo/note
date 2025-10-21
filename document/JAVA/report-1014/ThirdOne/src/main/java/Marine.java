package ThirdOne.src.main.java;

public class Marine extends Unit {
    private static final int DEFAULT_MARINE_HP = 45;
    private static final int DEFAULT_MARINE_ATK = 6;
    private static final int STIM_HP_COST = 10;
    private static final int STIM_ATTACK_BONUS = 2;
    private static final int MAX_HP = 60;

    private int stimTurns;
    private int coverTurns;

    public Marine() {
        this("Marine", DEFAULT_MARINE_HP, DEFAULT_MARINE_ATK);
    }

    public Marine(String name) {
        this(name, DEFAULT_MARINE_HP, DEFAULT_MARINE_ATK);
    }

    public Marine(String name, int hp, int attack) {
        super(name, hp, attack);
    }

    @Override
    public void attack() {
        System.out.println(name + ": 탕탕탕! (Damage " + attackPower + ")");
    }

    public void stimpack() {
        if (hp > STIM_HP_COST) {
            hp -= STIM_HP_COST;
            if (stimTurns == 0) {
                attackPower += STIM_ATTACK_BONUS;
            }
            stimTurns = 3;
            System.out.println(name + " uses Stimpack! HP:" + hp + " ATK:" + attackPower + " (3턴 지속)");
        } else {
            System.out.println(name + " is too injured to use Stimpack.");
        }
    }

    public void burrow() {
        coverTurns = 2;
        System.out.println(name + " takes cover behind sandbags. (2턴 피해 감소)");
    }

    public void attack(Unit target) {
        super.attack(target);
    }

    public int absorbDamage(int rawDamage) {
        if (coverTurns > 0) {
            int mitigated = Math.max(1, rawDamage - 3);
            System.out.println(name + " is fortified! Incoming damage reduced to " + mitigated + ".");
            return mitigated;
        }
        return rawDamage;
    }

    @Override
    public void takeDamage(int incomingDamage) {
        super.takeDamage(incomingDamage);
        if (!isAlive()) {
            System.out.println(name + " has fallen...");
        }
    }

    @Override
    public void endTurn() {
        if (stimTurns > 0) {
            stimTurns--;
            // 짐레이너 무적 상태  조건 구체화해서 수정 적용
            if (stimTurns == 0 && hp < MAX_HP) {
                attackPower -= STIM_ATTACK_BONUS;
                System.out.println(name + "'s Stimpack effect wears off. ATK:" + attackPower);
            }
        }

        if (coverTurns > 0) {
            coverTurns--;
            if (coverTurns == 0) {
                System.out.println(name + " is no longer behind cover.");
            }
        }

        if (hp < MAX_HP && stimTurns == 0) {
            hp = Math.min(MAX_HP, hp + 1);
        }
    }
}
