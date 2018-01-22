package xin.yiliya.thread;

import javafx.scene.control.Alert;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.service.UserService;

public class UnReplyNumThread extends Thread {

    private UserService userService;

    private UserBean userBean;

    public UnReplyNumThread(){
        userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");
        userBean = (UserBean) SpringContext.ctx.getBean("userBean");
    }
    @Override
    public void run() {
        while (true){
            if(userService.getReplyNum(userBean.getUserId())!=0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("QQ");
                alert.setHeaderText("好友提醒");
                alert.setContentText("您有新的好友申请，请进行处理！");
                alert.showAndWait();
            }
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
