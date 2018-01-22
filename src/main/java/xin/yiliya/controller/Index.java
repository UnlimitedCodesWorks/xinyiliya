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
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.pojo.UserLaunch;
import xin.yiliya.service.UserService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class Index {
    @FXML
    private FlowPane myFriend;

    @FXML
    private ImageView userImg;

    @FXML
    private Text userName;

    @FXML
    private Text userComment;

    private Stage personalStage;

    private Stage searchStage;

    private Stage addmessageStage;

    private Stage dialog;

    private xin.yiliya.controller.Index index;

    private List<Integer> receiveIds = new LinkedList<Integer>();

    private List<Text> unreadTextList = new LinkedList<Text>();

    private UserService userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");

    private UserBean userBean = (UserBean) SpringContext.ctx.getBean("userBean");

    public  void  init() throws IOException {
        UserLaunch userLaunch = userService.getUserInfo(userBean.getUserId());
        User user = userLaunch.getUser();
        if(user!=null){
            userImg.setImage(new Image(user.getUserHead()));
            userName.setText(user.getUserName());
            userComment.setText(user.getUserIntroduce());
        }
        List<User> userList = userLaunch.getFriends();
        if(userList!=null){
            createFriend(userList);
        }
    }

    private void createFriend(List<User> friendList) throws IOException {
        myFriend.getChildren().removeAll(myFriend.getChildren());
        for(final User user:friendList){
            //动态生成好友
            //好友总模块
            receiveIds.add(user.getId());
            AnchorPane myfriend1 = new AnchorPane();
            myfriend1.setPrefWidth(300.0);
            myfriend1.setPrefHeight(85.0);
            myfriend1.setStyle("-fx-cursor: hand");
            //好友头像
            ImageView friendimg1 = new ImageView();
            friendimg1.setImage(new Image(user.getUserHead()));
            friendimg1.setFitWidth(75.0);
            friendimg1.setFitHeight(75.0);
            friendimg1.setLayoutX(7.0);
            friendimg1.setLayoutY(7.0);
            myfriend1.getChildren().add(friendimg1);
            //好友昵称
            final Text friendname1 = new Text();
            friendname1.setStyle("-fx-font-size: 15;-fx-font-weight: bold;-fx-stroke-type: outside");
            friendname1.setText(user.getUserName());
            friendname1.setLayoutX(90.0);
            friendname1.setLayoutY(30.0);
            myfriend1.getChildren().add(friendname1);
            //好友签名
            Text friendcommeny1 = new Text();
            friendcommeny1.setFill(Color.rgb(117,115,115));
            friendcommeny1.setLayoutX(90.0);
            friendcommeny1.setLayoutY(54.0);
            friendcommeny1.setText(user.getUserIntroduce());
            friendcommeny1.setStyle("-fx-font-size: 12;-fx-stroke-type: outside");
            friendcommeny1.setWrappingWidth(165.0);
            myfriend1.getChildren().add(friendcommeny1);
            //未读消息
            Text friendunread = new Text();
            friendunread.setLayoutX(238.0);
            friendunread.setLayoutY(72.0);
            friendunread.setStyle("-fx-font-size: 10;-fx-stroke-type: outside");
            friendunread.setText("1条未读信息");
            unreadTextList.add(friendunread);
            myfriend1.getChildren().add(friendunread);
            //初始化会话Stage
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/dialog.fxml"));
            Parent root = fxmlLoader.load();
            dialog.setTitle("会话");
            dialog.setScene(new Scene(root, 600, 500));
            dialog.setResizable(false);
            final Dialog dialogController = fxmlLoader.getController();

            //监听事件添加(用作弹出消息对话框，添加顺序在朋友昵称生成后，不然获取不到)
            myfriend1.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    Integer receiveId = user.getId();
                    dialogController.setStage(dialog);
                    dialog.show();
                }
            });
            //总体生成
            myFriend.getChildren().add(myfriend1);
        }
    }
    //点击头像的ImageView 打开个人信息界面
    public void showPersonal(MouseEvent mouseEvent) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/personal.fxml"));
        Parent root = fxmlLoader.load();
        xin.yiliya.controller.Personal personal = fxmlLoader.getController();
        Stage newStage = new Stage();
        personal.setStage(newStage);
        personal.setPersonal(personal);
        personalStage.setTitle("个人信息");
        personalStage.setScene(new Scene(root));
        personalStage.setResizable(false);
        personalStage.show();
        personal.init();
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
        addmessage.setIndex(index);
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

    public void setIndex(Index index) {
        this.index = index;
    }

    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }
}
