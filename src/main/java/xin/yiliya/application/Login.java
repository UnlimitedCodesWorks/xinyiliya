package xin.yiliya.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.UserBean;

import java.io.IOException;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = fxmlLoader.load();
        xin.yiliya.controller.Login login = fxmlLoader.getController();
        login.addStage("primary", primaryStage);
        login.init();
        primaryStage.setTitle("登录");
        final Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
