// 다음의 조건을 만족하는 프로젝트를 개발하자.
// V (자유롭게) 부모클래스와 자식클래스를 생성
// V 부모클래스의 생성자를 3개 작성 (생성자 오버로딩 사용) 
// V 부모클래스의 메소드를 3개 작성
// V 자식클래스의 생성자를 3개 작성 (생성자 오버로딩 사용)
// V 자식클래스의 메소드를 3개 작성
// • 자식클래스의 생성자 코드안에 super() 키워드 넣기
// • 자식 클래스의 메소드 1개는 부모클래스 메소드 1개를 오버라이딩 함

public class thirdone {
    public static void main() {

    }

    protected static class parentOne {
        // Default contstructor
        public parentOne() {

        }
        // Overloaded contstructor - Variable Int 
        public parentOne(int... x) {

        }

        // Overloaded constructior - Variable String
        public parentOne(String... x) {}

        // method - 1 
        public void shouting() {

        }

        // method - 2
        public void smile() {

        }

        // method - 3 
        public void sleep() {

        }
    }

    private static class childOne extends parentOne {
        public childOne() {
            super();
        }

        public childOne(int... x) {
            super();
        }

        public childOne(String... x) {
            super();
        }

        // override
        public void shouting() {
            System.out.println("Don't bothering me!");
        }

        public void eat(String someFood) {
            System.out.println("I wanna eat " + someFood);
        }

        public void fighting() {
            System.out.println("Hit somebody!");
        }
        
    }
}