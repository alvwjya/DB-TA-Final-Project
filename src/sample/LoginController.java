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

public class LoginController {
    public Button loginButton;
    public TextField usernameField;
    public TextField passwordField;

    Connection connect = new Connection();


    public void loginButton() throws SQLException {
        PreparedStatement prepStat = connect.getPrepStat("SELECT * FROM users WHERE username = '" + usernameField.getText() + "' AND password = '" + passwordField.getText() + "';");
        ResultSet rs = prepStat.executeQuery();
        if (!rs.next()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Something Wrong!");
            alert.setHeaderText("Wrong Username or Password!");
            alert.setContentText("Register to become a member");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Register.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert root != null;
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Stage closeWindow = (Stage) loginButton.getScene().getWindow();
                closeWindow.close();
            }

        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieList.fxml"));
                Parent root = loader.load();
                MovieListController mCon = loader.getController();
                mCon.setUsername(usernameField.getText());
                mCon.loadFirst();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage closeWindow = (Stage) loginButton.getScene().getWindow();
                closeWindow.close();
            } catch (
                    IOException e) {
                System.out.println("File Not Found!");
            }
        }
    }


}
