package com.cern.service.impl;

import com.cern.domain.ResponseResult;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.exception.SystemException;
import com.cern.service.UploadService;
import com.cern.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Data
@ConfigurationProperties(prefix = "qi-niu-oss")
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // 判定文件上传大小及其类型是否合法
        String originalFilename = img.getOriginalFilename();
        if (img.getSize() > 2 * 1024 * 1024) {
            // 抛出文件大小超过限制的异常
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        //对原始文件名进行判断大小。只能上传png或jpg文件
        if(!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        // 文件大小合法上传七牛云
        // 生成上传路径
        String path = PathUtils.generateFilePath(originalFilename);
        System.out.println("========开始上传");
        String url = uploadOss(img, path);
        // 返回文件上传结果
        return ResponseResult.okResult(url);
    }

    /**
     * 实现文件上传七牛云
     * @return 文件在线访问地址
     */
    private String uploadOss(MultipartFile imgFile, String filePath){
        //构造一个带指定 Region 对象的配置类。你的七牛云OSS创建的是哪个区域的，那么就调用Region的什么方法即可
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);
        //文件名，如果写成null的话，就以文件内容的hash值作为文件名
        String key = filePath;

        try {
            // 将文件转换成流
            InputStream inputStream = imgFile.getInputStream();
            // 访问权限
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                //把前端传过来的inputStream图片上传到七牛云
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println("上传成功! 生成的key是: "+putRet.key);
                System.out.println("上传成功! 生成的hash是: "+putRet.hash);
                // 返回文件访问地址
                return "http://s5w6y8yk4.hd-bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
}
