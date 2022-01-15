package com.example.storage.property;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageProperties {
    private String uploadDir = "";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
