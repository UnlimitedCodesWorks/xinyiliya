package xin.yiliya.controller;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import xin.yiliya.exception.ImageFormatException;
import xin.yiliya.pojo.*;
import xin.yiliya.service.FileService;
import xin.yiliya.service.MessageService;
import xin.yiliya.service.PictureService;
import xin.yiliya.service.UserService;
import xin.yiliya.tool.AliOssTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @FXML
    private ScrollPane scroll2;

    @FXML
    private FlowPane container2;

    private Stage stage;

    private Timer timer = new Timer();

    private SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    private Integer receiverId;

    private UserBean userBean;

    private MessageService messageService;

    private UserService userService;

    private PictureService pictureService;

    private FileService fileService;

    private User sender;

    private AliOssTool aliOssTool;

    public void init() {
        userBean = (UserBean) SpringContext.ctx.getBean("userBean");
        messageService = (MessageService) SpringContext.ctx.getBean("messageServiceImpl");
        userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");
        pictureService = (PictureService) SpringContext.ctx.getBean("pictureServiceImpl");
        fileService = (FileService) SpringContext.ctx.getBean("fileServiceImpl");
        aliOssTool = (AliOssTool) SpringContext.ctx.getBean("aliOssTool");
        User receiveUser = userService.getUserInfo(receiverId).getUser();
        head.setImage(new Image(receiveUser.getUserHead()));
        name.setText(receiveUser.getUserName());
        introduce.setText(receiveUser.getUserIntroduce());
        sender = userService.getUserInfo(userBean.getUserId()).getUser();
        createMessNode(messageService.viewRecentNew(receiverId,sender.getId()));
        //启动线程监听是否有新消息
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //更新JavaFX的主线程的代码放在此处
                            createMessNode(messageService.viewNew(receiverId,sender.getId()));
                        }
                    });
                    try {
                        Thread.sleep(1000*10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();

    }

    //创建消息结点
    private void createMessNode(List<MessagePicture> list){
        for(MessagePicture messagePicture : list){
            Message message = messagePicture.getMessage();
            Picture picture = messagePicture.getPicture();
            //容器
            FlowPane flowPane = new FlowPane();
            flowPane.setMinWidth(690);
            flowPane.setMaxWidth(690);
            flowPane.setHgap(20);
            flowPane.setVgap(10);

            //时间
            Label time = new Label(formatter.format(message.getMsgTime()));
            time.setMinWidth(690);
            time.setTextFill(Color.web("#A8A8A8"));

            //头像
            ImageView head = new ImageView(message.getSender().getUserHead());
            head.setFitHeight(100);
            head.setFitWidth(100);
            head.setPreserveRatio(true);

            flowPane.getChildren().add(time);
            flowPane.getChildren().add(head);
            if(picture!=null){
                //图片
                Image image = new Image(picture.getImgPath());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(400);
                imageView.setPreserveRatio(true);
                flowPane.getChildren().add(imageView);
            }else {
                //内容
                Label text = new Label(message.getContent());
                text.setWrapText(true);
                text.setMaxWidth(570);
                flowPane.getChildren().add(text);
            }
            container.getChildren().add(flowPane);
        }
        timer.schedule(new TimerTask(){
            public void run(){
                scroll.setVvalue(1);
                this.cancel();
            }},50);
    }
    //发送消息
    public void submit(ActionEvent event) {
        if (content.getText().length() != 0) {
            Message message = new Message();
            message.setContent(content.getText());
            message.setSendId(sender.getId());
            message.setReceiveId(receiverId);
            if(messageService.sendMessage(message)!=0){
                //容器
                FlowPane flowPane = new FlowPane();
                flowPane.setMinWidth(690);
                flowPane.setMaxWidth(690);
                flowPane.setHgap(20);
                flowPane.setVgap(10);

                //时间
                Label time = new Label(formatter.format(new Date()));
                time.setMinWidth(690);
                time.setTextFill(Color.web("#A8A8A8"));

                //头像
                ImageView head = new ImageView(sender.getUserHead());
                head.setFitHeight(100);
                head.setFitWidth(100);
                head.setPreserveRatio(true);

                //内容
                Label text = new Label(content.getText());
                text.setWrapText(true);
                text.setMaxWidth(570);

                flowPane.getChildren().add(time);
                flowPane.getChildren().add(head);
                flowPane.getChildren().add(text);
                container.getChildren().add(flowPane);
                content.setText("");
                content.requestFocus();
            }
        }
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
        final File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            //容器
            FlowPane flowPane = new FlowPane();
            flowPane.setMinWidth(250);
            flowPane.setMaxWidth(250);
            flowPane.setVgap(10);

            //文件名
            Label name = new Label(file.getName());
            name.setMinWidth(250);

            //进度条
            ProgressBar progressBar = new ProgressBar();
            progressBar.setMinWidth(250);

            //下载
            Hyperlink hpl = new Hyperlink("下载");
            hpl.setMinWidth(250);
            hpl.setStyle("-fx-alignment: TOP_RIGHT;");
            hpl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(file.getName());
                }
            });

            flowPane.getChildren().add(name);
            flowPane.getChildren().add(progressBar);
            flowPane.getChildren().add(hpl);
            container2.getChildren().add(flowPane);
        }
    }

    //选择图片
    public void selectImage(ActionEvent event) throws FileNotFoundException, ImageFormatException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Message message = new Message();
            message.setSendId(sender.getId());
            message.setReceiveId(receiverId);
            Picture picture = new Picture();
            picture.setImgPath(aliOssTool.putImage(file));
            MessagePicture messagePicture = new MessagePicture();
            messagePicture.setMessage(message);
            messagePicture.setPicture(picture);
            if(pictureService.sendPicture(messagePicture)!=0){
                //容器
                FlowPane flowPane = new FlowPane();
                flowPane.setMinWidth(690);
                flowPane.setMaxWidth(690);
                flowPane.setHgap(20);
                flowPane.setVgap(10);

                //时间
                Label time = new Label(formatter.format(new Date()));
                time.setMinWidth(690);
                time.setTextFill(Color.web("#A8A8A8"));

                //头像
                ImageView head = new ImageView(sender.getUserHead());
                head.setFitHeight(100);
                head.setFitWidth(100);
                head.setPreserveRatio(true);

                //图片
                Image image = new Image(new FileInputStream(file));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(400);
                imageView.setPreserveRatio(true);

                flowPane.getChildren().add(time);
                flowPane.getChildren().add(head);
                flowPane.getChildren().add(imageView);
                container.getChildren().add(flowPane);
            }

        }
    }

    //查看历史消息
    public void browseHistory(ActionEvent event) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
}
