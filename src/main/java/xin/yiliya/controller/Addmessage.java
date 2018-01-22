package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import xin.yiliya.pojo.Friends;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.service.FriendService;
import xin.yiliya.service.UserService;

import java.util.List;


public class Addmessage {

    @FXML
    private FlowPane messagePane;

    private UserService userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");

    private FriendService friendService = (FriendService) SpringContext.ctx.getBean("friendServiceImpl");

    private UserBean userBean = (UserBean) SpringContext.ctx.getBean("userBean");

    private xin.yiliya.controller.Index index;

    public void init() {
        List<User> userList = userService.getReplyUsers(userBean.getUserId());
        createNode(userList);
    }

    private void createNode(List<User> userList){
        for(final User user:userList){
            //动态生成添加好友信息
            //总体
            final AnchorPane message1 = new AnchorPane();
            message1.setPrefHeight(70.0);
            message1.setPrefWidth(600.0);
            //头像
            ImageView messageimg1 = new ImageView();
            messageimg1.setImage(new Image(user.getUserHead()));
            messageimg1.setFitHeight(50.0);
            messageimg1.setFitWidth(50.0);
            messageimg1.setLayoutX(25.0);
            messageimg1.setLayoutY(10.0);
            message1.setTopAnchor(messageimg1,10.0);
            message1.getChildren().add(messageimg1);
            //用户昵称
            final Text messagename1 = new Text();
            messagename1.setLayoutY(40.0);
            messagename1.setLayoutX(90.0);
            messagename1.setStyle("-fx-font-size: 15;-fx-stroke-type: outside");
            messagename1.setText(user.getUserName());
            message1.getChildren().add(messagename1);
            //同意按钮
            Button agree1 = new Button();
            agree1.setMnemonicParsing(false);
            agree1.setTextFill(Color.rgb(255,255,255));
            agree1.setStyle("-fx-background-color: #3498db;-fx-font-size: 12");
            agree1.setPrefHeight(25.0);
            agree1.setPrefWidth(60.0);
            agree1.setLayoutX(447.0);
            agree1.setLayoutY(22.0);
            agree1.setText("添加");
            agree1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Integer friendId = user.getId();
                    Friends friends = new Friends();
                    friends.setFriendId(userBean.getUserId());
                    friends.setUserId(friendId);
                    if(friendService.sureFriend(friends)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("恭喜您，添加好友成功");
                        alert.show();
                        messagePane.getChildren().remove(message1);
                        index.init();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("由于网络或其他原因，添加好友失败！");
                        alert.show();
                    }
                }
            });
            message1.getChildren().add(agree1);
            //拒绝按钮
            Button disagree1 = new Button();
            disagree1.setMnemonicParsing(false);
            disagree1.setTextFill(Color.rgb(255,255,255));
            disagree1.setStyle("-fx-background-color: #ff0000;-fx-font-size: 12");
            disagree1.setPrefHeight(25.0);
            disagree1.setPrefWidth(60.0);
            disagree1.setLayoutX(517.0);
            disagree1.setLayoutY(22.0);
            disagree1.setText("拒绝");
            disagree1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Integer friendId = user.getId();
                    Friends friends = new Friends();
                    friends.setFriendId(userBean.getUserId());
                    friends.setUserId(friendId);
                    if(friendService.refuseFriend(friends)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("拒绝了添加好友的请求");
                        alert.show();
                        messagePane.getChildren().remove(message1);
                        index.init();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("由于网络或其他原因，拒绝好友失败！");
                        alert.show();
                    }

                }
            });
            message1.getChildren().add(disagree1);
            //总体添加
            messagePane.getChildren().add(message1);
        }
    }

    public void setIndex(Index index) {
        this.index = index;
    }
}
