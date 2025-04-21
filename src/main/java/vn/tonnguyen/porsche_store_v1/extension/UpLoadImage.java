package vn.tonnguyen.porsche_store_v1.extension;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class UpLoadImage {
    private static final String UPLOAD_DIR = "E:/java_store/images/porsche_store/cars";
    public static String processUpload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String path = UPLOAD_DIR + fileName;
            file.transferTo(new File(path));
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

}

