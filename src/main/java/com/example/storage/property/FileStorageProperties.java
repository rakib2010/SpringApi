package com.example.storage.property;

import javax.servlet.annotation.MultipartConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class FileStorageProperties {
    private String uploadDir = "C:\\Users\\Student\\Desktop\\Rakib\\resource";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
