public class P5CarTest {
    public static void main(String[] args) {
        System.out.println("=== Car 테스트 ===");
        P5Car car = new P5Car();
        car.color = "빨강";
        car.speed = 0;
        car.direction = P5Car.Direction.STRAIGHT;
        
        System.out.println(car);
        car.speedUp();
        System.out.println("속도 증가 후: " + car.speed);
        System.out.println(car.changeDirection(P5Car.Direction.LEFT));
        System.out.println(car.changeDirection(P5Car.Direction.RIGHT));
        System.out.println(car.lightening(1));
        System.out.println(car);
        
        System.out.println("\n=== Taxi 테스트 ===");
        Taxi taxi = new Taxi("노랑");
        System.out.println(taxi);
        taxi.pickUp();
        taxi.speedUp();
        taxi.addFare(5);
        System.out.println("5km 이동 후 요금: " + taxi.fare);
        taxi.dropOff();
        taxi.dropOff();
        System.out.println(taxi);
        
        System.out.println("\n=== Bus 테스트 ===");
        Bus bus = new Bus(302, 40);
        System.out.println(bus);
        bus.boardPassenger(15);
        bus.boardPassenger(20);
        bus.speedUp();
        bus.speedUp();
        System.out.println("현재 속도: " + bus.speed);
        bus.alightPassenger(10);
        bus.boardPassenger(30);
        bus.alightPassenger(25);
        System.out.println(bus);
    }
}
