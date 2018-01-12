package xin.yiliya.start;

import javafx.application.Application;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xin.yiliya.application.Login;
import xin.yiliya.tool.AliOssTool;
import xin.yiliya.tool.PutObjectProgressListener;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:config/spring-config.xml"})
public class Client {

    /**
     * 客户端运行通道
     */
    @Test
    public void run(){
        Application.launch(Login.class);
    }
}
