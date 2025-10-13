package Models;

/**
 * EcoScore models a simple eco friendliness metric for destinations.
 */
public class EcoScore {
    private int score; // 1-10

    public EcoScore(int score) { this.score = score; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
