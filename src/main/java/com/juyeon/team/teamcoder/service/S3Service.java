package com.juyeon.team.teamcoder.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
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
        String fileName = file.getOriginalFilename() + "-" + date.format(new Date());

        // key가 존재하면 기존 파일은 삭제
        if (!"".equals(pastFilePath) && pastFilePath != null) {
            boolean isExistObject = s3Client.doesObjectExist(bucket, pastFilePath);

            if (isExistObject) {
                s3Client.deleteObject(bucket, pastFilePath);
            }
        }
        // groups or users
        fileName = parentPath.concat(fileName);

        // file upload
        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));  // 외부에 공개될 이미지, public read 권한
        return fileName;
    }
}
