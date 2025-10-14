
public class P5Car {
    enum Direction {LEFT, RIGHT, STRAIGHT}
    
    String color;
    int speed;
    int gear;
    Direction direction;
    int light;

    @Override
    public String toString() {
        return "Car [color=" + color + ", speed="
        + speed + ", gear=" + gear + ", direction=" + direction + "]";
    }
    
    String changeDirection(Direction d) {
        if (d == null) {
            return "유효하지 않은 방향입니다";
        }
        this.direction = d;
        return "방향 변경: " + d;
    }

    String lightening(int light) {
        this.light = light;
        return light == 1 ? "라이트 켜짐" : "라이트 꺼짐";
    }

    void changeGear(int g) {
        gear = g;
    }
    
    void speedUp() {
        speed = speed + 10;
    }
    
    void speedDown() {
        speed = speed - 10;
    }
}

class Taxi extends P5Car {
    int fare;
    boolean hasPessenger;

    Taxi(String color) {
        this.color = color;
        this.fare = 3000;
        this.hasPessenger = false;
    }

    void pickUp() {
        if (!hasPessenger) {
            hasPessenger = true;
            System.out.println("승객 탑승");
        } else {
            System.out.println("이미 승객이 있습니다");
        }
    }

    void dropOff() {
        if (hasPessenger) {
            hasPessenger = false;
            System.out.println("승객 하차. 요금: " + fare + "원");
        } else {
            System.out.println("승객이 없습니다");
        }
    }

    void addFare(int distance) {
        fare += distance * 1000;
    }

    @Override
    public String toString() {
        return "Taxi [color=" + color + ", speed=" + speed 
        + ", fare=" + fare + ", hasPessenger=" + hasPessenger + "]";
    }
}

class Bus extends P5Car {
    int routeNumber;
    int passengerCount;
    int maxCapacity;

    Bus(int routeNumber, int maxCapacity) {
        this.routeNumber = routeNumber;
        this.maxCapacity = maxCapacity;
        this.passengerCount = 0;
        this.color = "파란색";
    }

    void boardPassenger(int count) {
        if (passengerCount + count <= maxCapacity) {
            passengerCount += count;
            System.out.println(count + "명 탑승. 현재 승객: " + passengerCount + "명");
        } else {
            System.out.println("정원 초과! 최대 " + (maxCapacity - passengerCount) + "명만 탑승 가능");
        }
    }

    void alightPassenger(int count) {
        if (passengerCount >= count) {
            passengerCount -= count;
            System.out.println(count + "명 하차. 현재 승객: " + passengerCount + "명");
        } else {
            System.out.println("하차 인원이 현재 승객보다 많습니다");
        }
    }

    @Override
    public String toString() {
        return "Bus [routeNumber=" + routeNumber + ", color=" + color 
        + ", speed=" + speed + ", passengers=" + passengerCount 
        + "/" + maxCapacity + "]";
    }
}