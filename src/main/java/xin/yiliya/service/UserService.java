package xin.yiliya.service;

public interface UserService {

    /**
     * 用户登录接口
     * @param account 用户账号
     * @param password 用户密码
     * @return 用户的id，若失败则返回0
     */
    Integer login(String account,String password);

}
