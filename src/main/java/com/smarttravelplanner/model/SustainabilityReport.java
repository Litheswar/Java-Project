package com.smarttravelplanner.model;

public class SustainabilityReport {
    private int score; // 1-10
    private double co2Footprint; // in tons
    private String travelMode;
    private String tips;
    
    public SustainabilityReport() {}
    
    public SustainabilityReport(int score, double co2Footprint, String travelMode, String tips) {
        this.score = score;
        this.co2Footprint = co2Footprint;
        this.travelMode = travelMode;
        this.tips = tips;
    }
    
    // Getters and Setters
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public double getCo2Footprint() {
        return co2Footprint;
    }
    
    public void setCo2Footprint(double co2Footprint) {
        this.co2Footprint = co2Footprint;
    }
    
    public String getTravelMode() {
        return travelMode;
    }
    
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
    
    public String getTips() {
        return tips;
    }
    
    public void setTips(String tips) {
        this.tips = tips;
    }
    
    @Override
    public String toString() {
        return "SustainabilityReport{" +
                "score=" + score +
                ", co2Footprint=" + co2Footprint +
                ", travelMode='" + travelMode + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
    
    /**
     * Generates a formatted report string
     * @return Formatted report
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("🌿 Sustainability Report:\n");
        report.append("Score: ").append(score).append("/10\n");
        report.append("CO₂ Estimate: ").append(String.format("%.1f", co2Footprint)).append(" tons\n");
        report.append("Travel Mode: ").append(travelMode).append("\n");
        report.append("Tip: ").append(tips).append("\n");
        return report.toString();
    }
}