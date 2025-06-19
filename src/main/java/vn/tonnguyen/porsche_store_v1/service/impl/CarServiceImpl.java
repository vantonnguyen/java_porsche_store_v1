package vn.tonnguyen.porsche_store_v1.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.repository.CarRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;

import java.util.List;
@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public List<Car> findAll(){
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
    public String deleteById(int id) {
        return carRepository.deleteById(id);
    }
}
