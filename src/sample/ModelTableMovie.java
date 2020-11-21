package sample;

public class ModelTableMovie {
private String movie;
private int rating;

    public ModelTableMovie(String movie, int rating) {
        this.movie = movie;
        this.rating = rating;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
