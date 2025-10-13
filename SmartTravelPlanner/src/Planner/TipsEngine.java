package Planner;

import Models.UserInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TipsEngine {
    private final Random rnd = new Random();

    public List<String> generateTips(UserInput ui, double estimatedCost, double userBudget) {
        List<String> tips = new ArrayList<>();
        // Travel mode tips
        if ("air".equalsIgnoreCase(ui.getTravelMode())) {
            tips.add("✈️ Tip: Booking flights 4–6 weeks early could save up to ₹8000.");
        } else if ("rail".equalsIgnoreCase(ui.getTravelMode())) {
            tips.add("🚆 Tip: Consider overnight trains to save on hotel nights.");
        } else if ("road".equalsIgnoreCase(ui.getTravelMode())) {
            tips.add("🛣️ Tip: Carpooling can reduce per-person travel cost by 20–30%.");
        } else {
            tips.add("🧭 Tip: Mixed mode chosen — we will balance cost and time automatically.");
        }
        // Accommodation tips
        if ("premium".equalsIgnoreCase(ui.getAccommodationType())) {
            tips.add("🏨 Suggestion: Switching to Standard accommodation could save around 15–25% on stay.");
        } else if ("budget".equalsIgnoreCase(ui.getAccommodationType())) {
            tips.add("💡 Tip: Budget stays fill fast during weekends — book early.");
        }
        // Meal preference tips
        if ("non-veg".equalsIgnoreCase(ui.getMealPreference())) {
            tips.add("🍽️ Tip: Non-veg plans add ~10% to meal costs. Mixing veg meals can reduce spend.");
        }
        // Budget gap tip
        if (estimatedCost > userBudget) {
            tips.add("📉 Suggestion: Reduce days or choose Budget accommodation to close the budget gap.");
        } else {
            tips.add("✅ Tip: Your budget covers the plan. Consider adding a leisure activity.");
        }
        // Randomize to 2–3 tips
        List<String> out = new ArrayList<>();
        int count = 2 + rnd.nextInt(2); // 2 or 3 tips
        for (int i = 0; i < count && !tips.isEmpty(); i++) {
            out.add(tips.remove(rnd.nextInt(tips.size())));
        }
        return out;
    }
}
