package com.apricity.post_svc.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploadService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile multipartFile) {

        try {
            Map<String, String> uploadResponse = cloudinary.uploader().upload(
                    multipartFile.getBytes(),
                    ObjectUtils.asMap(
                            "unique_filename", true,
                                    "folder", "apricity"
                    ));
            String assetId = uploadResponse.get("asset_id");
            String assetUrl = uploadResponse.get("secure_url");
            log.info("Asset Id: {} {}", assetId, assetUrl);
            return assetUrl;
        } catch (Exception e) {
            log.error("Error uploading to Cloudinary {}", e.getMessage());
            return null;
        }
    }
}
