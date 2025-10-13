package Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.File;

/**
 * TravelDiary demonstrates File I/O for logging trips in a simple text file.
 */
public class TravelDiary {
    private final String diaryPath;

    public TravelDiary(String diaryPath) {
        this.diaryPath = diaryPath;
    }

    public void appendEntry(String userName, String destination, double estimatedCost) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(diaryPath, true))) {
            bw.write(LocalDateTime.now() + " | " + userName + " | " + destination + " | ₹" + estimatedCost);
            bw.newLine();
        } catch (IOException e) {
            // Ignore for demo purposes
        }
    }

    public void appendCsv(String csvPath, String userName, String country, String state, String destination,
                          double estimatedCost, int familyMembers, int days, int mealsPerDay) {
        try {
            File f = new File(csvPath);
            boolean writeHeader = !f.exists() || f.length() == 0;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath, true))) {
                if (writeHeader) {
                    bw.write("timestamp,user,country,state,destination,estimated_cost,family_members,days,meals_per_day");
                    bw.newLine();
                }
                String line = String.join(",",
                        LocalDateTime.now().toString(),
                        safe(userName), safe(country), safe(state), safe(destination),
                        String.valueOf(estimatedCost), String.valueOf(familyMembers), String.valueOf(days), String.valueOf(mealsPerDay)
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ignored) { }
    }
    private String safe(String s) { return s == null ? "" : s.replace(',', ';'); }
}
