package vn.tonnguyen.porsche_store_v1.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageUploadUtil {
    @Value("${app.upload.dir}")
    private String uploadDir;
    public  String saveImageFile(MultipartFile file) throws IOException {
        try {
            String fileName = file.getOriginalFilename();
            String path = uploadDir + fileName;
            file.transferTo(new File(path));
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

}

