package vn.tonnguyen.porsche_store_v1.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.tonnguyen.porsche_store_v1.utils.ImageUploadUtil;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.repository.CarRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private ImageUploadUtil imageUploadUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,ImageUploadUtil imageUploadUtil) {
        this.carRepository = carRepository;
        this.imageUploadUtil = imageUploadUtil;
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findBySlug(String slug) {
        return carRepository.findBySlug(slug);
    }

    @Override
    public Car findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car update(Car updatedCar, MultipartFile imageFile) {
        if (updatedCar.getId() == null) {
            throw new IllegalArgumentException("Car ID must not be null");
        }

        Optional<Car> existingOpt = carRepository.findById(updatedCar.getId());
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Car with id " + updatedCar.getId() + " not found");
        }

        Car existingCar = existingOpt.get();

        existingCar.setName(updatedCar.getName());
        existingCar.setSlug(updatedCar.getSlug());
        existingCar.setYear(updatedCar.getYear());
        existingCar.setPrice(updatedCar.getPrice());
        existingCar.setColor(updatedCar.getColor());
        existingCar.setEngine(updatedCar.getEngine());
        existingCar.setHorsepower(updatedCar.getHorsepower());
        existingCar.setMaxSpeed(updatedCar.getMaxSpeed());
        existingCar.setTransmission(updatedCar.getTransmission());
        existingCar.setFuelType(updatedCar.getFuelType());
        existingCar.setDescription(updatedCar.getDescription());
        existingCar.setStock(updatedCar.getStock());
        existingCar.setCarModel(updatedCar.getCarModel());
        existingCar.setStatus(updatedCar.getStatus());
        // Upload ảnh mới nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            try {

                String fileName = imageUploadUtil.saveImageFile(imageFile);
                if (fileName != null && !fileName.isBlank()) {
                    existingCar.setImageUrl(fileName);
                }
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        return this.save(existingCar);
    }

    @Override
    public Car create(Car car, MultipartFile imageFile) {
        if (car == null) {
            throw new IllegalArgumentException("Car object must not be null");
        }
        try {
            String imageUrl = "default.png";
            if(imageFile != null && !imageFile.isEmpty()) {
                String fileName = imageUploadUtil.saveImageFile(imageFile);
                if(fileName != null && !fileName.isBlank()) {
                    imageUrl = fileName;
                }
            }
            car.setImageUrl(imageUrl);
            return this.save(car);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create car", ex);
        }
    }


}
