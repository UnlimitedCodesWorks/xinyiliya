package xin.yiliya.thread;

import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.service.UserService;

public class UnReplyNumThread extends Thread {

    private UserService userService;

    private UserBean userBean;

    private Text text;

    public UnReplyNumThread(){
        userService = (UserService) SpringContext.ctx.getBean("userServiceImpl");
        userBean = (UserBean) SpringContext.ctx.getBean("userBean");
    }
    @Override
    public void run() {
        while (true){
            Integer num = userService.getReplyNum(userBean.getUserId());
            if(num!=0){
                text.setText("≡ 查看消息"+num);
            }
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setText(Text text) {
        this.text = text;
    }
}
