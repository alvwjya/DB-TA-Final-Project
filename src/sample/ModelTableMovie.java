package sample;

public class ModelTableMovie {
    private int movieId;
    private String movieName;
    private float rating;

    public ModelTableMovie(int movieId, String movieName, float rating) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.rating = rating;
    }

    public String getMovie() {
        return movieName;
    }

    public void setMovie(String movieName) {
        this.movieName = movieName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
