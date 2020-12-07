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

public class RatingController {
    public Button refreshButton, saveButton, backButton;
    public TextArea reviewArea;
    public TextField rateField;
    public Connection connect = new Connection();
    public int movieId;
    public String username;

    ObservableList<ModelTableRating> oblist = FXCollections.observableArrayList();

    @FXML
    private TableView<ModelTableRating> ratingTable;


    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void refreshButton() {
        System.out.println(movieId);
        System.out.println(username);
    }

    @FXML
    public void saveButton() {

        if ((reviewArea.getText().isEmpty()) || rateField.getText().isEmpty() || (Integer.parseInt(rateField.getText()) <= 0) || (Integer.parseInt(rateField.getText()) > 5)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Something Wrong!");
            alert.setHeaderText("Check your input");
            alert.setContentText("Make sure you fill all field with correct value");
            alert.show();
        } else {
            try {
                PreparedStatement prepStatCheck = connect.getPrepStat("SELECT * FROM ratings WHERE username = '" + username + "' AND movieId = " + movieId + ";");
                ResultSet rsc = prepStatCheck.executeQuery();
                if (rsc.next()) {
                    PreparedStatement prepStatUpdate = connect.getPrepStat("UPDATE ratings SET description = '" + reviewArea.getText() + "', rate = " + Integer.parseInt(rateField.getText()) + " WHERE movieId = " + movieId + " AND username ='" + username + "';");
                    prepStatUpdate.executeUpdate();
                } else {
                    PreparedStatement prepStat = connect.getPrepStat("INSERT INTO ratings (movieId, username, description, rate) VALUES ('" + movieId + "', '" + username + "', '" + reviewArea.getText() + "', '" + rateField.getText() + "');");
                    prepStat.executeUpdate();
                    ModelTableRating modelTableRating = new ModelTableRating(username, reviewArea.getText(), Integer.parseInt(rateField.getText()));
                    oblist.add(modelTableRating);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("Save Successful!");
                alert.setHeaderText("SAVED");
                alert.show();
                PreparedStatement prepStatUpdate = connect.getPrepStat("UPDATE movies SET movieRating = (SELECT AVG(rate) FROM ratings WHERE movieId = '" + movieId + "') WHERE movieId = '" + movieId + "';");
                prepStatUpdate.executeUpdate();

                loadFirst();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public void backButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieList.fxml"));
            Parent root = loader.load();
            MovieListController mCon = loader.getController();
            mCon.setUsername(username);
            mCon.loadFirst();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage closeWindow = (Stage) ratingTable.getScene().getWindow();
            closeWindow.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }
    }

    public void showTable() {
        try {
            PreparedStatement prepStat = connect.getPrepStat("SELECT * FROM ratings WHERE movieId = '" + movieId + "';");
            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                oblist.add(new ModelTableRating(rs.getString("username"), rs.getString("description"), rs.getInt("rate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadFirst() {
        System.out.println(movieId);
        System.out.println(username);
        ratingTable.getItems().clear();
        ratingTable.getColumns().clear();
        showTable();

        try {
            PreparedStatement prepStatCheck = connect.getPrepStat("SELECT * FROM ratings WHERE username = '" + username + "' AND movieId = " + movieId + ";");
            ResultSet rsc = prepStatCheck.executeQuery();
            PreparedStatement prepStat = connect.getPrepStat("SELECT moviename from movies where movieId = " + movieId + ";");
            ResultSet rs = prepStat.executeQuery();
            if (rsc.next()) {
                reviewArea.setText(rsc.getString("description"));
                rateField.setText(String.valueOf(rsc.getInt("rate")));
            }
            if (rs.next()) {
                TableColumn userCol = new TableColumn(rs.getString("moviename"));
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
