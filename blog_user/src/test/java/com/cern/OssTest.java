package com.cern;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

//@Component
@SpringBootTest
//@ConfigurationProperties(prefix = "qiniouoss")//指定读取application.yml文件的myoss属性的数据
public class OssTest {

    //注意要从application.yml读取属性数据，下面的3个成员变量的名字必须对应application.yml的qiniouoss属性的三个子属性名字
    private String accessKey;
    private String secretKey;
    private String bucket;
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Test
    public void OssT() throws FileNotFoundException {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            InputStream inputStream = new FileInputStream("D:\\Download\\aDrive\\006BFMdqly1gh5gtxaaiqj31jk12wu0x.jpg");
            // 生成凭证
            Auth auth = Auth.create(accessKey, secretKey);
            // 上传凭证
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);
                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
