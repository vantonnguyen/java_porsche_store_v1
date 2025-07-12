package vn.tonnguyen.porsche_store_v1.service.interf;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import vn.tonnguyen.porsche_store_v1.model.Car;


import java.util.List;

public interface CarService {
    List<Car> findAll();
    Car findBySlug(String slug);
    Car save(Car car);
    void deleteById(Integer id);
    Car findById(Integer id);

    Car update(Car updatedCar, MultipartFile imageFile);
    Car create(Car car, MultipartFile imageFile);
    void decreaseStockAfterOrder(Integer carId,int quantity);

}
