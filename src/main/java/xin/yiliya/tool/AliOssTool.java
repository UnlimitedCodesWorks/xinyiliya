package xin.yiliya.tool;

import com.aliyun.oss.OSSClient;
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
     *
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
     * 传送图片
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
