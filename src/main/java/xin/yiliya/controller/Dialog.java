package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class Dialog {
    @FXML
    private ImageView head;

    @FXML
    private Label name;

    @FXML
    private Label introduce;

    @FXML
    private FlowPane container;

    @FXML
    private TextArea content;

    @FXML
    private ScrollPane scroll;

    private Stage stage;

    public void init() {

    }

    //发送消息
    public void submit(ActionEvent event) {
        if (content.getText().length() != 0) {
            Label text = new Label(content.getText());
            text.setWrapText(true);
            text.setMaxWidth(560);
            text.setMinWidth(560);
            container.getChildren().add(text);
            content.setText("");
            content.requestFocus();
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                scroll.setVvalue(1);
                this.cancel();
            }},50);
    }

    //选择文件
    public void selectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showOpenDialog(stage);
        Label label = new Label(file.getName());
        container.getChildren().add(label);
    }

    //选择图片
    public void selectImage(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);
        Image image = new Image(new FileInputStream(file));
        Label picture = new Label();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(280);
        imageView.setPreserveRatio(true);
        picture.setGraphic(imageView);
        container.getChildren().add(picture);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
