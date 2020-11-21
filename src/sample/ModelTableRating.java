package sample;

public class ModelTableRating {
    private String user, description;
    private int rating;

    public ModelTableRating(String user, String description, int rating) {
        this.user = user;
        this.description = description;
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
