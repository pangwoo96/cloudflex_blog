package com.cloudflex_blog.modules.post.application.fileUploader;

import org.springframework.stereotype.Service;

@Service("AzureFileUploader")
public class AzureFileUploader implements FileUploader {

    public String upload(String imageUrl) {
        // Azure Blob 업로드 로직
        return "https://azure.blob.core.windows.net/" + imageUrl;
    }
}

