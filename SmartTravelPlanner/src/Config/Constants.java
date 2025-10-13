package Config;

public final class Constants {
    private Constants() {}

    // Limits
    public static final int MIN_AGE = 1;
    public static final int MAX_AGE = 80;
    public static final int MIN_FAMILY = 1;
    public static final int MAX_FAMILY = 10;
    public static final int MIN_DAYS = 1;
    public static final int MAX_DAYS = 50; // v3 increase
    public static final int MIN_MEALS = 1;
    public static final int MAX_MEALS = 5;
    public static final double MIN_BUDGET = 1000.0;

    // Cost baselines
    public static final double FOOD_PER_MEAL_PER_PERSON = 200.0;
    public static final double STAY_PER_DAY_PER_PERSON = 800.0;
    public static final double SHOPPING_PER_PERSON = 200.0;
    public static final double TRAVEL_RATE_PER_KM = 10.0;

    // Multipliers
    public static final double MODE_ROAD_COST = 1.0;
    public static final double MODE_RAIL_COST = 0.8;
    public static final double MODE_AIR_COST = 1.6;

    public static final double ACC_BUDGET = 0.8;
    public static final double ACC_STANDARD = 1.0;
    public static final double ACC_PREMIUM = 1.5;

    public static final double MEAL_VEG = 1.0;
    public static final double MEAL_NONVEG = 1.1;
    public static final double MEAL_MIXED = 1.05;

    public static final double LEISURE_PERCENT = 0.08;        // of (stay + food)
    public static final double LOCAL_COMMUTE_PERCENT = 0.05;  // of travel

    // Carbon factors (kg CO2 per km per person, rough)
    public static final double CARBON_ROAD = 0.15;
    public static final double CARBON_RAIL = 0.06;
    public static final double CARBON_AIR = 0.25;

    // Regional price multipliers (rough realism)
    public static final double REGION_DEFAULT = 1.0;
    public static final double REGION_EUROPE = 1.25;  // France, Italy
    public static final double REGION_USA = 1.2;      // USA
    public static final double REGION_AUSTRALIA = 1.2;// Australia
    public static final double REGION_UAE = 1.15;     // UAE
    public static final double REGION_JAPAN = 1.2;    // Japan
    public static final double REGION_INDIA = 1.0;    // India

    public static double resolveRegionFactor(String country) {
        if (country == null) return REGION_DEFAULT;
        String c = country.trim().toLowerCase();
        if (c.equals("france") || c.equals("italy")) return REGION_EUROPE;
        if (c.equals("usa") || c.equals("united states") || c.equals("united states of america")) return REGION_USA;
        if (c.equals("australia")) return REGION_AUSTRALIA;
        if (c.equals("uae") || c.equals("united arab emirates")) return REGION_UAE;
        if (c.equals("japan")) return REGION_JAPAN;
        if (c.equals("india")) return REGION_INDIA;
        return REGION_DEFAULT;
    }
}

