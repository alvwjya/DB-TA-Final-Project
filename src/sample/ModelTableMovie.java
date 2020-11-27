package sample;

public class ModelTableMovie {
private String movie;
private float rating;

    public ModelTableMovie(String movie, float rating) {
        this.movie = movie;
        this.rating = rating;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
