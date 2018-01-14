package xin.yiliya;


import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xin.yiliya.pojo.User;
import xin.yiliya.service.UserService;

import java.io.File;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:config/spring-config.xml"})
public class TangTest {

    @Autowired
    private UserService userService;

    @Test
    public void start(){
        Logger logger = LogManager.getLogger("testLog");
        User user = new User();
        user.setUserNum("1303399592");
        user.setUserPass("541196176");
        user.setUserGender("男");
        user.setUserHead("http://computer-net.oss-cn-shanghai.aliyuncs.com/timg.jpg");
        user.setUserName("艾尔雪枫");
        user.setUserIntroduce("我没有介绍");
        //logger.debug(JSON.toJSONString(userService.getUsersByInput("h"),true));
    }
}
