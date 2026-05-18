package pages.components;

public class RatingToStarsComponent {
    public String toStars(int value) {
        int limit = 5;
        return "★".repeat(value) + "☆".repeat(limit - value);
    }
}