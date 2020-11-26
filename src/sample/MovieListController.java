package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MovieListController implements Initializable {
    public Button refreshButton;
    Connection connect = new Connection();
    ObservableList<ModelTableMovie> oblist = FXCollections.observableArrayList();

    @FXML
    private TableView<ModelTableMovie> movieTable;

    public void refreshButton(){

    }

    public void getVal() {

    }

    public void getMovie(){
        //ModelTableMovie movie = movieTable.getSelectionModel().getSelectedItem();
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("Rating.fxml"));
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }
        assert root != null;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage closeWindow = (Stage) movieTable.getScene().getWindow();
        closeWindow.close();
    }

    public void showTable() {
        try {
            PreparedStatement prepStat = connect.getPrepStat("SELECT * FROM movies;");
            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                oblist.add(new ModelTableMovie(rs.getString("title"), rs.getInt("movie_rating")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void initialize(URL location, ResourceBundle resource) {
        showTable();
        //oblist.add(new ModelTableMovie("Joko",3));

        TableColumn movCol = new TableColumn("Movie");
        movCol.setMinWidth(550);
        movCol.setCellValueFactory(
                new PropertyValueFactory<ModelTableMovie, String>("movie"));

        TableColumn ratCol = new TableColumn("Rating");
        ratCol.setMinWidth(100);
        ratCol.setCellValueFactory(
                new PropertyValueFactory<ModelTableMovie, Integer>("rating"));
        movieTable.setItems(oblist);
        movieTable.getColumns().addAll(movCol, ratCol);

    }

}
