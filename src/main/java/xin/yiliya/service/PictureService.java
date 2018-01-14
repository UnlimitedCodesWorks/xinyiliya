package xin.yiliya.service;

import xin.yiliya.pojo.MessagePicture;

public interface PictureService {

    /**
     * 发送图片
     * @param messagePicture 图片信息
     * @return 发送成功则返回图片id，失败则返回0
     */
    Integer sendPicture(MessagePicture messagePicture);

}
