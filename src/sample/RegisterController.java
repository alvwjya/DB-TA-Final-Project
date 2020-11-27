package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RegisterController extends Connection{
    public TextField firstnameField;
    public TextField lastnameField;
    public TextField usernameField;
    public TextField passwordField;
    public Button registerButton;
    public Connection connect = new Connection();

    public void registerButton() throws SQLException {
        PreparedStatement prepStatCheck = connect.getPrepStat("SELECT * FROM users WHERE username = '" + usernameField.getText() + "';");
        ResultSet rs = prepStatCheck.executeQuery();
        if (!rs.next()) {
            PreparedStatement prepStat = connect.getPrepStat("INSERT INTO users (username, pass, fname, lname) VALUES ('" + usernameField.getText() + "', '" + passwordField.getText() + "', '" + firstnameField.getText() + "', '" + lastnameField.getText() + "');");
            prepStat.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("New User Register Successful!");
            alert.setHeaderText("REGISTERED");
            alert.show();

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
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
                Stage closeWindow = (Stage) registerButton.getScene().getWindow();
                closeWindow.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Please change your username!");
            alert.setHeaderText("USERNAME ALREADY EXISTS");
            alert.show();
        }

    }
}
