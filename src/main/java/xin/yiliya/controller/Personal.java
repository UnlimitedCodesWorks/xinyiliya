package xin.yiliya.controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class Personal {
    private Stage stage;

    public void changeP(MouseEvent mouseEvent) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/changePsl.fxml"));
        Parent root1 = fxmlLoader.load();
        xin.yiliya.controller.ChangePsl changePsl = fxmlLoader.getController();
        changePsl.setStage(stage);
        stage.setTitle("修改个人信息");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
