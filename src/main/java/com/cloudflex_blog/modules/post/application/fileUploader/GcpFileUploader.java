package com.cloudflex_blog.modules.post.application.fileUploader;

import org.springframework.stereotype.Service;

@Service("GcpFileUploader")
public class GcpFileUploader implements FileUploader {

    public String upload(String imageUrl) {
        // GCP Storage 업로드 로직
        return "https://storage.googleapis.com/" + imageUrl;
    }
}