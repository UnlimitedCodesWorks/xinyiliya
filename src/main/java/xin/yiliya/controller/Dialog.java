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
import xin.yiliya.tool.GetObjectProgressListener;
import xin.yiliya.tool.PutObjectProgressListener;

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

    private int fileNum = 0;

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
                        Thread.sleep(1000*2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        //启动线程监听是否有新文件
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    List<xin.yiliya.pojo.File> fileList = fileService.readFile(receiverId,sender.getId());
                    if(fileList.size()!=0){
                        for(final xin.yiliya.pojo.File file:fileList){
                            final GetObjectProgressListener getObjectProgressListener = (GetObjectProgressListener) SpringContext.ctx.getBean("getObjectProgressListener");
                            final String filePath = file.getFilePath();
                            final String fileName = file.getFileName();
                            final Thread[] t = new Thread[1];
                            //容器
                            final FlowPane flowPane = new FlowPane();
                            flowPane.setMinWidth(250);
                            flowPane.setMaxWidth(250);
                            flowPane.setVgap(10);

                            //文件名
                            Label name = new Label(fileName);
                            name.setMinWidth(250);

                            //进度条
                            final ProgressBar progressBar = new ProgressBar();
                            progressBar.setMinWidth(250);

                            //取消下载
                            final Hyperlink hplz = new Hyperlink("取消");
                            hplz.setMinWidth(125);
                            hplz.setStyle("-fx-alignment: TOP_LEFT;");

                            //下载
                            Hyperlink hpl = new Hyperlink("下载");
                            hpl.setMinWidth(125);
                            hpl.setStyle("-fx-alignment: TOP_RIGHT;");

                            flowPane.getChildren().add(name);
                            flowPane.getChildren().add(progressBar);
                            flowPane.getChildren().add(hplz);
                            flowPane.getChildren().add(hpl);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    //更新JavaFX的主线程的代码放在此处
                                    container2.getChildren().add(flowPane);
                                }
                            });
                            hpl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                     t[0] = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getObjectProgressListener.setProgressBar(progressBar);
                                            final File newFile = new File("src/resource/image/"+fileName);
                                            aliOssTool.getObject(newFile,filePath,getObjectProgressListener);
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //更新JavaFX的主线程的代码放在此处
                                                    container2.getChildren().removeAll(container2.getChildren().get(0));
                                                }
                                            });
                                        }
                                    });
                                    t[0].start();
                                }
                            });

                            hplz.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            //更新JavaFX的主线程的代码放在此处
                                            container2.getChildren().removeAll(container2.getChildren().get(0));
                                        }
                                    });
                                    if(t[0]!=null){
                                        t[0].stop();
                                    }
                                }
                            });
                        }
                    }
                    try {
                        Thread.sleep(1000*2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.start();
    }

    //创建消息结点(后端添加）
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

    //创建消息结点(前端添加）
    private void createMessNode1(List<MessagePicture> list){
        container.getChildren().removeAll();
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
            container.getChildren().add(0,flowPane);
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
        final PutObjectProgressListener putObjectProgressListener = (PutObjectProgressListener) SpringContext.ctx.getBean("putObjectProgressListener");
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
            final ProgressBar progressBar = new ProgressBar();
            progressBar.setMinWidth(250);

            //暂停
            //取消
            final Hyperlink hplz = new Hyperlink("暂停");
            hplz.setMinWidth(125);
            hplz.setStyle("-fx-alignment: TOP_LEFT;");

            //取消
            Hyperlink hpl = new Hyperlink("取消");
            hpl.setMinWidth(125);
            hpl.setStyle("-fx-alignment: TOP_RIGHT;");

            flowPane.getChildren().add(name);
            flowPane.getChildren().add(progressBar);
            flowPane.getChildren().add(hplz);
            flowPane.getChildren().add(hpl);
            container2.getChildren().add(flowPane);
            final Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    putObjectProgressListener.setProgressBar(progressBar);
                    MessageFile messageFile = new MessageFile();
                    Message message = new Message();
                    message.setReceiveId(receiverId);
                    message.setSendId(sender.getId());
                    xin.yiliya.pojo.File file1 = new xin.yiliya.pojo.File();
                    file1.setFileName(file.getName());
                    file1.setFilePath(aliOssTool.putObject(file,putObjectProgressListener));
                    messageFile.setFile(file1);
                    messageFile.setMessage(message);
                    if(fileService.sendFile(messageFile)!=0){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //更新JavaFX的主线程的代码放在此处
                                container2.getChildren().removeAll(container2.getChildren().get(fileNum));
                            }
                        });
                    }else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //更新JavaFX的主线程的代码放在此处
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("QQ");
                                alert.setHeaderText("上传状态");
                                alert.setContentText("由于意外错误，上传失败！");
                                alert.showAndWait();
                            }
                        });
                    }
                }
            });
            hplz.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(hplz.getText().equals("暂停")){
                        t.suspend();
                        hplz.setText("继续");
                    }else if(hplz.getText().equals("继续")){
                        t.resume();
                        hplz.setText("暂停");
                    }
                }
            });
            hpl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    container2.getChildren().removeAll(container2.getChildren().get(fileNum));
                    t.stop();
                }
            });
            t.start();
            fileNum++;
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
        createMessNode1(messageService.viewHistory(receiverId,sender.getId()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
}
