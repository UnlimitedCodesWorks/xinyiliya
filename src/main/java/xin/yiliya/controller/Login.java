package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    @FXML TextField number;

    @FXML PasswordField password;

    @FXML GridPane gridPane;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public  void isLogin(ActionEvent event) throws IOException {
        if (number.getText().length() == 0) {
            number.setPromptText("请输入账号");
            number.setStyle("-fx-border-color: red;");
        }
        else if (password.getText().length() == 0) {
            password.setPromptText("请输入密码");
            password.setStyle("-fx-border-color: red;");
        }
        else {
            stage.hide();
        }
    }

    public void focusNumber(MouseEvent event) {
        number.setStyle("-fx-border-color: #3498db");
        number.setPromptText("账号");
    }

    public void focusPassword(MouseEvent event) {
        password.setStyle("-fx-border-color: #3498db");
        password.setPromptText("密码");
    }
}
