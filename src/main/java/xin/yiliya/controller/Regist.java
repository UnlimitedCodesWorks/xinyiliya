package xin.yiliya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import xin.yiliya.exception.ImageFormatException;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.service.UserService;
import xin.yiliya.tool.AliOssTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Regist {
    @FXML
    private TextField number;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField sex;

    @FXML
    private TextArea introduce;

    @FXML
    private Label notice;

    @FXML
    private ImageView head;

    private Map<String, Stage> stageMap;

    private Stage stage;

    private File file;

    private Image image;

    public void setStageMap(Map<String, Stage> stageMap) {
        this.stageMap = stageMap;
    }

    public void init() {
        introduce.setTooltip(new Tooltip("个性签名"));
        sex.setTooltip(new Tooltip("性别"));
        name.setTooltip(new Tooltip("昵称"));
        password.setTooltip(new Tooltip("密码"));
        number.setTooltip(new Tooltip("账号"));
        stage = stageMap.get("primary");
    }

    //点击选择头像
    public void selectHead(ActionEvent event) throws FileNotFoundException {
        notice.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择头像");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        file = fileChooser.showOpenDialog(stage);
        image = new Image(new FileInputStream(file));
        head.setImage(image);
    }

    //点击取消
    public void returnLogin(ActionEvent event) throws IOException {
        goLogin();
    }

    //点击注册
    public void finishRegist(ActionEvent event) throws IOException {
        if (number.getText().length() == 0 || password.getText().length() == 0
                || name.getText().length() == 0 || sex.getText().length() == 0
                || introduce.getText().length() == 0) {
            notice.setText("请填写完整信息");
        }
        else if (!(sex.getText().equals("男")) && !(sex.getText().equals("女"))) {
            notice.setText("请正确填写性别 （男/女）");
        }
        else if (image == null) {
            notice.setText("请选择头像");
        }
        else {
            User user = new User();
            user.setUserName(name.getText());
            user.setUserNum(number.getText());
            user.setUserPass(password.getText());
            user.setUserIntroduce(introduce.getText());
            user.setUserGender(sex.getText());
            AliOssTool aliOssTool = (AliOssTool) SpringContext.ctx.getBean("aliOssTool");
            try {
                user.setUserHead(aliOssTool.putImage(file));
            } catch (ImageFormatException e) {
                e.printStackTrace();
            }
            UserService userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");
            Integer userId = userService.register(user);
            if(userId!=0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("QQ");
                alert.setHeaderText("注册状态");
                alert.setContentText("注册成功!");
                alert.showAndWait();
                goLogin();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("QQ");
                alert.setHeaderText("注册状态");
                alert.setContentText("注册失败!");
                alert.showAndWait();
            }
        }
    }

    public void focus(MouseEvent event) {
        notice.setText("");
    }

    private void goLogin() throws IOException {
        stage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = fxmlLoader.load();
        Login login = fxmlLoader.getController();
        login.addStage("primary", stage);
        login.init();
        stage.setTitle("登录");
        final Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
