package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingController {
    public Button refreshButton, saveButton;
    public TextArea reviewArea;
    public TextField rateField;
    public Connection connect = new Connection();
    public String movieTitle;
    public String username;

    ObservableList<ModelTableRating> oblist = FXCollections.observableArrayList();

    @FXML
    private TableView<ModelTableRating> ratingTable;


    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void refreshButton() {
        System.out.println(movieTitle);
        System.out.println(username);
    }

    @FXML
    public void saveButton() throws SQLException {
        PreparedStatement prepStat = connect.getPrepStat("INSERT INTO ratings (movie_title, username, description, rate) VALUES ('" + movieTitle + "', '" + username + "', '" + reviewArea.getText() + "', '" + rateField.getText() + "');");
        prepStat.executeUpdate();
        PreparedStatement prepStatUpdate = connect.getPrepStat("UPDATE movies SET movie_rating = (SELECT AVG(rate) FROM ratings WHERE movie_title = '" + movieTitle + "') WHERE title = '" + movieTitle + "';");
        prepStatUpdate.executeUpdate();
        ModelTableRating modelTableRating = new ModelTableRating(username, reviewArea.getText(), Integer.parseInt(rateField.getText()));
        oblist.add(modelTableRating);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Save Successful!");
        alert.setHeaderText("SAVED");
        alert.show();
    }


    public void loadFirst() {
        System.out.println(movieTitle);
        System.out.println(username);

        TableColumn userCol = new TableColumn(movieTitle);
        userCol.setMinWidth(150);
        userCol.setCellValueFactory(
                new PropertyValueFactory<ModelTableMovie, String>("user"));

        TableColumn descCol = new TableColumn("Description");
        descCol.setMinWidth(400);
        descCol.setCellValueFactory(
                new PropertyValueFactory<ModelTableMovie, String>("description"));

        TableColumn ratCol = new TableColumn("Rating");
        ratCol.setMinWidth(100);
        ratCol.setCellValueFactory(
                new PropertyValueFactory<ModelTableMovie, Integer>("rating"));
        ratingTable.setItems(oblist);
        ratingTable.getColumns().addAll(userCol, descCol, ratCol);
        try {
            PreparedStatement prepStat = connect.getPrepStat("SELECT * FROM ratings WHERE movie_title = '" + movieTitle + "';");
            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                oblist.add(new ModelTableRating(rs.getString("username"), rs.getString("description"), rs.getInt("rate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
