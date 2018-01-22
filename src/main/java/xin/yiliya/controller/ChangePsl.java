package xin.yiliya.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.pojo.UserLaunch;
import xin.yiliya.service.UserService;

public class ChangePsl {

    private Stage stage;

    @FXML
    private TextField userName;

    @FXML
    private TextArea userRemark;

    @FXML
    private TextField userGender;

    private xin.yiliya.controller.Personal personal;

    private UserService userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");

    private UserBean userBean = (UserBean) SpringContext.ctx.getBean("userBean");

    private Integer userId;

    public void init(){
        UserLaunch userLaunch = userService.getUserInfo(userBean.getUserId());
        User user = userLaunch.getUser();
        userId = user.getId();
        userName.setText(user.getUserName());
        userGender.setText(user.getUserGender());
        userRemark.setText(user.getUserIntroduce());

    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void savechange(MouseEvent mouseEvent) {
        User user = new User();
        user.setId(userId);
        user.setUserName(userName.getText());
        user.setUserGender(userGender.getText());
        user.setUserIntroduce(userRemark.getText());
        if(userService.updateInfo(user)!=0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("QQ");
            alert.setHeaderText("修改状态");
            alert.setContentText("修改成功!");
            alert.showAndWait();
            personal.init();
            stage.hide();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("QQ");
            alert.setHeaderText("修改状态");
            alert.setContentText("修改失败!");
            alert.showAndWait();
        }

    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
