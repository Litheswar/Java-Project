package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {
    // data/activities.txt format:
    // city|activity text
    public static List<String> loadActivitiesForCity(String filePath, String city) {
        List<String> out = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) return out;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] p = line.split("\\|", 2);
                if (p.length >= 2 && p[0].equalsIgnoreCase(city)) out.add(p[1]);
            }
        } catch (IOException ignored) { }
        return out;
    }
}
