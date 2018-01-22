package xin.yiliya.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.pojo.UserLaunch;
import xin.yiliya.service.UserService;

import java.text.SimpleDateFormat;


public class Personal {
    @FXML
    private ImageView userImage;

    @FXML
    private Text userNameB;

    @FXML
    private Text userRemark;

    @FXML
    private Text userAccount;

    @FXML
    private Text userName;

    @FXML
    private Text userGender;

    private Stage stage;

    private xin.yiliya.controller.Personal personal;

    private UserService userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");

    private UserBean userBean = (UserBean) SpringContext.ctx.getBean("userBean");

    public void init(){
        UserLaunch userLaunch = userService.getUserInfo(userBean.getUserId());
        User user = userLaunch.getUser();
        userImage.setImage(new Image(user.getUserHead()));
        userName.setText(user.getUserName());
        userNameB.setText(user.getUserName());
        userRemark.setText(user.getUserIntroduce());
        userAccount.setText(user.getUserNum());
        userGender.setText(user.getUserGender());
    }

    public void changeP(MouseEvent mouseEvent) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/changePsl.fxml"));
        Parent root1 = fxmlLoader.load();
        xin.yiliya.controller.ChangePsl changePsl = fxmlLoader.getController();
        changePsl.setStage(stage);
        changePsl.setPersonal(personal);
        stage.setTitle("修改个人信息");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();
        changePsl.init();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
