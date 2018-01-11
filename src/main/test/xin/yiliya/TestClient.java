package xin.yiliya;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xin.yiliya.tool.AliOssTool;
import xin.yiliya.tool.GetObjectProgressListener;
import xin.yiliya.tool.PutObjectProgressListener;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:config/spring-config.xml"})
public class TestClient {
    @Autowired
    private AliOssTool aliOssTool;

    //注入进度条
    @Autowired
    private PutObjectProgressListener progress;

    @Autowired
    private GetObjectProgressListener getProgress;

    //设置子线程数为1
    private int i = 1;
    /*
     * 线程计数器
     * 	将线程数量初始化
     * 	每执行完成一条线程，调用countDown()使计数器减1
     * 	主线程调用方法await()使其等待，当计数器为0时才被执行
     *
     */
    private CountDownLatch latch = new CountDownLatch(i);

    /**
     * 样例
     */
    @Test
    public void test(){
        final Logger logger = LogManager.getLogger("mylog");
        final File file = new File("src/resource/image/logo.jpg");
        final File newFile = new File("src/resource/image/abc.jpg");
        Thread oneThread = new Thread(new Runnable() {
            public void run() {
                try {
                    String link = aliOssTool.putObject(file,progress);
                    logger.debug(link);
                    aliOssTool.getObject(newFile,link,getProgress);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            }
        });
        oneThread.start();
//        try {
//            Thread.sleep(1000);
//            //暂停线程
//            oneThread.suspend();
//            Thread.sleep(2000);
//            //重启线程
//            oneThread.resume();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
