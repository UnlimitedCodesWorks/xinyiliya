package xin.yiliya.service;

import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserLaunch;

import java.util.List;

/**
 * 用户接口
 * @author  aiurmaple
 */
public interface UserService {

    /**
     * 用户登录
     * @param account 用户账号
     * @param password 用户密码
     * @return 用户的id，若失败则返回0
     */
    Integer login(String account,String password);

    /**
     * 用户注册
     * @param user 用户注册信息
     * @return 用户的id，若失败则返回0
     */
    Integer register(User user);

    /**
     * 用户修改信息
     * @param user 用户修改信息
     * @return 修改成功返回id，失败返回0
     */
    Integer updateInfo(User user);

    /**
     * 获取用户主界面信息
     * @param userId 用户id
     * @return 用户主界面信息
     */
    UserLaunch getUserInfo(Integer userId);

    /**
     * 根据QQ号、用户名模糊查找用户
     * @param input 输入的内容
     * @return 查找到的用户
     */
    List<User> getUsersByInput(String input);

    /**
     * 得到待同意的好友列表
     * @param userId 用户id
     * @return 待同意的好友列表
     */
    List<User> getReplyUsers(Integer userId);

    /**
     * 得到待同意的好友数目
     * @param userId 用户id
     * @return 待同意的好友数目
     */
    Integer getReplyNum(Integer userId);
}
