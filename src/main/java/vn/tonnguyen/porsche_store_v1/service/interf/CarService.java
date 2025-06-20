package vn.tonnguyen.porsche_store_v1.service.interf;

import org.springframework.web.multipart.MultipartFile;
import vn.tonnguyen.porsche_store_v1.model.Car;


import java.util.List;

public interface CarService {
    public List<Car> findAll();
    public Car findBySlug(String slug);
    public Car findById(int id);
    public Car save(Car car);
    public void deleteById(int id);
    public Car update(Car updatedCar, MultipartFile imageFile);
}
