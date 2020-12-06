package com.juyeon.team.teamcoder.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "daahc1tubhj11.cloudfront.net";

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    //bean이 한번만 초기화 될 수 있도록 함.
    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    // parentPath : "groups" or "users"
    public String upload(String pastFilePath, MultipartFile file, String parentPath) throws IOException {
        // 고유한 key 값을 갖기위해 현재 시간을 postfix로 붙여줌
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        StringBuilder builder = new StringBuilder(fileName);

        builder.insert(fileName.lastIndexOf("."),"-" + date.format(new Date()));
        fileName=builder.toString();

        // key가 존재하면 기존 파일은 삭제
        if (!"".equals(pastFilePath) && pastFilePath != null) {
            boolean isExistObject = s3Client.doesObjectExist(bucket, pastFilePath);

            if (isExistObject) {
                s3Client.deleteObject(bucket, pastFilePath);
            }
        }
        // groups or users
        fileName = parentPath.concat(fileName);

        // for warning (No content length specified for stream data. Stream contents will be buffered in memory and could result in out of memory errors.)
        ObjectMetadata objMeta = new ObjectMetadata();
        byte[] bytes = IOUtils.toByteArray(file.getInputStream());

        objMeta.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

        // file upload
        s3Client.putObject(new PutObjectRequest(bucket, fileName, byteArrayIs, null)
                .withCannedAcl(CannedAccessControlList.PublicRead));  // 외부에 공개될 이미지, public read 권한
        return fileName;
    }
}
