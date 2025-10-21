package BeanHouse.src.main.java;

public class GF_heritage {
    String land = "서울 땅 100평";
    private String SecretMoney = "swiss bank account";

    void divideLand() {
        System.out.println("할아버지 : 땅만 상속한다. 킹받쥬ㅋㅋㅋㅋㅋ");
    }
}


class GF_heritage_child extends GF_heritage {
    String MyBudget = "Bitcoin";

    @Override
    void divideLand() { 
        System.out.println("땅이라도 받자");
        super.divideLand();
    }
}

class GF_heritage_grandchild extends GF_heritage_child {
    String MyBudget = "Minus Account";

    @Override
    void divideLand() {
        System.out.println("콩가루 집안이라 땅도 없음 ㅋㅋㅋㅋㅋㅋ");
    }
}

