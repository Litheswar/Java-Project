package Models;

/**
 * User demonstrates Encapsulation with private fields and getters/setters.
 */
public class User {
    private String name;
    private int familyMembers;
    private double totalBudget;

    public User(String name, int familyMembers, double totalBudget) {
        this.name = name;
        this.familyMembers = familyMembers;
        this.totalBudget = totalBudget;
    }

    public String getName() { return name; }
    public int getFamilyMembers() { return familyMembers; }
    public double getTotalBudget() { return totalBudget; }

    public void setName(String name) { this.name = name; }
    public void setFamilyMembers(int familyMembers) { this.familyMembers = familyMembers; }
    public void setTotalBudget(double totalBudget) { this.totalBudget = totalBudget; }
}
