package xin.yiliya.service;

import xin.yiliya.pojo.File;
import xin.yiliya.pojo.MessageFile;

import java.util.List;

public interface FileService {

    /**
     * 发送文件
     * @param messageFile 文件信息
     * @return 发送成功返回文件id，失败则为0
     */
    Integer sendFile(MessageFile messageFile);

    /**
     * 查看未下载文件
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return 文件列表
     */
    List<File> readFile(Integer sendId,Integer receiveId);

    /**
     * 取消文件下载
     * @param file 文件信息
     * @return 文件已确认返回1，未确认返回0
     */
    Integer cancelFile(File file);
}
