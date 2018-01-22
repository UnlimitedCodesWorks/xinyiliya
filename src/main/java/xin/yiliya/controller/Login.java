package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login {
    @FXML
    private TextField number;

    @FXML
    private PasswordField password;

    private Map<String, Stage> stageMap = new HashMap<String, Stage>();

    private Stage stage;

    public void addStage(String key, Stage stage) {
        stageMap.put(key, stage);
    }

    public void init() {
        password.setTooltip(new Tooltip("密码"));
        number.setTooltip(new Tooltip("账号"));
        stage = stageMap.get("primary");
    }

    //点击登录
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
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/index.fxml"));
            Parent root = fxmlLoader.load();
            xin.yiliya.controller.Index index = fxmlLoader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("QQ2018");
            stage.setResizable(false);
            index.init();
            stage.show();
            Stage personalStage = new Stage();
            index.setPersonal(personalStage);
            Stage searchStage = new Stage();
            index.setSearch(searchStage);
            Stage addmessageStage = new Stage();
            index.setAddmessage(addmessageStage);
        }
    }

    //点击注册
    public void goRegist(ActionEvent event) throws IOException {
        stage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/regist.fxml"));
        Parent root = fxmlLoader.load();
        Regist regist = fxmlLoader.getController();
        regist.setStageMap(stageMap);
        regist.init();
        stage.setTitle("注册");
        final Scene scene = new Scene(root, 450, 850);
        scene.getStylesheets().add(getClass().getResource("/css/regist.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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
