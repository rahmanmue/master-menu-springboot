package com.enigmacamp.mastermenu.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.enigmacamp.mastermenu.exception.InvalidFileFormatException;
import com.enigmacamp.mastermenu.model.dto.response.MenuDetailRes;
import com.enigmacamp.mastermenu.service.MenuService;
import com.enigmacamp.mastermenu.service.S3Service;

import java.io.FileOutputStream;
import java.io.IOException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Impl implements S3Service {

    @Value("${aws.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3Client;
    private final MenuService menuService;

    @Override
    public String uploadFile(MultipartFile file) {
        
        validateFileFormat(file);
        
        File fileObj = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, fileObj);
        
        s3Client.putObject(putObjectRequest);
        
        // Menghapus file setelah di-upload ke S3
        fileObj.delete();
        
        // Mengembalikan URL dari file yang di-upload di S3
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    @Override
    public String updateFile(MultipartFile file, String idMenu) {
        
        MenuDetailRes menu = menuService.getMenuById(idMenu);
        String imageUrl = menu.getImageUrl();
        String oldFileName = imageUrl.split("/")[imageUrl.split("/").length - 1];

        if(oldFileName != null && !oldFileName.isEmpty()){
            deleteFile(oldFileName);
        }

        return uploadFile(file);
    }

    @Override
    public void deleteFile(String fileName) {
        // Membuat request untuk menghapus file berdasarkan nama file di bucket
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    @Override
    public File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error converting multipart file to file", e);
        }
        return convertedFile;
    }

    private void validateFileFormat(MultipartFile file){
        // Pengecekan jenis file
        if(file.isEmpty()){
            throw new InvalidFileFormatException("File is not Empty.");
        }

        String contentType = file.getContentType();
        if (!(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jpg"))) {
            throw new InvalidFileFormatException("Only JPG, JPEG, and PNG files are allowed.");
        }
    }



   
   
}
