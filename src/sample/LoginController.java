package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button loginButton;
    public TextField usernameField;
    public TextField passwordField;

public void loginButton(){
    Parent root = null;
    try{
        root = FXMLLoader.load(getClass().getResource("MovieList.fxml"));
    } catch (
            IOException e) {
        System.out.println("File Not Found!");
    }
    assert root != null;
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.show();
    Stage closeWindow = (Stage) loginButton.getScene().getWindow();
    closeWindow.close();
}
}
