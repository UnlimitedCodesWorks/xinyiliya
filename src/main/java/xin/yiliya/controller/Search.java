package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import xin.yiliya.pojo.Friends;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.service.FriendService;
import xin.yiliya.service.UserService;

import java.util.List;


public class Search {
    @FXML
    TextField search_text;

    @FXML
    FlowPane searchFlow;

    @FXML
    Button search_button;

    private UserService userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");

    private FriendService friendService = (FriendService) SpringContext.ctx.getBean("friendServiceImpl");

    private UserBean userBean = (UserBean) SpringContext.ctx.getBean("userBean");

    @FXML
    public void searchP(MouseEvent mouseEvent) {
        if (search_text.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("QQ");
            alert.setHeaderText("输入状态");
            alert.setContentText("请输入搜索信息！");
            alert.showAndWait();
        } else {
            List<User> userList = userService.getUsersByInput(search_text.getText());
            createSearch(userList);
        }
    }

    private void createSearch(List<User> friendList){
        searchFlow.getChildren().removeAll(searchFlow.getChildren());
        for(final User user:friendList){
            //生成
            //总的大布局
            AnchorPane anchorPane1 = new AnchorPane();
            anchorPane1.setPrefHeight(100);
            anchorPane1.setPrefWidth(100);
            //头像块
            ImageView searchimg1 = new ImageView();
            searchimg1.setImage(new Image(user.getUserHead()));
            searchimg1.setFitHeight(80.0);
            searchimg1.setFitWidth(80.0);
            searchimg1.setLayoutX(26.0);
            searchimg1.setLayoutY(25.0);
            anchorPane1.setTopAnchor(searchimg1,10.0);
            anchorPane1.setLeftAnchor(searchimg1,10.0);
            anchorPane1.getChildren().add(searchimg1);
            //昵称块
            final Text searchname1 =new Text();
            searchname1.setLayoutX(107.0);
            searchname1.setLayoutY(29.0);
            searchname1.setText(user.getUserName());
            searchname1.setWrappingWidth(100.0);
            searchname1.setStyle("-fx-font-weight: bold;-fx-font-size:12.0;-fx-stroke-type: outside");
            anchorPane1.setTopAnchor(searchname1,15.0);
            anchorPane1.setLeftAnchor(searchname1,95.0);
            anchorPane1.getChildren().add(searchname1);
            //账号块
            Text searchnumber1 = new Text();
            searchnumber1.setLayoutX(90.0);
            searchnumber1.setLayoutY(53.0);
            searchnumber1.setText(user.getUserNum());
            searchnumber1.setWrappingWidth(100.0);
            searchnumber1.setStyle("-fx-font-size: 12.0;-fx-stroke-type: outside");
            anchorPane1.setTopAnchor(searchnumber1,30.0);
            anchorPane1.setLeftAnchor(searchnumber1,95.0);
            anchorPane1.getChildren().add(searchnumber1);
            //详细信息
            Text searchaddress1 = new Text();
            searchaddress1.setFill(Color.rgb(137,127,127));
            searchaddress1.setStyle("-fx-font-size:12.0;-fx-stroke-type: outside");
            searchaddress1.setLayoutX(92.0);
            searchaddress1.setLayoutY(65.0);
            searchaddress1.setText(user.getUserIntroduce());
            searchaddress1.setWrappingWidth(100.0);
            anchorPane1.setTopAnchor(searchaddress1,50.0);
            anchorPane1.setLeftAnchor(searchaddress1,95.0);
            anchorPane1.getChildren().add(searchaddress1);
            //加好友按钮
            Button addbtn1 = new Button();
            addbtn1.setLayoutX(94.0);
            addbtn1.setLayoutY(67.0);
            addbtn1.setMnemonicParsing(false);
            addbtn1.setPrefHeight(22.0);
            addbtn1.setPrefWidth(66.0);
            addbtn1.setStyle("-fx-background-color: #3498db;-fx-font-size: 10");
            addbtn1.setText("加好友");
            addbtn1.setTextAlignment(TextAlignment.CENTER);
            addbtn1.setTextFill(Color.rgb(255,255,255));
            //动态给按钮绑定监听事件
            addbtn1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Integer receiveId = user.getId();
                    Friends friends = new Friends();
                    friends.setUserId(userBean.getUserId());
                    friends.setFriendId(receiveId);
                    if(friendService.addFriend(friends)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("添加成功，等待对方同意");
                        alert.show();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("添加失败！");
                        alert.show();
                    }

                }
            });
            anchorPane1.setTopAnchor(addbtn1,67.0);
            anchorPane1.setLeftAnchor(addbtn1,95.0);
            anchorPane1.getChildren().add(addbtn1);
            //总体添加
            searchFlow.getChildren().add(anchorPane1);
        }
    }

    public void mouseEnter(MouseEvent mouseEvent) {
        search_button.setStyle("-fx-background-color: #3498ff; -fx-font-size: 15");
    }

    public void mouseExited(MouseEvent mouseEvent) {
        search_button.setStyle("-fx-background-color: #3498db; -fx-font-size: 15");
    }
}

