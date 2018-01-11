package xin.yiliya.tool;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import xin.yiliya.baseInterface.BaseProgress;
import xin.yiliya.exception.ImageFormatException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AliOssTool {

    private final static String BUCKETNAME = "computer-net";
    private final static String ENDPOINT = "http://computer-net.oss-cn-shanghai.aliyuncs.com/";

    private OSSClient ossClient;

    @Autowired
    public AliOssTool(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    /**
     * 创建一个50MB的临时文件
     */
    public File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));

        for (int i = 0; i < 1000; i++) {
            writer.write("abcdefghijklmnopqrstuvwxyz\n");
            writer.write("0123456789011234567890\n");
        }

        writer.close();

        return file;
    }


    /**
     * 传送单个文件
     * @param file 文件对象
     * @param putObjectProgressListener 自定义进度条
     * @return 文件的链接
     */
    public String putObject(File file,BaseProgress putObjectProgressListener){
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);
        fileName = date+uuid+suffix;
        StringBuilder link = new StringBuilder(ENDPOINT);
        ossClient.putObject((new PutObjectRequest(BUCKETNAME, fileName, file).
                <PutObjectRequest>withProgressListener(putObjectProgressListener)));
        link.append(fileName);
        return String.valueOf(link);
    }

    /**
     * 下载一个文件
     * @param file 存放文件对象
     * @param link 目标文件链接
     */
    public void getObject(File file,String link,BaseProgress getObjectProgressListener){
        String fileName = link.substring(link.lastIndexOf("/")+1);
        // 带进度条的下载
        ossClient.getObject(new GetObjectRequest(BUCKETNAME, fileName).
                        <GetObjectRequest>withProgressListener(getObjectProgressListener),
                file);
    }

    /**
     * 上传一个图片
     * @param file 图片对象
     * @return
     * @throws ImageFormatException 图标格式不支持则报错
     * @throws FileNotFoundException 文件无法找到则报错
     */
    public String putImage(File file) throws ImageFormatException, FileNotFoundException {
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);
        StringBuilder imageName = new StringBuilder(date+uuid);
        ObjectMetadata meta = new ObjectMetadata();
        if(suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("jpe")){
            meta.setContentType("image/jpeg");
            imageName.append(".jpg");
        }else if(suffix.equals("gif")){
            meta.setContentType("image/gif");
            imageName.append(".gif");
        }else if(suffix.equals("png")){
            meta.setContentType("image/png");
            imageName.append(".png");
        }else{
            throw new ImageFormatException("不能上传除jpg，gif，png以外的图片或文件");
        }
        StringBuilder link = new StringBuilder(ENDPOINT);
        ossClient.putObject(BUCKETNAME, String.valueOf(imageName),new FileInputStream(file),meta);
        link.append(imageName);
        return String.valueOf(link);
    }
}
