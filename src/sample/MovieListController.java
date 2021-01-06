package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MovieListController {
    public Button logOutButton;
    public int movieId;
    public String username;
    public Label greetingsLabel;

    Connection connect = new Connection();
    ObservableList<ModelTableMovie> oblist = FXCollections.observableArrayList();

    @FXML
    private TableView<ModelTableMovie> movieTable;


    public void logOutButton() {
        username = "";
        movieId = 0;

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage closeWindow = (Stage) logOutButton.getScene().getWindow();
        closeWindow.close();
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void getVal() {
        ModelTableMovie movie = movieTable.getSelectionModel().getSelectedItem();
        movieId = movie.getMovieId();
    }


    public void getMovie() {
        getVal();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Rating.fxml"));
            Parent root = loader.load();

            RatingController rCon = loader.getController();
            rCon.setUsername(username);
            rCon.setMovieId(movieId);
            rCon.loadFirst();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage closeWindow = (Stage) movieTable.getScene().getWindow();
            closeWindow.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }
    }


    public void showTable() {
        try {
            PreparedStatement prepStat = connect.getPrepStat("SELECT * FROM movies;");
            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                oblist.add(new ModelTableMovie(rs.getInt("movieId"), rs.getString("moviename"), rs.getFloat("movieRating")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addButton() {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter New Movie Title");
        Optional<String> result = td.showAndWait();
        if (!result.isEmpty()) {
            try {
                if (!td.getEditor().getText().isEmpty()) {
                    PreparedStatement prepStat = connect.getPrepStat("INSERT INTO movies (moviename) VALUES ('" + td.getEditor().getText() + "');");
                    prepStat.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void refreshButton() {
        movieTable.getItems().clear();
        showTable();
    }


    public void loadFirst() {
        movieTable.getItems().clear();
        showTable();

        greetingsLabel.setText("Welcome " + username + "!");


        TableColumn movCol = new TableColumn("Movie");
        movCol.setMinWidth(550);
        movCol.setCellValueFactory(
                new PropertyValueFactory<ModelTableMovie, String>("movie"));

        TableColumn ratCol = new TableColumn("Rating");
        ratCol.setMinWidth(100);
        ratCol.setCellValueFactory(
                new PropertyValueFactory<ModelTableMovie, Float>("rating"));
        movieTable.setItems(oblist);
        movieTable.getColumns().addAll(movCol, ratCol);
    }
}
