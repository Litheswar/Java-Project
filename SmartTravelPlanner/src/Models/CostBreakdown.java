package Models;

public class CostBreakdown {
    private final double travel;
    private final double food;
    private final double stay;
    private final double shopping;
    private final double leisure;
    private final double localCommute;
    private final double total;
    private final int sustainabilityScore; // 1-10
    private final double carbonFootprintKg;

    public CostBreakdown(double travel, double food, double stay, double shopping, double leisure, double localCommute, int sustainabilityScore, double carbonFootprintKg) {
        this.travel = travel;
        this.food = food;
        this.stay = stay;
        this.shopping = shopping;
        this.leisure = leisure;
        this.localCommute = localCommute;
        this.total = travel + food + stay + shopping + leisure + localCommute;
        this.sustainabilityScore = sustainabilityScore;
        this.carbonFootprintKg = carbonFootprintKg;
    }

    public double getTravel() { return travel; }
    public double getFood() { return food; }
    public double getStay() { return stay; }
    public double getShopping() { return shopping; }
    public double getLeisure() { return leisure; }
    public double getLocalCommute() { return localCommute; }
    public double getTotal() { return total; }
    public int getSustainabilityScore() { return sustainabilityScore; }
    public double getCarbonFootprintKg() { return carbonFootprintKg; }
}
