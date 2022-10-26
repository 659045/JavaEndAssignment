package com.example.javaendassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginController {

    Database db = new Database();

    ArrayList<User> users = db.getUsers();

    @FXML
    private Label lblError;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void onButtonLoginClick() throws IOException {
        checkLogin(users);
    }


    private void checkLogin(ArrayList<User> users) throws IOException {
        for (User u: users) {
            if(Objects.equals(u.username, txtUsername.getText()) && Objects.equals(u.password, txtPassword.getText())){
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 530, 340);
                Stage stage = new Stage();
                stage.setTitle("Main");
                stage.setScene(scene);
                stage.show();
            }
            else if (Objects.equals("", txtUsername.getText())|| Objects.equals("", txtPassword.getText())){
                lblError.setText("Please fill in all the fields");
            }
            else{
                lblError.setText("Incorrect Login Credentials");
            }
        }
    }
}