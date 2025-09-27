package travelplanner;

/**
 * Represents a person traveling, with different roles affecting trip costs.
 * Demonstrates Encapsulation with private fields and public getters/setters.
 */
public class Person {
    private String name;
    private int age;
    private Role role;

    public enum Role {
        ADULT(1.0),
        CHILD(0.5),
        SENIOR(0.7);

        private final double costMultiplier;

        Role(double costMultiplier) {
            this.costMultiplier = costMultiplier;
        }

        public double getCostMultiplier() {
            return costMultiplier;
        }
    }

    // Default constructor
    public Person() {
    }

    // Parameterized constructor
    public Person(String name, int age, Role role) {
        this.name = name;
        this.age = age;
        this.role = role;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Role getRole() {
        return role;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the cost multiplier based on the person's role
     */
    public double getCostMultiplier() {
        return role.getCostMultiplier();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", role=" + role +
                '}';
    }
}