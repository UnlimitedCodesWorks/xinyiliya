package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Index {
    @FXML
    FlowPane myFriend;

    Stage personalStage;
    Stage searchStage;
    Stage addmessageStage;
    public  void  init(){
        //动态生成好友
            //好友总模块
        AnchorPane myfriend1 = new AnchorPane();
        myfriend1.setPrefWidth(300.0);
        myfriend1.setPrefHeight(85.0);
        myfriend1.setStyle("-fx-cursor: hand");
            //好友头像
        ImageView friendimg1 = new ImageView();
        friendimg1.setImage(new Image("image/head1.jpg"));
        friendimg1.setFitWidth(75.0);
        friendimg1.setFitHeight(75.0);
        friendimg1.setLayoutX(7.0);
        friendimg1.setLayoutY(7.0);
        myfriend1.getChildren().add(friendimg1);
            //好友昵称
        final Text friendname1 = new Text();
        friendname1.setStyle("-fx-font-size: 15;-fx-font-weight: bold;-fx-stroke-type: outside");
        friendname1.setText("三息步行");
        friendname1.setLayoutX(90.0);
        friendname1.setLayoutY(30.0);
        myfriend1.getChildren().add(friendname1);
            //好友签名
        Text friendcommeny1 = new Text();
        friendcommeny1.setFill(Color.rgb(117,115,115));
        friendcommeny1.setLayoutX(90.0);
        friendcommeny1.setLayoutY(54.0);
        friendcommeny1.setText("我王鑫全世界最最帅");
        friendcommeny1.setStyle("-fx-font-size: 12;-fx-stroke-type: outside");
        friendcommeny1.setWrappingWidth(165.0);
        myfriend1.getChildren().add(friendcommeny1);
            //未读消息
        Text friendunread = new Text();
        friendunread.setLayoutX(238.0);
        friendunread.setLayoutY(72.0);
        friendunread.setStyle("-fx-font-size: 10;-fx-stroke-type: outside");
        friendunread.setText("3条未读信息");
        myfriend1.getChildren().add(friendunread);
            //监听事件添加(用作弹出消息对话框，添加顺序在朋友昵称生成后，不然获取不到)
        myfriend1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println(friendname1.getText());

            }
        });
            //总体生成
        myFriend.getChildren().add(myfriend1);
    }
    //点击头像的ImageView 打开个人信息界面
    public void showPersonal(MouseEvent mouseEvent) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/personal.fxml"));
        Parent root = fxmlLoader.load();
        xin.yiliya.controller.Personal personal = fxmlLoader.getController();
        Stage newStage = new Stage();
        personal.setStage(newStage);
        personalStage.setTitle("个人信息");
        personalStage.setScene(new Scene(root));
        personalStage.setResizable(false);
        personalStage.show();
    }
    //点击"+ 添加好友"，打开搜索好友界面
    public void showSearch(MouseEvent mouseEvent) throws Exception{
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
        Parent root = fxmlLoader.load();
        searchStage.setTitle("查询好友");
        searchStage.setScene(new Scene(root));
        searchStage.setResizable(false);
        searchStage.show();
    }
    // 点击""，打开加好友确认消息页面
    public void showMessage(MouseEvent mouseEvent) throws Exception{
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/addmessage.fxml"));
        Parent root = fxmlLoader.load();
        xin.yiliya.controller.Addmessage addmessage = fxmlLoader.getController();
        addmessageStage.setTitle("验证消息");
        addmessageStage.setScene(new Scene(root));
        addmessageStage.setResizable(false);
        addmessageStage.show();
        addmessage.init();
    }

    public void setPersonal(Stage personalStage) {
        this.personalStage = personalStage;
    }

    public void setSearch(Stage searchStage) {
        this.searchStage = searchStage;
    }

    public void setAddmessage(Stage addmessageStage) {
        this.addmessageStage = addmessageStage;
    }


}
