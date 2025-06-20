package vn.tonnguyen.porsche_store_v1.service.interf;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadCarImage(MultipartFile file);
}
