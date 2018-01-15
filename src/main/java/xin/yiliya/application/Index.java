package xin.yiliya.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Index extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/index.fxml"));
        Parent root = fxmlLoader.load();
        xin.yiliya.controller.Index index = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("QQ2018");
        primaryStage.setResizable(false);
        index.init();
        primaryStage.show();
        Stage personalStage = new Stage();
        index.setPersonal(personalStage);
        Stage searchStage = new Stage();
        index.setSearch(searchStage);
    }
}
