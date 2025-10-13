package Planner;

import CostManager.CostManager;
import LocationList.Location;
import LocationList.LocationRepository;
import LocationList.Route;
import Models.User;
import Models.TravelDiary;
import Models.Traveler;
import Models.CostBreakdown;
import Models.UserInput;
import Database.DBUtils;
import Utils.ExportUtils;
import Utils.ActivityUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * PlannerMain orchestrates console interaction and demonstrates Exception Handling.
 */
public class PlannerMain {
    public static void run() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean again = true;
            while (again) {
            LocationRepository repo = new LocationRepository();
            boolean loaded = false;
            try {
                repo.loadFromDatabase();
                System.out.println("✅ Database data loaded.");
                loaded = true;
            } catch (SQLException e) {
                System.out.println("⚠️ DB unavailable, falling back to files.");
            }
            if (!loaded) {
                try {
                    repo.loadFromFiles("SmartTravelPlanner/data/locations.txt", "SmartTravelPlanner/data/routes.txt");
                    System.out.println("✅ File data loaded.");
                } catch (IOException e) {
                    System.out.println("❌ Failed to load files: " + e.getMessage());
                    return;
                }
            }

            System.out.println("==============================");
            System.out.println(" SMART TRAVEL PLANNER 3.0");
            System.out.println("==============================");
            System.out.print("Enter your name: ");
            String name = sc.nextLine();
            int age;
            while (true) {
                System.out.print("Enter your age: ");
                try { age = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { age = -1; }
                if (age >= 1 && age <= 80) break;
                System.out.println("Age seems unrealistic. Please enter a valid age (1–80):");
            }
            int family;
            while (true) {
                System.out.print("Family members count: ");
                try { family = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { family = -1; }
                if (family >= 1 && family <= 10) break;
                System.out.println("Please enter a realistic family count (1–10):");
            }
            int days;
            while (true) {
                System.out.print("Trip days: ");
                try { days = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { days = -1; }
                if (days >= 1 && days <= 50) break;
                System.out.println("Please enter a realistic duration in days (1–50):");
            }
            int mealsPerDay;
            while (true) {
                System.out.print("Meals per day: ");
                try { mealsPerDay = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { mealsPerDay = -1; }
                if (mealsPerDay >= 1 && mealsPerDay <= 5) break;
                System.out.println("Maximum recommended meals per day is 5. Please re-enter:");
            }
            double suggestedMinBudget = days * mealsPerDay * 300.0 + 5000.0;
            double budget;
            while (true) {
                System.out.print("Total budget (₹): ");
                try { budget = Double.parseDouble(sc.nextLine().trim()); } catch (Exception e) { budget = -1; }
                if (budget >= 1000) break;
                System.out.println("The entered budget seems too low to cover any trip. Please enter a realistic budget:");
                System.out.println("Estimate ≈ (days × meals × ₹300) + ₹5000 = ₹" + Math.round(suggestedMinBudget));
                System.out.println("Would you like to: \n1) Adjust budget \n2) View destinations for a 1-day short trip under ₹5000");
                String choice = sc.nextLine().trim();
                if ("2".equals(choice)) {
                    SmartSuggestionEngine sse = new SmartSuggestionEngine(repo);
                    List<Location> shortTrips = sse.suggestAffordableTrips(5000, "");
                    if (shortTrips.isEmpty()) System.out.println("No 1-day short trips found under ₹5000.");
                    else {
                        System.out.println("Suggestions under ₹5000:");
                        for (int i = 0; i < Math.min(3, shortTrips.size()); i++) {
                            System.out.println("→ " + shortTrips.get(i).getCity() + " (₹" + Math.round(shortTrips.get(i).getCostEstimate()) + ")");
                        }
                    }
                }
            }
            User user = new User(name, family, budget);
            Traveler traveler = new Traveler(name, age);
            UserInput ui = new UserInput();
            ui.setFamilyMembers(family);
            ui.setTotalBudget(budget);
            ui.setDays(days);
            ui.setMealsPerDay(mealsPerDay);

            try {
                // Country pre-selection by number
                List<String> countries = repo.countries();
                if (countries.isEmpty()) {
                    System.out.println("No countries available in the dataset.");
                    return;
                }
                System.out.println("Available countries:");
                for (int i = 0; i < countries.size(); i++) System.out.println((i+1) + ". " + countries.get(i));
                int cIdx;
                while (true) {
                    System.out.print("Select country number: ");
                    try { cIdx = Integer.parseInt(sc.nextLine().trim()) - 1; } catch (Exception e) { cIdx = -1; }
                    if (cIdx >= 0 && cIdx < countries.size()) break;
                    System.out.println("Please choose a valid country number from the list.");
                }
                String country = countries.get(cIdx);
                ui.setCountry(country);
                List<String> states = repo.statesByCountry(country);
                if (states.isEmpty()) {
                    System.out.println("Available countries: " + String.join(", ", repo.countries()));
                    throw new MissingDestinationException("No states found for country: " + country);
                }
                System.out.println("Available states:");
                for (int i = 0; i < states.size(); i++) System.out.println((i+1) + ". " + states.get(i));
                int sIdx;
                while (true) {
                    System.out.print("Select state number: ");
                    try { sIdx = Integer.parseInt(sc.nextLine().trim()) - 1; } catch (Exception e) { sIdx = -1; }
                    if (sIdx >= 0 && sIdx < states.size()) break;
                    System.out.println("Valid states: " + String.join(", ", states));
                }
                if (sIdx < 0 || sIdx >= states.size()) {
                    System.out.println("Valid states: " + String.join(", ", states));
                    throw new InvalidRouteException("Invalid state selection.");
                }
                String state = states.get(sIdx);
                ui.setState(state);

                List<Location> dests = repo.destinationsByState(country, state);
                if (dests.isEmpty()) {
                    System.out.println("No destinations found in state: " + state);
                    throw new MissingDestinationException("No destinations found in state: " + state);
                }
                System.out.println("Destinations in " + state + ":");
                for (int i = 0; i < dests.size(); i++) System.out.println((i+1) + ". " + dests.get(i).getCity() + " (₹" + dests.get(i).getCostEstimate() + ")");
                int dIdx;
                while (true) {
                    System.out.print("Select destination number: ");
                    try { dIdx = Integer.parseInt(sc.nextLine().trim()) - 1; } catch (Exception e) { dIdx = -1; }
                    if (dIdx >= 0 && dIdx < dests.size()) break;
                    System.out.println("Please choose a valid destination number.");
                }
                if (dIdx < 0 || dIdx >= dests.size()) { throw new MissingDestinationException("Invalid destination selection."); }
                Location chosen = dests.get(dIdx);
                ui.setDestinationCity(chosen.getCity());

                // Preferences
                System.out.println();
                System.out.println("Choose your preferred mode of travel:\n1) Road  2) Rail  3) Air  4) Mixed (Smart Optimize)");
                String travelMode;
                while (true) {
                    String sel = sc.nextLine().trim();
                    if ("1".equals(sel)) { travelMode = "road"; break; }
                    if ("2".equals(sel)) { travelMode = "rail"; break; }
                    if ("3".equals(sel)) { travelMode = "air"; break; }
                    if ("4".equals(sel)) { travelMode = "mixed"; break; }
                    System.out.println("Please select 1, 2, 3, or 4:");
                }
                ui.setTravelMode(travelMode);
                System.out.println("Choose accommodation type:\n1) Budget  2) Standard  3) Premium");
                String acc;
                while (true) {
                    String sel = sc.nextLine().trim();
                    if ("1".equals(sel)) { acc = "budget"; break; }
                    if ("2".equals(sel)) { acc = "standard"; break; }
                    if ("3".equals(sel)) { acc = "premium"; break; }
                    System.out.println("Please select 1, 2, or 3:");
                }
                ui.setAccommodationType(acc);
                System.out.println("Select meal preference:\n1) Veg  2) Non-Veg  3) Mixed");
                String mealPref;
                while (true) {
                    String sel = sc.nextLine().trim();
                    if ("1".equals(sel)) { mealPref = "veg"; break; }
                    if ("2".equals(sel)) { mealPref = "non-veg"; break; }
                    if ("3".equals(sel)) { mealPref = "mixed"; break; }
                    System.out.println("Please select 1, 2, or 3:");
                }
                ui.setMealPreference(mealPref);

                // Confirmation step
                System.out.println();
                System.out.println("Please confirm your details:");
                System.out.println("Name: " + name + ", Age: " + age);
                System.out.println("Country: " + ui.getCountry() + ", State: " + ui.getState() + ", Destination: " + ui.getDestinationCity());
                System.out.println("Days: " + days + ", Meals/day: " + mealsPerDay + ", Family count: " + family + ", Budget: ₹" + Math.round(budget));
                System.out.println("Travel: " + travelMode + ", Stay: " + acc + ", Meals: " + mealPref);
                System.out.print("Proceed? (Y/N) ");
                String conf = sc.nextLine().trim();
                if (!conf.equalsIgnoreCase("Y")) {
                    System.out.println("What would you like to adjust? 1) Budget  2) Days  3) Continue");
                    String adj = sc.nextLine().trim();
                    if ("1".equals(adj)) {
                        while (true) {
                            System.out.print("Enter new budget (₹): ");
                            try { budget = Double.parseDouble(sc.nextLine().trim()); } catch (Exception e) { budget = -1; }
                            if (budget >= 1000) break;
                            System.out.println("Please enter a realistic budget (≥ ₹1000). Suggested min: ₹" + Math.round(suggestedMinBudget));
                        }
                        user.setTotalBudget(budget);
                        ui.setTotalBudget(budget);
                    } else if ("2".equals(adj)) {
                        while (true) {
                            System.out.print("Enter new days: ");
                            try { days = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { days = -1; }
                            if (days >= 1 && days <= 50) break;
                            System.out.println("Please enter a realistic duration in days (1–50):");
                        }
                        ui.setDays(days);
                    }
                }

                // Build plan and show available routes from first leg (simulate city to next legs)
                TravelPlan plan;
                System.out.print("Trip type? 1) CityPlan  2) TourPlan : ");
                String type = sc.nextLine().trim();
                plan = "2".equals(type) ? new TourPlan() : new CityPlan();

                List<Route> fromCityRoutes = repo.routesFrom(chosen.getCity());
                if (fromCityRoutes.isEmpty()) {
                    // if no outgoing routes, add a dummy step using destination estimate
                    plan.addStep(new Route("Start", chosen.getCity(), 0, chosen.getCostEstimate()));
                } else {
                    for (Route r : fromCityRoutes) plan.addStep(r);
                }

                plan.displayPlan();
                // Show activities for destination if available
                List<String> activities = ActivityUtils.loadActivitiesForCity("SmartTravelPlanner/data/activities.txt", chosen.getCity());
                if (!activities.isEmpty()) {
                    System.out.println("Top activities in " + chosen.getCity() + ":");
                    for (int i = 0; i < Math.min(3, activities.size()); i++) {
                        System.out.println((i+1) + ". " + activities.get(i));
                    }
                }
                // Mock 'shortest' (by distance) and 'fastest' (by duration) route showcase
                if (!plan.getSteps().isEmpty()) {
                    Route shortest = plan.getSteps().stream().min(Comparator.comparingInt(Route::getDistanceKm)).orElse(null);
                    Route fastest = plan.getSteps().stream().min(Comparator.comparingInt(Route::getDurationMinutes)).orElse(null);
                    if (shortest != null) System.out.println("Shortest leg: " + shortest);
                    if (fastest != null) System.out.println("Fastest leg: " + fastest);
                }

                CostManager cm = new CostManager();
                double groupDiscount = (user.getFamilyMembers() >= 5) ? 0.05 : 0.0;
                double effectiveDiscount = Math.min(0.4, traveler.getDiscountRate() + groupDiscount);
                CostBreakdown breakdown = cm.getBreakdown(plan, user.getFamilyMembers(), days, mealsPerDay, effectiveDiscount, ui.getTravelMode(), ui.getAccommodationType(), ui.getMealPreference(), country);
                double estimated = breakdown.getTotal();
                System.out.println();
                System.out.println("Traveler: " + traveler.getName() + " (Age " + traveler.getAge() + ")");
                System.out.println("Family Members: " + user.getFamilyMembers());
                System.out.println("Country: " + country);
                System.out.println("State: " + state);
                System.out.println("Destination: " + chosen.getCity());
                System.out.println("Trip Duration: " + days + " days");
                System.out.println("Meals per Day: " + mealsPerDay);
                System.out.println();
                System.out.println("--- Expense Breakdown ---");
                System.out.println("Travel: ₹" + Math.round(breakdown.getTravel()));
                System.out.println("Food: ₹" + Math.round(breakdown.getFood()));
                System.out.println("Stay: ₹" + Math.round(breakdown.getStay()));
                System.out.println("Shopping: ₹" + Math.round(breakdown.getShopping()));
                System.out.println("Leisure/Entertainment: ₹" + Math.round(breakdown.getLeisure()));
                System.out.println("Local Commute: ₹" + Math.round(breakdown.getLocalCommute()));
                System.out.println("--------------------------");
                System.out.println("Total Estimated: ₹" + Math.round(estimated));
                System.out.println("Your Budget: ₹" + Math.round(user.getTotalBudget()));
                System.out.println("Sustainability Score: " + breakdown.getSustainabilityScore() + "/10");
                System.out.println("Estimated CO₂ footprint: " + Math.round(breakdown.getCarbonFootprintKg()) + " kg");
                if (estimated > user.getTotalBudget()) {
                    System.out.println("⚠️ Budget shortfall: ₹" + Math.round(estimated - user.getTotalBudget()));
                }

                // Context-based tips
                TipsEngine tips = new TipsEngine();
                List<String> tipList = tips.generateTips(ui, estimated, user.getTotalBudget());
                for (String t : tipList) System.out.println(t);

                List<Location> suggestedAlternates = new ArrayList<>();
                if (estimated > user.getTotalBudget()) {
                    System.out.println("Your budget is ₹" + user.getTotalBudget() + " but the estimated trip cost is ₹" + estimated + ".");
                    System.out.println("Options:\n1. Increase your budget\n2. View destinations under ₹" + user.getTotalBudget());
                    String opt = sc.nextLine().trim();
                    if ("1".equals(opt)) {
                        System.out.print("Enter new budget (₹): ");
                        double nb = Double.parseDouble(sc.nextLine().trim());
                        user.setTotalBudget(nb);
                        System.out.println("Updated budget to ₹" + nb);
                    } else {
                        SmartSuggestionEngine sse = new SmartSuggestionEngine(repo);
                        List<Location> alts = sse.suggestAffordableTrips(user.getTotalBudget(), country);
                        if (alts.isEmpty()) alts = sse.suggestAffordableTrips(user.getTotalBudget());
                        if (alts.isEmpty()) {
                            System.out.println("No affordable alternatives found.");
                        } else {
                            System.out.println("Suggested Affordable Destinations:");
                            int count = 0;
                            for (Location alt : alts) {
                                // build a minimal plan for breakdown
                                TravelPlan altPlan = new CityPlan();
                                altPlan.addStep(new Route("Start", alt.getCity(), 0, alt.getCostEstimate()));
                                CostBreakdown altBd = cm.getBreakdown(altPlan, user.getFamilyMembers(), days, mealsPerDay, traveler.getDiscountRate(), ui.getTravelMode(), ui.getAccommodationType(), ui.getMealPreference(), alt.getCountry());
                                System.out.println("→ " + alt.getCity() + " (₹" + Math.round(altBd.getTotal()) + ")");
                                System.out.println("   Travel: ₹" + Math.round(altBd.getTravel()));
                                System.out.println("   Food: ₹" + Math.round(altBd.getFood()));
                                System.out.println("   Stay: ₹" + Math.round(altBd.getStay()));
                                suggestedAlternates.add(alt);
                                if (++count >= 3) break;
                            }
                        }
                    }
                } else {
                    System.out.println("✅ Your budget covers the trip!");
                }

                // If budget is significantly higher, show premium suggestions
                if (estimated < user.getTotalBudget() * 0.6) {
                    SmartSuggestionEngine sse = new SmartSuggestionEngine(repo);
                    List<Location> premiums = sse.suggestPremiumTrips(user.getTotalBudget());
                    if (!premiums.isEmpty()) {
                        System.out.println("Premium Suggestions:");
                        for (int i = 0; i < Math.min(3, premiums.size()); i++) {
                            Location p = premiums.get(i);
                            TravelPlan pPlan = new CityPlan();
                            pPlan.addStep(new Route("Start", p.getCity(), 0, p.getCostEstimate()));
                            CostBreakdown pBd = cm.getBreakdown(pPlan, user.getFamilyMembers(), days, mealsPerDay, traveler.getDiscountRate(), ui.getTravelMode(), ui.getAccommodationType(), ui.getMealPreference(), p.getCountry());
                            System.out.println("→ " + p.getCity() + " (₹" + Math.round(pBd.getTotal()) + ")");
                        }
                    }
                }

                // Persist user/trip (best-effort; ignore failures) and append diary entry
                int userId = DBUtils.insertUser(user.getName(), user.getFamilyMembers(), user.getTotalBudget());
                if (userId != -1) {
                    DBUtils.insertTrip(userId, chosen.getCity(), estimated, estimated <= user.getTotalBudget() ? "PLANNED" : "ADJUST_NEEDED");
                }
                TravelDiary diary = new TravelDiary("SmartTravelPlanner/data/diary/travel_diary.txt");
                diary.appendEntry(user.getName(), chosen.getCity(), estimated);
                diary.appendCsv("SmartTravelPlanner/data/diary/travel_summary.csv", user.getName(), country, state, chosen.getCity(), estimated, user.getFamilyMembers(), days, mealsPerDay);

                // Export formatted summary to TXT and CSV
                ExportUtils.exportPlanToFile("SmartTravelPlanner/data/diary/plan_summary.txt", traveler, user, ui, chosen, plan, breakdown, suggestedAlternates);
                String status = estimated <= user.getTotalBudget() ? "PLANNED" : "ADJUST_NEEDED";
                ExportUtils.exportPlanToCsv("SmartTravelPlanner/exports", traveler, user, ui, chosen, breakdown, status);

                System.out.println("Thank you for using Smart Travel Planner!");
            } catch (MissingDestinationException | InvalidRouteException ex) {
                System.out.println("❌ " + ex.getMessage());
            }
            System.out.println();
            System.out.println("Would you like to: \n1) Plan another trip \n2) Exit");
            String againSel = sc.nextLine().trim();
            again = "1".equals(againSel);
            }
        }
    }
}
