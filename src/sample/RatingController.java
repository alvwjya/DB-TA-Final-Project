package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingController {
    public Button refreshButton, saveButton;
    public TextArea reviewArea;
    public TextField rateField;
    public Connection connect;
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
        PreparedStatement prepStat = connect.getPrepStat("INSERT INTO ratings (movie_title, username, description, rate) VALUES ('" + movieTitle + "', '" + "username" + "', '" + reviewArea.getText() + "', '" + rateField.getText() + "');");
        prepStat.executeUpdate();
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
    }
}
