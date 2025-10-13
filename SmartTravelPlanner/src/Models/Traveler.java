package Models;

public class Traveler {
    private String name;
    private int age;
    private double discountRate;

    public Traveler(String name, int age) {
        this.name = name;
        this.age = age;
        this.discountRate = calculateDiscount(age);
    }

    private double calculateDiscount(int age) {
        if (age < 18) return 0.20;
        if (age > 60) return 0.15;
        return 0.0;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public double getDiscountRate() { return discountRate; }
}
