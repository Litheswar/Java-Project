package Models;

/**
 * UserInput encapsulates all user-provided inputs including budget breakdowns.
 */
public class UserInput {
    private String country;
    private String state;
    private String destinationCity;
    private int familyMembers;
    private double budgetFood;
    private double budgetHotel;
    private double budgetTravel;
    private double budgetShopping;
    private double totalBudget;
    private int days;
    private int mealsPerDay;
    private String travelMode;        // road/rail/air/mixed
    private String accommodationType; // budget/standard/premium
    private String mealPreference;    // veg/non-veg/mixed

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }

    public int getFamilyMembers() { return familyMembers; }
    public void setFamilyMembers(int familyMembers) { this.familyMembers = familyMembers; }

    public double getBudgetFood() { return budgetFood; }
    public void setBudgetFood(double budgetFood) { this.budgetFood = budgetFood; }

    public double getBudgetHotel() { return budgetHotel; }
    public void setBudgetHotel(double budgetHotel) { this.budgetHotel = budgetHotel; }

    public double getBudgetTravel() { return budgetTravel; }
    public void setBudgetTravel(double budgetTravel) { this.budgetTravel = budgetTravel; }

    public double getBudgetShopping() { return budgetShopping; }
    public void setBudgetShopping(double budgetShopping) { this.budgetShopping = budgetShopping; }

    public double getTotalBudget() { return totalBudget; }
    public void setTotalBudget(double totalBudget) { this.totalBudget = totalBudget; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public int getMealsPerDay() { return mealsPerDay; }
    public void setMealsPerDay(int mealsPerDay) { this.mealsPerDay = mealsPerDay; }

    public String getTravelMode() { return travelMode; }
    public void setTravelMode(String travelMode) { this.travelMode = travelMode; }

    public String getAccommodationType() { return accommodationType; }
    public void setAccommodationType(String accommodationType) { this.accommodationType = accommodationType; }

    public String getMealPreference() { return mealPreference; }
    public void setMealPreference(String mealPreference) { this.mealPreference = mealPreference; }
}
