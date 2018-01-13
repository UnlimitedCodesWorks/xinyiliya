package xin.yiliya.controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangePsl {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void savechange(MouseEvent mouseEvent) {
        stage.hide();
    }
}
