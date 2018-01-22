package xin.yiliya.start;

import javafx.application.Application;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xin.yiliya.application.Login;
import xin.yiliya.pojo.SpringContext;
import xin.yiliya.pojo.UserBean;
import xin.yiliya.tool.AliOssTool;
import xin.yiliya.tool.PutObjectProgressListener;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class Client {

    /**
     * 客户端运行通道
     */
    public static void main(String[] args){
        SpringContext.ctx =new ClassPathXmlApplicationContext("config/spring-config.xml");
        Application.launch(Login.class);
    }
}
