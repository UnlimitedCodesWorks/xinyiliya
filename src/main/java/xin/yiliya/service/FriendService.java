package xin.yiliya.service;

import xin.yiliya.pojo.Friends;

/**
 * 好友接口
 * @author  aiurmaple
 */
public interface FriendService {

    /**
     * 添加好友
     * @param friends 好友信息
     * @return 添加成功返回true，失败返回false
     */
    Boolean addFriend(Friends friends);

    /**
     * 确认添加好友
     * @param friends 好友信息
     * @return 确认成功返回true，失败返回false
     */
    Boolean sureFriend(Friends friends);

    /**
     * 拒绝添加好友
     * @param friends 好友信息
     * @return 拒绝成功返回true，失败返回false
     */
    Boolean refuseFriend(Friends friends);

    /**
     * 删除好友
     * @param friends 好友信息
     * @return 删除成功返回true，失败返回false
     */
    Boolean deleteFriend(Friends friends);
}
