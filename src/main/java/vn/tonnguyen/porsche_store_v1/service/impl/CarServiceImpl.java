package vn.tonnguyen.porsche_store_v1.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import vn.tonnguyen.porsche_store_v1.service.interf.ImageService;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.repository.CarRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ImageService imageService) {
        this.carRepository = carRepository;
        this.imageService = imageService;
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
    public Car findById(Integer id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteById(Integer id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car update(Car updatedCar, MultipartFile imageFile) {
        if (updatedCar.getId() == null) {
            throw new IllegalArgumentException("Car ID must not be null");
        }
        Car existingCar = carRepository.findById(updatedCar.getId())
                .orElseThrow(() -> new RuntimeException("Car with ID " + updatedCar.getId() + " not found"));

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
        existingCar.setImageUrl(existingCar.getImageUrl());
        // Upload ảnh mới nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = imageService.uploadCarImage(imageFile);
                existingCar.setImageUrl(fileName);
            } catch (Exception e) {
                // Không dừng chương trình, có thể log lại
                logger.error("Upload failed", e);
            }
        }
        return this.save(existingCar);
    }

    @Override
    public Car create(Car car, MultipartFile imageFile) {
        if (car == null) {
            throw new IllegalArgumentException("Car object must not be null");
        }
        car.setImageUrl("default.png");
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = imageService.uploadCarImage(imageFile);
                car.setImageUrl(fileName);
            } catch (Exception e) {
                // Không dừng chương trình, có thể log lại
                logger.error("Upload failed", e);
            }
        }
        return this.save(car);
    }


}
