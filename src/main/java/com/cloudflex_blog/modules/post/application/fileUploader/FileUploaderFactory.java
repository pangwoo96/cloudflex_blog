package com.cloudflex_blog.modules.post.application.fileUploader;

import com.cloudflex_blog.modules.post.exception.errorcode.PostErrorCode;
import com.cloudflex_blog.modules.post.exception.exception.PostException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.cloudflex_blog.modules.post.exception.errorcode.PostErrorCode.WRONG_CSP_TYPE;

@Component
@RequiredArgsConstructor
public class FileUploaderFactory {

    private final Map<String, FileUploader> uploaderMap;

    @PostConstruct
    public void init() {
        uploaderMap.forEach((key, value) -> {
            System.out.println("Uploader 등록됨: " + key + " -> " + value.getClass().getSimpleName());
        });
    }

    public FileUploader getUploader(String cspType) {
        return switch (cspType.toUpperCase()) {
            case "AWS" -> uploaderMap.get("AwsFileUploader"); // new AwsFileUploader 로 만들어준다.
            case "GCP" -> uploaderMap.get("GcpFileUploader");
            case "AZURE" -> uploaderMap.get("AzureFileUploader");
            case "SCP" -> uploaderMap.get("ScpFileUploader");
            default -> throw new PostException(WRONG_CSP_TYPE);
        };
    }
}