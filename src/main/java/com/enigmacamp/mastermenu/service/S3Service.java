package com.enigmacamp.mastermenu.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;

public interface S3Service {
    String uploadFile(MultipartFile file);
    String updateFile(MultipartFile file, String oldFileName);
    File convertMultipartFileToFile(MultipartFile file);
    void deleteFile(String fileName);
}
