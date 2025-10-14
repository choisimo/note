public class ConstructorCar {
    private String brand;
    private String model;
    private int year;
    private String color;

    private ConstructorCar(Builder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
        this.color = builder.color;
    }

    public static class Builder {
        private String brand;
        private String model;
        private int year;
        private String color;

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public ConstructorCar build() {
            return new ConstructorCar(this);
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                '}';
    }

    public static void main(String[] args) {
        ConstructorCar car = new ConstructorCar.Builder()
                .brand("Tesla")
                .model("Model 3")
                .year(2024)
                .color("Black")
                .build();

        System.out.println(car);
    }
}