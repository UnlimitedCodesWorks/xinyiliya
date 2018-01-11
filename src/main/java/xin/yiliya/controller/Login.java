package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Login {
    @FXML
    TextField number;

    @FXML
    TextField password;

    public  void isLogin(ActionEvent event) {
        if (number.getText().length() == 0) {
            number.setPromptText("请输入账号");
        }
        else if (password.getText().length() == 0) {
            password.setPromptText("请输入密码");
        }
    }
}
