package vn.tonnguyen.porsche_store_v1.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.tonnguyen.porsche_store_v1.service.interf.ImageService;

import java.io.File;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${app.upload.car.dir}")
    private String uploadDir;

    @Override
    public String uploadCarImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException("File name is invalid.");
        }
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists() && !uploadPath.mkdirs()) {
            throw new RuntimeException("Failed to create upload directory: " + uploadDir);
        }
        try {
            String path = uploadDir + originalFileName;
            file.transferTo(new File(path));
            return originalFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }
}

