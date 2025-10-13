package Utils;

import Models.CostBreakdown;
import Models.Traveler;
import Models.User;
import Models.UserInput;
import Planner.TravelPlan;
import LocationList.Route;
import LocationList.Location;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExportUtils {
    public static void exportPlanToFile(String filePath,
                                        Traveler traveler,
                                        User user,
                                        UserInput input,
                                        Location destination,
                                        TravelPlan plan,
                                        CostBreakdown breakdown,
                                        List<Location> suggestedAlternates) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write("==============================\n");
            bw.write(" SMART TRAVEL PLANNER 3.0\n");
            bw.write("==============================\n\n");
            bw.write("Traveler: " + traveler.getName() + " (Age " + traveler.getAge() + ")\n");
            bw.write("Family Members: " + user.getFamilyMembers() + "\n");
            bw.write("Country: " + input.getCountry() + "\n");
            bw.write("State: " + input.getState() + "\n");
            bw.write("Destination: " + destination.getCity() + "\n");
            bw.write("Trip Duration: " + input.getDays() + " days\n");
            bw.write("Meals per Day: " + input.getMealsPerDay() + "\n\n");

            bw.write("--- Expense Breakdown ---\n");
            bw.write("Travel: ₹" + Math.round(breakdown.getTravel()) + "\n");
            bw.write("Food: ₹" + Math.round(breakdown.getFood()) + "\n");
            bw.write("Stay: ₹" + Math.round(breakdown.getStay()) + "\n");
            bw.write("Shopping: ₹" + Math.round(breakdown.getShopping()) + "\n");
            bw.write("Leisure/Entertainment: ₹" + Math.round(breakdown.getLeisure()) + "\n");
            bw.write("Local Commute: ₹" + Math.round(breakdown.getLocalCommute()) + "\n");
            bw.write("--------------------------\n");
            bw.write("Total Estimated: ₹" + Math.round(breakdown.getTotal()) + "\n");
            bw.write("Your Budget: ₹" + Math.round(user.getTotalBudget()) + "\n");
            bw.write("Sustainability Score: " + breakdown.getSustainabilityScore() + "/10\n\n");
            bw.write("Estimated CO₂ footprint: " + Math.round(breakdown.getCarbonFootprintKg()) + " kg\n\n");

            if (suggestedAlternates != null && !suggestedAlternates.isEmpty()) {
                bw.write("Suggested Affordable Destinations:\n");
                for (Location alt : suggestedAlternates) {
                    bw.write("→ " + alt.getCity() + " (₹" + Math.round(alt.getCostEstimate()) + ")\n");
                }
                bw.write("\n");
            }

            if (!plan.getSteps().isEmpty()) {
                bw.write("Plan Steps:\n");
                int i = 1;
                for (Route r : plan.getSteps()) {
                    bw.write((i++) + ". " + r.toString() + "\n");
                }
                bw.write("\n");
            }
        } catch (IOException ignored) { }
    }

    public static void exportPlanToCsv(String baseDir,
                                       Traveler traveler,
                                       User user,
                                       UserInput input,
                                       Location destination,
                                       CostBreakdown breakdown,
                                       String status) {
        try {
            File dir = new File(baseDir);
            if (!dir.exists()) dir.mkdirs();
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File file = new File(dir, "trip_" + ts + ".csv");
            boolean writeHeader = true;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                if (writeHeader) {
                    bw.write("timestamp,name,age,country,state,city,days,meals,family,budget,total_cost,status,sustainability,carbon_kg");
                    bw.newLine();
                    writeHeader = false;
                }
                String line = String.join(",",
                        LocalDateTime.now().toString(),
                        safe(traveler.getName()), String.valueOf(traveler.getAge()),
                        safe(input.getCountry()), safe(input.getState()), safe(destination.getCity()),
                        String.valueOf(input.getDays()), String.valueOf(input.getMealsPerDay()),
                        String.valueOf(user.getFamilyMembers()), String.valueOf(Math.round(user.getTotalBudget())),
                        String.valueOf(Math.round(breakdown.getTotal())), status,
                        String.valueOf(breakdown.getSustainabilityScore()), String.valueOf(Math.round(breakdown.getCarbonFootprintKg()))
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ignored) { }
    }

    private static String safe(String s) { return s == null ? "" : s.replace(',', ';'); }
}
