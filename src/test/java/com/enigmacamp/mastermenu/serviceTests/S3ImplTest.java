package com.enigmacamp.mastermenu.serviceTests;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.enigmacamp.mastermenu.exception.InvalidFileFormatException;
import com.enigmacamp.mastermenu.model.dto.response.MenuDetailRes;
import com.enigmacamp.mastermenu.service.MenuService;
import com.enigmacamp.mastermenu.service.impl.S3Impl;

class S3ImplTest {

    @InjectMocks
    private S3Impl s3Service;

    @Mock
    private AmazonS3 s3Client;

    @Mock
    private MenuService menuService;

    @Mock
    private MultipartFile multipartFile;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        s3Service = new S3Impl(s3Client, menuService);
    }

   @Test
    void uploadFile_ShouldUploadAndReturnUrl() throws IOException {
        // Mock MultipartFile
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(multipartFile.getContentType()).thenReturn("image/jpeg");

        // Mock bucketName
        String bucketName = "mock-bucket";
        ReflectionTestUtils.setField(s3Service, "bucketName", bucketName);

        // Mock fileName and fileUrl
        String fileName = System.currentTimeMillis() + "_test.jpg";
        String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        // Mock s3Client
        when(s3Client.getUrl(eq(bucketName), eq(fileName))).thenReturn(new java.net.URL(fileUrl));

        // Call method
        String result = s3Service.uploadFile(multipartFile);

        // Assert result
        assertNotNull(result);
        assertEquals(fileUrl, result);

        // Verify putObject is called
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class));
    }


    @Test
    void uploadFile_ShouldThrowInvalidFileFormatException_WhenFileIsEmpty() {
        when(multipartFile.isEmpty()).thenReturn(true);

        // Assert exception
        InvalidFileFormatException exception = assertThrows(InvalidFileFormatException.class, () -> {
            s3Service.uploadFile(multipartFile);
        });

        assertEquals("File is not Empty.", exception.getMessage());
    }

    @Test
    void updateFile_ShouldDeleteOldFileAndUploadNewFile() throws IOException {
        // Mock MenuService
        MenuDetailRes menuDetailRes = new MenuDetailRes();
        menuDetailRes.setImageUrl("https://bucket-name.s3.amazonaws.com/old-file.jpg");
        when(menuService.getMenuById("1")).thenReturn(menuDetailRes);

        // Mock MultipartFile
        when(multipartFile.getOriginalFilename()).thenReturn("new-file.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(multipartFile.getContentType()).thenReturn("image/jpeg");

        // Mock AmazonS3
        String newFileUrl = "https://bucket-name.s3.amazonaws.com/new-file.jpg";
        when(s3Client.getUrl(anyString(), anyString())).thenReturn(new java.net.URL("https://default-url.com/test.jpg"));


        // Call method
        String result = s3Service.updateFile(multipartFile, "1");

        // Assert result
        assertNotNull(result);
        assertEquals(newFileUrl, result);

        // Verify interactions
        verify(s3Client, times(1)).deleteObject(any(DeleteObjectRequest.class));
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class));
    }

    @Test
    void deleteFile_ShouldDeleteFile() {
        String fileName = "test.jpg";

        // Call method
        s3Service.deleteFile(fileName);

        // Verify interactions
        verify(s3Client, times(1)).deleteObject(any(DeleteObjectRequest.class));

    }

    @Test
    void convertMultipartFileToFile_ShouldConvertFile() throws IOException {
        // Mock MultipartFile
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3});

        // Call method
        File result = s3Service.convertMultipartFileToFile(multipartFile);

        // Assert result
        assertNotNull(result);
        assertEquals("test.jpg", result.getName());

        // Cleanup
        result.delete();
    }

    @Test
    void validateFileFormat_ShouldThrowException_ForInvalidFileType() {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getContentType()).thenReturn("application/pdf");

        // Assert exception
        InvalidFileFormatException exception = assertThrows(InvalidFileFormatException.class, () -> {
            s3Service.uploadFile(multipartFile);
        });

        assertEquals("Only JPG, JPEG, and PNG files are allowed.", exception.getMessage());
    }
}

