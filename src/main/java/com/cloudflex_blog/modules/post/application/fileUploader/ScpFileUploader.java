package com.cloudflex_blog.modules.post.application.fileUploader;

import org.springframework.stereotype.Service;

@Service("ScpFileUploader")
public class ScpFileUploader implements FileUploader {
    public String upload(String imageUrl) {
        // SCP 업로드 로직
        return "https://scp.storage.com/" + imageUrl;
    }
}