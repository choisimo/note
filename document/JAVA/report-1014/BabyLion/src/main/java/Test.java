package BabyLion.src.main.java;

public class Test {

    public static void main(String[] args) {
        Animal animal = new Animal();
        Lion lion = new Lion();
        Eagle eagle = new Eagle();

        animal.eat();
        lion.eat();
        eagle.eat();

        animal.sleep();
        lion.sleep();
        eagle.sleep();;

        lion.roar();
        eagle.fly();


        BabyLion babyLion = new BabyLion();
        babyLion.eat();
        babyLion.sleep();
        babyLion.roar();
        babyLion.meow();
    }

    // Lion  derived from Animal
    /**
     *  protected : only the inheritance of this instance has the affection.
     */
    protected static class Lion extends Animal {
        public void roar() {
            System.out.println("The lion is roaring");
        }
    }

    // Eagle derived from Animal
    /**
    *  protected : only the inheritance of this instance has the affection.
    */
    protected static class Eagle extends Animal {
        public void fly() {
            System.out.println("The eagle is flying");
        }       
    }

    // BabyLion derived from Lion
    private static class BabyLion extends Lion {
        // The super's destination instance is the Lion instance
        // @override
        public void eat() {
            super.eat();
        }

        // @override
        public void roar() {
            super.roar();
        }

        // The new method for own it-self
        private void meow() {
            System.out.println("I'm a little baby lion. Meow~~~");
        }
    }   
}