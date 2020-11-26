package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RatingController extends MovieListController implements Initializable {
    public Button refreshButton, saveButton;
    public TextArea reviewArea;
    public TextField rateField;
    public Connection connect;
    public LoginController login = new LoginController();

    ObservableList<ModelTableRating> oblist = FXCollections.observableArrayList();
    //String movietitle;

    @FXML
    private TableView<ModelTableRating> ratingTable;


    public void refreshButton(){

    }

    public void saveButton() throws SQLException {
        PreparedStatement prepStat = connect.getPrepStat("INSERT INTO ratings (movie_title, username, description, rate) VALUES ('" + movietitle + "', '" + login.username + "', '" + reviewArea.getText() + "', '" + rateField.getText() + "');");
        prepStat.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Save Successful!");
        alert.setHeaderText("SAVED");
        alert.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        oblist.add(new ModelTableRating("User 1", "Aku adalah anak gembala selalu riang tidak pernah tidur," +
                "karena aku suka membaca tidak pernah puas walaupun tidur, tralalala. aku hehehe", 2));
        System.out.println(login.username);
        System.out.println(movietitle);

        TableColumn userCol = new TableColumn(movietitle);
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
