package com.cloudflex_blog.modules.post.application.fileUploader;

import org.springframework.stereotype.Service;

@Service("AwsFileUploader")
public class AwsFileUploader implements FileUploader{

    @Override
    public String upload(String imageUrl) {
        return "https://s3.aws.com/" + imageUrl;
    }
}
