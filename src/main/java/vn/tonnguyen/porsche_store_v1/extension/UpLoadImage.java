package vn.tonnguyen.porsche_store_v1.extension;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UpLoadImage {
    private static final String UPLOAD_DIR = "E:/porsche_web_java/cars/";
    public static String processUpload(MultipartFile file) throws IOException {
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

