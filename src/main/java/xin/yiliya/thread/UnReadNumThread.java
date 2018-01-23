package xin.yiliya.thread;

import javafx.scene.text.Text;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.service.MessageService;

public class UnReadNumThread extends Thread {

    private Text text;

    private Integer sendId;

    private Integer receiveId;

    private MessageService messageService;

    public UnReadNumThread(){
        messageService = (MessageService) SpringContext.ctx.getBean("messageServiceImpl");
    }
    @Override
    public void run() {
        while (true){
            Integer num =messageService.viewNewNum(sendId,receiveId);
            text.setText(""+num.toString()+"条未读消息");
            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }
}
