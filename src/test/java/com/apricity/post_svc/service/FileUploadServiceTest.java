package com.apricity.post_svc.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

    @Mock
    private Cloudinary cloudinary;
    @Mock
    private Uploader uploader;
    @InjectMocks
    private FileUploadService fileUploadService;

    @Test
    @DisplayName("Should return a url for uploaded file on successful upload to cloudinary")
    void uploadFileSuccess() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        Map<String, String> response = Map.of("secure_url", "http://cool-image.png");

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any())).thenReturn(response);

        String url = fileUploadService.uploadFile(multipartFile);

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(response.get("secure_url"));

        verify(cloudinary).uploader();
        verify(uploader).upload(any(), any());
        verifyNoMoreInteractions(cloudinary, uploader);
    }

    @Test
    @DisplayName("Should return null for uploaded file on failed upload to cloudinary")
    void uploadFileFail() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any())).thenThrow(RuntimeException.class);

        String url = fileUploadService.uploadFile(multipartFile);

        assertThat(url).isNull();

        verify(cloudinary).uploader();
        verify(uploader).upload(any(), any());
        verifyNoMoreInteractions(cloudinary, uploader);
    }
}
