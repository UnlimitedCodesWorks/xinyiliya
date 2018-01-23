package xin.yiliya.service;

import xin.yiliya.pojo.Message;
import xin.yiliya.pojo.MessagePicture;

import java.util.List;

public interface MessageService {

    /**
     * 发送消息
     * @param message 消息
     * @return 发现消息成功返回id，失败为0
     */
    Integer sendMessage(Message message);

    /**
     * 查看历史消息
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return 消息的list
     */
    List<MessagePicture> viewHistory(Integer sendId,Integer receiveId);

    /**
     * 查看未读消息
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return 消息的list
     */
    List<MessagePicture> viewNew(Integer sendId,Integer receiveId);

    /**
     * 查看最近消息
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return 消息的list
     */
    List<MessagePicture> viewRecentNew(Integer sendId,Integer receiveId);

    /**
     * 用户查看未读消息数目
     * @param sendId
     * @param receiveId
     * @return 返回未读消息数目
     */
    Integer viewNewNum(Integer sendId,Integer receiveId);

}
